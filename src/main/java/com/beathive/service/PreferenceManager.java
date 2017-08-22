
package com.beathive.service;

import java.util.List;

import com.beathive.service.Manager;
import com.beathive.model.Preference;
import com.beathive.dao.PreferenceDao;

public interface PreferenceManager extends Manager {
    /**
     * Retrieves all of the preferences
     */
    public List getPreferences(Preference preference);

    /**
     * Gets preference's information based on userId.
     * @param userId the preference's userId
     * @return preference populated preference object
     */
    public Preference getPreference(final String userId);

    /**
     * Saves a preference's information
     * @param preference the object to be saved
     */
    public void savePreference(Preference preference);

    /**
     * Removes a preference from the database by userId
     * @param userId the preference's userId
     */
    public void removePreference(final String userId);
}

