package com.beathive.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * @author rdennison
 * 
 * @hibernate.class table="preference"
 * @struts.form include-all="true" extends="BaseForm"
 **/
public class Preference extends BaseObject {
	private static final long serialVersionUID = -9174135801175086319L;
	private String format;
	private Boolean hideOwned;
	private Boolean hideFav;
//	private String currency;
	private String locale;
	protected Long userId;
    protected String sort;
	
	/**
	 * @return Returns the userId.
	 * @hibernate.id generator-class="assigned"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
	 * @hibernate.property
	 * @param formats
	 */	
	public String getFormat(){
		return format;
	}
	/**
	 * @hibernate.property
	 * @param locale
	 */
	public String getLocale(){
		return locale;
	}
	
	/**
	 * @param locale
	 */
//	public String getCurrency(){
//		return currency;
//	}
	
	public void setFormat(String format){
		this.format=format;
	}

	public void setLocale(String locale){
		this.locale=locale;
	}

//	public void setCurrency(String currency){
//		this.currency=currency;
//	}
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

	/**
	 * @hibernate.property type="yes_no"
	 * @return the hideOwned
	 */
	public Boolean getHideOwned() {
		return hideOwned;
	}

	/**
	 * @param hideOwned the hideOwned to set
	 */
	public void setHideOwned(Boolean hideOwned) {
		this.hideOwned = hideOwned;
	}

	/**
	 * @hibernate.property type="yes_no"
	 * @return the hideFav
	 */
	public Boolean getHideFav() {
		return hideFav;
	}

	/**
	 * @param hideFav the hideFav to set
	 */
	public void setHideFav(Boolean hideFav) {
		this.hideFav = hideFav;
	}

	/**
	 * @hibernate.property
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

}
