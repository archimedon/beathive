
package com.beathive.dao;

import java.util.List;

import com.beathive.dao.Dao;
import com.beathive.model.Preference;

public interface PreferenceDao extends Dao {

    /**
     * Retrieves all of the preferences
     */
    public List getPreferences(Preference preference);

    /**
     * Gets preference's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if 
     * nothing is found.
     * 
     * @param userId the preference's userId
     * @return preference populated preference object
     */
    public Preference getPreference(final Long userId);

    /**
     * Saves a preference's information
     * @param preference the object to be saved
     */    
    public void savePreference(Preference preference);

    /**
     * Removes a preference from the database by userId
     * @param userId the preference's userId
     */
    public void removePreference(final Long userId);
}

