package com.beathive.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * A User adds a Store properties.
 * In effect his class extendes User and creates the basis for a store-front representation
 *  
 * @author devmo
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="store"
 */

public class Store extends BaseObject{

	/** "producer" */
	public static final String BASIC_PRODUCER_ROLE = "producer";
	/** "premiumproducer" */
	public static final String PREMIUM_PRODUCER_ROLE = "premiumproducer";
	
	/** the stores name */
	protected String name;
	
	/** the address we use to pay producers*/
	protected String paymentEmail;
	/** the path to the store banner */
	protected String bannerImg;
	/** short blurb describing the store */
	protected String description;
	/** the date the store was created */
	protected Date created = new Date();
	/** Last-modified date */
	protected Date modified = new Date();
	/** no User necessary */
//	protected User user;
//	protected String username;
	protected Long userId;
	// distinguishes basic/adv/prem -producers
	protected Integer level;
	protected Integer views = new Integer(0);
	protected List comments;

	protected Boolean agreement;
	
	

	// references the shops status
	protected Long statusId;
//	protected Status status;
	
	protected int numLoops;
	protected int numTrackpacks;
	protected Float average;
	private User user;
	
	private Long id;
	
	
	/** the producer name - looked with hib.formula */
	protected String producerName;
	private Set genres;
	/**
	 * hibernate.property formula="(select username from app_user user where user.id=userId)"
	 * @return the producerName
	 */
	public String getProducerName() {
		return producerName;
	}

	/**
	 * @param producerName the producerName to set
	 */
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	
	/**
	 * 
	 * @hibernate.id generator-class="native" unsaved-value="null"
     * hibernate.generator-param name="property" value="user"
	 * @return Returns the Id.
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public Store(User user){
		super();
		this.user = user;
	}
	/**
	 * @hibernate.many-to-one column="userId" outer-join="false" insert="false" update="false" cascade="none" lazy="proxy" constrained="true"
	 * @return
	 */
	public User getUser(){
		return user;
	}
	/**
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
		if (user!= null){
			this.setProducerName(user.getUsername());
		}
	}
	
	
	/**
     * @struts.validator type="required"
	 * @hibernate.property  
	 * @return Returns the userId.
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	// joined loops
	
//	protected List loops;
	// joined trackpacks
//	protected List trackpacks;
	// the top 3 most prevalent genres among the shop's loops
//	protected Set genres;
	
//	~ associations ========================	
	/**
     * @struts.validator type="required"
	 * @hibernate.property insert="true" update="false" type="yes_no"
	 * @return String the contracts.
	 */
	public Boolean getAgreement() {
		return agreement;
	}
	/**
	 * @param agreement The agreement to set.
	 */
	public void setAgreement(Boolean agreement) {
		this.agreement = agreement;
	}
    

	public Store(){
		super();
	}
	
	/**
     * struts.validator type="required" page="0"
	 * @hibernate.property
	 * @return Returns the level.
	 */
	public Integer getLevel() {
		return level;
	}
	
	/**
	 * @param level The levelId to set.
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/**
     * @struts.validator
	 * @hibernate.property
	 * @return Returns the bannerImg.
	 */
	public String getBannerImg() {
		return bannerImg;
	}
	/**
	 * @param bannerImg The bannerImg to set.
	 */
	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}
	/**
     * @struts.validator type="required"
	 * @hibernate.property type="text" length="2000"
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
     * @struts.validator type="required"
	 * @hibernate.property
	 * @return Returns the modified.
	 */
	public Date getModified() {
		return modified;
	}
	/**
	 * @param modified The modified to set.
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	/**
     * @struts.validator type="required"
	 * @hibernate.property not-null="true" unique="true"
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * hibernate.one-to-one constrained="true"
	 * hibernate.many-to-one column="username" insert="false" update="false" cascade="none" lazy="false"
	 * @return Returns the user.
	 */
//	public User getUser() {
//		return user;
//	}
	/**
	 * @param user The user to set.
	 */
//	public void setUser(User user) {
//		this.user = user;
//	}
	/**
     * struts.validator type="required" page="0"
	 * hibernate.id generator-class="foreign" 
	 * hibernate.generator-param name="property" value="user"
	 * hibernate.id generator-class="assigned"
	 * @return Returns the username.
	 */
//	public String getUsername() {
//		return username;
//	}
	/**
	 * param username The username to set.
	 */
//	public void setUsername(String username) {
//		this.username = username;
//	}
	/**
	 * @hibernate.property update="false" not-null="true" type="date"
	 * @return Returns the created.
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created The created to set.
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * hibernate.bag name="loops" inverse="false" lazy="false" fetch="join" cascade="delete"
	 * where="trackpack_id is null and type='Loop' and searchable='T'"
	 * hibernate.collection-key column="username"
	 * hibernate.collection-one-to-many class="com.beathive.model.Loop"
	 * @return Returns the loops.
	 */
