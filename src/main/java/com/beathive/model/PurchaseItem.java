package com.beathive.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author rdennison
 * @hibernate.class table="purchase_item"
 * @struts.form include-all="true" extends="BaseForm"
 */
public class PurchaseItem extends BaseObject implements Serializable{

	private Long id;
	// True if this item has been paid
	private Boolean paid = Boolean.FALSE;
	private Long purchaseId;
	private Purchase purchase;

	private Long fileId;			// preset
	// optional used for setting up a returning customers screen
	private Long customerId;

	// a reference to store used for making payouts, nothing to do with customers
	private Long storeId;
	private String storeName;		// stored for historical ref; in case the shop name changes
	private String samplePath;			// set path to the download directory path 
	private String fileRef;			// set path to the download directory path 
	private String instrument;		// used for renaming file
	private String genre;			// used for renaming file
//	private String producerName;	// stored for historical ref
	private Long clipId;			// reference to the the original soundclip

	private String type;			// if a collective item like a trackpack
	private String parentName;

	private String formatName;		// might not need 
	private String clipName;		// used for renaming file

	private Double price;			// TODO consider calculating deducting dscounts and fees
									// before saving vs. the current implementation where  
									// the price is a straight copy

	private Integer numLoops;			// if it is a Trackpack 
//	private User customer;

	/** the 3 below were added to facilitate 
	 * sales reporting. The fields are not persisted but derived by hib.formula */
	private Double trxnFee;
	private Double net;
	private Number groupedNum;

	public boolean isLoop(){
		return this.type.equalsIgnoreCase(SoundClip.LOOP_TYPE);
	}
//	public void setAudioFormat(java.util.Set formats){
//		if (formats!=null && ! formats.isEmpty()){
//			Iterator it = formats.iterator(); 
//			while (it.hasNext()){
//				AudioFileForm af = (AudioFileForm)it.next();
//				if (af.getId().equals(this.fileId)){
//					this.fileRef = af.getFileRef();
//					this.clipId = new Long (af.getClipId());
//					this.storeId = new Long(af.getStoreId());
//					break;
//				}
//			}
//		}
//	}

	/**
     * @hibernate.property not-null="true" length="10" 
	 * @return Returns the purchaseId.
	 */
	public Long getPurchaseId() {
		return purchaseId;
	}
	/**
	 * @param purchaseId The purchaseId to set.
	 */
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	/**
	 * 
	 * @return Returns the trackpack.
	 * @hibernate.many-to-one column="purchaseId" insert="false" update="false" cascade="none" lazy="false"
	 */
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase o){
		this.purchase = o;
	}

	/**
     * @hibernate.property not-null="true" length="10"
	 * @return Returns the clipFormatId.
	 */
    public Long getFileId()
    {
        return this.fileId;
    }
   /**
    */

    public void setFileId( Long clipFormatId )
    {
        this.fileId = clipFormatId;
    }

	/**
     * @hibernate.property not-null="true"
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
     * @hibernate.id generator-class="native" unsaved-value="null" type="long" 
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
	/**
     * @hibernate.property
	 * @return Returns the instrument.
	 */
	public String getInstrument() {
		return instrument;
	}
	/**
	 * @param instrument The instrument to set.
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	/**
     * @hibernate.property not-null="true"
	 * @return Returns the price.
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
     * @hibernate.property not-null="true"
	 * @return Returns the shopname.
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * @param shopname The shopname to set.
	 */
	public void setStoreName(String shopname) {
		this.storeName = shopname;
	}
	/**
     * @hibernate.property
	 * @return Returns the type.
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
	
	public String toString(){
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("customerId", this.customerId)
        .append("clipName", this.clipName)
        .append("parentName", this.parentName)
        .append("clipFormatId", this.fileId)
        .append("storeName", this.storeName)
        .append("storeId", this.storeId)
        .append("type", this.type)
        .append("purchaseId", this.purchaseId)
        .append("id", this.id)
        .toString();
	}
	/**
     * @hibernate.property
	 * @return Returns the genre.
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * @param genre The genre to set.
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * @hibernate.property not-null="true"
	 * @return Returns the formatName.
	 */
	public String getFormatName() {
		return formatName;
	}
	/**
	 * @param formatName The formatName to set.
	 */
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	/**
	 * @hibernate.property
	 * @return
	 */
	public String getParentName() {
		return this.parentName;
	}
	/**
	 * @param parentName The parentName to set.
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
    public boolean equals(Object o) {
    	PurchaseItem rhs = (PurchaseItem)o;
        return new EqualsBuilder()
        .append(this.fileId, rhs.getFileId())
        .append(this.purchaseId, rhs.getPurchaseId())
        .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-2003579277, 1452046551)
        .append(this.purchaseId)
        .append(this.fileId)
        .toHashCode();
    }

	/**
	 * @hibernate.property not-null="true" type="long"
	 * @return
	 */
    public Long getClipId() {
		return clipId;
	}
    
	public void setClipId(Long clipId) {
		this.clipId = clipId;
	}

