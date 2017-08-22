package com.beathive.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.beathive.Constants;
/**
 * SoundClip. the base class of both Track packs and Loops. 
 * 
 * 
 * <p>
 * <a href="SoundClip.java.html"> <i>View Source </i> </a>
 * </p>
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
public class SoundClip extends BaseObject{
	//~ Instance fields
	// ========================================================

	private static final long serialVersionUID = -4928338030868122786L;
	public static String TRACKPACK_TYPE = "Trackpack";
	public static String LOOP_TYPE = "Loop";
	protected Date modified = new Date();
	protected Date created = null;
	protected Long id;
//	protected String type;
	protected String keyword;
	/** name is the name of the file this Object describes. */
	protected String name;
	/** At least 1 Genre is required */
	protected List<Genre> genre = new LinkedList<Genre>();
	
	/** statusId has three values:
	 * 1 = Clip was sold but later deleted by owner
	 * 2 = When a component loop's parent is not ready
	 * null = Clip is in good standing
	 */
	protected Integer statusId;
	protected Price price = new Price();
	/** BPM is required at upload **/
//	protected Integer bpm;
	/** avgScore is calculated on hte fly */
	protected Float avgScore;
	protected Integer timesrated;
	
//	protected Score score;
	/** the clip format available */
	protected Set<AudioFile> audioFormat = new LinkedHashSet<AudioFile>();
	/** determines whether or not found in a search*/
	/** used to determine whether the loop is ready*/
//	protected int numFormats = 0;
	
	protected boolean ready = false;
//	protected User producer;
	/** a producer is required */
	protected Long storeId;
	
	/** storeName looked up with storeId */
	protected String storeName;
	
	protected Map descriptors = new HashMap();
//	protected Set comments;
//	protected Integer numComments;

	// ~ Constructors	
	public SoundClip(){
		super();
	}
	
	public SoundClip(String name){
		this();
		setName(name);
	}
	
	
	//  ~ Methods ===========================================================
	
	
	
	/**
	 * @return Returns the producer.
	 * hibernate.many-to-one
     *  insert="false" update="false" cascade="none" column="userId" lazy="proxy"
	 */
//	public User getUser() {
//		return producer;
//	}
//	/**
//	 * @param producer The producer to set.
//	 */
//	public void setUser(User producer) {
//		this.producer = producer;
//	}

	

	/**
	 * Returns the id.  generator-class="native"
	 * @return Long
	 * #hibernate.id column="id" generator-class="native" unsaved-value="null"
	 * hibernate.generator-param name="class" value="native"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 * The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the producer ID.
	 * @struts.validator type="required"
	 * #hibernate.property not-null="true"
	 */
	public Long getStoreId() {
		return storeId;
	}
	
	/**
	 * @param producer
	 * The storeId to set.
	 */
	public void setStoreId(Long shopId) {
		this.storeId = shopId;
	}

	/**
	 * @return Returns the name.
	 * @struts.validator type="required"
	 * #hibernate.property not-null="true" unique="false"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the modified.
	 * #hibernate.property not-null="true" type="timestamp"
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * @param modified
	 *            The creation date to set.
	 */
	public void setModified(Date lastmodified) {
		this.modified = lastmodified;
	}

	/**
	 * @return Returns the created.
	 * #hibernate.property not-null="true" update="false" type="timestamp"
	 */
	public Date getCreated() {
		return created;
	}

	
	/**
	 * @param created
	 * The creation date to set.
	 */
	public void setCreated(Date pubdate) {
		this.created = pubdate;
	}

	/** statusId has three values:
	 * 1 = Clip was sold but later deleted by owner
	 * 2 = When a component loop's parent is not ready
	 * null = Clip is in good standing

	 * @return Returns the clipStatusId.
	 * @struts.validator type="required"
	 * #hibernate.property
	 */
	public Integer getStatusId() {
		return statusId;
	}
	
	/** statusId has three values:
	 * 1 = Clip was sold but later deleted by owner
	 * 2 = When a component loop's parent is not ready
	 * null = Clip is in good standing
	 * @param clipStatusId The clipStatusId to set.
	 */
	public void setStatusId(Integer clipStatusId) {
		this.statusId = clipStatusId;
	}

	/**	
	 * Returns the clip's genre.
	 * 
     * #hibernate.list  table="clip_genre" cascade="none" lazy="true"
     * #hibernate.key column="id"
     * #hibernate.list-index column="genreOrder"
     * #hibernate.many-to-many class="com.beathive.model.Genre" unique="true" column="genre_id"  outer-join="auto"
     * 
	 */
	public List<Genre> getGenre() {
		return genre;
	}

	public void setGenre(List<Genre> genre) {
		this.genre = genre;
	}

	


	/** 
	 * NOTES: make inverse=false to simplyfy and automate format setting
	 * set cascade="none" and hand off deletion of audio files
	 * OR cascade all and intercept to delete associated file.
	 *  
	 * #struts.validator type="required"
	 * 
	 * #hibernate.set table="clip_audiofile" inverse="false" lazy="false" cascade="all" fetch="select"
	 * #hibernate.key column="clip_id"
     * #hibernate.many-to-many class="com.beathive.model.AudioFile" column="fileId" unique="true" order-by="formatId asc"
	 * @return Returns the format.
	 */
	public Set<AudioFile> getAudioFormat() {
		return audioFormat;
	}
	/**
	 * @param format The format to set.
	 */
	public void setAudioFormat(Set format) {
		this.audioFormat = format;
	}

	/**
     * Returns the Price
     * #hibernate.component
	 */
	public Price getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(Price price) {
		this.price = price;
	}
	/**
	 * @param type The type to set.
	 */
