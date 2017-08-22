package com.beathive.model;

import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * This class is used to represent available clip descriptiones.
 *
 * <p>
 * <a href="Status.java.do"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:ron@secretriver.com">Ronald Dennison</a>
 * @version $Revision: 0.9 $ $Date: 2004/07/31 13:04:16 $
 *
 * 
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="status"
 */
public class Status extends BaseObject{
    //~ Instance fields ========================================================

	private static final long serialVersionUID = -2755189172861244121L;
	private Long id;
	private String labelKey;
	private String description;
	

	//~ Methods ================================================================
	/**
	 * @hibernate.id generator-class="native"
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

    public Status(){
	    	super();
	}

	/**
	 * @hibernate.property not-null="true"
	 * @return Returns the labelKey.
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

	/**
	 * @hibernate.property not-null="true"
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}    
    public String toString() {
    	return this.labelKey;
    }

    public boolean equals(Object o) {
    	if (!(o instanceof Status) || (labelKey == null)) {
            return false;
        }
    	Status rhs = (Status) o;
        return this.labelKey.equals(rhs.labelKey);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.labelKey)
		.toHashCode();
    }

}
