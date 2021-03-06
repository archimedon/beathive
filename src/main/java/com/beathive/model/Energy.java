package com.beathive.model;


/**
 * Energy class
 * 
 * This class is used to generate Hibernate persistence layer.
 * Primary use is creating menus at startup.
 * 
 * <p>
 * <a href="Energy.java.do"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 0.9 $ $Date: 2004/08/22 02:16:44 $
 *
 * @struts.form include-all="true" extends="DescriptorImplForm"
 * @hibernate.subclass discriminator-value="energy"
 */
public class Energy extends DescriptorImpl {
/*	public Energy(){
		super();
	    	String temp = this.getClass().getName();   
	    	setClazz(temp.substring(temp.lastIndexOf('.')).toLowerCase());
	}
*/}