//	public void setType(String type) {
//		this.type = type;
//	}

	/**
	 * @return Returns the searchable.
	 * #hibernate.property type="yes_no"
	 */

	/**
	 * @return Returns the keyword.
	 * #hibernate.property type="text"
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
    public boolean equals(Object object) {
        if (!(object instanceof SoundClip)) {
            return false;
        }

        SoundClip rhs = (SoundClip) object;

        return new EqualsBuilder()
        .append(this.name, rhs.name)
		.append(this.storeId, rhs.storeId)
//		.append(this.type , rhs.type)
		.append(this.getBpm(), rhs.getBpm())
//		.append(this.created, rhs.created)
		.isEquals();
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public int hashCode() {
        return new HashCodeBuilder()
        .append(this.name)
		.append(this.storeId)
//		.append(this.type)
		.append(this.getBpm())
		.append(this.created)
		.toHashCode();
    }
    
    public String toString(){
    	
    	return stringBuilder().toString();
    }
     
    public ToStringBuilder stringBuilder(){
    	
    	return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
    	.append("id", this.id)
    	.append("name", this.name)
    	.append("created", this.created)
    	.append("modified", this.modified)
    	.append("price", this.price)
    	.append("storeId", this.storeId)
    	.append("audioFormat", this.audioFormat)
    	.append("genre", this.genre)
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
    
	/**
	 * @return Returns the ready.
	 */
//	public boolean getReady() {
//		return numFormats > 0;
//	}

	/**
	 * @return Returns the numFormats.
	 * hibernate.property formula="(select count(cf.clip_id) from clip_format cf where cf.clip_id=id)"
	 */
	public int getNumFormats() {
		return audioFormat.size();
	}

	/**
	 * @return Returns the numFormats.
	 * #hibernate.property insert="false" update="false" formula="(select avg(cr.score) from user_clip_score cr where cr.clipId=id)" lazy="false"
	 */
	public Float getScore() {
		return avgScore;
	}
	public void setScore(Float average) {
		this.avgScore = average;
	}
	/**
	 * @return Returns the numFormats.
	 * #hibernate.property insert="false" update="false" formula="(select count(cr.clipId) from user_clip_score cr where cr.clipId=id)" lazy="false"
	 */
	public Integer getTimesrated() {
		return timesrated;
	}
	protected void setTimesrated(Integer timesrated) {
		this.timesrated = timesrated;
	}
	
