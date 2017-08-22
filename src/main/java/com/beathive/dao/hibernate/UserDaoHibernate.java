package com.beathive.dao.hibernate;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.beathive.ExtendedPaginatedList;
import com.beathive.dao.UserDao;
import com.beathive.dao.helper.SimpleOrderCriterion;
import com.beathive.model.ChangeEmailKey;
import com.beathive.model.Role;
import com.beathive.model.UserReport;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
import com.beathive.model.User;
import com.beathive.model.UserKey;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * <p><a href="UserDaoHibernate.java.html"><i>View Source</i></a></p>
 *
 *   Extended to implement Acegi UserDetailsService interface
 *   
 *   Feb 1st 2009 - upgraded to use spring-security
 *   
 * @author rdennison
 *   
*/

public class UserDaoHibernate extends BaseDaoHibernate implements UserDao, UserDetailsService {
    /**
     * @see com.beathive.dao.UserDao#getUser(Long)
     */
    public User getUser(Long userId) {
        User user = (User) getHibernateTemplate().get(User.class, userId);

        if (user == null) {
            log.warn("uh oh, user '" + userId + "' not found...");
            throw new ObjectRetrievalFailureException(User.class, userId);
        }
        return user;
    }

    /**
     * @see com.beathive.dao.UserDao#getUsers(com.beathive.model.User)
     */
    public List getUsers(User user) {
        return getHibernateTemplate().find("from User u order by upper(u.username)");
    }

    /**
     * @see com.beathive.dao.UserDao#saveUser(com.beathive.model.User)
     */
    public void saveUser(final User user) {
        if (log.isDebugEnabled()) {
            log.debug("user's id: " + user.getId());
        }
        getHibernateTemplate().saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
    }

    /**
     * @see com.beathive.dao.UserDao#removeUser(Long)
     */
    public void removeUser(Long userId) {
        getHibernateTemplate().delete(getUser(userId));
    }

