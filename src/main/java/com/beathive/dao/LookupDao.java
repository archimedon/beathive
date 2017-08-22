package com.beathive.dao;

import java.util.List;

import com.beathive.model.Registrant;

//import com.beathive.model.Instrumentgroup;
//import com.beathive.model.Instrument;


/**
 * Lookup Data Access Object (Dao) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 * <p>
 * <a href="LookupDao.java.html"><i>View Source</i></a>
 * </p>
 */
public interface LookupDao extends Dao {
    //~ Methods ================================================================


	/**
	 * Get stores without their loops & trackpacks
	 */
//	public List getStores();
	/**
	 * Return all possible user roles
	 */
    public List getRoles();
    
    /**
     * Return all Format
     * @return List
     */
//    public List getFormats();
    
    /**
     * Return all genre
     * @return List
     */
    public List getGenres();
    
    /**
     * Return all Instruments
     * @return List
     */
//    public List<Instrument> getInstruments();
    
    /**
     * Return all Instrumentgroups plan to get instruments via their group
     * @return List
     */
//    public List<Instrumentgroup> getInstrumentgroups();

    /**
     * A List of all Timesignatures
     * @return
     */
//    public List getTimesignatures();
    
    /**
     * Returns a List of all scale types
     * @return
     */
//    public List getScales();
    /**
     * Returns a List of all Keynotes
     * @return
     */
//    public List getKeynotes();
	
	/**
     * Returns a List of all Passage
     * @return List
	 */
//	public List getPassages();
	/**
     * Returns a List of all Energy
     * @return List
	 */
//	public List getEnergies();
	/**
     * Returns a List of all Passage
     * @return List
	 */
//	public List getFeels() ;
	/**
     * Returns a List of all Mood
     * @return List
	 */
//	public List getMoods() ;
	/**
     * Returns a List of all Origin
     * @return List
	 */
//	public List getOrigins();
	/**
     * Returns a List of all Sonority
     * @return List
	 */
//	public List getSonorities();
	/**
	 * The list of Tempos
	 * @return List
	 */
	public List getTempos();
	/**
     * Returns a List of all Texture
     * @return List
	 */
//	public List getTextures();
	
	/**
     * Returns a List of all Timespan
     * @return List
	 */	
//	public List getTimespans();
	
	/**
     * Returns a List of all Prices
     * @return List
	 */
//	public List getPrices();
	
	/**
     * Returns a List of all Status
     * @return List
	 */
//	public List getStati();
	
	/**
	 * Return the Loops picked by the home-producer
	 * @return List
	 */
//	public List getProducerPicks();

	/**
	 * @return
	 */
	public List getDescriptors();
//	public List getExchangeRates();
	
	/**
	 * Returns all states
	 * @return
	 */
//	public List getStates();
	
	/** 
	 * returns all countries
	 * @return
	 */
//	public List getCountries();
	public List getAllCanadaTerritories();
	
	public List getProducerUnboundAudio(Long storeId) ;

	/**
	 * @param clipId
	 * @return
	 */
	public List getClipAudio(Long clipId);

	/**
	 * @return
	 */
	public List getStates();

	/**
	 * @return
	 */
	public List getCountries();

	/**
	 * @param ids 
	 * @return
	 */
	public List getRegistrants(List ids);

	public void updateRegistrants(List<Registrant> registrants);

	/**
	 * @param registrants
	 */
	public void deleteRegistrants(List<Registrant> registrants);
}
