
package com.beathive.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;
import com.beathive.dao.SoundClipDao;
import com.beathive.dao.UserDao;
import com.beathive.model.AudioFile;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.Trackpack;
import com.beathive.model.User;
import com.beathive.model.UserClipScore;
import com.beathive.service.MailEngine;
import com.beathive.service.ServiceException;
import com.beathive.service.SoundClipManager;
import com.beathive.util.ConvertUtil;

public class SoundClipManagerImpl extends BaseManager implements SoundClipManager {
    private SoundClipDao soundClipDao;
    private UserDao userDao;
    private MailEngine mailEngine;
    private String audioFileRoot;
    private String usersDownloadDir;

    private static int BUFFER_SIZE = 1024;
    private static Double LOOPPRICE = new Double(Constants.BASE_LOOP_PRICE);
    private static Double TRACKPACKPRICE = new Double(Constants.BASE_LOOP_PRICE);
    /**
     * Set the Dao for communication with the data layer.
     * @param soundClipDao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Set the Dao for communication with the data layer.
     * @param soundClipDao
     */
    public void setSoundClipDao(SoundClipDao soundClipDao) {
        this.soundClipDao = soundClipDao;
    }

    /**
     * @see com.beathive.service.SoundClipManager#getSoundClips(com.beathive.model.SoundClip)
     */
    public List getSoundClips(SoundClip soundClip) {
    	List clips =  soundClipDao.getSoundClips(soundClip);
        return clips;
    }

    /**
     * @see com.beathive.service.SoundClipManager#getSoundClip(final String clipId)
     */
    public SoundClip getSoundClip(final String clipId) {
        return soundClipDao.getSoundClip(new Long(clipId));
    }

    /**
     * @see com.beathive.service.SoundClipManager#saveSoundClip(SoundClip soundClip)
     */
    public void saveSoundClip(SoundClip soundClip) {
    	soundClipDao.saveSoundClip(soundClip);
    }