    /** 
    * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
    */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getHibernateTemplate().findByNamedQueryAndNamedParam("loadUserByUsername", "username", username);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
        	User user = (User) users.get(0);
        	return (UserDetails) user;
        }
    }

	public User loadUserByEmail(String email) throws ObjectRetrievalFailureException {
		List users = getHibernateTemplate().findByNamedQueryAndNamedParam("loadUserByEmail", "email", email);
	
	    if (users == null ||  users.isEmpty()) {
	        log.warn("uh oh, user with'" + email + "' not found...");
	        throw new ObjectRetrievalFailureException(User.class, email);
	    }
	
	    return (User) users.get(0);
	}

	public boolean areCredentialsUnique(String username, String email) {
		return getSession().createQuery("from User where username=? or email=?")
			.setParameter(0, username)
			.setParameter(1, email)
			.list().isEmpty();
	}
	
	public void saveKey(UserKey userKey) {
		getHibernateTemplate().save(userKey);
	}
	
	public boolean isEmailUnique(String email) {
		return (0==
			((Integer)getSession().createCriteria(User.class)
		.add(Restrictions.eq("email",email))
		.setProjection(Projections.rowCount()
			).list().get(0)).intValue()
		);
	}

	public boolean isUserNameUnique(String username) {
		return ( 0 == ((Integer)getSession().createCriteria(User.class)
		.add(Restrictions.eq("username",username))
		.setProjection(
			Projections.projectionList()
			.add( Projections.rowCount())
			).list().get(0)).intValue()
		);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#deleteKey(com.beathive.model.UserKey)
	 */
	public void deleteKey(UserKey userKey) {
		getHibernateTemplate().delete(userKey);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#getKey(java.lang.String)
	 */
	public UserKey getKey(String activationKey) {
		// TODO Auto-generated method stub
		return (UserKey) getHibernateTemplate().get(UserKey.class, activationKey);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#upgradeUser(com.beathive.model.User, java.lang.String)
	 */
	public void saveUpgradeUser(User user, String producerRole) {
		// upgrade user
		List roles = getHibernateTemplate().find("from Role where name=?", producerRole);
        if (!roles.isEmpty()) {
        	user.addRole((Role) roles.get(0));		
        	getHibernateTemplate().update(user);
        	log.info("userRoles for [" + user.getId() + "] now: " + user.getRoles());
            // necessary to throw a DataIntegrityViolation and catch it in UserManager
            getHibernateTemplate().flush();
        }
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#updateUser(com.beathive.model.User)
	 */
	public void updateUser(User user) {
		log.info("updateUser [" + user.getId() + "]");
        getHibernateTemplate().update(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#getfindUsers(com.beathive.model.User, org.displaytag.pagination.PaginatedList)
	 */
	public void findUsers(PaginatedList pgr, boolean step) {
		UserReport userPager = (UserReport)pgr;
    	int fetchSize = userPager.getObjectsPerPage();
    	int firstIndex = userPager.getFirstRecordIndex();//.getPageNumber() - 1;
    	Session session = getSession();

		SortOrderEnum sortDirection = userPager.getSortDirection();
		boolean dir = sortDirection.equals(SortOrderEnum.ASCENDING);
		
		Criteria crit = session.createCriteria(User.class).setCacheable(true);
		List<Criterion> linked = new LinkedList();
    	if (userPager.getStartDate()!=null &&  userPager.getEndDate() != null) {
    		linked.add(Restrictions.between("created" , userPager.getStartDate() , userPager.getEndDate()));
    	}
    	if (StringUtils.isNotBlank(userPager.getUsername())) {
    		linked.add(Restrictions.like("username" , userPager.getUsername() , MatchMode.ANYWHERE));
    	}
    	if (StringUtils.isNotBlank(userPager.getFirstName())) {
    		linked.add(Restrictions.like("firstName" , userPager.getFirstName() , MatchMode.ANYWHERE));
    	}
    	if (StringUtils.isNotBlank(userPager.getLastName())) {
    		linked.add(Restrictions.like("lastName" , userPager.getLastName() , MatchMode.ANYWHERE));
    	}
    	if (StringUtils.isNotBlank(userPager.getRoleName())) {
    		crit.createAlias("roles", "role")
    		.add(Restrictions.eq("role.name", userPager.getRoleName()));
    	}
    	
    	for(Criterion c : linked){
    		crit.add(c);
    	}
    	
		int cnt = (
			(Integer) crit.setProjection(Projections.projectionList().add( Projections.rowCount()))
			.list().iterator().next()
		).intValue();
		
    	crit = session.createCriteria(User.class)
		.setCacheable(true);
    	if (userPager.getRoleName() !=null) {
    		crit.createAlias("roles", "role")
    		.add(Restrictions.eq("role.name", userPager.getRoleName()));
    	}

    	for (Criterion c : linked) {
    		crit.add(c);
    	}
    	
		crit.addOrder(new SimpleOrderCriterion(userPager.getSortCriterion() , dir));
    	if (step) {
    		crit.setFirstResult(firstIndex).setFetchSize(fetchSize).setMaxResults(fetchSize);
    	}
    	List users = crit.list();
    	
    	userPager.setFullListSize(cnt);
    	userPager.setList(users);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#getActiveUser(java.lang.Long)
	 */
	public User getActiveUser(Long id) {
		Criteria crit = getSession().createCriteria(User.class).setCacheable(true);
		List users = crit
		.setFetchMode("cartItems", FetchMode.JOIN)
		.add(Restrictions.idEq(id)).list();
		User  user = (User)users.get(0);
		return user;
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#deleteChangeEmailKey(com.beathive.model.ChangeEmailKey)
	 */
	public void deleteChangeEmailKey(ChangeEmailKey changeEmailKey) {
		getHibernateTemplate().delete((ChangeEmailKey)getHibernateTemplate().get(ChangeEmailKey.class, changeEmailKey.getAccesscode()));
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#getChangeEmailKey(java.lang.String)
	 */
	public Object getChangeEmailKey(String activationKey) {
		return (ChangeEmailKey)getHibernateTemplate().get(ChangeEmailKey.class, activationKey);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.UserDao#saveChangeEmailKey(com.beathive.model.ChangeEmailKey)
	 */
	public void saveChangeEmailKey(ChangeEmailKey changeEmailKey) throws org.springframework.dao.DataIntegrityViolationException {
		try{
			getHibernateTemplate().save(changeEmailKey);
		}catch(	org.hibernate.exception.ConstraintViolationException ce){
			throw new org.springframework.dao.DataIntegrityViolationException(ce.getMessage());
		}
	}
}
