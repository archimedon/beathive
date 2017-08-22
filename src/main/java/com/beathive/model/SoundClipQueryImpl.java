package com.beathive.model;

import com.beathive.Constants;

/**
 * @struts.form include-all="true" extends="BaseForm"
 * 
 * @author ron
 */
public class SoundClipQueryImpl extends SoundClip implements SoundClipQuery{
	private User viewer;
	private int fetchSize = Constants.FETCH_SIZE;
	private int startIndex = 0;
	private int countLoop = 0;
	private int countTrackpack = 0;
	private Class queryClass = SoundClip.class;
	protected Instrument instrument;


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
	 * @param viewer
	 */
	public SoundClipQueryImpl() {
		super();
	}

	/**
	 * @param viewer
	 */
	public SoundClipQueryImpl(User viewer) {
		this();
		this.viewer = viewer;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public User getViewer() {
		return viewer;
	}

	public void setViewer(User viewer) {
		this.viewer = viewer;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getCountLoop() {
		return countLoop;
	}

	public void setCountLoop(int countLoop) {
		this.countLoop = countLoop;
	}

	public int getCountTrackpack() {
		return countTrackpack;
	}

	public void setCountTrackpack(int countTrackpack) {
		this.countTrackpack = countTrackpack;
	}
	
	public Class getQueryClass() {
		return queryClass;
	}
	
	public void setQueryClass(Class queryClass) {
		this.queryClass = queryClass;
	}

}
