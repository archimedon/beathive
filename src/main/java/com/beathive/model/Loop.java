package com.beathive.model;


/**
 * As a subclass of SoundClip, Loop has the same basic requirements:
 * Primarily, the binary upload, username, BPM, the Price, Status and at least one Genre
 * Additionally, an instrument.
 * 
 * <p>
 * <a href="Loop.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison </a>
 * @struts.form include-all="true" extends="SoundClipForm"
 * @hibernate.subclass discriminator-value="Loop"
 */
public class Loop extends SoundClip {
	private static final long serialVersionUID = 5283393255897135714L;
	//~ Instance fields
	// ========================================================
	//	loop may have a parent trackpack; loop <--> trackpack
	protected Trackpack trackpack; 
	protected Long trackpackId;
	protected Instrument instrument;
	protected Long instrumentId;
	protected String type = SoundClip.LOOP_TYPE;
	
	protected boolean searchable = true;
	public boolean isSearchable(){
		return searchable;
	}
	/**
	 * @param searchable The searchable to set.
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	

	
	//~ Constructors
	// ========================================================

    public Loop(){ super();}
    
	//~ Methods
	// ========================================================

	/**
	 * @return Returns the trackpackId.
	 * @hibernate.property column="trackpackId"
	 */
	public Long getTrackpackId() {
		return trackpackId;
	}
	/**
	 * @param trackpackId The trackpackId to set.
	 */
	public void setTrackpackId(Long trackpackId) {
		this.trackpackId = trackpackId;
	}
	/**
	 * 
	 * @return Returns the trackpack.
	 * @hibernate.many-to-one column="trackpackId" insert="false" update="false" cascade="none" lazy="proxy"
	 * 
	 */
	public Trackpack getTrackpack() {
		return trackpack;
	}
	
	/**
	 * @return The trackpack this loop belongs to.
	 * @param trackpack
	 */
	public void setTrackpack(Trackpack trackpack) {
		this.trackpack = trackpack;
	}
	/**
	 * @return Returns the instrument.
	 * @hibernate.many-to-one column="instrument_id" insert="false" update="false" cascade="none" lazy="false"
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
	 * @return Returns the instrumentId.
	 * @hibernate.property column="instrument_id"
	 */
	public Long getInstrumentId() {
		return instrumentId;
	}
	/**
	 * @param instrumentId The instrumentId to set.
	 */
	public void setInstrumentId(Long instrumentId) {
		this.instrumentId = instrumentId;
	}
	
	public String toString(){
		return super.stringBuilder().append("type", this.type).append("searchable", this.searchable).append("instrumentId",this.instrumentId).append("instrument",this.instrument).toString();
	}
	
	
	public boolean isReady(){
		return ( (super.isReady()) 	&& instrumentId!=null);

	}
	/**
	 * @return Returns the clipStatusId.
	 * @struts.validator type="required"
	 * #hibernate.property
	 */
	public Integer getStatusId() {
		Integer deletedStat = new Integer(1);
		
		// if this loop in shadow mode return current stat
		if (this.statusId!=null && this.statusId.equals(deletedStat)){
			return this.statusId;
		}
		// if not in a TP then return null for OK
		if (trackpack==null){ return null;}
		// otherwise examine parent status and readiness
		// if TP not ready the return 2
		if (!trackpack.isReady()){
			return new Integer(2);
		}
		return null;
	}

	
}