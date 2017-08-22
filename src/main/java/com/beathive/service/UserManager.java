package com.beathive.service;

import java.util.List;
import java.util.Set;

import org.displaytag.pagination.PaginatedList;
import org.springframework.security.userdetails.UsernameNotFoundException;
import com.beathive.dao.UserDao;
import com.beathive.model.ChangeEmailKey;
import com.beathive.model.User;
import com.beathive.model.UserKey;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * <p><a href="UserManager.java.html"><i>View Source</i></a></p>
 */
public interface UserManager extends Manager{
    
    public void setUserDao(UserDao userDao);

    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId
     * @return User
     */
    public User getUser(String userId);
    
    /**
     * Finds a user by their username.
     * @param username
     * @return User a populated user object
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Retrieves a list of users, filtering with parameters on a user object
     * @param user parameters to filter on
     * @return List
     */
    public List getUsers(User user);

    /**
     * Saves a user's information
     *
     * @param user the user's information
     * @throws UserExistsException
     */
    public void saveUser(User user) throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    public void removeUser(String userId);

	public User loadUserByEmail(String email) throws ServiceException;

	//~ Methods ================================================================
	
	public boolean isEmailUnique(String email);

	public boolean isUserNameUnique(String username);

	public boolean areCredentialsUnique(String username, String email);
	
	public void saveKey(UserKey userKey) ;
	public void deleteKey(UserKey userKey) ;

	/**
	 * @param class1
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
	 * @param b 
	 */
	public void findUsers(PaginatedList userPager, boolean b);

	/**
	 * @param id
	 * @return
	 */
	public User getActiveUser(Long id);

	/**
	 * @param activationKey
	 * @return
	 */
	public Object getChangeEmailKey(String activationKey);

	/**
	 * @param changeEmailKey
	 */
	public void deleteChangeEmailKey(ChangeEmailKey changeEmailKey);

	/**
	 * @param changeEmailKey
	 */
	public void saveChangeEmailKey(ChangeEmailKey changeEmailKey);
}
