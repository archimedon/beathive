package com.beathive.webapp.form;

/**
 * @struts.form name="contactForm" 
 */
public class ContactForm
    extends    BaseForm
    implements java.io.Serializable
{

    protected String message;
    protected String senderName;
    protected String senderEmail;
    
    protected String recipientName;
    protected String recipientEmail;
    
    protected String subject;
    // the store owner
    protected String storeName;
    protected String storeId;
    
    
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
     * @struts.validator type="required" page="1"
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
     * @struts.validator type="required" page="1"
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return the senderEmail
	 */
	public String getSenderEmail() {
		return senderEmail;
	}
	/**
     * @struts.validator type="required" page="1"
	 * @param senderEmail the senderEmail to set
	 */
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}
	/**
     * @struts.validator type="required" page="1"
	 * @param recipientName the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	/**
	 * @return the recipientEmail
	 */
	public String getRecipientEmail() {
		return recipientEmail;
	}
	/**
     * @struts.validator type="required" page="1"
	 * @param recipientEmail the recipientEmail to set
	 */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
     * @struts.validator type="required" page="1"
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}
	/**
     * @struts.validator type="required" page="0"
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
}