//	/**
//	 * 
//	 * @return Returns the customer.
//	 * hibernate.many-to-one column="customerId" insert="false" update="false" cascade="none" lazy="proxy"
//	 */
//	public User getCustomer(){
//		return customer;
//	}
//	public void setCustomer(User customer) {
//		this.customer = customer;
//	}

	/**
	 * @hibernate.property
	 * @return
	 */	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	/**
	 * hibernate.property not-null="true"
	 * @return
	 */	
//	public String getCustomerName() {
//		return customerName;
//	}
//	public void setCustomerName(String customerName) {
//		this.customerName = customerName;
//	}
	/**
	 * @hibernate.property not-null="true"
	 * @param fileRef a path or reference to the file
	 * @return String
	 */	
	public String getFileRef() {
		return fileRef;
	}
	/**
	 * @param fileRef the user download path or reference to the file
	 */
	public void setFileRef(String fileRef) {
		this.fileRef = fileRef;
	}

	/**
	 * @hibernate.property not-null="true"
	 * @return the storeId
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
	/**
	 * @hibernate.property
	 * @return the numLoops
	 */
	public Integer getNumLoops() {
		return numLoops;
	}
	/**
	 * @param numLoops the numLoops to set
	 */
	public void setNumLoops(Integer numLoops) {
		this.numLoops = numLoops;
	}

	
	
	// the 3 below were added to facilitate 
	//  sales reporting. The fields are not persisted but derived by hib.formula 

	
	/**
	 * @hibernate.property insert="false" update="false" formula="(select ( pi.price/p.subTotal * ((p.rateFee * p.total) + p.flatFee) / 2 ) from purchase_item pi join purchase p on p.id=pi.purchaseId where p.authCode &lt;&gt; 'PREAUTH' and pi.id=id)"  lazy="false"
	 * @return the numLoops
	 */
	public Double getTrxnFee(){
		return trxnFee;

		//		return (price.doubleValue() / purchase.getSubTotal().doubleValue()) * purchase.getTrxnFee();
//		// get the portion that this loop contributed to the overall cost (before discount)
//		double ratio = price.doubleValue() / purchase.getSubTotal().doubleValue();
//		// determine proportion recovered after trxn costs
//		return ratio * purchase.getDeducted();
	}

	/**
	 * @hibernate.property insert="false" update="false" formula="(select ( pi.price/p.subTotal * (p.total - ((p.rateFee * p.total) + p.flatFee)) /2 ) from purchase_item pi join purchase p on p.id=pi.purchaseId where p.authCode &lt;&gt; 'PREAUTH' and pi.id=id)" lazy="false"
	 * @return the numLoops
	 */
	public Double getNet(){
		return net;
		// get the portion that this loop contributed to the overall cost (before discount)
//		double ratio = price.doubleValue() / purchase.getSubTotal().doubleValue();
		// determine proportion recovered after trxn costs
//		return ratio * purchase.getNet();
	}

	/**
	 * @param trxnFee the trxnFee to set
	 */
	public void setTrxnFee(Double trxnFee) {
		this.trxnFee = trxnFee;
	}


	/**
	 * @param net the net to set
	 */
	public void setNet(Double net) {
		this.net = net;
	}


	/**
	 * @return the groupedNum
	 */
	public Number getGroupedNum() {
		return groupedNum;
	}

	/**
	 * @param groupedNum the groupedNum to set
	 */
	public void setGroupedNum(Number groupedNum) {
		this.groupedNum = groupedNum;
	}

	/**
	 * @return the samplePath
	 * @hibernate.property not-null="true"
	 */
	public String getSamplePath() {
		return samplePath;
	}

	/**
	 * @param samplePath the samplePath to set
	 */
	public void setSamplePath(String samplePath) {
		this.samplePath = samplePath;
	}

	/**
	 * @return the paid
	 * @hibernate.property not-null="true" default='N'
	 */
	public Boolean getPaid() {
		return paid;
	}

	/**
	 * @param paid the paid to set
	 */
	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

}
