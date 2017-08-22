package com.beathive.model;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
/**
 * SoundClip. the base class of both Track packs and Loops. 
 * 
 * <p>
 * <a href="SoundClip.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * 
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison </a>
 * @version $Revision: 2.0 $ $Date: 2008/06 $
 * @struts.form include-all="true" extends="BaseForm"
 * #hibernate.class table="soundclip"
 * #hibernate.discriminator column="type"
 * hibernate.cache usage="read-write"
 * hibernate.query name="GetUserClips" query="from SoundClip clip where clip.username=?"
 * hibernate.query name="getViewerScore" query="from UserClipScore ucs where ucs.userId=:viewerId and ucs.clipId=id"
 */
public class UploadClip extends HashMap{
	private static final long serialVersionUID = -5726222325927704343L;
	//~ Instance fields
	// ========================================================

	public static final String TRACKPACK_TYPE = "Trackpack";
	public static final String LOOP_TYPE = "Loop";
	
	// ~ Constuctor	
	public UploadClip( String name , String type , String storeId){
		super();
//		put("created" , date);
//		put("modified" , date);
		put("searchable" , Boolean.FALSE);
		put("storeId" , storeId);
		put("type" , type);
		if(type.equals(this.TRACKPACK_TYPE)){
			put("loops" , new LinkedList());
		}
		put("name" , name);
//		put("modified" , date);
//		put("descriptors" , new HashMap());
//		put("genre" , new LinkedList());
		put("audioFormat" , new LinkedHashSet());
	}

	
	//  ~ Methods ===========================================================
	/**
	 * @param object
	 */
	public void addAudioFile(Object audioFile) {
		((Set)get("audioFormat")).add(audioFile);
	}

	public void addLoop(Object clip) {
		((List)get("loops")).add(clip);
	}
	

	
	
	
    public boolean equals(Object object) {
        if (!(object instanceof Map)) {
            return false;
        }

        Map rhs = (Map) object;

        return new EqualsBuilder()
        .append(get("name"), rhs.get("name"))
		.append(get("storeId"), rhs.get("storeId"))
		.append(get("type") , rhs.get("type"))
		.append(get("audioFormat"), get("audioFormat"))
		.isEquals();
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public int hashCode() {
        return new HashCodeBuilder()
        .append(get("name"))
		.append(get("storeId"))
//		.append(this.type)
		.append(get("type"))
		.append(get("audioFormat"))
		.toHashCode();
    }
    
    
/*    
    public String toString(){
    	
    	return stringBuilder().toString();
    }
     
    public ToStringBuilder stringBuilder(){
    	
    	return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
    	.append("id", this.getId())
    	.append("name", this.getName())
    	.append("created", this.getCreated())
    	.append("modified", this.getModified())
    	.append("price", this.price)
    	.append("storeId", this.storeId)
    	.append("audioFormat", this.audioFormat)
    	.append("genre", this.genre)
    	.append("searchable", this.searchable)
    	.append("avgScore", this.avgScore)
    	.append("timesrated", this.timesrated)
    	.append("viewerScore", this.viewerScore)
    	.append("ownedByViewer", this.ownedByViewer)
    	.append("aFavorite", this.aFavorite)
    	.append("descriptors", this.descriptors)
  //  	.append("getScales", this.getScale() +"")
 //   	.append("bpm", this.getBpm() +"")
//    	.append("energy", this.getEnergy())
    	;
    }
*/    

}