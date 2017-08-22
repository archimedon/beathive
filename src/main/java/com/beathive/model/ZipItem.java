package com.beathive.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * An item in the a userZip
 * @author rdennison
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="zipitem"
 * hibernate.discriminator column="type"
 */
public class ZipItem extends BaseObject{
	private static final long serialVersionUID = 7847117674218996030L;
	private Long id;
	private Long zipId;
//	private Long parentId;
	private Long clipFormatId;
	// a reference to the eternal-static record of producer identity info at time of purchase
	private Long producerIdentityId;
	private Long purchaseId;
	private Long purchaseItemId;
	private String shopname;
	private String producerName;
	private String clipName;
//	private Price price;
	private String type;
//	private List componentLoops;
	/**
     * @hibernate.property
	 * @return Returns the zipId.
	 */
	public Long getZipId() {
		return zipId;
	}
	/**
	 * @param zipId The zipId to set.
	 */
	public void setZipId(Long zipId) {
		this.zipId = zipId;
	}
	/**
     * @hibernate.property
	 * @return Returns the clipFormatId.
	 */
	public Long getClipFormatId() {
		return clipFormatId;
	}
	/**
	 * @param clipFormatId The clipFormatId to set.
	 */
	public void setClipFormatId(Long clipFormatId) {
		this.clipFormatId = clipFormatId;
	}
	/**
     * @hibernate.property
	 * @return Returns the clipName.
	 */
	public String getClipName() {
		return clipName;
	}
	/**
	 * @param clipName The clipName to set.
	 */
	public void setClipName(String clipName) {
		this.clipName = clipName;
	}
	/**
     * @hibernate.id generator-class="native" unsaved-value="null"
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
/*	*//**
     * @hibernate.property
	 * @return Returns the price.
	 *//*
	public Price getPrice() {
		return price;
	}
	*//**
	 * @param price The price to set.
	 *//*
	public void setPrice(Price price) {
		this.price = price;
	}
*/	/**
     * @hibernate.property
	 * @return Returns the producerIdentityId.
	 */
	public Long getProducerIdentityId() {
		return producerIdentityId;
	}
	/**
	 * @param producerIdentityId The producerIdentityId to set.
	 */
	public void setProducerIdentityId(Long producerIdentityId) {
		this.producerIdentityId = producerIdentityId;
	}
	/**
     * @hibernate.property
	 * @return Returns the producerName.
	 */
	public String getProducerName() {
		return producerName;
	}
	/**
	 * @param producerName The producerName to set.
	 */
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}
	/**
     * @hibernate.property
	 * @return Returns the shopname.
	 */
	public String getShopname() {
		return shopname;
	}
	/**
	 * @param shopname The shopname to set.
	 */
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
/*	*//**
     * hibernate.property
	 * @return Returns the parentId.
	 *//*
	public Long getParentId() {
		return parentId;
	}
	*//**
	 * @param parentId The parentId to set.
	 *//*
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	*//**
	 * hibernate.bag name="componentLoops" inverse="false" lazy="false" outer-join="true" cascade="none" where="parentId is not null"
	 * hibernate.collection-key column="parentId"
	 * hibernate.collection-one-to-many class="com.beathive.model.ZipItem"
	 * return Returns the componentLoops.
	 *//*
	public List getComponentLoops() {
		return componentLoops;
	}
	*//**
	 * @param componentLoops The componentLoops to set.
	 *//*
	public void setComponentLoops(List componentLoops) {
		this.componentLoops = componentLoops;
	}
*/
	/**
	 * @return Returns the type.
	 * @hibernate.property
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	/**
     * @hibernate.property
	 */
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long orderId) {
		this.purchaseId = purchaseId;
	}
	/**
     * @hibernate.property
	 */
	public Long getPurchaseItemId() {
		return purchaseItemId;
	}
	public void setPurchaseItemId(Long orderItemId) {
		this.purchaseItemId = orderItemId;
	}
    public String toString() {
    	return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
	
}
