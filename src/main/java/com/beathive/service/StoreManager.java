/**
 * 
 */
package com.beathive.service;

import java.util.List;

import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;

/**
 * @author ron
 *
 */
public interface StoreManager extends Manager {

	/**
	 * @param shopId
	 */
	public void incrementViews(String shopId);
    public SoundClip getTrackpack(Long clipId , ExtendedPaginatedList paginatedList);

	/**
	 * @param username
	 * @return
	 */
	public List getUserStores(String username) throws NotFoundException ;
	
//	/**
//	 * @param storeIdstr
//	 * @return
//	 */
//	public List getLoops(String storeIdstr);
//
//	/**
//	 * @param storeIdstr
//	 * @return
//	 */
//	public List getTrackpacks(String storeIdstr);

    /**
     * Retrieves all of the stores
     */
    public List getStores(Store store);

    /**
     * Gets store's information based on id.
     * @param id the store's id
     * @return store populated store object
     */
    public Store getStore(final String id);

    /**
     * Saves a store's information
     * @param store the object to be saved
     */
    public void saveStore(Store store);

    /**
     * Removes a store from the database by id
     * @param id the store's id
     */
    public void removeStore(final String id);

	/**
	 * @param storeId
	 * @param paginatedList
	 * @return
	 */
	public List getLoops(String storeId, ExtendedPaginatedList paginatedList);

	/**
	 * @param loop
	 * @param qm
	 * @return
	 */
	public List getTrackpacks(String storeId, ExtendedPaginatedList paginatedList);

	
	public List getLoops(String storeIdstr, ExtendedPaginatedList paginatedList  , Boolean readystat);
	public List getTrackpacks(String storeIdstr, ExtendedPaginatedList paginatedList, Boolean readystat);

	
    public Trackpack getTrackpackComponents(final String clipId , ExtendedPaginatedList paginatedList);
    
    
    
	public List getRefPackagedLoops(String storeIdstr, ExtendedPaginatedList paginatedList, Boolean readystat);
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getTrackpacks(java.lang.String)
	 */
	public List getRefPackagedTrackpacks(String storeIdstr, ExtendedPaginatedList paginatedList, Boolean readystat);
	
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getLoops(java.lang.String)
	 */
	public int getNumRefPackagedLoops(String storeIdstr, Boolean readystat);
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getTrackpacks(java.lang.String)
	 */
	public int getNumRefPackagedTrackpacks(String storeIdstr,  Boolean readystat);
	
	public int getNumSold(String storeId, String type);
	/**
	 * @param parameter
	 * @return
	 */
	public Store getStoreByName(String parameter);


}
