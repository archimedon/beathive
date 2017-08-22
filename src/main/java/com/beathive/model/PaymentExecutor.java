/**
 * 
 */
package com.beathive.model;

/**
 * @author ron ron@beathive.com
 * @version 1.9 Date: May 2, 2009
 * @struts.form name="paymentExecutorForm" 
 * @hibernate.class table="paymentExecutor"
 */
public class PaymentExecutor extends BaseObject {
	
	private String name;
	private Double flatFee = new Double(0);
	private Double rateFee = new Double(0);
	private Long id;
	
	/**
	 * @hibernate.id generator-class="native" unsaved-value="null"
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property not-null="true"
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property not-null="true"
	 * @return the flatFee
	 */
	public Double getFlatFee() {
		return flatFee;
	}

	/**
	 * @param flatFee the flatFee to set
	 */
	public void setFlatFee(Double flatFee) {
		this.flatFee = flatFee;
	}

	/**
	 * @hibernate.property not-null="true"
	 * @return the rateFee
	 */
	public Double getRateFee() {
		return rateFee;
	}

	/**
	 * @param rateFee the rateFee to set
	 */
	public void setRateFee(Double rateFee) {
		this.rateFee = rateFee;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
