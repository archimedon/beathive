package com.beathive.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * DescriptorImpl class
 * 
 * This class is used to generate Hibernate persistence layer.
 * Primary use is creating menus at startup.
 * 
 * <p>
 * <a href="DescriptorImpl.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 0.9 $ $Date: 2004/08/22 02:16:44 $

 * @struts.form include-all="true" extends="BaseForm" implements="DescriptorForm"
 * @hibernate.class table="descriptor"
 * @hibernate.discriminator column="clazz"
 * hold off til after dev
 * hibernate.cache usage="read-only"
 */
public class DescriptorImpl extends BaseObject implements Descriptor{
	//~ Instance fields
	// ========================================================
	protected String clazz;
	protected String labelKey;
	protected String id;
	protected Long sortorder;
	
	public DescriptorImpl(){
	    	super();
	    	String temp = this.getClass().getName();   
	    	setClazz(temp.substring(temp.lastIndexOf('.') + 1).toLowerCase());
	}
//  ~ Methods ===========================================================
	/**
	 * @return Returns the id.
     * @struts.validator type="required"
     * @hibernate.id column="id" generator-class="assigned" unsaved-value="null" not-null="true"
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param idstr The idkey to set.
	 */
	public void setId(String idstr) {
		this.id = (String)idstr;
	}
	

	/**
	 * @return Returns the sortorder.
	 * @hibernate.property column="sort_order"
	 */
	public Long getSortorder() {
		return sortorder;
	}
	/**
	 * @param sortorder The sortorder to set.
	 */
	public void setSortorder(Long sortorder) {
		this.sortorder = sortorder;
	}
	/**
	 * @return Returns the clazz.
	 */
	public String getClazz() {
		return clazz;
	}
	/**
	 * @param clazz The clazz to set.
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	/**
	 * @return Returns the labelKey.
     * @struts.validator type="required"
	 * @hibernate.property column="labelkey" unique="true" not-null="true"
	 */
	public String getLabelKey() {
		return labelKey;
	}
	/**
	 * @param labelKey The labelKey to set.
	 */
	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
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