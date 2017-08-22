package com.beathive.model;


/**
 * Feel class
 * 
 * This class is used to generate Hibernate persistence layer.
 * Primary use is creating menus at startup.
 * 
 * <p>
 * <a href="Feel.java.do"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 0.9 $ $Date: 2004/08/22 02:16:44 $
 *
 * @struts.form include-all="true" extends="DescriptorImplForm"
 * @hibernate.subclass discriminator-value="feel"
 */
public class Feel extends DescriptorImpl {
	public Feel(){
		super();
	}
}