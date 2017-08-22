/**
 * 
 */
package com.beathive.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author ron@beathive.com
 * @version 1.9 Date: Apr 24, 2009
 * 
 * @struts.form include-all="true" extends="BaseForm" 
 * @hibernate.class table="promo"
 */
public class Promo extends BaseObject implements Serializable {

	private static final long serialVersionUID = -8169406905795168381L;
	
	private String code;
	private Date start;
	private Integer duration;
	//Calendar cal = Calendar.getInstance();
	private Double discount;
	private Long id;
	
	/**
	 * @hibernate.id generator-class="native" unsaved-value="null"
	 * @return the code
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @struts.validator type="required"
	 * @hibernate.property not-null="true" unique="true"
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @struts.validator type="required"
	 * @hibernate.property type="date" not-null="true"
	 * @return the begin
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the begin to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @struts.validator type="required"
	 * @hibernate.property not-null="true"
	 * @return the end
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param end the end to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @struts.validator type="required"
	 * @hibernate.property not-null="true"
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
	
	
	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
    	
    	
    	if (o instanceof Promo){
    		Promo af = (Promo)o;
    		
    		return af.getCode().equals(this.code);
    	}
    	return false;
    }

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	/* (non-Javadoc)
	 * @see com.beathive.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.code;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


}
