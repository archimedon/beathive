package com.beathive.webapp.form;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @struts.form name="clipDetailsForm"
 * 
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 * @version $Revision: 2 $ $Date: 2008-15-06 $
 * 
 */
public class ClipDetailsForm extends BaseForm implements java.io.Serializable {

	protected static final Log log = LogFactory.getLog(ClipDetailsForm.class);
	private static final long serialVersionUID = -3166222337987074117L;

	private String loopId;

	private Map attrib = new HashMap();

	public ClipDetailsForm() {
		super();
	}

	public void setAttrib(String key, String value) {
		attrib.put(key, value);
	}

	/**
	 * @return the values
	 */
	public Map getAttribs() {
		return attrib;
	}

	/**
	 * @return the loopId
	 */
	public String getLoopId() {
		return loopId;
	}

	/**
	 * @param loopId
	 *            the loopId to set
	 */
	public void setLoopId(String loopId) {
		this.loopId = loopId;
	}
}
