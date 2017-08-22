
package com.beathive.service.impl;

import java.util.List;

import com.beathive.service.impl.BaseManager;
import com.beathive.model.Preference;
import com.beathive.dao.PreferenceDao;
import com.beathive.service.PreferenceManager;

public class PreferenceManagerImpl extends BaseManager implements PreferenceManager {
    private PreferenceDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao
     */
    public void setPreferenceDao(PreferenceDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.beathive.service.PreferenceManager#getPreferences(com.beathive.model.Preference)
     */
    public List getPreferences(final Preference preference) {
        return dao.getPreferences(preference);
    }

    /**
     * @see com.beathive.service.PreferenceManager#getPreference(String userId)
     */
    public Preference getPreference(final String userId) {
        return dao.getPreference(new Long(userId));
    }

    /**
     * @see com.beathive.service.PreferenceManager#savePreference(Preference preference)
     */
    public void savePreference(Preference preference) {
        dao.savePreference(preference);
    }

    /**
     * @see com.beathive.service.PreferenceManager#removePreference(String userId)
     */
    public void removePreference(final String userId) {
        dao.removePreference(new Long(userId));
    }
}
