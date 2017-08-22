package com.beathive.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class is used to represent an address.</p>
 *
 * <p><a href="Address.java.html"><i>View Source</i></a></p>
 *
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="address"
 */
public class Address extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7148205586865903697L;
	protected String street1;
	protected String street2;
    protected String city;
    protected String province;
    protected String country;
    protected String postalCode;
	protected String areacode;
	protected String phone;
	protected Long userId;

	protected String AVSCode;	
//	private User user;
	
	
	
	public Address(){		
		super();
	}
	public Address(Long userid){
		super();
		this.userId = userId;
	}
	/**
	 * hibernate.one-to-one outer-join="false" constrained="true"
	 * @return
	 */
//	public User getUser(){
//		return user;
//	}
//	/**
//	 * 
//	 * @param user
//	 */
//	public void setUser(User user) {
//		this.user = user;
//	}

	/**
	 * hibernate.id name="userId" generator-class="foreign" type="java.lang.Long" column="userId" unsaved-value="null"
     * hibernate.generator-param name="property" value="user"
     * 
     * @hibernate.id generator-class="assigned"
	 * @return Returns the userId.
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.property column="avs_code"
	 * @return Returns the AVSCode a reliability rating.
	 */
	public String getAVSCode() {
		return AVSCode;
	}

	public void setAVSCode(String authFactorCode) {
		this.AVSCode = authFactorCode;
	}

//	public String getPhoneNumber() {
//		return areacode +" " + phone;
//	}
//	
	/**
	 * struts.validator type="mask" msgkey="errors.areacode"
     * struts.validator-var name="mask" value="${areacode}"
	 * @hibernate.property
	 * @return Returns the areacode.
	 */
	public String getAreacode() {
		return areacode;
	}
	/**
	 * @param areacode The areacode to set.
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	/**
	 * struts.validator type="mask" msgkey="errors.phone"
     * struts.validator-var name="mask" value="${phone}"
	 * @hibernate.property
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

    /**
     * @hibernate.property column="street1" not-null="false" length="150"
     */
    public String getStreet1() {
        return street1;
    }

    /**
     * @hibernate.property column="street2" not-null="false" length="50"
     */
    public String getStreet2() {
    	return street2;
    }
    
    /**
     * @struts.validator type="required"
     * @hibernate.property column="city" not-null="true" length="50"
     */
    public String getCity() {
        return city;
    }

    /**
     * @struts.validator type="required"
     * @hibernate.property column="province" length="100"
     */
    public String getProvince() {
        return province;
    }

    /**
     * @struts.validator type="required"
     * @hibernate.property column="country" length="100"
     */
    public String getCountry() {
        return country;
    }

    /**
     * @struts.validator type="required"
     * @struts.validator type="mask" msgkey="errors.zip"
     * @struts.validator-var name="mask" value="${zip}"
     * @hibernate.property column="postal_code" not-null="true" length="15"
     */
    public String getPostalCode() {
        return postalCode;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public void setStreet2(String street2) {
    	this.street2 = street2;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        final Address address1 = (Address) o;

        if (street1 != null ? !street1.equals(address1.street1) : address1.street1 != null) return false;
        if (city != null ? !city.equals(address1.city) : address1.city != null) return false;
        if (country != null ? !country.equals(address1.country) : address1.country != null) return false;
        if (postalCode != null ? !postalCode.equals(address1.postalCode) : address1.postalCode != null) return false;
        if (province != null ? !province.equals(address1.province) : address1.province != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (street1 != null ? street1.hashCode() : 0);
        result = 29 * result + (city != null ? city.hashCode() : 0);
        result = 29 * result + (province != null ? province.hashCode() : 0);
        result = 29 * result + (country != null ? country.hashCode() : 0);
        result = 29 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("country", this.country)
                .append("street1", this.street1).append("province",
                        this.province).append("postalCode", this.postalCode)
                .append("city", this.city).toString();
    }

}
