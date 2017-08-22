package com.beathive.model;


/**
 * Keynote class
 * 
 * This class is used to generate Hibernate persistence layer.
 * Primary use is creating menus at startup.
 * 
 * <p>
 * <a href="Keynote.java.do"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 0.9 $ $Date: 2004/08/22 02:16:44 $
 * 
 * @struts.form include-all="true" extends="DescriptorImplForm"
 * @hibernate.subclass discriminator-value="keynote"
 */
public class Keynote extends DescriptorImpl {
	//~ Instance fields
	// ========================================================
/*
	protected String note;
	
	
//  ~ Methods ===========================================================

	*//**
	 * Returns the id.
	 * @return String
	 * @hibernate.id column="note" generator-class="assigned" unsaved-value="null"
	 *//*
	public String getName() {
		return note;
	}

	*//**
	 * @param id
	 * The id to set.
	 *//*
	public void setName(String note) {
		this.note = note;
	}
*/
}