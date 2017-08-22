/**
 * 
 */
package com.beathive.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author ron
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="favorite"
 */
public class Favorite extends BaseObject {

	protected Long id;
	protected Long clipId;
	protected Long userId;
	protected Date created;
	protected String comment;
    protected Long fileId; // the selected audioFile id
	/**
	 * @struts.validator type="required"
	 * @hibernate.property
	 * @return the fileId
	 */
	public Long getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	
	/**
	 * @hibernate.id generator-class="native" unsaved-value="null"
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    /**
     * @hibernate.property not-null="true"
     */
	public Long getClipId() {
		return clipId;
	}

	public void setClipId(Long clipId) {
		this.clipId = clipId;
	}

//    private SoundClip soundClip;
//	/**
//	 * 
//	 * @return Returns the soundClip.
//	 * @hibernate.many-to-one column="clipId" insert="false" update="false" cascade="none" lazy="false"
//	 * 
//	 */
//	public SoundClip getSoundClip() {
//		return soundClip;
//	}
//	
//	/**
//	 * @return The soundClip this loop belongs to.
//	 * @param soundClip
//	 */
//	public void setSoundClip(SoundClip soundClip) {
//		this.soundClip = soundClip;
//	}
//
    /**
     * @hibernate.property not-null="true"
     */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    /**
     * @hibernate.property type="timestamp"
     */
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

    /**
     * @hibernate.property type="text"
     */
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#equals()
	 */
	@Override
	public boolean equals(Object o) {
    	if (o instanceof Favorite){
    		Favorite af = (Favorite)o;
    		return (af.getUserId().equals(this.userId) && af.getClipId().equals(this.clipId));
    	}
        return EqualsBuilder.reflectionEquals(this, o);
    }

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return this.clipId + "_" + this.fileId;
	}

}
