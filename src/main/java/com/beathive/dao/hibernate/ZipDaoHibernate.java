package com.beathive.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Hashtable;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.beathive.model.Zip;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.beathive.dao.ZipDao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.beathive.dao.ZipDao;


/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Zip objects.
 *
 * <p>
 * <a href="ZipDAOHibernate.java.do"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison</a>
 */
public class ZipDaoHibernate extends BaseDaoHibernate implements ZipDao {
    private Log log = LogFactory.getLog(ZipDaoHibernate.class);

    
	public List getUserZips(String username){
		return getHibernateTemplate().find("from Zip z where z.customerName=?" , username);
	}
    /**
     * @see com.beathive.persistence.ZipDAO#getZip(java.lang.String)
     */
    public Zip getZip(Long id) throws ObjectRetrievalFailureException {
        Zip zip = (Zip) getHibernateTemplate().get(Zip.class, id);

        if (zip == null) {
            log.warn("uh oh, zip '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(Zip.class, id);
        }

        return zip;
    }


    /**
     * @see com.beathive.persistence.ZipDAO#getZips(com.beathive.model.Zip)
     */
    public List getZips(Zip zip) {
        return getHibernateTemplate().find("from Zip u order by u.creationTime desc");
    }


    public void updateZip(final Zip zip) throws ObjectRetrievalFailureException {
    		getHibernateTemplate().update(zip);
    		getHibernateTemplate().flush();
    }
    
    /**
     * @throws ZipExistsException 
     * @see com.beathive.persistence.ZipDAO#saveZip(com.beathive.model.Zip)
     * 
     * 
     * if zip exists then we throw an Exception
     */
    public void saveZip(final Zip zip) {

		getHibernateTemplate().saveOrUpdate(zip);
		getHibernateTemplate().flush();
	}

    /**
	 * @see com.beathive.persistence.ZipDAO#removeZip(java.lang.String)
	 */
    public void removeZip(Long id) throws ObjectRetrievalFailureException {
        Zip zip = getZip(id);
        getHibernateTemplate().delete(zip);
    }
	public Zip getZip(String username, String zipname) {
		List zips = getSession().createSQLQuery("select {z.*} from zip z left join user_zip uz on uz.username=:username and z.id = uz.zipId where z.name=:zipname")
		.addEntity("z", Zip.class)
		.setParameter("username", username, Hibernate.STRING)
		.setParameter("zipname", zipname, Hibernate.STRING)
	    .list();
		Object o = null;
		if (zips != null){
			o = zips.get(0);
		}
		return (Zip)o;
	}
	/* (non-Javadoc)
	 * @see com.beathive.dao.ZipDao#getZipByKey(java.lang.String)
	 */
	public Zip getZipByKey(String key) {
		List qres = getHibernateTemplate().find("from Zip where accessKey=?" , key);
		if (qres.isEmpty()){
			return null;
		}
		return (Zip)qres.get(0);
	}
	/* (non-Javadoc)
	 * @see com.beathive.dao.ZipDao#incrementZipAccess(java.lang.Long)
	 */
	public int incrementZipAccess(Long id)  throws ObjectRetrievalFailureException{
		Zip zobject = getZip(id);
		if (zobject!=null){
			int cnt = zobject.getAccessCount().intValue() + 1;
			saveZip(zobject);
			return cnt;
		}
		return 0;
	}


}
