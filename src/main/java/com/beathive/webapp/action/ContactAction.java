package com.beathive.webapp.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.mail.SimpleMailMessage;

import com.beathive.service.MailEngine;
import com.beathive.webapp.form.FeedbackForm;

/**
 * Implements add, view and remove items from Cart.
 * Expects the request.parameter("format") to contain the specific clipformat_id;
 * Simply forwards to the view page
 * 
 * 
 * @struts.action path="/sendFeedback" scope="request" name="feedbackForm"
 *                input="form" validate="false" parameter="action"
 * 
 * @struts.action-forward name="form" path=".sendFeedback"
 *  
 */
public class ContactAction extends BaseAction {
	private static Log log = LogFactory.getLog(ContactAction.class);

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		ActionMessages errors = form.validate(mapping, request);
		if(!errors.isEmpty()){
            saveErrors(request, errors);
            log.debug("error");
            return mapping.getInputForward();			
		}

		
		FeedbackForm feedbackForm = (FeedbackForm) form;
		ActionMessages messages = new ActionMessages();
		try{
			sendFeedback(feedbackForm , request);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add(ActionMessages.GLOBAL_MESSAGE,
                       new ActionMessage("errors.general"));
            while (e != null) {
                errors.add(ActionMessages.GLOBAL_MESSAGE,
                           new ActionMessage("errors.detail", e.getMessage()));
                e = (Exception) e.getCause();
            }
            saveErrors(request, errors);
            return mapping.getInputForward();
        }
        MessageResources resources = getResources(request);

        // add success messages
        messages.add(ActionMessages.GLOBAL_MESSAGE,
                     new ActionMessage("feedback.message.sent" , resources.getMessage(feedbackForm.getRecipient())));
        saveMessages(request.getSession(), messages);
        request.getSession().setAttribute("sent" , Boolean.TRUE);
        request.getSession().setAttribute("feedbackForm" , feedbackForm);
		return mapping.getInputForward();
	}

	/**
	 * @param feedback
	 * @param request
	 * @throws Exception
	 */
	private void sendFeedback(FeedbackForm feedbackForm, HttpServletRequest request) throws Exception {
        MessageResources resources = getResources(request);
        String userName="";
        if (request.getRemoteUser() !=null){
        		userName = request.getRemoteUser();
        }
        String recipientEmailAddress = resources.getMessage(feedbackForm.getRecipient());
        
		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		message.setTo(recipientEmailAddress);

		message.setSubject(resources.getMessage("feedback.email.subject"));

		Map data = new HashMap();
		data.put("feedback" , feedbackForm);
		data.put( "username" , userName);
		
		String template = resources.getMessage("feedback.template");
		MailEngine engine = (MailEngine) getBean("mailEngine");
		engine.sendMessage(message , template, data);
	}
	
}