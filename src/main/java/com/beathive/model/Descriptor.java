package com.beathive.model;


/**
 * Descriptor class
 * 
 * This class is used to generate Hibernate persistence layer.
 * Primary use is creating menus at startup.
 * 
 * <p>
 * <a href="Descriptor.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 0.9 $ $Date: 2004/08/22 02:16:44 $
 * struts.form include-all="true" extends="BaseForm"
 */

public interface Descriptor{
	
	/**
	 * @return Returns the id.
     * struts.validator type="required"
	 */
//	public Long getId() ;
	/**
	 * @param idstr The idkey to set.
	 */
//	public void setId(Long idstr) ;
	
//  ~ Methods ===========================================================
	/**
	 * @return Returns the sortorder.
	 * @!--hibernate.property column="sort_order"
	 */
	public Long getSortorder() ;
	/**
	 * @param sortorder The sortorder to set.
	 */
	public void setSortorder(Long sortorder) ;
	/**
	 * @return Returns the clazz.
	 */
	public String getClazz() ;
	/**
	 * @param clazz The clazz to set.
	 */
	public void setClazz(String clazz) ;
	/**
	 * @return Returns the labelKey.
     * struts.validator type="required"
	 * @!--hibernate.property column="labelkey" unique="true" not-null="true"
	 */
	public String getLabelKey() ;
	/**
	 * @param labelKey The labelKey to set.
	 */
	public void setLabelKey(String labelKey) ;
}