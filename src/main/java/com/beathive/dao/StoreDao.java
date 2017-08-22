/**
 * 
 */
package com.beathive.dao;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.beathive.ExtendedPaginatedList;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.SoundClipQuery;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
import com.beathive.model.User;
import com.beathive.model.UserClipScore;

/**
 * @author ron
 *
 */
public interface StoreDao extends Dao {
	
	public void incrementViews(Long shopId);
	
	public List getUserStores(String username)  throws ObjectRetrievalFailureException;

    public SoundClip getTrackpack(Long clipId , ExtendedPaginatedList paginatedList);
	/**
	 * @param storeId
	 * @param paginatedList
	 * @return
	 */
	public List getLoops(Long storeId, ExtendedPaginatedList paginatedList, Boolean readystat);

	/**
	 * @param storeId
	 * @return
	 */
	public List getTrackpacks(Long storeId, ExtendedPaginatedList paginatedList, Boolean readystat);

    /**
     * Retrieves all of the stores
     */
    public List getStores(Store store);

    /**
     * Gets store's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if 
     * nothing is found.
     * 
     * @param id the store's id
     * @return store populated store object
     */
    public Store getStore(final Long id) throws ObjectRetrievalFailureException;

    /**
     * Saves a store's information
     * @param store the object to be saved
     */    
    public void saveStore(Store store);

    /**
     * Removes a store from the database by id
     * @param id the store's id
     */
    public void removeStore(final Long id);

    public Trackpack getTrackpackComponents(final Long clipId , ExtendedPaginatedList paginatedList);

	/**
	 * @param long1
	 * @param readystat
	 * @return
	 */
	public int getNumLoops(Long long1, Boolean readystat);

	/**
	 * @param long1
	 * @param readystat
	 * @return
	 */
	public int getNumTrackpacks(Long long1, Boolean readystat);
	
	
	public int getNumSold(Long storeId, String type) ;

	/**
	 * @param storeName
	 * @return
	 */
	public Store getStoreByName(String storeName);


}
