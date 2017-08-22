/**
 * 
 */
package com.beathive.webapp.form;

/**
 * @author ron ron@beathive.com
 * @version 1.9 Date: Sep 16, 2009
 * 
 * @hibernate.class table="SoundClipFormCart"
 */
public interface SoundClipFormCart {

	public AudioFileForm getAudioFile();
	
	/**
	 * @return the formatId
	 */
	public String getFileId();
	public String getId();

	/**
	 * @param formatId the formatId to set
	 */
	public void setFileId(String fileId);
    public String getType();
    public void setType(String clazz);

}
