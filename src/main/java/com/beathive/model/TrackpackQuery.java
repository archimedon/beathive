/**
 * 
 */
package com.beathive.model;

import com.beathive.Constants;
import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;


/**
 * @struts.form include-all="true" extends="BaseForm"
 * 
 * @author ron
 */
public class TrackpackQuery extends Trackpack implements SoundClipQuery{
	private Instrument instrument;
	private QueryMeta queryMeta = new QueryMetaImpl(new User(new Long(-1)));
	private Class queryClass = Trackpack.class;
	
	/**
	 * @param viewer
	 */
	public TrackpackQuery() {
		super();
	}
	
	/**
	 * @param viewer
	 */
	public TrackpackQuery(User viewer) {
		this();
		queryMeta.setViewer(viewer);
	}
	
	/**
	 * @return Returns the instrument.
	 */
	public Instrument getInstrument() {
		return instrument;
	}
	/**
	 * @param instrument The instrument to set.
	 */
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	/**
	 * @return the queryMeta
	 */
	public QueryMeta getQueryMeta() {
		return queryMeta;
	}

	/**
	 * @param queryMeta the queryMeta to set
	 */
	public void setQueryMeta(QueryMeta queryMeta) {
		this.queryMeta = queryMeta;
	}
	
	public Class getQueryClass() {
		return super.getClass();
	}
	
	public void setQueryClass(Class queryClass) {
	}
	
}
