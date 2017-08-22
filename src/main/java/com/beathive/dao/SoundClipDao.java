/**
 * 
 */
package com.beathive.dao;

import java.util.List;
import java.util.Map;

import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.model.AudioFile;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.SoundClipQuery;
import com.beathive.model.Store;
import com.beathive.model.User;
import com.beathive.model.UserClipScore;

/**
 * @author ron
 *
 */
public interface SoundClipDao extends Dao {
	
	public SoundClip getSoundClip(Long clipId);

	/**
	 * @param datobj
	 */
	public void saveSoundClip(SoundClip clip);

	/**
	 * This gets soundclips but queries the DB assuming a non-existent viewer (i.e. UID=-1)
	 * Thus, no isOwnedBy, nor isAfavorite etc...
	 * This query is (I think [14 Sept 1008]) class neutral and as such returns
	 * a mixed List 
	 * @param clip
	 * @return
	 */
	public List<SoundClip> getSoundClips(SoundClip clip);
	
	/**
	 * This query is (I think [14 Sept 1008]) class neutral and as such returns
	 * a mixed List 
	 * @param clip
	 * @param params query params
	 * @return
	 */
	public List<SoundClip> getSoundClips(SoundClip aClip, QueryMeta params);
	
	public List<SoundClip> getPaginatedTrackpacks(Loop aClip, ExtendedPaginatedList paginatedList);
	public List<Loop> getPaginatedLoops(Loop aClip, ExtendedPaginatedList paginatedList);
	
	public void saveUserClipScore(final UserClipScore usc);
	
	/**
 	 * Get the top-ten most popular soundclips (BASED on SALES).
 	 * By default returns the top over the last 60 days
	 * If nothing has sold over the past 'interval' days then return empty or NULL
	 * 
	 * TODO Problem getting loops that aren't related by trackpack while still getting TPs
	 * Don't know how to efficiently do a look behind to make sure s.trackpackId is unique in the first select.
	 * May have to split into 5 TP and 5 single loops

	 * @param qClip - providing fetchsize and possibly the viewer's Id
	 * @param days the top seller over an interval of 'days' 
	 * @return
	 */
	public List getTopTenClips(QueryMeta params , int days);	

	/**
	 * 10 most recent uploaded packs
	 * 
	 * @param qClip a query clip contains the viewer (User) mainly for the uid
	 * @param viewer
	 * @return
	 */
	public List getNewestTrackpacks(QueryMeta params);
	
	/**
	 * 5 "top shops" which have sold the most items in the previous 60 days.
	 * This will be the shop banner and description for each.
	 * 
	 * @param qClip a query clip contains the viewer (User) mainly for the uid but other props might be used)
	 * @return
	 */
	public List getTopShop(User viewer);
	
	/**
	 * 1 Random shop. This will be the shop banner and description.
	 * 
	 * @return Store
	 */
	public Store getRandomShop();

	/**
	 * @param audFile
	 */
	public void updateAudio(Map audFile);

	/**
	 * @param checkSum
	 * @return the name of the soundclip if exists
	 */
	public String hasFile(String checkSum);
	
	/* Notes
	 * sortedUsers = s.createFilter( group.getUsers(), "order by this.name" ).list();
	 * sort by name, age, etc at runtime
	 */
	
	/**
	 * returns true if any action taken on the clips score
	 */
	public boolean rateClip(UserClipScore ucs);
	
	public List getTrackpackComponents(Long clipId,	ExtendedPaginatedList componentsPager);

	/**
	 * @param soundClip
	 * @param paginatedList
	 * @return
	 */
	public Integer[] getCounts(Loop soundClip,
			ExtendedPaginatedList paginatedList);
	
    public List findStores(final Loop aClip, ExtendedPaginatedList paginatedList);

    /**
     * Removes a soundClip from the database by clipId
     * @param string the soundClip's clipId
     */
//    public void removeSoundClip(Long clipId, Boolean deep);

	/**
	 * @param cmd
	 */
	public void removeSoundClip(Map cmd);

	/**
	 * @param id
	 * @return
	 */
	public String getClipName(Long id);

}
