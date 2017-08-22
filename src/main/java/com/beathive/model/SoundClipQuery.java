/**
 * 
 */
package com.beathive.model;

/**
 * @struts.form include-all="true" extends="BaseForm"
 * 
 * @author ron
 */
public interface SoundClipQuery{

	public Class getQueryClass();
	
	public void setQueryClass(Class returnType);
	
	public Instrument getInstrument();
	
	/**
	 * @param instrument The instrument to set.
	 */
	public void setInstrument(Instrument instrument);
	
}
