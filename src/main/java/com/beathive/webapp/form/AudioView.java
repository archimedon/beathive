package com.beathive.webapp.form;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

/**
 * @author ron
 *
 */
public class AudioView  extends HashMap implements Serializable , Map{
	private static final long serialVersionUID = 3452673777245904196L;
	
	public AudioView(){
		super();
		this.put("messages", new LinkedList());
	}
	
	public void addMessage(ActionMessage errmsg){
		((List)get("messages")).add(errmsg);
	}
	/**
	 * @return the errors
	 */
	public List getMessages() {
		return (List)get("messages");
	}
	/**
	 * @param errors the errors to set
	 */
	public void setMessages(List messages) {
		this.put("messages", messages);
	}

	/**
	 * @return the fileExt
	 */
	public String getFileExt() {
		return (String)get("fileExt");
	}
	/**
	 * @param fileExt the fileExt to set
	 */
	public void setFileExt(String fileExt) {
		this.put("fileExt", fileExt);
		this.put("formatId", fileExt);
	}
	/**
	 * @return the sampleSize
	 */
	public int getSampleSize() {
		if (! containsKey("sampleSize")){
			return -1;
		}
		return ((Integer)get("sampleSize")).intValue();
	}
	/**
	 * @param sampleSize the sampleSize to set
	 */
	public void setSampleSize(int sampleSize) {
		put("sampleSize", new Integer(sampleSize));
	}
	/**
	 * @return the sampleRate
	 */
	public float getSampleRate() {
		if (! containsKey("sampleRate")){
			return -1;
		}

		return ((Float)get("sampleRate")).floatValue();
	}
	/**
	 * @param sampleRate the sampleRate to set
	 */
	public void setSampleRate(float sampleRate) {
		put("sampleRate", new Float(sampleRate));
	}
	/**
	 * @param l
	 */
	public void setFileSize(long l) {
		put("fileSize", new Long(l));

	}
	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		if (! containsKey("fileSize")){
			return -1;
		}
		 return ((Long)get("fileSize")).longValue();
	}
	/**
	 * @return the samplePath
	 */
	public String getSamplePath() {
		return (String)get("samplePath");
	}
	/**
	 * @param samplePath the samplePath to set
	 */
	public void setSamplePath(String samplePath) {
		put("samplePath", samplePath);
	}
	/**
	 * @return the fileRef
	 */
	public String getFileRef() {
		return (String)get("fileRef");
	}
	/**
	 * @param fileRef the fileRef to set
	 */
	public void setFileRef(String fileRef) {
		put("fileRef", fileRef);
	}
	/**
	 * @return the formatId
	 */
	public String getFormatId() {
		return (String)get("fileExt");
	}

	/**
	 * @param replaceAll
	 */
	public void setName(String name) {
		put("name", name);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return (String)get("name");
	}
	/**
	 * @param fwrite
	 */
	public void setFile(File file) {
		put("file", file);
	}
	/**
	 * @return the file
	 */
	public File getFile() {
		return (File)get("file");
	}
	/**
	 * @return the dead
	 */
	public boolean isDead() {
		return ((Boolean)get("dead")).booleanValue();
	}
	/**
	 * @param dead the dead to set
	 */
	public void setDead(boolean dead) {
		put("dead", new Boolean(dead));
	}
	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return (String)get("storeId");
	}
	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		put("storeId", storeId);
	}
	/**
	 * @return the clipId
	 */
	public String getClipId() {
		return (String)get("clipId");
	}
	/**
	 * @param clipId the clipId to set
	 */
	public void setClipId(String clipId) {
		put("clipId", clipId);
	}
	/**
	 * @param string
	 */
	public void setId(String idStr) {
		put("idStr", idStr);
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return (String)get("id");
	}
	/**
	 * @return the memberOf
	 */
	public String getMemberOf() {
		return (String)get("memberOf");
	}
	/**
	 * @param memberOf the memberOf to set
	 */
	public void setMemberOf(String memberOf) {
		put("memberOf", memberOf);
	}
	/**
	 * @return the checkSum
	 */
	public String getCheckSum() {
		return  (String)get("checkSum");
	}
	/**
	 * @param checkSum the checkSum to set
	 */
	public void setCheckSum(String checkSum) {
		this.put("checkSum", checkSum);
	}
}
