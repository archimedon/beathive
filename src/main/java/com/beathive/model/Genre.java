package com.beathive.model;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This class is used to represent available genres in the database.
 * </p>
 * 
 * <p>
 * <a href="Genre.java.do"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison</a>
 * @version $Revision: 1.3 $ $Date: 2004/05/16 02:16:44 $
 * 
 * @struts.form include-all="true" extends="BaseForm" implements="DescriptorForm"
 * @hibernate.class table="genre" polymorphism="explicit"
 * hibernate.cache usage="read-only"
 */
public class Genre extends BaseObject implements Descriptor {
	//~ Instance fields
	// ========================================================

	private static final long serialVersionUID = -4423203161328280670L;
	protected Long id;
	protected String clazz;
	protected String labelKey;
	protected Long sortorder;
	
	public Genre(){
		super();
		setClazz(getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1).toLowerCase());
	}
	
	public Genre(String groupname){
		this();
		setLabelKey(groupname);
	}
	/**
	 * @param long1
	 */
	public Genre(Long long1) {
		this();
		setId(long1);
	}

	/**
	 * @return the genre id.
     * @hibernate.id column="id" generator-class="native" unsaved-value="null"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
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
    	if (!(o instanceof Genre) || (labelKey == null)) {
            return false;
        }
        Genre rhs = (Genre) o;
        return this.labelKey.equals(rhs.labelKey);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}