    /**
     * @see com.beathive.service.SoundClipManager#removeSoundClip(Map settings)
     */
    public void removeSoundClip(Map settings) {
    	soundClipDao.removeSoundClip(settings);
    }
    
	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getLoop(java.lang.String)
	 */
	public Loop getLoop(String loopId) {
		return null;
//		return soundClipDao.getLoop(new Long(loopId));
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getLoops(com.beathive.model.Loop, com.beathive.model.QueryMeta)
	 */
	public List getPaginatedLoops(Loop loop, ExtendedPaginatedList paginatedList) {
		return soundClipDao.getPaginatedLoops(loop, paginatedList);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getSoundClips(com.beathive.model.SoundClip, com.beathive.model.QueryMeta)
	 */
	public List getSoundClips(SoundClip clip, QueryMeta qm) {
		// TODO Auto-generated method stub
		return soundClipDao.getSoundClips(clip , qm);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getTrackpacks(com.beathive.model.Loop, com.beathive.model.QueryMeta)
	 */
	public List getTrackpacks(Loop loop, ExtendedPaginatedList paginatedList) {
		return soundClipDao.getPaginatedTrackpacks(loop , paginatedList);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#saveLoop(com.beathive.model.Loop)
	 */
	public void saveLoop(Loop soundClip) {
		soundClipDao.saveSoundClip(soundClip);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getTopShop(com.beathive.model.QueryMeta)
	 */
	public List getTopShop(QueryMeta qm) {
		// TODO Auto-generated method stub
		if (qm == null){
			qm= new QueryMetaImpl();
		}
		return soundClipDao.getTopShop(qm.getViewer());
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getTopTenClips(com.beathive.model.QueryMeta, int)
	 */
	public List getTopTenClips(QueryMeta qm, int numdays) {
		if (qm == null){
			qm= new QueryMetaImpl();
		}
		return soundClipDao.getTopTenClips(qm, numdays);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getTopTenClips()
	 */
	public List getTopTenClips() {
		return soundClipDao.getTopTenClips(new QueryMetaImpl() , 500);
	}
	
	
	 public String greet(){
		 return "HELO";
	 }

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#saveUploads(java.util.HashMap)
	 */
	public Object saveUserUploads(Map clips) {

		List touched = new LinkedList();

		try {
			Date date = new Date();
			Iterator it = clips.values().iterator();
			while (it.hasNext()) {
				Map clip = (Map) it.next();
				if (((String) clip.get("type")).equalsIgnoreCase("trackpack")) {
					Trackpack tp = convertUploadPack(clip, date);
					if (tp.getId()!=null){
						Map map = BeanUtils.describe(tp);
						touched.add(map);
					}
				} else {
					Loop loop = convertUploadLoop(clip, date);
					if (loop.getId()!=null){
						Map map = BeanUtils.describe(loop);
						touched.add(map);
					}				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return touched;
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#saveUploads(java.util.HashMap)
	 */
	public Map saveUserUploads(Map clips, String userId) {

		List resp = (List)saveUserUploads(clips);
		Map data = null;
		User user = userDao.getUser(new Long(userId));
		Map o = (Map)clips.values().iterator().next();
		String storeId = (String)o.get("storeId");
		try {
			data = BeanUtils.describe(user);
			data.remove("roles");
			data.remove("clipScore");
			data.remove("stores");
			data.remove("purchases");
			data.remove("archives");
			data.remove("favorites");

			data.put("loops" , resp);
			data.put("storeId" , storeId);
			
			String from = "Client Admin <support@beathive.com>";
			String to = user.getUsername() + " <" + user.getEmail() + ">";
			String subject = "Store updated";
			String template = "storeUpdated.vm";
			
			mailEngine.sendMessage(from, to, subject, template, data);
		} catch (Exception e) {
			log.error(e);
		}
		return data;
	}
	
	/**
	 * Updates an individual
	 * 
	 * @param audiofile
	 * @return
	 */
	public Object updateUserClip(Map audiofile) {
				soundClipDao.updateAudio(audiofile);
		return "audiofile:" + audiofile.toString();
		
	}
	
	/**
	 * @param loop
	 * @return
	 * @throws Exception 
	 */
	private Loop convertUploadLoop(Map loop , Date date) throws Exception {
		Loop inner = new Loop();

		BeanUtils.copyProperties(inner , loop);
		Set<AudioFile> formats = (Set)ConvertUtil.convertList(inner.getAudioFormat() , AudioFile.class);
		String tmp = null;
		for (AudioFile auf : formats){
			if(StringUtils.isNotBlank(hasFile(auf.getCheckSum()))){
				auf.setCheckSum("late_stage_duplicate_" + Calendar.getInstance().getTimeInMillis() );
			}
			renameFileToBH(auf);
		}
		inner.getPrice().setAmount(LOOPPRICE);
		inner.setAudioFormat(formats);
		inner.setCreated(date);
		inner.setModified(date);
		soundClipDao.saveSoundClip(inner);
		formats = inner.getAudioFormat();
		for (AudioFile auf : formats){
			auf.setClipId(inner.getId());
		}
		soundClipDao.saveSoundClip(inner);

		return inner;
	}
	
	private Trackpack convertUploadPack(Map val , Date date) throws Exception {
		Trackpack trackpack = new Trackpack();
		BeanUtils.copyProperties( trackpack , val);
		trackpack.setCreated(date);
		trackpack.setModified(date);
		Set frms = trackpack.getAudioFormat();
		
		Set<AudioFile> formats = (Set)ConvertUtil.convertList(frms , AudioFile.class);
		List loops = trackpack.getLoops();
		String tmp = null;
		
		for (AudioFile auf : formats){

			if(StringUtils.isNotBlank(hasFile(auf.getCheckSum()))){
				auf.setCheckSum("late_stage_duplicate_" + Calendar.getInstance().getTimeInMillis() );
			}
			renameFileToBH(auf);
		}
		trackpack.setAudioFormat(formats);
		trackpack.setLoops(null);
		trackpack.getPrice().setAmount(TRACKPACKPRICE);
		soundClipDao.saveSoundClip(trackpack);
		List asLoops = new LinkedList();
		for(int y = 0 ; y < loops.size(); y++){
			Map inner = (Map)loops.get(y);
			asLoops.add(convertUploadLoop(inner , date));
		}
		Long clipId = trackpack.getId();

		trackpack.setLoops(asLoops);
		formats = trackpack.getAudioFormat();
		for (AudioFile auf : formats){
			auf.setClipId(clipId);
		}

		soundClipDao.saveSoundClip(trackpack);
		return trackpack;
	}

	
	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getNewestTrackpacks(com.beathive.model.QueryMeta)
	 */
	public List getNewestTrackpacks(QueryMeta qm) {
		// TODO Auto-generated method stub
		return soundClipDao.getNewestTrackpacks(qm);
	}

	/**
	 * @param mailEngine the mailEngine to set
	 */
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#isOriginal(java.lang.String[])
	 */
	/**
	 * @param checkSum
	 * @return
	 */
	public String hasFile(String checkSum){
		return soundClipDao.hasFile(checkSum);
	}
	
	public boolean[] filesExist(String[] ppaths){
		boolean[] status = new boolean[ppaths.length];
		for (int i = 0 ; i < ppaths.length; i++){
			status[i] = (new File(audioFileRoot + ppaths[i])).exists();
		}
		return status;
	}

	public void renameFilesToBH(Set<AudioFile> fileSet){
		for(AudioFile af : fileSet){
			renameFileToBH(af);
		}
	}
	
	public void renameFileToBH(AudioFile af ){
		// the user tomcat does not have copy permissions on mounted volume
/*		File ofile = new File(audioFileRoot + af.getFileRef());
		System.err.println("infile: " + ofile.getAbsolutePath());
		File dest = new File(ofile.getParent() + Constants.FILE_SEP + "BH_" + ofile.getName());
		try {
			org.apache.commons.io.FileUtils.copyFile(ofile , dest);
			af.setFileRef(dest.getAbsolutePath().replace(audioFileRoot, ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("out ppath: " + af.getFileRef());
*/	}
	
	/**
	 * @return the audioFileRoot
	 */
	public String getAudioFileRoot() {
		return audioFileRoot;
	}

	/**
	 * @param audioFileRoot the audioFileRoot to set
	 */
	public void setAudioFileRoot(String audioFileRoot)  throws ServiceException{
		this.audioFileRoot = audioFileRoot;
		boolean ok = false;
		try{
			ok = (new File(this.audioFileRoot)).isDirectory();			
		}catch(SecurityException e){
			throw new ServiceException(e);
		}
		if (!ok){
			throw new ServiceException("directory: audioFileRoot'" +audioFileRoot +"' does not exist");
		}

	}


	private BufferedInputStream getFileStream(String filepath) throws FileNotFoundException{
		System.err.println("REPO: filepath" + filepath);
		return new BufferedInputStream(new FileInputStream(filepath), BUFFER_SIZE);
	}
	/**
	 * Simply zips a collection.
	 */
	public boolean zipem(String destfilename,  Map zdata) {
		try {
			// Reference to the file we will be adding to the zipfile
			BufferedInputStream origin = null;
			// Reference to our zip file
			FileOutputStream dest = new FileOutputStream(destfilename);
			// Wrap our destination zipfile with a ZipOutputStream
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			// Create a byte[] buffer that we will read data from the source
			// files into and then transfer it to the zip file
			byte[] data = new byte[BUFFER_SIZE];
			
			
			Object[] key = zdata.keySet().toArray();  
			Arrays.sort(key);  

			for   (int   i   =   0;   i   <   key.length;   i++)   {  

			// Iterate over all of the files in our list
//			for (Iterator i = zdata.entrySet().iterator(); i.hasNext();) {
//				Map.Entry mentry = (Map.Entry) i.next();
				// Get a BufferedInputStream that we can use to read the source file
				String filename = (String) key[i];
				origin = new BufferedInputStream(new FileInputStream(audioFileRoot + zdata.get(filename) ));
				System.err.println("REPO: Adding: " + filename);
				// Setup the entry in the zip file
				ZipEntry entry = new ZipEntry(filename);
				out.putNextEntry(entry);

				// Read data from the source file and write it out to the zip file
				int count;
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					out.write(data, 0, count);
				}
				// Close the source file
				origin.close();
			}
			// Close the zip file
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#rateClip(com.beathive.model.UserClipScore)
	 */
	public boolean rateClip(UserClipScore ucs) {
		return soundClipDao.rateClip(ucs);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getTrackpackComponents(java.lang.String, com.beathive.ExtendedPaginatedList)
	 */
	public List getTrackpackComponents(String clipId,
			ExtendedPaginatedList componentsPager) {
		return soundClipDao.getTrackpackComponents(new Long(clipId), componentsPager);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.SoundClipManager#getCounts(com.beathive.model.Loop, com.beathive.ExtendedPaginatedList)
	 */
	public Integer[] getCounts(Loop soundClip,
			ExtendedPaginatedList paginatedList) {
		// TODO Auto-generated method stub
		return soundClipDao.getCounts(soundClip, paginatedList);
	}

	/**
	 * @return the usersRootDir
	 */
	public String getUsersDownloadDir() {
		return usersDownloadDir;
	}

	/**
	 * @param usersRootDir the usersRootDir to set
	 * @throws ServiceException 
	 */
	public void setUsersDownloadDir(String usersDownloadDir) throws ServiceException{
		this.usersDownloadDir = usersDownloadDir;
		boolean ok = false;
		try{
			ok = (new File(this.usersDownloadDir)).isDirectory();			
		}catch(SecurityException e){
			throw new ServiceException(e);
			
		}
		if (!ok){
			throw new ServiceException("directory: usersDownloadDir'" +usersDownloadDir +"' does not exist");
		}
		
	}
	public List findStores(Loop clip, ExtendedPaginatedList paginatedList) {
		return soundClipDao.findStores(clip, paginatedList);
	}

	
}
