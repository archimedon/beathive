package com.beathive.webapp.form;

import java.io.Serializable;




/**
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 * @version $Revision: 2 $ $Date: 2008-15-06 $
 * 
 * @struts.form name="passwordForm"
 */
public class PasswordForm extends BaseForm implements Serializable {

	private String email;
	private String password;
	private String previousPassword;
	private String confirmPassword;

	//~ Methods ================================================================
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

   /**
    * @struts.validator type="required"
    * @struts.validator type="email"
    */
	public void setEmail(String email) {
		this.email = email;
	}
	
    public String getPassword() {
        return password;
    }

	/**
	 * Returns the confirmedPassword.
	 * 
	 * @return String
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword The confirmPassword to set.
	 * @struts.validator type="required"
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

    /**
     * Returns the password.
     * @return String
     *
     * @struts.validator type="required"
     * @struts.validator type="twofields" msgkey="errors.twofields"
     * @struts.validator-args arg1resource="passwordForm.password"
     * @struts.validator-args arg1resource="passwordForm.confirmPassword"
     * @struts.validator-var name="secondProperty" value="confirmPassword"
     * @hibernate.property column="password" not-null="true"
     */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the previousPassword.
	 */
	public String getPreviousPassword() {
		return previousPassword;
	}

	/**
	 * @param previousPassword The previousPassword to set.
	 */
	public void setPreviousPassword(String previousPassword) {
		this.previousPassword = previousPassword;
	}

}
