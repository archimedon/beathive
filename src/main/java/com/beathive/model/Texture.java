package com.beathive.model;


/**
 * Texture class
 * 
 * This class is used to generate Hibernate persistence layer.
 * Primary use is creating menus at startup.
 * 
 * <p>
 * <a href="Texture.java.do"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 0.9 $ $Date: 2004/08/22 02:16:44 $
 * 
 * @struts.form include-all="true" extends="DescriptorImplForm"
 * @hibernate.subclass discriminator-value="texture"
 */
public class Texture extends DescriptorImpl {
	//~ Instance fields
	// ========================================================

/*	protected String name;
	protected Long sortorder;
	
	
//  ~ Methods ===========================================================

	*//**
	 * Returns the id.
	 * @return String
	 * @hibernate.id column="name" generator-class="assigned" unsaved-value="null"
	 *//*
	public String getName() {
		return name;
	}

	*//**
	 * @param name
	 * The name to set.
	 *//*
	public void setName(String name) {
		this.name = name;
	}
	*//**
	 * @return Returns the sortorder.
	 * @hibernate.property column="sort_order"
	 *//*
	public Long getSortorder() {
		return sortorder;
	}
	*//**
	 * @param sortorder The sortorder to set.
	 *//*
	public void setSortorder(Long sortorder) {
		this.sortorder = sortorder;
	}
*/
}