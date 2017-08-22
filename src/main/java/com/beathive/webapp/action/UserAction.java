package com.beathive.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.displaytag.tags.TableTagParameters;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.Constants;
import com.beathive.model.Address;
import com.beathive.model.ChangeEmailKey;
import com.beathive.model.Preference;
import com.beathive.model.Role;
import com.beathive.model.User;
import com.beathive.model.UserReport;
import com.beathive.service.MailEngine;
import com.beathive.service.RoleManager;
import com.beathive.service.UserExistsException;
import com.beathive.service.UserManager;
import com.beathive.util.StringUtil;
import com.beathive.webapp.form.AddressForm;
import com.beathive.webapp.form.ChangeEmailKeyForm;
import com.beathive.webapp.form.UserForm;
import com.beathive.webapp.form.UserReportForm;
import com.beathive.webapp.helper.PaginatedListFactory;
import com.beathive.webapp.util.RequestUtil;

import net.sf.json.JSONObject;

/**
 * Implementation of <strong>Action</strong> that interacts with the
 * {@link UserForm} and retrieves values. It interacts with the
 * {@link UserManager} to retrieve/persist values to the database.
 *
 * <p>
 * <a href="UserAction.java.html"><i>View Source</i></a>
 * </p>
 *
 *
 * @struts.action name="userForm" path="/users" scope="request" validate="false"
 *                parameter="method" input="mainMenu" roles="admin"
 * @struts.action name="userForm" path="/editUser" scope="request"
 *                validate="false" parameter="method" input="list" roles="admin"
 * @struts.action name="userForm" path="/editProfile" scope="request"
 *                validate="false" parameter="method" input="mainMenu"
 *                roles="user"
 * @struts.action name="userForm" path="/saveUser" scope="request"
 *                validate="false" parameter="method" input="edit"
 *
 * @struts.action name="userForm" path="/upgradeUser" scope="request"
 *                validate="false" parameter="method" input="mainMenu"
 *                roles="user"
 *
 * @struts.action-forward name="list" path="/WEB-INF/pages/userList.jsp"
 * @struts.action-forward name="edit" path="/WEB-INF/pages/userForm.jsp"
 */
