package com.beathive.model;

import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Instrumentgroup class
 * 
 * This class is used to generate Spring Validation rules as well as the
 * Hibernate persistence later.
 * 
 * <p>
 * <a href="Instrumentgroup.java.do"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison </a>
 * @version $Revision: 1.4 $ $Date: 2004/07/22 02:16:44 $
 * 
 * @struts.form include-all="true" extends="BaseForm" implements="DescriptorForm"
 * @hibernate.class table="instrument_group"
 * hibernate.cache usage="read-only"
 */
public class Instrumentgroup extends BaseObject implements Descriptor {
	//~ Instance fields
	// ========================================================

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4454154147206132695L;
	protected Long id;
	protected String clazz;
	protected String labelKey;
	protected Long sortorder;
	protected List instruments;
	
	public Instrumentgroup(){
		super();
		setClazz(getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1).toLowerCase());
	}
	
	public Instrumentgroup(String groupname){
		super();
		setLabelKey(groupname);
	}
	/**
	 * @return Returns the instruments.
	 * 
 	 * @hibernate.bag name="instruments" inverse="false" lazy="false" cascade="none" fetch="subselect" order-by="sort_order"
	 * @hibernate.collection-key column="groupId"
	 * @hibernate.collection-one-to-many class="com.beathive.model.Instrument"
	 * hibernate.collection-cache usage="read-only" 
	 */
	public List getInstruments() {
		return instruments;
	}
	/**
	 * @param instruments The instruments to set.
	 */
	public void setInstruments(List instruments) {
		this.instruments = instruments;
	}
	/**
	 * @return the genre id.
     * @hibernate.id column="id" generator-class="native"
     *  unsaved-value="null"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param idstr The idkey to set.
	 */
	public void setId(Long idlong) {
		this.id = (Long)idlong;
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
	 * @hibernate.property column="clazz"
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
    	if (!(o instanceof Instrumentgroup) || (labelKey == null)) {
            return false;
        }
    	Instrumentgroup rhs = (Instrumentgroup) o;
        return this.labelKey.equals(rhs.labelKey);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.labelKey)
		.toHashCode();
    }
}