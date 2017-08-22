package com.beathive.webapp.action;

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
import com.beathive.Constants;
import com.beathive.service.MailEngine;
import com.beathive.webapp.form.OffenseForm;
import org.springframework.mail.SimpleMailMessage;

/**
 * Implements add, view and remove items from Cart.
 * Expects the request.parameter("format") to contain the specific clipformat_id;
 * Simply forwards to the view page
 * 
 * 
 * @struts.action path="/reportOffense" scope="request" name="offenseForm"
 *                input="report" validate="false" parameter="action"
 * 
 * @struts.action-forward name="report" path=".reportOffense"
 *  
 */
public class OffenseAction extends BaseAction {
	private static Log log = LogFactory.getLog(OffenseAction.class);

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")){
			updateFormBean(mapping, request, new OffenseForm());
			return mapping.findForward("report");
		}
		
		OffenseForm offenseForm = (OffenseForm) form;
		ActionMessages errors = form.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		if(!errors.isEmpty()){
            saveErrors(request, errors);
			return mapping.findForward("report");
		}
		try{
			sendReport(offenseForm , request);
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
			return mapping.findForward("report");
        }

        // add success messages
        messages.add(ActionMessages.GLOBAL_MESSAGE,
                     new ActionMessage("offense.message.sent"));
        saveMessages(request, messages);
        request.setAttribute("sent" , Boolean.TRUE);
		return mapping.findForward("report");
	}

	/**
	 * Sends a receipt to the user.
	 * A duplicate is sent to support via BCC
	 * @param offense
	 * @param request
	 * @throws Exception
	 */
	private void sendReport(OffenseForm offense, HttpServletRequest request) throws Exception {
        MessageResources resources = getResources(request);
        // Send user an e-mail
        log.info("Sending support a loop-offense email from '" + offense.getEmail());
    	MailEngine engine = (MailEngine) super.getBean("mailEngine");

    	SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		String complainerEmail = offense.getFullname() + " <" + offense.getEmail() + ">";	
		message.setTo(complainerEmail);
		// bcc to support
		message.setBcc(resources.getMessage("offense.handler.emailaddr"));
		message.setFrom(Constants.DEFAULT_SUPPORT_EMAIL);
		StringBuffer buf = new StringBuffer();
		buf.append("\n"+resources.getMessage("offense.email.body" , offense.getComplaint()));
		buf.append("\n\n" + offense.toString());
		
		message.setText(buf.toString());
		message.setSubject(resources.getMessage("offense.email.subject",  offense.getId() ));
		engine.send(message);
	}
	
}