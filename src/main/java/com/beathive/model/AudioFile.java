package com.beathive.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
/**
 * Format class used to determine format for a clip
 *
 * <p>
 * <a href="ClipFormat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 * @version $Revision: 0.1 $ $Date: 2004/07/22 02:16:44 $
 *
 * @struts.form include-all="true" extends="BaseForm" 
 * @hibernate.class table="audiofile"
 * 
 */
public class AudioFile extends BaseObject {
	private static final long serialVersionUID = -4387263483906921114L;
		protected Long id;
		protected String formatId;
		protected String fileRef;
		protected String checkSum;
		protected String samplePath;
		protected Float sampleRate;
		protected Long sampleSize;
		protected Long clipId;
		protected Long storeId;
		
	    //~ Constructors ===========================================================
	    /**
		 * @return the clipId
		 * @hibernate.property
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
		/**
		 * @return the storeId
		 * @hibernate.property
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
		public AudioFile() {
	    		super();
	    }
	    /**
	     * 
	     * @param formatid
	     */
	    public AudioFile( String formatid)  {
			super();
			this.setFormatId(formatid);
	    }

	    //~ Methods ================================================================
		/**
		 * @return Returns the samplePath.
	     * @hibernate.property
		 */
		public String getSamplePath() {
			return samplePath;
		}
		/**
		 * @param samplePath The samplePath to set.
		 */
		public void setSamplePath(String samplePath) {
			this.samplePath = samplePath;
		}
		/**
		 * The base reference path to the 
		 * @return Returns the fileRef.
	     * @hibernate.property
		 */
		public String getFileRef() {
			return fileRef;
		}
		/**
		 * @param fileRef The fileRef to set.
		 */
		public void setFileRef(String fileRef) {
			this.fileRef = fileRef;
		}

		/**
		 * @return id.
		 * @hibernate.id generator-class="native" unsaved-value="null"
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id
		 *            The id to set.
		 */
		public void setId(Long id) {
			this.id = id;
		}

	    /**
	     * @return the format Id.
	     *
	     * @struts.validator type="required"
	     * @hibernate.property Xnot-null="true" 
	     */
	    public String getFormatId() {
	        return formatId;
	    }

	    /**
	     * Sets the formatId.
	     * @param The format name to set
	     */
	    public void setFormatId(String formatid) {
	        this.formatId = formatid;
	    }
		/**
		 * @return Returns the sampleSize.
		 * @hibernate.property
		 */
		public Long getSampleSize() {
			return sampleSize;
		}
		/**
		 * @param sampleSize The sampleSize to set.
		 */
		public void setSampleSize(Long sampleSize) {
			this.sampleSize = sampleSize;
		}
		/**
		 * @return Returns the sampleRate.
		 * @hibernate.property
		 */
		public Float getSampleRate() {
			return sampleRate;
		}
		/**
		 * @param sampleRate The sampleRate to set.
		 */
		public void setSampleRate(Float sampleRate) {
			this.sampleRate = sampleRate;
		}
		public String toString(){
			return this.fileRef;
			/*return ToStringBuilder.reflectionToString(this,
	                ToStringStyle.MULTI_LINE_STYLE);
	        */
		}

		/**
		 * @return Returns the checkSum.
		 * @hibernate.property unique="true" not-null="true"
		 */
		public String getCheckSum() {
			return checkSum;
		}
		/**
		 * @param checkSum the checkSum to set
		 */
		public void setCheckSum(String checkSum) {
			this.checkSum = checkSum;
		}

		public boolean equals(Object o) {
	    	
	    	
	    	if (o instanceof AudioFile){
	    		AudioFile af = (AudioFile)o;
	    		
	    		return (af.getClipId().equals(this.clipId) && af.getFormatId().equals(this.formatId));
	    	}
	    	return false;
//	        return EqualsBuilder.reflectionEquals(this, o);
	    }

	    public int hashCode() {
	        return HashCodeBuilder.reflectionHashCode(this);
	    }

}
