package com.beathive.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.model.AudioFile;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.UserClipScore;

public interface SoundClipManager extends Manager {
    /**
     * Retrieves all of the loops
     */
//    public List getLoops(Loop loop);
    public List getPaginatedLoops(Loop loop, ExtendedPaginatedList paginatedList);
    
    /**
     * Trackpacks selection is also affected by it's component loops
     * @param loop
     * @param paginatedList
     * @return
     */
    public List getTrackpacks(Loop loop, ExtendedPaginatedList paginatedList);
	/**
	 * Returns a list of either Trackpacks , Loops or both
	 * depending on the actual class of the argument
	 * 
	 * @param SoundClip a criteria clip
	 * @param QueryMeta contains info about the viewer and fetch parameters
	 */
    public List getSoundClips(SoundClip clip, QueryMeta qm);
    
	/**
	 * Returns a list of either Trackpacks or Loops
	 * determined by the sub-class of the clip
	 * @param SoundClip a criteria clip
	 * @param QueryMeta contains info about the viewer and fetch parameters
	 */
    public List getSoundClips(SoundClip clip);
    
    public SoundClip getSoundClip(final String clipId);
   
    /**
     * Gets loop's information based on clipId.
     * @param clipId the soundClip's clipId
     * @return soundClip populated soundClip object
     */
    public Loop getLoop(final String loopId);

    /**
     * Saves a soundClip's information
     * @param soundClip the object to be saved
     */
    public void saveLoop(Loop loop);

    /**
     * Saves a soundClip's information
     * @param soundClip the object to be saved
     */
    public void saveSoundClip(SoundClip soundClip);
    
    
    public List  getNewestTrackpacks(QueryMeta qm);
////    public Store getRandomShop();
//    public SoundClip getSoundClip(Long clipId);
    public List getTopShop(QueryMeta qm);
    public List getTopTenClips();
    public List getTopTenClips(QueryMeta qm , int numdays);
//    public void saveUserClipScore(UserClipScore userclipscore);
    
    /**
     * Say "hello" to remote clients
     */
    public String greet();
    
    /**
     * Saves a soundClip's information
     * @param soundClip the object to be saved
     */
    public Object saveUserUploads(Map uploadClips);
 
    /**
     * saves and notifies the user
     * 
     * @param clips
     * @param userId
     * @return
     */
    public Map saveUserUploads(Map clips, String userId);
    
	/**
	 * Updates an individual 
	 * @param audiofile
	 * @return
	 */
	public Object updateUserClip(Map audiofile);
	
	

	/**
	 * @param checkSum
	 * @return the name of the soundclip if exists
	 */
	public String hasFile(String checkSum);
	
	
	public boolean[] filesExist(String[] ppaths);
	
	/**
	 * Creates a zip file at 'destfilename'. The contents of the zip are taken from the zipdata 
	 * 
	 * @param destfilename
	 * @param zipdata - virtual_zippath : src_ppath 
	 */
	public boolean zipem(String destfilename,  Map zipdata);

	/**
	 * returns true if any action taken on the clips score
	 */
	public boolean rateClip(UserClipScore ucs);

	/**
	 * @param clipId
	 * @param componentsPager
	 * @return
	 */
	public List getTrackpackComponents(String clipId,
			ExtendedPaginatedList componentsPager);

	/**
	 * @param soundClip
	 * @param paginatedList
	 * @return
	 */
	public Integer[] getCounts(Loop soundClip,
			ExtendedPaginatedList paginatedList);
	
	/**
	 * The root directory for user downloads
	 * @return
	 */
	public String getUsersDownloadDir();
	
  public List findStores(final Loop aClip, ExtendedPaginatedList paginatedList);

/**
 * @param hm
 */
public void removeSoundClip(Map hm);

/**
 * Removes a soundClip from the database by clipId
 * @param string the soundClip's clipId
 */
//public void removeSoundClip(final String string);

/**
 * removes Soundclip. If clip is a Trackpack delete 
 * @param clipId
 * @param deep
 */
//public void removeSoundClip(final String clipId, Boolean deep) ;


}

