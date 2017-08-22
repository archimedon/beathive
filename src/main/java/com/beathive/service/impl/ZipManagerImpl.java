package com.beathive.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.beathive.model.Zip;
import com.beathive.service.ServiceException;
import com.beathive.dao.ZipDao;

import com.beathive.service.ServiceException;
import com.beathive.service.ZipManager;

public class ZipManagerImpl extends BaseManager implements ZipManager {
	    private Log log = LogFactory.getLog(ZipManagerImpl.class);
	    private ZipDao dao;

    /**
     * Set the DAO for communication with the data layer.
     * @param dao
     */
    public void setZipDao(ZipDao dao) {
        this.dao = dao;
    }

	public Zip getZip(Long id) throws ServiceException {
		try {
			return dao.getZip(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public List getZips(Zip zip) {
		return dao.getZips(zip);
	}

	public void saveZip(Zip zip) {
		dao.saveZip(zip);
	}

	public void removeZip(Long id) throws ServiceException {
		try {
			dao.removeZip(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public List getUserZips(String username) {
		return dao.getUserZips(username);
	}

	public Zip getZip(String username, String zipname) {
		return dao.getZip(username , zipname);
	}

	public Zip getZipByKey(String key) {
		return dao.getZipByKey(key);
	}

	public int incrementZipAccess(Long id) throws ServiceException {
		return dao.incrementZipAccess(id);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.ZipManager#getZip(java.lang.String)
	 */
	public Zip getZip(String id) {
		try {
			return dao.getZip(new Long(id));
		} catch (Exception e) {
			return null;
		}
	}

}
