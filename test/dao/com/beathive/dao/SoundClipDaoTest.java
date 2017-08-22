package com.beathive.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;
import com.beathive.model.AudioFile;
import com.beathive.model.Genre;
import com.beathive.model.Instrument;
import com.beathive.model.Loop;
import com.beathive.model.Price;
import com.beathive.model.SoundClip;
import com.beathive.model.User;

/**
 * Tests SoundClipDao implementation
 */
public class SoundClipDaoTest extends BaseDaoTestCase {
    private SoundClipDao soundClipDao = null;
    private UserDao udao = null;
    private Dao baseDao = null;
    
    public void setUserDao(UserDao udao) {
        this.udao = udao;
    }
    
    public void setSoundClipDao(SoundClipDao dao) {
        this.soundClipDao = dao;
    }
    
    public void setDao(Dao bdao) {
    	this.baseDao = bdao;
    }
 
	public void testGetSoundClip() throws Exception {
		Long clipId = new Long(2);
		try {
			assertEquals(soundClipDao.getSoundClip(clipId).getId(), clipId);
		} catch (DataAccessException d) {
			fail("Clip " + clipId + " not found");
		}
	}
	
	public void testGetAFewSoundClip() throws Exception {
		int numLoops = 10;
	
		QueryMeta queryMeta = new QueryMetaImpl();
		queryMeta.setFetchSize(numLoops);
	
		List<SoundClip> loops = soundClipDao.getSoundClips(new Loop(), queryMeta);
		assertEquals(numLoops, loops.size());
	}
	
	public void testGetSoundClipInvalid() throws Exception {
		Long clipId = new Long(9000000);
		try {
			soundClipDao.getSoundClip(clipId);
			fail("Clip id " + clipId +  " should not be in database");
		} catch (DataAccessException d) {
			assertNotNull(d);
		}
	}
    
    public void testGetLoops4AuthdUser() throws Exception {
    	int numLoops = 10;
   	
    	QueryMeta viewerQuery = new QueryMetaImpl( getTestUser(null) );
    	viewerQuery.setFetchSize(numLoops);
    	List<SoundClip> loops = soundClipDao.getSoundClips(new Loop(), viewerQuery);
    	
    	assertEquals(numLoops, loops.size());
    }

    public void testFindLoopsByFeature4Authd() throws Exception {
    	int numLoops = 12;
    	
    	QueryMeta viewerQuery = new QueryMetaImpl(getTestUser(null));
    	viewerQuery.setFetchSize(numLoops);
    	
    	Genre genre = (Genre) baseDao.getObject(Genre.class, new Long(5));
    	Instrument instrument = getAnInstrument(new Long(5), new Long(1));
    	
    	Loop loop = (Loop) makeACriteriaExample(genre, instrument, true);

    	List<SoundClip> loops = soundClipDao.getSoundClips(loop, viewerQuery);
    	
    	assertEquals(numLoops, loops.size());
    }

    /**
     * A search that we know will return trackpacks
     * @throws Exception
     * 
     * com.beathive.model.Trackpack@e08e1[
    [junit]   id=2
    [junit]   ownedByViewer=false
    [junit]   isAFavorite=false
    [junit]   viewerScore=<null>
    [junit]   avgScore=3.0
    [junit]   timesrated=1
    [junit]   name=test tp1
    [junit]   storeId=1
    [junit]   genre=[label.genre.jazz, label.genre.experimental]
    [junit]   searchable=true
    [junit]   audioFormat=[/path/to/file2.aif]
    [junit]   getScales=label.scale.minor
    [junit]   bpm=55
    [junit]   getEnergy=label.energy.relaxed
     */
    public void testGetSelectedUserTrackPacks() throws Exception {
    	Long jazzGenreId = new Long(4);// 4 = jazz
    	
    	QueryMeta meta = new QueryMetaImpl(getTestUser(null));
    	meta.setFetchSize(10);

    	Genre genre = (Genre) baseDao.getObject(Genre.class, jazzGenreId);
    	
    	SoundClip trackpack = makeACriteriaExample(genre, null, true);

    	List<SoundClip> trackpacks = soundClipDao.getSoundClips(trackpack, meta);
    	SoundClip testc = null;
    	for (Iterator<SoundClip> it = trackpacks.iterator(); it.hasNext(); testc = it.next()) {
    		assertEquals(testc.getGenre(), genre);
    	}
    }
    
