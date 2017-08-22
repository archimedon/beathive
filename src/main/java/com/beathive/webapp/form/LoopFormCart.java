package com.beathive.webapp.form;
import java.util.Iterator;


/**
 * @struts.form name="loopFormCart" 
 */
public class LoopFormCart extends LoopForm implements SoundClipFormCart , java.io.Serializable {

	private String fileId;

	public AudioFileForm getAudioFile(){
		AudioFileForm af = null;
		Iterator it = super.getAudioFormat().iterator(); 
		while (it.hasNext()){
			af = (AudioFileForm)it.next();
			if (af.getId().equals(this.fileId)){
				return af;
			}
		}
		return null;
	}
	
	/**
	 * @return the formatId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param formatId the formatId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
    public boolean equals(Object o ){
    	if (o != null && o instanceof LoopFormCart){
    		return this.id.equals(((LoopFormCart)o).getId());
    	}
    	return false; 
    }

    public int hashCode() {
        return new org.apache.commons.lang.builder.HashCodeBuilder()
        .append(this.id).toHashCode();
    }
    public String toString() {
    	return this.id +"_" + this.fileId;
    }
    public String type;
    public String getType() {
    	return this.type;
    }
    public void setType(String clazz) {
    	this.type = clazz;
    }
}

