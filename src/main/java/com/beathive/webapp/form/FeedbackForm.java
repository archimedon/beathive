package com.beathive.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * @struts.form name="feedbackForm" 
 */
public class FeedbackForm
    extends    BaseForm
    implements java.io.Serializable
{

    protected String message;
    protected String recipient;
    protected String email;
    protected String subject;
    protected String userip;
    protected String username;
    protected String creationTime;

    public String getEmail()
    {
        return this.email;
    }
   /**
    * @struts.validator type="required"
    * @struts.validator type="email"
    */

    public void setEmail( String email )
    {
        this.email = email;
    }

   /**
    * @struts.validator type="required"
    */
    public void setUserip( String userip )
    {
        this.userip = userip;
    }

    public String getUsername()
    {
        return this.username;
    }
   /**
    */

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getCreationTime()
    {
        return this.creationTime;
    }
   /**
    * @struts.validator type="required"
    */
    public void setCreationTime( String creationTime )
    {
        this.creationTime = creationTime;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *                                                javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // reset any boolean data types to false

    }
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
     * @struts.validator type="required"
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return Returns the recipient.
	 */
	public String getRecipient() {
		return recipient;
	}
	/**
	 * @param recipient The recipient to set.
     * @struts.validator type="required"
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject The subject to set.
	 * @struts.validator type="required"
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return Returns the userip.
	 */
	public String getUserip() {
		return userip;
	}

}
