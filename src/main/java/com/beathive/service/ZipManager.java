package com.beathive.service;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.beathive.model.Zip;


/**
 * Business Delegate (Proxy) Interface to handle communication between web and
 * persistence layer. 
 *
 * <p>
 * <a href="ZipManager.java.do"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison</a>
 * @version $Revision: 1.6 $ $Date: 2004/05/25 06:27:20 $
 */
public interface ZipManager {
    //~ Methods ================================================================

    /**
     * Retrieves a zip by id.  An exception is thrown if now zip 
     * is found.
     *
     * @param id
     * @return Zip
     * @throws ServiceException
     */
    public Zip getZip(Long id) throws ServiceException;
    
    /**
     * @param id
     * @return
     */
    public Zip getZip(String id);

    /**
     * Retrieves a list of zips, filtering with parameters on a zip object
     * @param zip parameters to filter on
     * @param zip
     * @return List
     * @throws ServiceException
     */
    public List getZips(Zip zip);

    /**
     * Saves a zip's information
     *
     * @param zip the zip's information
     * @return updated zip information
     * @throws ServiceException
     * @throws ZipExistsException 
     */
    public void saveZip(Zip zip);

    /**
     * Removes a zip from the database by their zipname
     *
     * @param zip the zip's zipname
     * @throws ServiceException
     */
    public void removeZip(Long id) throws ServiceException;

	public List getUserZips(String username);

	public Zip getZip(String username, String zipname);
    
	public Zip getZipByKey(String key);
	public int incrementZipAccess(Long id) throws ServiceException;


}