/*	public List getLoops() {
		return loops;
	}
*/	/**
	 * @param loops The loops to set.
	 */
/*	public void setLoops(List loops) {
		this.loops = loops;
	}
*/	/**
	 * @hibernate.property
	 * @return Returns the statusId.
	 */
	public Long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	/**
     * hibernate.bag inverse="false" lazy="false" fetch="join" cascade="delete"
     * 	 type='Trackpack' and searchable='T'" embed-xml="false"
     * 	order-by="average desc"
     * hibernate.collection-key column="username"
     * hibernate.collection-one-to-many class="com.beathive.model.Trackpack"
	 * @return Returns the trackpacks.
	 */
/*	public List getTrackpacks() {
		return trackpacks;
	}
*/	/**
	 * @param trackpacks The trackpacks to set.
	 */
/*	public void setTrackpacks(List trackpacks) {
		this.trackpacks = trackpacks;
	}
	*//**
	 * @return Returns the genres.
	 *//*
	public Set getGenres() {
		return genres;
	}
	*//**
	 * @param genres The genres to set.
	 *//*
	public void setGenres(Set genres) {
		this.genres = genres;
	}
*/

//	/**
//	 * #hibernate.set table="clip_genre" inverse="false" lazy="false" cascade="none" fetch="select"
//	 * #hibernate.key formula="clipId"
//     * #hibernate.one-to-many class="com.beathive.model.Genre" column="genreId" order-by="sortorder asc"
//	 */
//	public Set getGenres() {
//		return genres;
//	}
//	/**
//	 * #param genres The genres to set.
//	 */
//	public void setGenres(Set genres) {
//		this.genres = genres;
//	}
//	
	/**
	 * hibernate.many-to-one column="statusId" insert="false" update="false" cascade="none" lazy="false"
	 * @return Returns the status.
	 */
//	public Status getStatus() {
//		return status;
//	}

	/**
	 * @param status The status to set.
	 */
//	public void setStatus(Status status) {
//		this.status = status;
//	}
	
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;

        final Store store = (Store) o;

        if (name != null ? !name.equals(store.name) : store.name != null) return false;

        return true;
    }

    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", this.name)
//                .append("username",this.username)
                        .toString();
    }
	/**
	 *  and s.trackpackId is NULL
	 * @hibernate.property formula="(select count(s.id) from soundclip s where s.storeId=id and s.searchable='Y' and s.type='Loop' and s.ready='Y' and (s.statusId is NULL or s.statusId=2) and  IF(s.trackpackId is not NULL , (select g.ready='Y' from soundclip g where g.id=s.trackpackId) ,0))"
	 * @return Returns the numLoops.
	 */
	public int getNumLoops() {
		return numLoops;
	}
	/**
	 * @param numLoops The numLoops to set.
	 */
	public void setNumLoops(int numLoops) {
		this.numLoops = numLoops;
	}
	/**
	 * @hibernate.property formula="(select count(t.id) from soundclip t where t.storeId=id and t.type='Trackpack' and t.ready='Y' and t.statusId is NULL)"
	 * @return Returns the numTrackpacks.
	 */
	public int getNumTrackpacks() {
		return numTrackpacks;
	}
	/**
	 * @param numTrackpacks The numTrackpacks to set.
	 */
	public void setNumTrackpacks(int numTrackpacks) {
		this.numTrackpacks = numTrackpacks;
	}

	
    /**
     * Returns the store's comment. 
     * @return Set
     * @hibernate.bag table="store_comment" cascade="delete" lazy="true"
     * @hibernate.collection-key column="storeId"
     * @hibernate.collection-many-to-many class="com.beathive.model.Comment" unique="true" column="commentId" order-by="id desc"
     */
	public List getComments() {
		return comments;
	}
	public void setComments(List comments) {
		this.comments = comments;
	}
	public void addComment(Comment c) {
		if (this.comments == null){
			comments = new LinkedList();
		}
		comments.add(c);
	}
	/**
	 * @hibernate.property formula="(select avg(cs.score) from user_clip_score cs where cs.storeId=id)"
	 * @return Returns the store average.
	 */
	public Float getAverage() {
		return average;
	}
	public void setAverage(Float average) {
		this.average = average;
	}

	
	/**
     * @struts.validator type="required"
	 * @hibernate.property
	 */
	public String getPaymentEmail() {
		return paymentEmail;
	}
	public void setPaymentEmail(String paymentEmail) {
		this.paymentEmail = paymentEmail;
	}

	/**
	 * Views are updated from a user's session thus we need only read this value
	 * @hibernate.property update="false" default="0"
	 * #not-null="true"
	 * @return
	 */
	public Integer getViews() {
		
/*		if (this.views == null){
			this.views = new Integer(0);
		}
*/		return this.views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
}
