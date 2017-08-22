package com.beathive.dao;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.beathive.model.ChangeEmailKey;
import com.beathive.model.User;
import com.beathive.model.UserKey;

/**
 * User Data Access Object (Dao) interface.
 *
 * <p>
 * <a href="UserDao.java.html"><i>View Source</i></a>
 * </p>
 * @author Ronald Dennison
 */

public interface UserDao extends Dao {
    /**
     * Gets users information based on user id.
     * @param userId the user's id
     * @return user populated user object
     */
    public User getUser(Long userId);

    /**
     * Gets users information based on login name.
     * @param username the user's username
     * @return userDetails populated userDetails object
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
    /**
     * Gets a list of users based on parameters passed in.
     *
     * @return List populated list of users
     */
    public List getUsers(User user);

    /**
     * Saves a user's information
     * @param user the object to be saved
     */
    public void saveUser(User user);

    /**
     * Removes a user from the database by id
     * @param userId the user's id
     */
    public void removeUser(Long userId);

	public User loadUserByEmail(String email) throws ObjectRetrievalFailureException;

	public boolean isUserNameUnique(String username);

	public boolean isEmailUnique(String email);

	public boolean areCredentialsUnique(String username, String email);
	public void saveKey(UserKey userKey) ;

	/**
	 * @param userKey
	 */
	public void deleteKey(UserKey userKey);

	/**
	 * @param activationKey
	 * @return
	 */
	public UserKey getKey(String activationKey);

	/**
	 * @param user
	 * @param producerRole
	 */
	public void saveUpgradeUser(User user, String producerRole);

	/**
	 * @param user
	 */
	public void updateUser(User user);

	/**
	 * @param userPager
	 * @param step 
	 */
	public void findUsers(PaginatedList userPager, boolean step);

	/**
	 * @param id
	 * @return
	 */
	public User getActiveUser(Long id);

	/**
	 * @param changeEmailKey
	 */
	public void deleteChangeEmailKey(ChangeEmailKey changeEmailKey);

	/**
	 * @param activationKey
	 * @return
	 */
	public Object getChangeEmailKey(String activationKey);

	/**
	 * @param changeEmailKey
	 */
	public void saveChangeEmailKey(ChangeEmailKey changeEmailKey);
	
}
