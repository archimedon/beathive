package com.beathive.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 
 *  <table name="comment">
    <column>id</column>
    <column>entryTime</column>
    <column>postedBy</column>
    <column>statement</column>
    <column>userId</column>
    <column>postedById</column>
    <column>postedByUsername</column>
  </table>
 * 
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 *
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="comment"
 */

public class Comment extends BaseObject {

	protected String statement;
	protected Date entryTime ;
	protected Long postedById;
	protected String postedByUsername;
	protected Long userId;
	protected Long id;
	
	/**
     * @struts.validator type="required"
     * @hibernate.property not-null="true" type="timestamp"
     * @return String
     */
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	
    /**
     * @hibernate.id column="id" generator-class="native" unsaved-value="null"
     * @return Long
     */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
     * @struts.validator type="required" page="1"
     * @hibernate.property not-null="false"
     * @return String
     */
	public String getPostedByUsername() {
		return postedByUsername;
	}
	public void setPostedByUsername(String postedBy) {
		this.postedByUsername = postedBy;
	}

	/**
     * @struts.validator type="required" page="1"
     * @hibernate.property not-null="false"
     * @return String
     */	
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	/**
     * @struts.validator type="required" page="0"
     * @hibernate.property not-null="false"
     * @return String
     */	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
    public String toString() {
    	return statement;
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
	/**
	 * @return the postedById
     * @hibernate.property not-null="false"
	 */
	public Long getPostedById() {
		return postedById;
	}
	/**
	 * @param postedById the postedById to set
	 */
	public void setPostedById(Long postedById) {
		this.postedById = postedById;
	}
	
}