public final class UserAction extends BaseAction {

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'add' method");
		}

		User user = new User();
		user.addRole(new Role(Constants.USER_ROLE));
		UserForm userForm = (UserForm) convert(user);
		if (userForm.getAddressForm() == null) {
			AddressForm addressForm = new AddressForm();
			addressForm.setUserId(userForm.getId());
			userForm.setAddressForm(addressForm);
		}
		updateFormBean(mapping, request, userForm);

		checkForRememberMeLogin(request);

		return mapping.findForward("edit");
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'cancel' method");
		}

		if (!StringUtils.equals(request.getParameter("from"), "list")) {
			return mapping.findForward("mainMenu");
		} else {
			return mapping.findForward("viewUsers");
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'delete' method");
		}

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		UserForm userForm = (UserForm) form;

		// Exceptions are caught by ActionExceptionHandler
		UserManager mgr = (UserManager) getBean("userManager");
		mgr.removeUser(userForm.getId());

		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("user.deleted", userForm.getFirstName() + ' ' + userForm.getLastName()));

		saveMessages(request.getSession(), messages);

		// return a forward to searching users
		return mapping.findForward("viewUsers");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'edit' method");
		}

		UserForm userForm = (UserForm) form;
		String username = request.getRemoteUser();

		if (StringUtils.isBlank(username)) {
			ActionMessages errors = form.validate(mapping, request);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.login.required"));
			saveErrors(request, errors);
			return mapping.findForward("mainMenu");
		}
		if (StringUtils.isNotBlank(request.getParameter("username")) && request.isUserInRole("admin")) {
			// allow resetting username
			username = request.getParameter("username");
		}

		HttpSession session = request.getSession();

		// Exceptions are caught by ActionExceptionHandler
		UserManager mgr = (UserManager) getBean("userManager");
		User user = mgr.getUserByUsername(username);
		BeanUtils.copyProperties(userForm, convert(user));
		user = null;
		userForm.setConfirmPassword(userForm.getPassword());
		updateFormBean(mapping, request, userForm);

		checkForRememberMeLogin(request);
		// return a forward to edit forward
		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'save' method");
		}

		// run validation rules on this form
		ActionMessages errors = form.validate(mapping, request);

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("edit");
		}

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		UserManager mgr = (UserManager) getBean("userManager");

		UserForm userForm = (UserForm) form;
		User user = new User();

		boolean isNew = StringUtils.isBlank(userForm.getId());

		Map<String, Object> propVal = new HashMap();
		Address addr = null;
		if (!isNew) {
			user = mgr.getUser(userForm.getId());
			String[] props = new String[] { "favorites", "archives", "purchases", "stores", "preference" };// ,"roles"
			for (int g = 0; g < props.length; g++) {
				Object o = PropertyUtils.getProperty(user, props[g]);
				if (o != null) {
					if (props[g].equals("preference")) {
						if (((Preference) o).getUserId() != null) {
							propVal.put(props[g], o);
							PropertyUtils.setProperty(userForm, props[g], null);
						}
					} else {
						propVal.put(props[g], o);
						PropertyUtils.setProperty(userForm, props[g], null);
					}
				} else {
					try {
						PropertyUtils.setNestedProperty(userForm, props[g] + "Form.userId", userForm.getId());
					} catch (Exception er) {
						log.error(er.getMessage());
					}
				}
			}

			if (user.getAddress() != null && userForm.getAddress() != null) {
				addr = user.getAddress();
				BeanUtils.copyProperties(addr, userForm.getAddress());
				userForm.setAddress(null);
			}
		}

		// Exceptions are caught by ActionExceptionHandler
		// all we need to persist is the parent object
		BeanUtils.copyProperties(user, userForm);
		user.setAddress(addr);

		for (String key : propVal.keySet()) {
			PropertyUtils.setProperty(user, key, propVal.get(key));
		}

		Boolean encrypt = (Boolean) getConfiguration().get(Constants.ENCRYPT_PASSWORD);

		if (StringUtils.equals(request.getParameter("encryptPass"), "true")
				&& (encrypt != null && encrypt.booleanValue())) {
			String algorithm = (String) getConfiguration().get(Constants.ENC_ALGORITHM);

			if (algorithm == null) { // should only happen for test case
				log.info("assuming testcase, setting algorithm to 'SHA'");
				algorithm = "SHA";
			}

			user.setPassword(StringUtil.encodePassword(user.getPassword(), algorithm));
		}

		RoleManager roleMgr = (RoleManager) getBean("roleManager");
		String[] userRoles = request.getParameterValues("userRoles");

		for (int i = 0; userRoles != null && i < userRoles.length; i++) {
			String roleName = userRoles[i];
			user.addRole(roleMgr.getRole(roleName));
		}

		try {
			if (isNew) {
				// need
				mgr.saveUser(user);
				Preference pref = new Preference();
				pref.setHideFav(Boolean.TRUE);
				pref.setHideOwned(Boolean.TRUE);
				pref.setUserId(user.getId());
				pref.setFormat(RequestUtil.is_pc_platform(request) ? "wav" : "aif");
				user.setPreference(pref);
			} else {
				mgr.updateUser(user);
			}
		} catch (UserExistsException e) {
			log.warn(e.getMessage());
			errors.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.existing.user", userForm.getUsername(), userForm.getEmail()));
			saveErrors(request, errors);

			BeanUtils.copyProperties(userForm, convert(user));
			userForm.setConfirmPassword(userForm.getPassword());
			// reset the version # to what was passed in
			userForm.setVersion(request.getParameter("version"));
			updateFormBean(mapping, request, userForm);

			return mapping.findForward("edit");
		}

		BeanUtils.copyProperties(userForm, convert(user));
		userForm.setConfirmPassword(userForm.getPassword());
		updateFormBean(mapping, request, userForm);

		if (!StringUtils.equals(request.getParameter("from"), "list")) {
			// add success messages
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.saved"));
			saveMessages(request.getSession(), messages);

			// return a forward to main Menu
			return new ActionForward("/user/home.html", true);
		} else {
			// add success messages
			if ("".equals(request.getParameter("version"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.added", user.getFullName()));
				saveMessages(request.getSession(), messages);
				sendNewUserEmail(request, userForm);

				return mapping.findForward("addUser");
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("user.updated.byAdmin", user.getFullName()));
				saveMessages(request, messages);

				return mapping.findForward("edit");
			}
		}
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'search' method");
		}

		UserReportForm userPagerForm = (UserReportForm) form;
		UserReport userList = new UserReport();
		String exp = request.getParameter(TableTagParameters.PARAMETER_EXPORTING);

		request.setAttribute(Constants.USER_LIST, userList);
		// Exceptions are caught by ActionExceptionHandler
		UserManager mgr = (UserManager) getBean("userManager");
		PaginatedListFactory paginatedListFactory = (PaginatedListFactory) getBean("paginatedListFactory");
		userList = (UserReport) paginatedListFactory.getNamedPaginatedList(request, Constants.USER_LIST, false);
		BeanUtils.copyProperties(userList, userPagerForm);

		mgr.findUsers(userList, (StringUtils.isBlank(exp)));

		// return a forward to the user list definition
		return mapping.findForward("list");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserForm userForm = (UserForm) form;
		String username = request.getRemoteUser();

		if (StringUtils.isBlank(username)) {
			ActionMessages errors = form.validate(mapping, request);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.login.required"));
			saveErrors(request, errors);
			return mapping.findForward("mainMenu");
		}

		HttpSession session = request.getSession();

		// Exceptions are caught by ActionExceptionHandler
		UserManager mgr = (UserManager) getBean("userManager");
		User user = mgr.getUserByUsername(username);
		BeanUtils.copyProperties(userForm, convert(user));
		user = null;
		updateFormBean(mapping, request, userForm);
		return mapping.findForward("home");
	}

	private void sendEmailChangeTest(HttpServletRequest request, User user, ChangeEmailKey changeKey) throws Exception {

		MessageResources resources = getResources(request);
		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		MailEngine engine = (MailEngine) getBean("mailEngine");

		String username = user.getUsername();
		String notifyEmail = user.getEmail();

		String userkey = changeKey.getAccesscode();
		String newemail = changeKey.getEmail();

		String userip = request.getRemoteAddr();
		String from = resources.getMessage("support.email");
		String subject = resources.getMessage("support.subject");
		Map data = new HashMap();

		data.put("username", username);
		data.put("newemail", newemail);
		data.put("userkey", userkey);
		data.put("userip", userip);
		data.put("basehref", RequestUtil.getAppURL(request));

		message.setTo(username + " <" + newemail + ">");
		message.setFrom(from);
		message.setSubject(subject);

		engine.sendMessage(message, resources.getMessage("changeemail.validate.template"), data);

		if (message.getSentDate() != null) {
			message = (SimpleMailMessage) getBean("mailMessage");
			message.setTo(username + " <" + notifyEmail + ">");
			message.setFrom(from);
			message.setSubject(subject);

			engine.sendMessage(message, resources.getMessage("changeemail.notify.template"), data);
		}

		// Send user an e-mail
		if (log.isDebugEnabled()) {
			log.debug("Sending user '" + user.getUsername() + "' an account information e-mail");
		}

	}

	private void sendNewUserEmail(HttpServletRequest request, UserForm userForm) throws Exception {
		MessageResources resources = getResources(request);

		// Send user an e-mail
		if (log.isDebugEnabled()) {
			log.debug("Sending user '" + userForm.getUsername() + "' an account information e-mail");
		}

		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		message.setTo(userForm.getFullName() + "<" + userForm.getEmail() + ">");

		StringBuffer msg = new StringBuffer();
		msg.append(resources.getMessage("newuser.email.message", userForm.getFullName()));
		msg.append("\n\n" + resources.getMessage("userForm.username"));
		msg.append(": " + userForm.getUsername() + "\n");
		msg.append(resources.getMessage("userForm.password") + ": ");
		msg.append(userForm.getPassword());
		msg.append("\n\nLogin at: " + RequestUtil.getAppURL(request));
		message.setText(msg.toString());

		message.setSubject(resources.getMessage("signup.email.subject"));

		MailEngine engine = (MailEngine) getBean("mailEngine");
		engine.send(message);
	}

	private void checkForRememberMeLogin(HttpServletRequest request) {
		// if user logged in with remember me, display a warning that they can't
		// change passwords
		log.info("checking for remember me login...");

		AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
		SecurityContext ctx = SecurityContextHolder.getContext();

		if (ctx != null) {
			Authentication auth = ctx.getAuthentication();

			if (resolver.isRememberMe(auth)) {
				request.getSession().setAttribute("cookieLogin", "true");

				// add warning message
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("userProfile.cookieLogin"));
				saveMessages(request, messages);
			}
		}
	}

	/**
	 * Validate the users email
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserManager mgr = (UserManager) getBean("userManager");

		// otherwise, it's a POST either a new email or validation
		if (request.getMethod().equalsIgnoreCase("POST")) {

			JSONObject jsonresp = new JSONObject();
			response.setContentType("application/json; charset=UTF-8");

			ChangeEmailKeyForm changeForm = (ChangeEmailKeyForm) form;
			String activationKey = null;
			// first assume it is a validation attempt
			if (StringUtils.isNotBlank(activationKey = request.getParameter("vericode"))) {
				jsonresp.put("id", "confirm");
				ChangeEmailKey changeEmailKey = (ChangeEmailKey) mgr.getChangeEmailKey(activationKey);
				if (changeEmailKey != null && changeEmailKey.getUsername().equals(request.getRemoteUser())) {
					User user = changeEmailKey.getUser();
					user.setEmail(changeEmailKey.getEmail());
					user.setModified(new java.util.Date());
					mgr.deleteChangeEmailKey(changeEmailKey);
					try {
						mgr.saveUser(user);
						jsonresp.put("status", "ok");
						jsonresp.put("message", "valid");
						jsonresp.put("param", "email");
						jsonresp.put("value", changeEmailKey.getEmail());
						// put error
						changeEmailKey = null;
						user = null;
					} catch (UserExistsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						jsonresp.put("error", e.getMessage());
						response.getOutputStream().print(jsonresp.toString());
					}
				} else {
					jsonresp.put("error", "invalid activation key");
				}
				response.getOutputStream().print(jsonresp.toString());
				return null;
			}
			// user is submitting an email
			else if (StringUtils.isNotBlank(changeForm.getEmail())) {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				jsonresp.put("id", "confirm");

				if (changeForm.getEmail().equalsIgnoreCase(user.getEmail())) {
					jsonresp.put("errorcode", "nonunique");

					jsonresp.put("error", getResources(request).getMessage("errors.same.email"));
					jsonresp.put("param", "email");
					jsonresp.put("value", user.getEmail());
				}
				// if the email is unique
				else if (mgr.isEmailUnique(changeForm.getEmail())) {
					ChangeEmailKey changeEmailKey = new ChangeEmailKey();
					BeanUtils.copyProperties(changeEmailKey, changeForm);
					changeEmailKey.setCreated(new Date());
					changeEmailKey.setUsername(user.getUsername());
					changeEmailKey.setUserId(user.getId());
					String key = RequestUtil.makeRandString(7);
					changeEmailKey.setAccesscode(key);
					try {
						mgr.saveChangeEmailKey(changeEmailKey);
						sendEmailChangeTest(request, user, changeEmailKey);
						jsonresp.put("message", getResources(request).getMessage("changeemail.validationsent.message"));
						jsonresp.put("action", "sent");
						jsonresp.put("param", "email");
						jsonresp.put("value", changeEmailKey.getEmail());
					} catch (Exception e) {
						jsonresp.put("errorcode", "keyrequired");
						jsonresp.put("error", getResources(request).getMessage("errors.changeemail.keyrequired",
								changeEmailKey.getEmail()));
						jsonresp.put("param", "email");
						jsonresp.put("value", changeForm.getEmail());
					}
				}
				// otherwise email not unique
				else {
					jsonresp.put("errorcode", "nonunique");

					jsonresp.put("error",
							getResources(request).getMessage("errors.existing.email", changeForm.getEmail()));
					jsonresp.put("param", "email");
					jsonresp.put("value", changeForm.getEmail());
				}
				response.getOutputStream().print(jsonresp.toString());

				return null;
			}
		}
		return mapping.findForward("form");

	}

}
