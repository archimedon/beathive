package com.beathive.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * as a conveneince for maintaining state info during dev
 *
 * @struts.form include-all="true"
 * @hibernate.class table="country"
 */
public class Country extends BaseObject implements Serializable {
	protected String value;
	protected String label;
	
	/**
	 * @hibernate.property
	 * @return Returns the states name.
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @hibernate.id generator-class="assigned"
	 * @return Returns the Id aka. the state code.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param statename The statename to set.
	 */
	public void setLabel(String statename) {
		this.label = statename;
	}
	/**
	 * @param statecode The statecode to set.
	 */
	public void setValue(String statecode) {
		this.value = statecode;
	}

    public boolean equals(Object object) {
        if (!(object instanceof Country)) {
            return false;
        }

        Country rhs = (Country) object;

        return new EqualsBuilder().append(this.label, rhs.label)
                                  .isEquals();
    }

    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
    		return label;
    }

}
