package com.beathive.webapp.action;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.ui.savedrequest.SavedRequest;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.mail.SimpleMailMessage;

import com.beathive.Constants;
import com.beathive.model.Role;
import com.beathive.model.User;
import com.beathive.model.UserKey;
import com.beathive.service.MailEngine;
import com.beathive.service.Manager;
import com.beathive.service.RoleManager;
import com.beathive.service.ServiceException;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.PasswordForm;
import com.beathive.webapp.form.SignupForm;
import com.beathive.webapp.form.UserForm;
import com.beathive.webapp.util.RequestUtil;

/**
 * Action class to allow users to self-register.
 * 
 * <a href="SignupAction.java.html"><i>View Source</i></a>
 * 
 * @struts.action name="signupForm" path="/signup" scope="session"
 *                validate="true" input="signup"
 * 
 * @struts.action name="signupForm" path="/lostPassword" scope="request"
 *                validate="false" input="newPassword"
 * 
 * @struts.action-forward name="signup" path="/login.jsp"
 * @struts.action-forward name="success" path="/login.jsp" redirect="true"
 */
public final class SignupAction extends BaseAction {



	public ActionForward signup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward af = mapping.findForward("success");
		

		HttpSession session = request.getSession();
		SavedRequest secreq = (SavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
		AuthenticationProcessingFilterEntryPoint authenticationEntryPoint = (AuthenticationProcessingFilterEntryPoint)getBean("authenticationProcessingFilterEntryPoint");
		
		String target = RequestUtil.getAppURL(request) +authenticationEntryPoint.getLoginFormUrl();
		if (secreq != null){
			target = secreq.getRequestURL() +  ((secreq.getQueryString() !=null) ? "?"+ secreq.getQueryString() : "" );
			if (StringUtils.isNotBlank(target)){
				af = new ActionForward(target, true);
			}
		}
		// run validation rules on this form
		ActionMessages errors = new ActionMessages();
		if (log.isDebugEnabled()) {
			log.debug("registering user...");
		}

		SignupForm signupForm = (SignupForm) form;
		
		// check form a against DB
		UserManager mgr = (UserManager) getBean("userManager");
		// if the email is not unique
		if (!mgr.isEmailUnique(signupForm.getEmail())){
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.existing.email", signupForm.getEmail()));
			saveErrors(session, errors);
			return mapping.getInputForward();
		}
		// if the username is not unique
		if (!mgr.isUserNameUnique(signupForm.getUsername())){
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.existing.user", signupForm.getUsername()));
			saveErrors(session, errors);
			return mapping.getInputForward();
		}
		PasswordEncoder passwordEncoder = (PasswordEncoder)getBean("passwordEncoder");

		
		// convert form for saving
		User user = new User();
		BeanUtils.copyProperties(user, signupForm);
		String encpass = passwordEncoder.encodePassword(signupForm.getPassword() , null);
		user.setPassword(encpass);
		user.setConfirmPassword(encpass);
		user.setEnabled(false);
		user.setCreated(new java.util.Date());
		// Set the default user role on this new user
		RoleManager roleMgr = (RoleManager) getBean("roleManager");
		user.addRole(roleMgr.getRole(Constants.USER_ROLE));
		mgr.saveUser(user);
	
		String key = RequestUtil.generatePassword();
		
		UserKey userKey = new UserKey(key , user.getId());
		
		mgr.saveKey(userKey);
		target += StringUtils.contains(target, "?") ? "&uid="+key : "?uid="+key;

		ActionMessages messages = new ActionMessages();

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"user.registered", signupForm.getUsername() , signupForm.getEmail()));

		saveMessages(session, messages);
		session.setAttribute(Constants.REGISTERED, Boolean.TRUE);
		String nextUri =  request.getParameter("nextUri");
		session.setAttribute("nextUri" , nextUri);

		// Send user an e-mail
		if (log.isDebugEnabled()) {
			log.debug("Sending user '" + signupForm.getUsername()
					+ "' an account information e-mail");
		}
		session.setAttribute("uri",target);
		request.setAttribute("userFormEx",signupForm);
		MessageResources resources = getResources(request);
		
		emailUser(signupForm , resources , target);
		
		return mapping.findForward("success");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		// for now assume going to 'lostPassword'
		return recover(mapping, form, request, response);
	}
	public ActionForward recover(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();

		if (request.getMethod().equalsIgnoreCase("get")){
			return mapping.findForward("newPassword");
		}
		PasswordForm passwordForm = (PasswordForm) form;
		String key = passwordForm.getEmail();
		if (StringUtils.isBlank(key)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.required.email"));
			saveErrors(request, messages);
			return mapping.getInputForward();
		}
		UserManager mgr = (UserManager) getBean("userManager");
		try{
			User user = mgr.loadUserByEmail(key);
				// convert and set password
				String plainPass = RequestUtil.generatePassword();
	            PasswordEncoder passwordEncoder = (PasswordEncoder)getBean("passwordEncoder");
	            String encpass = passwordEncoder.encodePassword(plainPass , null);
				user.setPassword(encpass);
				user.setConfirmPassword(encpass);
				mgr.saveUser(user);
	
				UserForm userForm = (UserForm)ConvertUtil.convert(user, UserForm.class);
				request.getSession().setAttribute("newpassword" , Boolean.TRUE);
				MessageResources resources = getResources(request);
				emailNewPassword(userForm , plainPass , resources );
				user = null;
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"passwordForm.message.sent", userForm.getEmail()));

				saveMessages(request.getSession(), messages);
		}
		catch(ServiceException ore){
			log.error(ore.getMessage() , ore);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.nonexistent.email", key));
			saveErrors(request, messages);
			return mapping.getInputForward();
			
		}
	
		return new ActionForward("/resetPassword.html" , true);
	}
	
	
	private void emailNewPassword(UserForm signupForm, String passwd , MessageResources resources) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		message.setTo(signupForm.getUsername() + " <" + signupForm.getEmail() + ">");

		if (resources.isPresent("signup.newpassword.email.from")){
			message.setFrom(resources.getMessage("signup.newpassword.email.from"));
		}
		message.setSubject(resources.getMessage("signup.newpassword.email.subject"));

		Map data = new HashMap();
		data.put("user" , signupForm);
		data.put("password" , passwd);

		data.put("message", 
				resources.isPresent("signup.newpassword.email.text")
				? resources.getMessage("signup.newpassword.email.text")
				: ""
		);
		
		String template = resources.getMessage("signup.newpassword.template");
		MailEngine engine = (MailEngine) getBean("mailEngine");
		engine.sendMessage(message , template, data);
	}
	
	private void emailUser(SignupForm signupForm,  MessageResources resources, String target) {
		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		message.setTo(signupForm.getUsername() + " <" + signupForm.getEmail() + ">");

		if (resources.isPresent("signup.newuser.email.from")){
			message.setFrom(resources.getMessage("signup.newuser.email.from"));
		}
		message.setSubject(resources.getMessage("signup.newuser.email.subject"));

		Map data = new HashMap();
		data.put("user" , signupForm);
		data.put("applicationURL" , target);

		data.put("message", 
				resources.isPresent("signup.newuser.email.text")
				? resources.getMessage("signup.newuser.email.text")
				: ""
		);
		
		String template = resources.getMessage("signup.newuser.template");
		MailEngine engine = (MailEngine) getBean("mailEngine");
		engine.sendMessage(message , template, data);
	}
}
