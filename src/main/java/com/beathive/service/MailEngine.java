package com.beathive.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Send e-mail messages using Velocity templates
 * or with attachments.
 */
public class MailEngine {
    protected static final Log log = LogFactory.getLog(MailEngine.class);
    private MailSender mailSender;
    private VelocityEngine velocityEngine;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * Send a simple, Velocity template, message
     * 
     * @param msg
     * @param templateName
     * @param model
     */
    public void sendMessage(SimpleMailMessage msg, String templateName,
                            Map model) {
        String result = null;

        try {
            result =
                VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                                                            templateName, model);
        } catch (VelocityException e) {
            e.printStackTrace();
        }

        msg.setText(result);
        send(msg);
    }

    /**
     * Send simple message with pre-populated values.
     * @param msg
     */
    public void send(SimpleMailMessage msg) {
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            //log it and go on
            log.error(ex.getMessage());
        }
    }

    /**
     * Send rich message
     * 
     * @param from
     * @param to
     * @param subject
     * @param templateName
     * @param model
     * @throws MessagingException
     */
    public void sendMessage(String from ,String to ,String subject,String templateName, Map model)
    throws MessagingException {
        MimeMessage message =
            ((JavaMailSenderImpl) mailSender).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        
        try {
        	String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
        	helper.setText(text , text.toLowerCase().contains("<html"));
        } catch (VelocityException e) {
            e.printStackTrace();
        }
        
        ((JavaMailSenderImpl) mailSender).send(message);
    }

    /**
     * Send with attachments.
     * 
     * Borrowed from "Ben Gill"
     * 
     * @param emailAddresses
     * @param resource
     * @param bodyText
     * @param subject
     * @param attachmentName
     * @throws MessagingException
     *
     */
    public void sendMessage(String[] emailAddresses,
    		ClassPathResource resource, String bodyText,
    		String subject, String attachmentName)
    throws MessagingException {
    	MimeMessage message =
    		((JavaMailSenderImpl) mailSender).createMimeMessage();
    	
    	// use the true flag to indicate you need a multipart message
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	
    	helper.setTo(emailAddresses);
    	helper.setText(bodyText);
    	helper.setSubject(subject);
    	
    	helper.addAttachment(attachmentName, resource);
    	
    	((JavaMailSenderImpl) mailSender).send(message);
    }
    
	/**
	 * @return the mailSender
	 */
	public MimeMessage getMimeMessage() {
		return ((JavaMailSenderImpl) mailSender).createMimeMessage();
	}
}
