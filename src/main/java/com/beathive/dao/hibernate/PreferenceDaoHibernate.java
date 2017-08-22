
package com.beathive.dao.hibernate;

import java.util.List;

import com.beathive.dao.hibernate.BaseDaoHibernate;
import com.beathive.model.Preference;
import com.beathive.dao.PreferenceDao;

import org.springframework.orm.ObjectRetrievalFailureException;

public class PreferenceDaoHibernate extends BaseDaoHibernate implements PreferenceDao {

    /**
     * @see com.beathive.dao.PreferenceDao#getPreferences(com.beathive.model.Preference)
     */
    public List getPreferences(final Preference preference) {
        return getHibernateTemplate().find("from Preference");
    }

    /**
     * @see com.beathive.dao.PreferenceDao#getPreference(Long userId)
     */
    public Preference getPreference(final Long userId) {
        Preference preference = (Preference) getHibernateTemplate().get(Preference.class, userId);
        if (preference == null) {
            log.warn("uh oh, preference with userId '" + userId + "' not found...");
            throw new ObjectRetrievalFailureException(Preference.class, userId);
        }

        return preference;
    }

    /**
     * @see com.beathive.dao.PreferenceDao#savePreference(Preference preference)
     */    
    public void savePreference(final Preference preference) {
        getHibernateTemplate().saveOrUpdate(preference);
    }

    /**
     * @see com.beathive.dao.PreferenceDao#removePreference(Long userId)
     */
    public void removePreference(final Long userId) {
        getHibernateTemplate().delete(getPreference(userId));
    }
}
