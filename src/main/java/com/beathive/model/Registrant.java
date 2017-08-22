package com.beathive.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class represents a partial registrant.</p>
 * @struts.form include-all="true" extends="BaseForm"
 */
public class Registrant extends BaseObject implements Serializable {

	private static final long serialVersionUID = -42033783688638398L;

	protected java.math.BigInteger id;
	protected Integer version;
	protected String username;
	protected String firstName;
	protected String lastName;
    protected String email;
    protected String userPassword;
    protected String password;
    protected String accesscode;

	public Registrant(){		
		super();
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the userId
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setId(BigInteger userId) {
		this.id = userId;
	}


    /**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
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
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the accesscode
	 */
	public String getAccesscode() {
		return accesscode;
	}

	/**
	 * @param accesscode the accesscode to set
	 */
	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}

    public boolean equals(Object object) {
        if (!(object instanceof Registrant)) {
            return false;
        }

        Registrant rhs = (Registrant) object;

        return new EqualsBuilder()
        .append(this.id, rhs.id)
		.append(this.firstName, rhs.firstName)
		.append(this.lastName, rhs.lastName)
		.isEquals();
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public int hashCode() {
        return new HashCodeBuilder()
        .append(this.id)
		.append(this.firstName)
		.append(this.lastName)
		.toHashCode();
    }
    

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", this.id)
                .append("version", this.version)
                .append("firstName", this.firstName).append("lastName",
                        this.lastName).toString();
    }

}
