package com.beathive.webapp.action;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.model.Store;
import com.beathive.model.User;
import com.beathive.service.MailEngine;
import com.beathive.service.StoreManager;
import com.beathive.service.UserManager;
import com.beathive.webapp.form.ContactForm;

/**
 * Implements add, view and remove items from Cart.
 * Expects the request.parameter("format") to contain the specific clipformat_id;
 * Simply forwards to the view page
 * 
 * 
 * @struts.action path="/shop/contactProducer" scope="request" name="contactProducerForm"
 *                input="form" validate="false" parameter="action"
 * 
 * @struts.action path="/producer/reply" scope="request" name="producerReplyForm"
 *                input="reply" validate="false" parameter="action"
 * 
 * @struts.action-forward name="sendForm" path="/WEB-INF/pages/store/contactProducer.jsp"
 * @struts.action-forward name="prodMsgSent" path="/user/producerMessageSent.jsp"
 * @struts.action-forward name="userReplySent" path="/user/producerReplySent.jsp"
 * @struts.action-forward name="replyForm" path=".producerReply"
 *  
 */
public class ProducerContactAction extends BaseAction {
	private static Log log = LogFactory.getLog(ProducerContactAction.class);

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String uri = request.getRequestURI();
		boolean isReply = StringUtils.contains(uri, "reply");
		
		return mapping.findForward((isReply)?"replyForm":"sendForm");
	}

	public ActionForward reply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = form.validate(mapping, request);
		if(!errors.isEmpty()){
            saveErrors(request, errors);
            return mapping.getInputForward();			
		}
		ContactForm replyForm = (ContactForm) form;
		UserManager umgr = (UserManager)getBean("userManager");
		User user = umgr.getUserByUsername(replyForm.getRecipientName());
		replyForm.setRecipientEmail(user.getEmail());
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null ){//&& auth.getPrincipal() instanceof UserDetails) {
                User currentUser = (User) auth.getPrincipal();
                replyForm.setSenderEmail(currentUser.getEmail());
                replyForm.setSenderName(currentUser.getUsername());
            }
			
			Map data = new HashMap();
			data.put("message" , replyForm.getMessage());
			data.put("producerName" , replyForm.getSenderName());
			data.put("producerNameEncoded" , URLEncoder.encode( replyForm.getSenderName(), "UTF-8"));
			data.put("storename" , replyForm.getStoreName());

			sendMessage(replyForm , "producerreply.template" , data, request);

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

        request.getSession().setAttribute("replyForm", replyForm);
		return mapping.findForward("prodReplySent");
	}

	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {


		ActionMessages errors = form.validate(mapping, request);
		if(!errors.isEmpty()){
            saveErrors(request, errors);
            return mapping.getInputForward();			
		}

		
		ContactForm contactProducerForm = (ContactForm) form;
		try{
			StoreManager storeManager = (StoreManager) getBean("storeManager");
			Store store = storeManager.getStore(contactProducerForm.getStoreId());
			
			contactProducerForm.setStoreName(store.getName());
			contactProducerForm.setRecipientEmail(store.getUser().getEmail());
			contactProducerForm.setRecipientName(store.getProducerName());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null ){//&& auth.getPrincipal() instanceof UserDetails) {
                User currentUser = (User) auth.getPrincipal();
                contactProducerForm.setSenderEmail(currentUser.getEmail());
                contactProducerForm.setSenderName(currentUser.getUsername());
            }		

			
			Map data = new HashMap();
			data.put("message" , contactProducerForm.getMessage());
			data.put( "senderEncoded" , URLEncoder.encode( contactProducerForm.getSenderName() , "UTF-8"));
			data.put( "sender" , contactProducerForm.getSenderName());

			
			sendMessage(contactProducerForm , "contactproducer.template" , data, request);
			
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
		return mapping.findForward("prodMsgSent");
	}


	/**
	 * @param feedback
	 * @param request
	 * @throws Exception
	 */
	private void sendMessage(ContactForm contactProducerForm, String templateKey, Map data ,HttpServletRequest request) throws Exception {
        MessageResources resources = getResources(request);
		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		log.debug("sending to: " + contactProducerForm.getRecipientEmail());
		message.setFrom(contactProducerForm.getSenderName() + " <no-reply@BeatHive.com>");
		message.setTo(contactProducerForm.getRecipientName() + " <" + contactProducerForm.getRecipientEmail() + ">");
//		message.setBcc(resources.getMessage("contactproducer.internal.email" ));


		message.setSubject(resources.getMessage("beathivecircle.email.subject", contactProducerForm.getSubject()));


		MailEngine engine = (MailEngine) getBean("mailEngine");
		engine.sendMessage(message , resources.getMessage(templateKey), data);
	}
}