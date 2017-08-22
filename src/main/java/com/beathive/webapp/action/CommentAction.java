
package com.beathive.webapp.action;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.util.ConvertUtil;
import com.beathive.webapp.action.BaseAction;
import com.beathive.Constants;
import com.beathive.model.Comment;
import com.beathive.model.Store;
import com.beathive.model.User;
import com.beathive.service.CommentManager;
import com.beathive.service.StoreManager;
import com.beathive.webapp.form.CommentForm;
import com.beathive.webapp.form.StoreForm;

/**
 * Action class to handle CRUD on a Comment object
 *
 * @struts.action name="commentForm" path="/comments" scope="request"
 *                validate="false" parameter="method" input="mainMenu"
 * @struts.action name="commentForm" path="/editComment" scope="request"
 *                validate="false" parameter="method" input="list"
 * @struts.action name="commentForm" path="/saveComment" scope="request"
 *                validate="true" parameter="method" input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="comment" path="/WEB-INF/pages/comment.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/commentList.jsp"
 * @struts.action-forward name="search" path="/comments.html" redirect="true"
 */
public final class CommentAction extends BaseAction {
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("search");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		CommentForm commentForm = (CommentForm) form;

		// Exceptions are caught by ActionExceptionHandler
		CommentManager mgr = (CommentManager) getBean("commentManager");
		mgr.removeComment(commentForm.getId());

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("comment.deleted"));

		// save messages in session, so they'll survive the redirect
		saveMessages(request.getSession(), messages);

		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommentForm commentForm = (CommentForm) form;

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		if (commentForm.getId() != null) {
			CommentManager mgr = (CommentManager) getBean("commentManager");
			Comment comment = mgr.getComment(commentForm.getId());
			commentForm = (CommentForm) convert(comment);
			updateFormBean(mapping, request, commentForm);
		}
		String storeId = request.getParameter("storeId");

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		if (storeId != null) {
			StoreManager storeManager = (StoreManager) getBean("storeManager");
			Store store = storeManager.getStore(storeId);
			StoreForm storeForm = (StoreForm) convert(store);
			Collection commentList = ConvertUtil.convertList(storeForm.getComments(), CommentForm.class);
			request.setAttribute("commentList", commentList);
			request.setAttribute("storeForm", storeForm);
		}

		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		CommentForm commentForm = (CommentForm) form;
		String storeId = request.getParameter("storeId");
		String postedById = request.getParameter("postedById");
		String username = request.getRemoteUser();

		if (storeId == null || request.getRemoteUser() == null) {
			return null;// mapping.findForward("mainMenu");
		}

		boolean isNew = ("".equals(commentForm.getId()) || commentForm.getId() == null);

		CommentManager mgr = (CommentManager) getBean("commentManager");
		Comment comment = (Comment) convert(commentForm);
		comment.setEntryTime(new java.util.Date());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		comment.setPostedById(user.getId());
		comment.setPostedByUsername(username);
		mgr.saveComment(comment);

		StoreManager storeManager = (StoreManager) getBean("storeManager");
		Store store = storeManager.getStore(storeId);
		comment.setUserId(store.getUserId());
		store.addComment(comment);
		storeManager.saveStore(store);
		commentForm = (CommentForm) convert(comment);
		updateFormBean(mapping, request, commentForm);

		if (StringUtils.isNotBlank(request.getParameter("x")) || StringUtils.isNotBlank(request.getParameter("js"))) {
			return mapping.findForward("comment");
		}

		// add success messages
		if (isNew) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("comment.added"));

			// save messages in session to survive a redirect
			saveMessages(request.getSession(), messages);

		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("comment.updated"));
			saveMessages(request, messages);

		}
		String path = mapping.findForward("search").getPath() + "?storeId=" + storeId;

		return new ActionForward(path, true);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String storeId = request.getParameter("storeId");
		CommentForm commentForm = new CommentForm();
		commentForm.setPostedByUsername(request.getRemoteUser());
		commentForm.setEntryTime(com.beathive.util.DateUtil.getDate(new java.util.Date()));

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		if (storeId != null) {
			StoreManager storeManager = (StoreManager) getBean("storeManager");
			Store store = storeManager.getStore(storeId);
			StoreForm storeForm = (StoreForm) convert(store);
			List comments = storeForm.getComments();
			List commentList = (List) ConvertUtil.convertList(comments, CommentForm.class);
			java.util.Collections.reverse(commentList);
			request.setAttribute("commentList", commentList);
			commentForm.setUserId(storeForm.getUserId());
			request.setAttribute("storeForm", storeForm);
		}
		return mapping.findForward("list");
	}
}
