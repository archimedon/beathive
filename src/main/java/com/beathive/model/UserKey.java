package com.beathive.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A class used to sync the a new user's validation key with their 'User' account.
 * Used by {@link com.beathive.webapp.filter.EnableAccountFilter} and {@link com.beathive.webapp.action.SignupAction}
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 * 
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="useraccess"
 */
public class UserKey extends BaseObject implements Serializable {
	private static final long serialVersionUID = -857088441831351841L;
	protected Long userId;
	protected String accesscode;
	protected User user;

	public UserKey() {
		super();
	}

	public UserKey(String key) {
		super();
		setAccesscode(key);
	}

	public UserKey(String key, Long userId) {
		super();
		setAccesscode(key);
		setUserId(userId);
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
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the formatId.
	 * 
	 * @param The
	 *            format name to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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

}
