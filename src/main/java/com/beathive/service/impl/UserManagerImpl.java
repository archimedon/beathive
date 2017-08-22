package com.beathive.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.displaytag.pagination.PaginatedList;
import org.springframework.security.userdetails.UsernameNotFoundException;
import com.beathive.dao.UserDao;
import com.beathive.model.ChangeEmailKey;
import com.beathive.model.Store;
import com.beathive.model.User;
import com.beathive.model.UserKey;
import com.beathive.service.ServiceException;
import com.beathive.service.UserExistsException;
import com.beathive.service.UserManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;


/**
 * Implementation of UserManager interface.</p>
 * 
 * <p>
 * <a href="UserManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Ronald Dennison
 */
public class UserManagerImpl extends BaseManager implements UserManager {
    private UserDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao
     */
    public void setUserDao(UserDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.beathive.service.UserManager#getUser(java.lang.String)
     */
    public User getUser(String userId) {
        return dao.getUser(new Long(userId));
    }

    /**
     * @see com.beathive.service.UserManager#getUsers(com.beathive.model.User)
     */
    public List getUsers(User user) {
        return dao.getUsers(user);
    }

    /**
     * @see com.beathive.service.UserManager#saveUser(com.beathive.model.User)
     */
    public void saveUser(User user) throws UserExistsException {
    	// if new user, lowercase userId
//    	if (user.getVersion() == null) {
//            user.setUsername(user.getUsername().toLowerCase());
//    	}
        try {
            dao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    /**
     * @see com.beathive.service.UserManager#removeUser(java.lang.String)
     */
    public void removeUser(String userId) {
        if (log.isDebugEnabled()) {
            log.debug("removing user: " + userId);
        }

        dao.removeUser(new Long(userId));
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return (User)dao.loadUserByUsername(username);
    }

	public User loadUserByEmail(String email) throws ServiceException {
	    try {
	    	return dao.loadUserByEmail(email);
	    } catch (ObjectRetrievalFailureException d) {
	    	throw new ServiceException(d.getMessage(), d);
	    }
	}

	public boolean isEmailUnique(String email){
		return dao.isEmailUnique(email);
	}

	public boolean isUserNameUnique(String username){
		return dao.isUserNameUnique(username);
	}

	public boolean areCredentialsUnique(String username, String email){
		return dao.areCredentialsUnique(username , email);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#saveKey(com.beathive.model.UserKey)
	 */
	public void saveKey(UserKey userKey) {
		dao.saveKey(userKey);
	}
	public void deleteKey(UserKey userKey) {
		dao.deleteKey(userKey);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#getKey(java.lang.Class, java.lang.String)
	 */
	public UserKey getKey(String activationKey) {
		// TODO Auto-generated method stub
		return dao.getKey(activationKey);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#upgradeUser(com.beathive.model.User, java.lang.String)
	 */
	public void saveUpgradeUser(User user, String producerRole) {
		dao.saveUpgradeUser(user, producerRole);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#updateUser(com.beathive.model.User)
	 */
	public void updateUser(User user) {
    	// if new user, lowercase userId
//    	if (user.getVersion() == null) {
//            user.setUsername(user.getUsername().toLowerCase());
//    	}
		dao.updateUser(user);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#getfindUsers(com.beathive.model.User, org.displaytag.pagination.PaginatedList)
	 */
	public void findUsers(PaginatedList userPager, boolean b) {
		dao.findUsers(userPager, b);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#getActiveUser(java.lang.Long)
	 */
	public User getActiveUser(Long id) {
		// TODO Auto-generated method stub
		return 	dao.getActiveUser(id);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#deleteChangeEmailKey(com.beathive.model.ChangeEmailKey)
	 */
	public void deleteChangeEmailKey(ChangeEmailKey changeEmailKey) {
		dao.deleteChangeEmailKey(changeEmailKey);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#getChangeEmailKey(java.lang.String)
	 */
	public Object getChangeEmailKey(String activationKey) {
		return 	dao.getChangeEmailKey(activationKey);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.UserManager#saveChangeEmailKey(com.beathive.model.ChangeEmailKey)
	 */
	public void saveChangeEmailKey(ChangeEmailKey changeEmailKey) {
		dao.saveChangeEmailKey(changeEmailKey);
	}
}