    public void testGetSelectedUserLoops() throws Exception {
    	Long instrumentId = new Long(1);
    	Long instrumentGroupId = new Long(1); // must contain an inner loop with a value of 1
    	Long jazzGenreId = new Long(4);// 4 = jazz
    	
    	QueryMeta meta = new QueryMetaImpl(getTestUser(null));
    	meta.setFetchSize(10);
    	
    	Instrument instrument = getAnInstrument(instrumentGroupId, instrumentId);
    	
    	Genre genre = (Genre) baseDao.getObject(Genre.class, jazzGenreId);
    	
    	SoundClip loop = makeACriteriaExample(genre, instrument, true);
    	
    	List<SoundClip> clips = soundClipDao.getSoundClips(loop, meta);
    	SoundClip testc = null;
    	for (Iterator<SoundClip> it = clips.iterator(); it.hasNext(); testc = it.next()) {
    		assertEquals(testc.getGenre(), genre);
    		assertEquals(((Loop)testc).getInstrument(), instrument);
    	}
    }
    
    private Date getLastYear() {
    	Calendar cal = Calendar.getInstance();
    	cal.roll(Calendar.YEAR, false);
		return cal.getTime();
	}

	public void testGetTopten() throws Exception {
    	QueryMeta meta = new QueryMetaImpl(this.getTestUser(null));
    	List<SoundClip> clips = soundClipDao.getTopTenClips(meta, 60);
    	assertEquals(clips.size(), 60);
    }
  
    public void testGetNewestTrackpacks() throws Exception {
    	log.debug("newest loops");
    	Long uid = new Long(15);
    	QueryMeta meta = new QueryMetaImpl(getTestUser(uid));
    	meta.setFetchSize(30);

    	List<SoundClip> clips = soundClipDao.getNewestTrackpacks(meta);

    	assertNotNull(clips);
    	assertTrue(clips.size() > 0);
    }

    private SoundClip makeACriteriaExample(Genre genre, Instrument instrument, boolean drange) {
    	SoundClip clip = new SoundClip("RichCriteriaExample");

    	if (genre != null) clip.getGenre().add(genre);
    	if (instrument != null) ((Loop)clip).setInstrument(instrument);

    	/** Trigger since-last-login clause in Trackpack/Loop query */
    	if (drange) clip.setCreated(getLastYear());

    	clip.getAudioFormat().add(new AudioFile("aif"));
    	clip.setEnergy("label.energy.relaxed");
    	clip.setScale("label.scale.minor");
    	clip.setTimesignature("label.timesignature.4_4");
    	clip.setBpm(new Integer(56)); // actually 55 due to offset diff
    	
    	return clip;
    	
    }

    public void testUpdateSoundClip() throws Exception {
    	
        SoundClip clip = soundClipDao.getSoundClip(new Long(1));
    	clip.setEnergy("label.energy.intense");
    	clip.setScale("label.scale.major");
    	clip.setTimesignature("label.timesignature.6_8");
 
        soundClipDao.saveSoundClip(clip);
        endTransaction();

        assertEquals(clip.getEnergy(), "label.energy.intense");
        assertEquals(clip.getScale(), "label.scale.major");
        assertEquals(clip.getTimesignature(), "label.timesignature.6_8");
    }

	
    public void testNewSoundClip() throws Exception {
    	String newName = "newName";
    	SoundClip newClip = new SoundClip(newName);

    	// price/currency required
		newClip.setPrice(new Price(5.77 , "EU"));
		// Set ready status
		newClip.setStatusId(new Integer(1));
		// give it a feature
		newClip.setBpm(44);
		// put it in a test shop
		newClip.setStoreId(new Long(2));

		soundClipDao.saveSoundClip(newClip);
		
    	assertNotNull(newClip.getId());
    	assertEquals(newClip.getName(), newName);
        endTransaction();
    }

    private User getTestUser(Long uid) {
    	Long id = new Long(1);
    	if (uid != null) {
    		id = uid;
    	}
    	User user = udao.getUser(id);
    	/** Causes preference of clips that are new to this user */
    	user.setLastLogin(getLastYear());
    	return user;
    }
    
    private Instrument getAnInstrument(Long instrumentGroupId, Long instrumentId) {
    	Instrument instrument = new Instrument();
    	instrument.setGroupId(instrumentGroupId);
    	instrument.setId(instrumentId);
    	return instrument;
    }

}
