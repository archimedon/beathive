package com.beathive.dao.hibernate;

import java.util.List;

//import com.beathive.model.Store;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.beathive.dao.LookupDao;
import com.beathive.model.Descriptor;
import com.beathive.model.Registrant;
import com.beathive.model.User;


/**
 * Hibernate implementation of LookupDao.
 *
 * <p><a href="LookupDaoHibernate.java.html"><i>View Source</i></a></p>
 *
 */
public class LookupDaoHibernate extends BaseDaoHibernate implements LookupDao {

    /**
     * @see com.beathive.persistence.LookupDAO#getRoles()
     */
    public List getRoles() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all role names...");
        }
        return getHibernateTemplate().find("from Role r order by r.name");
    }

    public List getInstruments() {
        //"from SoundClip as clip left join fetch clip.format frm where frm.id=?
        return getHibernateTemplate().find("from Instrument as inst inner join fetch inst.instrumentgroup order by inst.sortorder");
    }

    public List getInstrumentgroups() {
	    if (log.isDebugEnabled()) {
	        log.debug("retrieving all instrument names...");
	    }
        return getHibernateTemplate().find("from Instrumentgroup");
    }
    
    public List getFormats() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Format names...");
        }
       return getHibernateTemplate().find("from Format f order by f.sortorder");
    }
    
    public List getGenres() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Genre names...");
        }
       return getHibernateTemplate().find("from Genre g order by g.sortorder");
    }
    
    public List getTimesignatures() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Timesignature names...");
        }
        return getHibernateTemplate().find("from Timesignature ts order by ts.sortorder");
    }
    
    public List getScales() {
    	if (log.isDebugEnabled()) {
            log.debug("retrieving all Scales...");
        }
        return getHibernateTemplate().find("from Scale sc order by sc.sortorder");
    }
    
    public List getKeynotes() {
    	if (log.isDebugEnabled()) {
            log.debug("retrieving all Keynote names...");
        }
        return getHibernateTemplate().find("from Keynote k order by k.sortorder");
    }

	/**
     * Returns a List of all Passage
     * @return List
	 */
	public List getPassages() {
		if (log.isDebugEnabled()) {
            log.debug("retrieving all Passage names...");
        }
        return getHibernateTemplate().find("from Passage k order by k.sortorder");
    }
	
	/**
     * Returns a List of all Energy
     * @return List
	 */
	public List getEnergies() {
		if (log.isDebugEnabled()) {
            log.debug("retrieving all Energy names...");
        }
        return getHibernateTemplate().find("from Energy k order by k.sortorder");
    }
	
	/**
     * Returns a List of all Feel
     * @return List
	 */
	public List getFeels(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Feel names...");
        }
        return getHibernateTemplate().find("from Feel k order by k.sortorder");
    }
	
	/**
     * Returns a List of all Mood
     * @return List
	 */
	public List getMoods(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Mood names...");
        }
        return getHibernateTemplate().find("from Mood k order by k.sortorder");
    }
	/**
     * Returns a List of all Origin
     * @return List
	 */
	public List getOrigins(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Origin names...");
        }
        return getHibernateTemplate().find("from Origin k order by k.sortorder");
    }
	/**
     * Returns a List of all Sonority
     * @return List
	 */
	public List getSonorities(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Sonority names...");
        }
        return getHibernateTemplate().find("from Sonority k order by k.sortorder");
    }
	/**
	 * @return Returns the Tempo.
	 */
	public List getTempos(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Tempo names...");
        }
        return getHibernateTemplate().find("from Tempo t order by t.sortorder");
    }

	/**
     * Returns a List of all Status
     * @return List
	 */
	public List getStati(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Status...");
        }
        return getHibernateTemplate().find("from Status t order by t.id");
    }
	/**
     * Returns a List of all Texture
     * @return List
	 */
	public List getTextures(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Texture names...");
        }
        return getHibernateTemplate().find("from Texture k order by k.sortorder");
    }
	/**
     * Returns a List of all Timespan
     * @return List
	 */
	public List getTimespans(){
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Timespan names...");
        }
        return getHibernateTemplate().find("from Timespan t order by t.sortorder");
    }

	public List getDescriptors(){
		List descs = null;
        if (log.isDebugEnabled()) {
            log.debug("retrieving all Descriptors...");
        }
        try{
        	Criteria crit =  getSession().createCriteria(Descriptor.class)
    		.addOrder(Order.asc("sortorder"))
    		.setLockMode(LockMode.READ)
    		.setCacheable(true);
        	
    		descs = crit.list();
        }catch(Exception e){log.warn(e);}
        return descs;
	}

	public List getStates() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all states ...");
        }
        return getHibernateTemplate().find("from State r order by r.label");
	}
	
	public List getCountries() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all country ...");
        }
        return getHibernateTemplate().find("from Country r order by r.label");
	}
	
	public List getAllCanadaTerritories() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all territory ...");
        }
        return getHibernateTemplate().find("from CanadaTerritory r order by r.label");
	}
	
	public List getProducerUnboundAudio(Long storeId) {
        if (log.isDebugEnabled()) {
            log.debug("retrieving ProducerUnboundAudio ...");
        }
        return getSession().createQuery("from AudioFile where storeId=? and clipId is NULL")
		.setParameter(0, storeId)
		.list();
	}

	/* (non-Javadoc)
	 */
	public List getClipAudio(Long clipId) {
        if (log.isDebugEnabled()) {
            log.debug("retrieving getProducerClipAudio ...");
        }
        return getSession().createQuery("from AudioFile where clipId=?")
		.setParameter(0, clipId)
		.list();
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.LookupDao#getRegistrants()
	 */
	public List getRegistrants(List ids) {
		String sql = "SELECT app_user.id, accesscode, app_user.version, app_user.firstName AS firstName, app_user.lastName AS lastName, app_user.username AS username, app_user.email AS email, app_user.password AS password FROM useraccess inner join app_user on app_user.id=useraccess.userId";
		String ordSql = " ORDER by app_user.account_created desc";
		return getSession().createSQLQuery(sql + ordSql).setResultTransformer(Transformers.aliasToBean(Registrant.class)).list();
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.LookupDao#updateRegistrants(List<Registrant> registrants)
	 */
	public void updateRegistrants(List<Registrant> registrants) {
        User user = null;
        HibernateTemplate tmplt = getHibernateTemplate();

        try{
			for(Registrant reg : registrants ){
		        user = (User) tmplt.get(User.class, new Integer(reg.getId().intValue()));
		        BeanUtils.copyProperties(user, reg);
		        tmplt.update(user);
			}
        } catch (Exception e){
        	log.error(e);
        }
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.LookupDao#deleteRegistrants(java.util.List)
	 */
	public void deleteRegistrants(List<Registrant> registrants) {
		User user = null;
		HibernateTemplate tmplt = getHibernateTemplate();

		for(Registrant reg : registrants ){
			user = (User) tmplt.get(User.class, new Integer(reg.getId().intValue()));
			tmplt.delete(user);
		}
	}
}
