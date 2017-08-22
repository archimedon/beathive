/**
 * 
 */
package com.beathive.model;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author ron
 *
 */
public class ClipToZip {
	private String name;
	private String parentName;
	private String parentFileExt;
	private String storeName;
	private String fileRef;
	private boolean aTP = false;
	private boolean aChild = false;
	private String fileExt;
	
//	private static String Win95VFATpatt = Pattern.quote("|\\?*<\":>+[]/");
	
	private static Pattern filepattern = Pattern.compile(Pattern.quote("\\<>[]\":+/'?*|%" +"&;,=!"));
//	private static Pattern filepattern = Pattern.compile("\\\\/:\\*\\?\\\"\\<\\>\\|");
	public ClipToZip(){
		super();
	}


	public String getName(){
		if (this.aTP){
			return this.name + "/" + this.name +"." +this.fileExt;
		}
		if (this.aChild){
			return this.parentName + "/" + this.name +"." +this.fileExt;
		}
		return this.name +"." +this.fileExt;
	}
	
	public void setTrackpack(Trackpack tp){
		if ( this.aChild = (tp != null)){
			this.parentName = filepattern.matcher(tp.getName()).replaceAll("");
			if (this.parentName.length() > 31){
				this.parentName = this.parentName.substring(0 , 30);
			}
			aTP = false;
		}
	}
	
	public void setStoreName(String storeName){
		this.storeName = filepattern.matcher(storeName).replaceAll("");
		if (this.storeName.length() > 31){
			this.storeName = this.storeName.substring(0 , 30);
		}
	}
	
	public ClipToZip(String fileExt){
		super();
		this.fileExt = fileExt;
	}
	
	public void setAudioFormat(Set<AudioFile> formats){
		for (AudioFile af : formats){
			if (af.getFormatId().equals(fileExt)){
				this.fileRef = af.getFileRef();
				break;
			}
		}
	}
	
	public void setLoops(List loops){
		if(loops != null && ! loops.isEmpty()){
			aTP = true;
		}
	}

	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
/*
 * 		this.name = filepattern.matcher(name).replaceAll("");
		if (this.name.length() > 31){
			this.name = this.name.substring(0 , 30);
		}
*/
	}


	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}


	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	/**
	 * @return the parentFileExt
	 */
	public String getParentFileExt() {
		return parentFileExt;
	}


	/**
	 * @param parentFileExt the parentFileExt to set
	 */
	public void setParentFileExt(String parentFileExt) {
		this.parentFileExt = parentFileExt;
	}


	/**
	 * @return the fileRef
	 */
	public String getFileRef() {
		return fileRef;
	}


	/**
	 * @param fileRef the fileRef to set
	 */
	public void setFileRef(String fileRef) {
		this.fileRef = fileRef;
	}


	/**
	 * @return the aTP
	 */
	public boolean isATP() {
		return aTP;
	}


	/**
	 * @param atp the aTP to set
	 */
	public void setATP(boolean atp) {
		aTP = atp;
	}


	/**
	 * @return the aChild
	 */
	public boolean isAChild() {
		return aChild;
	}


	/**
	 * @param child the aChild to set
	 */
	public void setAChild(boolean child) {
		aChild = child;
	}


	/**
	 * @return the fileExt
	 */
	public String getFileExt() {
		return fileExt;
	}


	/**
	 * @param fileExt the fileExt to set
	 */
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

}
