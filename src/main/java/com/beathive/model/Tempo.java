package com.beathive.model;

import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Tempo class
 * 
 * This class is used to generate Hibernate persistence layer.
 * Primary use is creating menus at startup.
 * 
 * <p>
 * <a href="Tempo.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 0.9 $ $Date: 2004/08/22 02:16:44 $
 * 
 * @!--hibernate.subclass discriminator-value="tempo"
 * @struts.form include-all="true" extends="BaseForm" implements="DescriptorForm"
 * @hibernate.class table="tempo" polymorphism="explicit"
 * hibernate.cache usage="read-only"
 */
public class Tempo extends BaseObject implements Descriptor{
	//~ Instance fields
	// ========================================================


	protected String clazz;
	protected String labelKey;
	protected Long id;
	protected Long sortorder;
	
	public Tempo(){
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
	public Long getId() {
		return id;
	}

	/**
	 * @param idstr The idkey to set.
	 */
	public void setId(Long idstr) {
		this.id = idstr;
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
    	return this.labelKey;
    }

    public boolean equals(Object o) {
    	if (!(o instanceof Tempo) || (labelKey == null)) {
            return false;
        }
    	Tempo rhs = (Tempo) o;
        return this.labelKey.equals(rhs.labelKey);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.labelKey)
		.toHashCode();
    }

	
}