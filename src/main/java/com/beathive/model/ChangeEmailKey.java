package com.beathive.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
  * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 * 
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="change_email_key"
 */
public class ChangeEmailKey extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8425954624885336115L;
	protected String username;
	protected Long userId;
	protected String accesscode;
	protected String email;
	protected Date created;
	protected User user;

	public ChangeEmailKey() {
		super();
	}

	public ChangeEmailKey(String key) {
		super();
		setAccesscode(key);
	}

	public ChangeEmailKey(String key, String username) {
		super();
		setAccesscode(key);
		setUsername(username);
	}

	public ChangeEmailKey(String key, String username , String email) {
		super();
		setAccesscode(key);
		setUsername(username);
		setEmail(email);
	}

	/**
     * @hibernate.id generator-class="assigned"
	 * @return the key
	 */
	public String getAccesscode() {
		return accesscode;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setAccesscode(String key) {
		this.accesscode = key;
	}

	/**
	 * @hibernate.property not-null="true" unique="true"
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the formatId.
	 * 
	 * @param The
	 *            format name to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return this.accesscode;
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @return Returns the User.
	 * @hibernate.many-to-one column="userId" insert="false" update="false" cascade="none" unique="true" lazy="false"
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @hibernate.property not-null="true" insert="true" update="false" type="date"
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @hibernate.property not-null="true" unique="true"
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
	 * @hibernate.property not-null="true" unique="true"
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
