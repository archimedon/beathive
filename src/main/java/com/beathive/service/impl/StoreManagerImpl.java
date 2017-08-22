package com.beathive.service.impl;

import java.util.List;

import org.springframework.security.userdetails.UsernameNotFoundException;

import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.dao.StoreDao;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
import com.beathive.service.NotFoundException;
import com.beathive.service.StoreManager;


/**
 * Implementation of UserManager interface.</p>
 * 
 * <p>
 * <a href="UserManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Ronald Dennison
 */
public class StoreManagerImpl extends BaseManager implements StoreManager {
    private StoreDao storeDao;
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#incrementViews(java.lang.String)
	 */
	public void incrementViews(String shopId) {
		storeDao.incrementViews(new Long(shopId));
	}
	public StoreDao getStoreDao() {
		return storeDao;
	}
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}
    public SoundClip getTrackpack(Long clipId , ExtendedPaginatedList paginatedList){
		return storeDao.getTrackpack(clipId, paginatedList);

    }
	public List getUserStores(String username) throws NotFoundException {
		try{
			return  storeDao.getUserStores(username);
		}catch(Exception e){
			throw new NotFoundException("error.user.stores" , e);
		}
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getLoops(java.lang.String)
	 */
	public List getLoops(String storeIdstr, ExtendedPaginatedList paginatedList) {
		return storeDao.getLoops(new Long(storeIdstr) , paginatedList, null);
	}
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getTrackpacks(java.lang.String)
	 */
	public List getTrackpacks(String storeIdstr, ExtendedPaginatedList paginatedList) {
		return storeDao.getTrackpacks(new Long(storeIdstr), paginatedList, null);
	}
	
	public List getLoops(String storeIdstr, ExtendedPaginatedList paginatedList  , Boolean readystat) {
		return storeDao.getLoops(new Long(storeIdstr) , paginatedList, readystat);
	}
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getTrackpacks(java.lang.String)
	 */
	public List getTrackpacks(String storeIdstr, ExtendedPaginatedList paginatedList, Boolean readystat) {
		return storeDao.getTrackpacks(new Long(storeIdstr), paginatedList, readystat);
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getLoops(java.lang.String)
	 */
	public List getRefPackagedLoops(String storeIdstr, ExtendedPaginatedList paginatedList, Boolean readystat) {
		return storeDao.getLoops(new Long(storeIdstr) , paginatedList, readystat);
	}
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getTrackpacks(java.lang.String)
	 */
	public List getRefPackagedTrackpacks(String storeIdstr, ExtendedPaginatedList paginatedList, Boolean readystat) {
		return storeDao.getTrackpacks(new Long(storeIdstr), paginatedList , readystat);
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getLoops(java.lang.String)
	 */
	public int getNumRefPackagedLoops(String storeIdstr, Boolean readystat) {
		return storeDao.getNumLoops(new Long(storeIdstr) , readystat);
	}
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getTrackpacks(java.lang.String)
	 */
	public int getNumRefPackagedTrackpacks(String storeIdstr,  Boolean readystat) {
		return storeDao.getNumTrackpacks(new Long(storeIdstr) , readystat);
	}
	
    /**
     * @see com.beathive.service.StoreManager#getStores(com.beathive.model.Store)
     */
    public List getStores(final Store store) {
        return storeDao.getStores(store);
    }

    /**
     * @see com.beathive.service.StoreManager#getStore(String id)
     */
    public Store getStore(final String id) {
        return storeDao.getStore(new Long(id));
    }

    /**
     * @see com.beathive.service.StoreManager#saveStore(Store store)
     */
    public void saveStore(Store store) {
    	storeDao.saveStore(store);
    }

    /**
     * @see com.beathive.service.StoreManager#removeStore(String id)
     */
    public void removeStore(final String id) {
    	storeDao.removeStore(new Long(id));
    }
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#findStores(com.beathive.model.Loop, com.beathive.ExtendedPaginatedList)
	 */
//	public List findStores(Loop clip, ExtendedPaginatedList paginatedList) {
//		// TODO Auto-generated method stub
//		return storeDao.findStores(clip, paginatedList);
//	}
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getTrackpackComponents(java.lang.String, com.beathive.ExtendedPaginatedList)
	 */
	public Trackpack getTrackpackComponents(String clipId,
			ExtendedPaginatedList paginatedList) {
		return storeDao.getTrackpackComponents(new Long(clipId), paginatedList);
	}
	
	public int getNumSold(String storeId, String type) {
		return storeDao.getNumSold(new Long(storeId), type) ;
	}
	/* (non-Javadoc)
	 * @see com.beathive.service.StoreManager#getStoreByName(java.lang.String)
	 */
	public Store getStoreByName(String storeName) {
		return storeDao.getStoreByName(storeName);
	}

}
