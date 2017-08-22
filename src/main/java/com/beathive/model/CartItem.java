/**
 * 
 */
package com.beathive.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;




/**
 * @author ron
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="cartItem"
 */
public class CartItem  extends BaseObject {
    protected Long id;
    protected Long fileId; // the selected audioFile id
    protected Long clipId;
    protected Long userId;
//    protected Integer idx;
 //   protected String producerName;
//    private SoundClip soundClip;
    
    
    
    
    public CartItem(){
    	super();
    }
    public CartItem(Long clipId, Long fileId , Long userId){
    	super();
    	this.clipId = clipId;
    	this.fileId = fileId;
    	this.userId = userId;
    	
    }
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
     * @hibernate.id generator-class="native" unsaved-value="null"
     */
    public Long getId() {
        return id;
    }
	public void setId(Long id) {
	    this.id = id;
	}

	/**
	 * 
	 * @return Returns the soundClip.
	 * hibernate.many-to-one column="clipId" insert="false" update="false" cascade="none" lazy="false"
	 * 
	 */
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
	/**
	 * @struts.validator type="required"
     * @hibernate.property not-null="true"
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
	 * @struts.validator type="required"
     * @hibernate.property not-null="true"
	 * @return the clipId
	 */
	public Long getClipId() {
		return clipId;
	}
	/**
	 * @param clipId the clipId to set
	 */
	public void setClipId(Long clipId) {
		this.clipId = clipId;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#equals()
	 */
	@Override
	public boolean equals(Object o) {
    	if (o instanceof CartItem){
    		CartItem af = (CartItem)o;
    		return (af.getClipId().equals(this.clipId) && af.getFileId().equals(this.fileId)&& af.getUserId().equals(this.userId));
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

	/**
	 * struts.validator type="required"
	 * hibernate.property
	 * @return the producerName
	 */
//	public String getProducerName() {
//		return producerName;
//	}
//
//	/**
//	 * @param producerName the producerName to set
//	 */
//	public void setProducerName(String producerName) {
//		this.producerName = producerName;
//	}
}
