package com.beathive.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Price is a component of SoundClip. 
 *
 * <p>
 * <a href="Price.java.html"><i>View Source</i></a>
 * </p>
 * @author <a href="mailto:ron@secretriver.net">Ronald Dennison</a>
 * @struts.form include-all="true" extends="BaseForm"
 */
public class Price extends BaseObject implements Serializable {
    //~ Instance fields ========================================================

	private static final long serialVersionUID = -1095794814749675848L;
	protected Double amount;
	protected String currencyCode;

	public Price(){
		super();
	}

	
	public Price(Double amt , String countryCode){
		this();
		this.amount = amt;
		this.currencyCode = countryCode;
	}
	/**
	 * @return Returns the currencyCode.
     * @hibernate.property 
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	/**
	 * @return Returns the amount.
     * @hibernate.property
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the amount as a String.
	 */
	public String toString(){
		return ""+amount;
	}

	 public boolean equals(Object o) {
		 if (!(o instanceof Price) ) {
			 return false;
		 }
		 Price rhs = (Price) o;
	        return new EqualsBuilder()
	        .append(this.amount, rhs.amount)
	        .append(this.currencyCode, rhs.currencyCode).isEquals();
	    }

    public int hashCode() {
        return new HashCodeBuilder()
        .append(this.amount)
        .append(this.currencyCode)
		.toHashCode();
    }

}
