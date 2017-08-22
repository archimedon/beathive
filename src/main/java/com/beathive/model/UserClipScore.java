package com.beathive.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Stores the rating given to a clip by a user
 * @struts.form include-all="true" extends="BaseForm"
 * #hibernate.class table="user_clip_score"
 */
public class UserClipScore extends BaseObject {
    //~ Instance fields ========================================================

//    private Long id;
    private Long userId;
    private Long clipId;
    private Long storeId;
    private Integer score;
    


	// ~ Methods ================================================================

    public UserClipScore(){super();}
    /**
     * 
     * @param userId
     * @param clipId
     * @param score
     */
    public UserClipScore(Long userId , Long clipId , Integer score){
    	this();
		this.setClipId(clipId);
		this.setUserId(userId);
		this.setScore(score);
    }
    /**
	 * @param userId2
	 * @param clipId2
	 * @param storeId2
	 * @param score2
	 */
	public UserClipScore(Long userId, Long clipId, Long storeId,
			Integer score) {
    	this();
		this.setClipId(clipId);
		this.setUserId(userId);
		this.setScore(score);
		this.setStoreId(storeId);
	}
	/**
     * #hibernate.property not-null="true"
     * @return Returns the clipId.
     */
    public Long getClipId() {
    	return clipId;
    }
    /**
     * @param clipId The clipId to set.
     */
    public void setClipId(Long clipId) {
    	this.clipId = clipId;
    }
    /**
     * #hibernate.id generator-class="native" unsaved-value="null"
     * @return Returns the id.
     */
//    public Long getId() {
//    	return id;
//    }
    /**
     * @param id The id to set.
     */
//    public void setId(Long id) {
//    	this.id = id;
//    }
    /**
     * #hibernate.property not-null="true"
     * @return Returns the score.
     */
    public Integer getScore() {
    	return score;
    }
    /**
     * @param score The score to set.
     */
    public void setScore(Integer score) {
    	this.score = score;
    }
	/**
	 * #hibernate.property not-null="true"
	 * @return Returns the userId.
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param username The userId to set.
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    public String toString() {
    	if (score!=null){
    	return this.score.toString();
    	}
    	return null;
    }

    public boolean equals(Object o) {
    	if (o==null || !( o  instanceof UserClipScore)){
    		return false;
    	}
    	UserClipScore usc = (UserClipScore)o;
    	return new EqualsBuilder().append(usc.getClipId(), this.clipId)
    	.append(usc.getUserId(), this.userId).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
        .append(this.clipId)
		.append(this.userId).toHashCode();
    }
	/**
	 * @return the storeId
	 */
	public Long getStoreId() {
		return storeId;
	}
	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

}
