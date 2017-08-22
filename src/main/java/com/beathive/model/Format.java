package com.beathive.model;

import java.io.Serializable;

/**
 * This class is used to represent available format in the database.
 * </p>
 * 
 * <p>
 * <a href="Format.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 1.3 $ $Date: 2004/05/16 02:16:44 $
 * 
 * @struts.form include-all="true" extends="DescriptorImplForm"
 * @hibernate.subclass discriminator-value="format"
 * @hibernate.cache usage="read-only"
 */
public class Format extends DescriptorImpl {
	public Format() {
		super();
	}
}