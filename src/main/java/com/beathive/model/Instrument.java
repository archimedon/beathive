package com.beathive.model;

import org.apache.commons.lang.builder.HashCodeBuilder;



/**
 * This class is used to represent available instruments in the database.</p>
 *
 * <p><a href="Instrument.java.do"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison</a>
 * @version $Revision: 1.3 $ $Date: 2004/05/16 02:16:44 $
 *
 * @struts.form include-all="true" extends="BaseForm" implements="DescriptorForm"
 * @hibernate.class table="instrument" polymorphism="explicit"
 * hibernate.cache usage="read-only"
 */
public class Instrument extends BaseObject implements Descriptor {
    //~ Instance fields ========================================================

	protected Long id;
	protected String clazz;
	protected String labelKey;
	protected Long sortorder;
	protected Instrumentgroup instrumentgroup;
	protected Long groupId;
	
    //~ Constructors
	
	/**
	 * No arg constructor
	 */
	public Instrument(){
    		super();
		setClazz(getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1).toLowerCase());
    }
    
    /**
     * 
     * @param name
     */
    public Instrument(String aLabel){
	    	this();
	    	this.setLabelKey(aLabel);
    }
    
    public Instrument(Long iid){
    	this();
    	this.setId(iid);
    }
    
    public Instrument(String aLabel , Long iid){
    	this();
    	this.setLabelKey(aLabel);
    	this.setId(iid);
    }
    
    //~ Methods ================================================================

	/**
	 * @return Returns the instrumentGroup.
	 * @hibernate.many-to-one column="groupId" insert="false" update="false" cascade="none" lazy="proxy"
	 *
	 */
	public Instrumentgroup getInstrumentgroup() {
		return this.instrumentgroup;
	}
	/**
	 * @param instrumentGroup The instrumentGroup to set.
	 */
	public void setInstrumentgroup(Instrumentgroup instrumentGroup) {
		this.instrumentgroup = instrumentGroup;
	}
    
	/**
	 * @return the genre id.
     * @hibernate.id column="id" generator-class="native" unsaved-value="null" not-null="true"
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = (Long)id;
	}
	/**
	 * @return Returns the groupId.
	 * @hibernate.property not-null="true"
	 */
	public Long getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
	 *  @hibernate.property column="clazz"
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
    	if (!(o instanceof Instrument) || (labelKey == null)) {
            return false;
        }
    	Instrument rhs = (Instrument) o;
        return this.labelKey.equals(rhs.labelKey);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.labelKey)
		.toHashCode();
    }

}
