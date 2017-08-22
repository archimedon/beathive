package com.beathive.webapp.form;

import java.io.Serializable;




/**
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 * @version $Revision: 2 $ $Date: 2008-15-06 $
 * 
 * @struts.form name="signupForm"
 */
public class SignupForm extends BaseForm implements Serializable {

    protected String created;

    protected String firstName;
    protected String lastName;
    protected String passwordHint;
    protected String username;
    protected String email;
    protected String password;
    protected String confirmPassword;


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
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
     * @struts.validator type="required"
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
     * @struts.validator type="required"
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
     * @struts.validator type="required"
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the passwordHint
	 */
	public String getPasswordHint() {
		return passwordHint;
	}

	/**
	 * @param passwordHint the passwordHint to set
	 */
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
     * @struts.validator type="required"
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


}
