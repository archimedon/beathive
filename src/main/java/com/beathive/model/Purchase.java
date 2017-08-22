package com.beathive.model;


import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.beathive.Constants;
import com.beathive.model.BaseObject;

/**
 * A user will first have to have a paymentMethod set before generating an order
 * @author rdennison
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="purchase"
 */
public class Purchase extends BaseObject{

	private Long id;
	private Set items = new LinkedHashSet();

	private String customerName; // required
	private Long customerId;
	private Long zipId; 
	private String firstName;
	private String middleName;
	private String lastName;
	
	private String requestId;
	private String reconciliationId;
	private String authCode = Constants.PREAUTH;
	private String email;
	private String downloadURL;
	/* a pay method name */
	private String ipAddress;
	private Double total;
	private Double subTotal;
	private Double discount;
	private Double promoDiscount;
	private PaymentExecutor executor;
	private Date created;

//	private Promo promo;
	protected String promoCode;
	private Double trxnFee;
	private Double net;

	/**
	 * @return Returns the Amount total. In other words, the total.
     * @hibernate.property not-null="true"
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            The total to set.
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return Returns the authCode.
	 * @hibernate.property not-null="true"
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @param authCode
	 *            The authCode to set.
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return Returns the created.
	 * @hibernate.property
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            The created to set.
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return Returns the email.
	 * @hibernate.property
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @hibernate.component
	 * @return Returns the fees.
	 */
	public PaymentExecutor getExecutor() {
		return executor;
	}

	/**
	 * @param fees
	 *            The fees to set.
	 */
	public void setExecutor(PaymentExecutor executor) {
		this.executor = executor;
	}

	/**
	 * @hibernate.property
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @hibernate.id generator-class="native" unsaved-value="null"
	 * @return Returns the id.
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
	 * @return Returns the iP.
	 * @hibernate.property column="ip_address"
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ip
	 *            The iP to set.
	 */
	public void setIpAddress(String ip) {
		ipAddress = ip;
	}

	/**
	 * @return Returns the lastName.
	 * @hibernate.property
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return Returns the subTotal.
	 * @hibernate.property not-null="true"
	 */
	public Double getSubTotal() {
		return subTotal;
	}

	/**
	 * @param subTotal
	 *            The subTotal to set.
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	/**
	 * @return Returns the customerId.
	 * @hibernate.property
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param username
	 *            The username to set.
	 */
	public void setCustomerId(Long userId) {
		this.customerId = userId;
	}

	/**
	 * @return Returns the items.
     * @hibernate.set name="items" lazy="false" cascade="all"
     * @hibernate.collection-key column="purchaseId"
     * @hibernate.collection-one-to-many class="com.beathive.model.PurchaseItem"
	 */
	public Set getItems() {
		return items;
	}
	/**
	 * @param items The items to set.
	 */
	public void setItems(Set items) {
		this.items = items;
	}

	public void addItem(PurchaseItem item){
		if (items == null){
			items = new LinkedHashSet();
		}
 		item.setPurchaseId(this.id);
		items.add(item);
	}
	
	public boolean equals(Object object) {
        if (!(object instanceof Purchase)) {
            return false;
        }

        Purchase rhs = (Purchase) object;

        return new EqualsBuilder()
        .append(this.created, rhs.created)
        .append(this.customerId, rhs.customerId)
		.append(this.subTotal, rhs.subTotal)
		.append(this.ipAddress , rhs.ipAddress)
		.append(this.authCode , rhs.authCode)
		.isEquals();
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public int hashCode() {
        return new HashCodeBuilder()
        .append(this.created)
		.append(this.customerId)
		.append(this.subTotal)
		.append(this.ipAddress)
		.append(this.authCode)
		.toHashCode();
    }
    
    public String toString(){
    		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("authCode", this.authCode)
            .append("email", this.email)
            .append("total", this.total)
            .append("customerId", this.customerId)
            .append("items", this.items)
            .toString();
    	}

	/**
     * @hibernate.property
	 * @return Returns the requestId.
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId The requestId to set.
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
     * @hibernate.property
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @hibernate.property
	 * @return Returns the reconciliationId.
	 */
	public String getReconciliationId() {
		return reconciliationId;
	}

	/**
	 * @param reconciliationId The reconciliationId to set.
	 */
	public void setReconciliationId(String reconciliationId) {
		this.reconciliationId = reconciliationId;
	}
	
// Added username to facilitate non-member purchases
// username may be anonymous or the users email address since that is provided
	/**
	 * @return Returns the customerName.
	 * @hibernate.property not-null="true"
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param username
	 *            The username to set.
	 */
	public void setCustomerName(String username) {
		this.customerName = username;
	}

	/**
	 * @hibernate.property column="bulk_discount"
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return
	 */
	public String getFullName() {
		return this.firstName + 
		(  StringUtils.isNotBlank(this.middleName) ? " " + this.middleName : "" ) +
				(  StringUtils.isNotBlank(this.lastName) ? " " + this.lastName : ""  ) ;
	}

	/**
	 * @hibernate.property
	 * @return the downloadURL
	 */
	public String getDownloadURL() {
		return downloadURL;
	}

	/**
	 * @param downloadURL the downloadURL to set
	 */
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	/**
	 * @hibernate.property
	 * @return the promoCode
	 */
	public String getPromoCode() {
		return promoCode;
	}

	/**
	 * @param promoCode the promoCode to set
	 */
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
/*
	**
	 * @hibernate.many-to-one column="promoCode" insert="false" update="false" cascade="none" lazy="false"
	 * @return the promo
	 *
	public Promo getPromo() {
		return promo;
	}

	**
	 * @param promo the promo to set
	 *
	public void setPromo(Promo promo) {
		this.promo = promo;
	}
*/
	/**
	 * @hibernate.property
	 * @return the promoDiscount
	 */
	public Double getPromoDiscount() {
		return promoDiscount;
	}

	/**
	 * @param promoDiscount the promoDiscount to set
	 */
	public void setPromoDiscount(Double promoDiscount) {
		this.promoDiscount = promoDiscount;
	}

	/**
	 * @param zipId
	 */
	public void setZipId(Long zipId) {
		this.zipId = zipId;
	}

	/**
	 * @hibernate.property
	 * @return the zipId
	 */
	public Long getZipId() {
		return zipId;
	}

	
	/**
	 * @hibernate.property insert="false" update="false" formula="( select ( ((p.rateFee * p.total) + p.flatFee) / 2 ) from purchase p where p.id=id )" lazy="false"
	 * These are used for salesReporting and other PRODUCER calcs
	 */
	public Double getTrxnFee(){
//		double cost = (executor.getRateFee().doubleValue() * this.total.doubleValue());
//		return executor.getFlatFee() + cost;
		return trxnFee;
	}

	/**
	 * @hibernate.property insert="false" update="false" formula="( select ( (p.total - ((p.rateFee * p.total) + p.flatFee)) /2 ) from purchase p where p.id=id )" lazy="false"
	 * @return
	 */
	public Double getNet() {
		return net;
		//		return (this.total.doubleValue() - getTrxnFee());
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
}