/*
 *****************************************************
 * All related to DESCRIPTORS. I would like to move to a join table and collect using a map-key instead
 *****************************************************
 */	
	/**
	 * @return Returns the numerical BPM.
     * @struts.validator type="required"
     * #hibernate.property column="bpm"
     */
	public Integer getBpm() {
		Integer intg = null;
		//return bpm;
		if (descriptors.get("bpm") != null && StringUtils.isNumeric(descriptors.get("bpm").toString())){
			intg = new Integer(descriptors.get("bpm").toString());
		}
		return intg;
	}
	/**
	 * @param The BPM to set.
	 */
	public void setBpm(Integer bpm) {
		descriptors.put("bpm" , bpm);
//		this.bpm = bpm;
	}
	/**
	 * @return Returns the keynote.
	 * #hibernate.property column="keynote"
	 */
	public String getKeynote() {
		return (String)descriptors.get("keynote");
	}

	/**
	 * @param keynote
	 *            The keynote to set.
	 */
	public void setKeynote(String keynote) {
		descriptors.put("keynote" , keynote);
	}

	/**
	 * @return Returns the scale.
	 * #hibernate.property column="scale"
	 */
	public String getScale() {
		return (String)descriptors.get("scale");
	}

	/**
	 * @param scale
	 *            The scale to set.
	 */
	public void setScale(String scale) {
		descriptors.put("scale" , scale);
	}

	/**
	 * @return Returns the timesignature.
	 * #hibernate.property column="timesignature"
	 */
	public String getTimesignature() {
		return (String)descriptors.get("timesignature");
	}

	/**
	 * @param timesignature
	 *            The timesignature to set.
	 */
	public void setTimesignature(String timesignature) {
		descriptors.put("timesignature" , timesignature);
	}

	/**
	 * @return Returns the passage.
	 * #hibernate.property column="passage"
	 */
	public String getPassage() {
		return (String)descriptors.get("passage");
		//		return passage;
	}

	/**
	 * @param passage
	 *            The passage to set.
	 */
	public void setPassage(String passage) {
		descriptors.put("passage" , passage);
	}

	/**
	 * @return Returns the energy.
	 * #hibernate.property column="energy"
	 */
	public String getEnergy() {
		return (String)descriptors.get("energy");
//		return energy;
	}

	/**
	 * @param energy
	 *            The energy to set.
	 */
	public void setEnergy(String energy) {
		descriptors.put("energy" , energy);
	}

	/**
	 * @return Returns the feel.
	 * #hibernate.property column="feel"
	 */
	public String getFeel() {
		return (String)descriptors.get("feel");
	}

	/**
	 * @param feel
	 *            The feel to set.
	 */
	public void setFeel(String feel) {
		descriptors.put("feel" , feel);
	}

	/**
	 * @return Returns the mood.
	 * #hibernate.property column="mood"
	 */
	public String getMood() {
		return (String)descriptors.get("mood");
	}

	/**
	 * @param mood
	 *            The mood to set.
	 */
	public void setMood(String mood) {
		descriptors.put("mood" , mood);
	}

	/**
	 * @return Returns the origin.
	 * #hibernate.property column="origin"
	 */
	public String getOrigin() {
		return (String)descriptors.get("origin");
	}

	/**
	 * @param origin
	 *            The origin to set.
	 */
	public void setOrigin(String origin) {
		descriptors.put("origin" , origin);
	}

	/**
	 * @return Returns the sonority.
	 * #hibernate.property column="sonority"
	 */
	public String getSonority() {
		return (String)descriptors.get("sonority");
	}

	/**
	 * @param sonority
	 *            The sonority to set.
	 */
	public void setSonority(String sonority) {
		descriptors.put("sonority" , sonority);
	}

	/**
	 * @return Returns the texture.
	 * #hibernate.property column="texture"
	 */
	public String getTexture() {
		return (String)descriptors.get("texture");
//		return texture;
	}

	/**
	 * @param texture
	 *            The texture to set.
	 */
	public void setTexture(String texture) {
		descriptors.put("texture" , texture);
	}
/*
 **************   END DESCRIPTORS *****************************************
 */		
	
	/**
	 * A section just to join user-activity related info from assoc tables at query time.
	 */
	private transient Set userClipScore = new HashSet();
	private transient Set userFavorites = new LinkedHashSet();
	private transient Set userItems = new HashSet();
