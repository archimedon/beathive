package com.beathive.service;

import java.util.List;

import com.beathive.dao.LookupDao;
import com.beathive.model.Registrant;


/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * <p>
 * <a href="LookupManager.java.html"><i>View Source</i></a>
 * </p>
 *
 */
public interface LookupManager extends Manager {
    //~ Methods ================================================================

    public void setLookupDao(LookupDao dao);
	/**
	 * Returns all states
	 * @return
	 */
	public List getAllStates();
	/**
	 * Retrieves all possible roles from persistence layer
	 * 
	 * @return List
	 */
	public List getAllRoles();

	/**
	 * Retrieves all possible genre from persistence layer
	 * 
	 * @return List
	 */
//	public List getAllFormats();

	/**
	 * Retrieves all possible genre from persistence layer
	 * 
	 * @return List
	 */
	public List getAllGenres();

	/**
	 * Retrieves all possible instruments from persistence layer
	 * 
	 * @return List
	public List getAllInstruments();
	 */

	/**
	 * Retrieves all Instrumentgroups from persistence layer
	 * 
	 * @return List
	 */
// 	public List getAllInstrumentGroups();

	/**
	 * Returns all Timesignatures
	 * 
	 * @return
	 */
 //	public List getAllTimesignatures();

	/**
	 * Returns all scales
	 * 
	 * @return
	 */
 //	public List getAllScales();

	/**
	 * Returns all Keynotes
	 * 
	 * @return
	 */
 //	public List getAllKeynotes();

	/**
	 * Returns a List of all Passage
	 * 
	 * @return List
	 */
 //	public List getAllPassages();

	/**
	 * Returns a List of all Energy
	 * 
	 * @return List
	 */
 //	public List getAllEnergies();

	/**
	 * Returns a List of all Passage
	 * 
	 * @return List
	 */
//	public List getAllFeels();

	/**
	 * Returns a List of all Mood
	 * 
	 * @return List
	 */
//	public List getAllMoods();

	/**
	 * Returns a List of all Origin
	 * 
	 * @return List
	 */
//	public List getAllOrigins();

	/**
	 * Returns a List of all Sonority
	 * 
	 * @return List
	 */
//	public List getAllSonorities();

	/**
	 * Returns a List of all Tempo
	 * 
	 * @return List
	 */
//	public List getAllTempos();

	/**
	 * Returns a List of all Prices
	 * 
	 * @return List
	 */
//	public List getAllPrices();

	/**
	 * Returns a List of all Status
	 * 
	 * @return List
	 */
//	public List getAllStati();

	/**
	 * Returns a List of all Texture
	 * 
	 * @return List
	 */
//	public List getAllTextures();
	/**
	 * Returns a List of all Timespan
	 * @return List
	 */
//	public List getAllTimespans();
	
	/**
	 * Return the Loops picked by the home-producer
	 * @return List
	 */
//	public List getProducerPicks();

	/**
	 * Return the Descriptors
	 * @return List
	 */
	public List getDescriptors();

//	public List getExchangeRates();
	
//	public List getStores();
	/**
	 * 
	 * @return The countries label-value pairs
	 */
	public List getAllCountries();
	
	/**
	 * 
	 * @return
	 */
	public List getAllCanadaTerritories();
	
	public List getProducerUnboundAudio(String storeId);
	/**
	 * @param storeId
	 * @param clipId
	 * @return
	 */
	public List getClipAudio(String clipId);

	public List getRegistrants(List list);
	public void updateRegistrants(List<Registrant> registrants);
	public void deleteRegistrants(List<Registrant> registrants);

}