//	private transient Set userCartItems = new HashSet();
	
	protected Boolean ownedByViewer = Boolean.FALSE;
	protected boolean aFavorite = false;
	private Integer viewerScore = new Integer(0);
	private Integer timesSold;
	
    /**
     * Sets the ratings.
     * @param roles The roles to set
     */
	protected void setUserClipScore(Set ratings) {
		this.userClipScore = ratings;
		if(ratings!=null && userClipScore.size() > 0){
			this.viewerScore = (Integer)userClipScore.iterator().next();
		}
    }

    /**
     * Needed primarily for creating 'user_cliprating' SoundClip will search this table to calculate avg and timesrated
     * Returns the user's ratings.
     * @return Set
     * #hibernate.set table="user_clip_score" lazy="true" cascade="all"
     * #hibernate.collection-key column="clipId"
     * #hibernate.collection-one-to-many class="com.beathive.model.UserClipScore"
     * #hibernate.filter name="thisUsersScore" condition="userId=:viewerId"
     */ 
    protected Set getUserClipScore() {
	    	return userClipScore;
    }
	
	public Integer getViewerScore(){
		return viewerScore; 
	}
	
	public void setViewerScore(Integer is){
		viewerScore = is; 
	}
	
	/**
     * #hibernate.set table="purchase_item" lazy="true" cascade="all"
     * #hibernate.collection-key column="clipId"
     * #hibernate.collection-one-to-many class="com.beathive.model.PurchaseItem"
     * #hibernate.filter name="thisUsersOwns" condition="customerId=:viewerId"          
     *                          
	 * @return Returns the orders.
	 */
	public Set getUserItems() {
		return userItems;
	}
	/**
	 * Does not store the items but instead sets ownedByViewer 
	 * @param orders The orders to set.
	 */
	protected void setUserItems(Set items) {
		this.userItems = items;
		ownedByViewer = new Boolean(!userItems.isEmpty());
	}

	public void setTimesSold(Integer timesSold){
		this.timesSold = timesSold;

	}
	
	/**
	 * A method to return whether the viewer owns this loop.
	 * @return
	 */
	public Boolean getOwnedByViewer(){
		
		return ownedByViewer;
	}
	public void setOwnedByViewer(Boolean v){
		ownedByViewer = v;
	}

	/**
     * #hibernate.set name="userFavorites" table="user_favorite" lazy="true" cascade="none"
     * #hibernate.key column="clipId"
     * #hibernate.collection-many-to-many class="com.beathive.model.SoundClip" column="clipId"
     * #hibernate.filter name="thisUsersFavorite" condition="userId=:viewerId"          

     * #hibernate.bag name="userFavorites" table="favorite" lazy="true" cascade="none"
     * #hibernate.key column="clipId"
     * #hibernate.collection-one-to-many class="com.beathive.model.Favorite"
     * #hibernate.filter name="thisUsersFavorite" condition="userId=:viewerId"          
     *                          
	 */
	protected Set getUserFavorites() {
		return userFavorites;
	}

	protected void setUserFavorites(Set userFavorites) {
		this.userFavorites = userFavorites;
		this.aFavorite = !this.userFavorites.isEmpty();
	}

	public boolean isAFavorite() {
		return aFavorite;
	}

	public void setAFavorite(boolean favorite) {
		this.aFavorite = favorite;
	}

	
	/**
	 * #hibernate.set name="userCartItems" table="user_cartitem" lazy="true" cascade="none"
	 * #hibernate.key column="clipId"
	 * #hibernate.one-to-many class="com.beathive.model.CartItem"
	 * #hibernate.filter name="thisUsersCartItem" condition="userId=:viewerId"
	 */                  
//	protected Set getUserCartItems() {
//		return userCartItems;
//	}
//	
//	protected void setUserCartItems(Set userCartItems) {
//		this.userCartItems = userCartItems;
//	}
	
	/**
	 * Determined by the DB
	 * @return
	 */
//	public boolean isInCart() {
//		return userCartItems != null && !this.userCartItems.isEmpty();
//	}
	
	/**
	 * does nothing incart is derived
	 * @param stub
	 */
//	public void setInCart(boolean stub) {
//		aCartItem = favorite;
//	}

	/**
	 * @return the descriptors
	 */
	public Map getDescriptors() {
		return descriptors;
	}

	/**
	 * @param descriptors the descriptors to set
	 * 
	 */
	protected void setDescriptors(Map descriptors) {
		this.descriptors = descriptors;
	}

	/**
	 * @param object
	 */
	public void addGenre(Genre genreObject) {
		genre.add(genreObject);
	}
	
	/**
	 * @param object
	 */
	public void addAudioFile(AudioFile audioFile) {
		audioFormat.add(audioFile);
	}

	/**
	 * @return the ready
	 * #hibernate.property
	 */
	public boolean isReady(){
		return (
			( genre!=null && ! genre.isEmpty() ) 
			&& ( audioFormat!=null && ! audioFormat.isEmpty() )
//			&& ( getBpm()!=null && ! getBpm().equals("") )
			&& ( price != null && price.getAmount() != null && ! ( price.getAmount().doubleValue() < .50))
		);
	}

	/**
	 * @param ready the ready to set
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	/**
	 * hibernate.property formula="select s.name from store s where s.id=storeId"
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	/**
	 * hibernate.property formula="( select count(p.id) from purchase_item p where p.clipId=id )"
	 * @return the timesSold
	 */
	public Integer getTimesSold() {
		return timesSold;
	}

	/**
	 * @return the numComments
	 */
//	public Integer getNumComments() {
//		return numComments;
//	}

	/**
	 * @param numComments the numComments to set
	 */
//	public void setNumComments(Integer numComments) {
//		this.numComments = numComments;
//	}

}