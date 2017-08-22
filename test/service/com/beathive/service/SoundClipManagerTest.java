
package com.beathive.service;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;
import com.beathive.service.BaseManagerTestCase;
import com.beathive.dao.SoundClipDao;
import com.beathive.dao.UserDao;
import com.beathive.model.AudioFile;
import com.beathive.model.Genre;
import com.beathive.model.Instrument;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.Trackpack;
import com.beathive.model.User;
import com.beathive.service.impl.SoundClipManagerImpl;
import com.beathive.service.impl.UserManagerImpl;

import org.jmock.Mock;
import org.jmock.core.Constraint;
import org.jmock.core.stub.DefaultResultStub;
import org.springframework.orm.ObjectRetrievalFailureException;



public class SoundClipManagerTest extends BaseManagerTestCase {
    private final String loopId = "1";
    private Mock soundClipDao = null;
    private Mock userDao = null;
    private SoundClipManagerImpl soundClipManager = new SoundClipManagerImpl();
	private UserManagerImpl userManager = new UserManagerImpl();

    protected void setUp() throws Exception {
        super.setUp();
        soundClipDao = new Mock(SoundClipDao.class);
        soundClipManager.setSoundClipDao((SoundClipDao) soundClipDao.proxy());
        userDao = new Mock(UserDao.class);
        userManager.setUserDao((UserDao) userDao.proxy());
        soundClipManager.setUserDao((UserDao) userDao.proxy());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        soundClipManager = null;
        userManager = null;
    }

    public void testGetSoundClips() throws Exception {

    	DefaultResultStub returnDefaultValue = new DefaultResultStub();

        List results = new ArrayList();
        SoundClip loop = new SoundClip();
        QueryMeta meta = new QueryMetaImpl();
        soundClipDao.expects(once()).method("getSoundClips").with(super.same(loop))
        .will(returnDefaultValue);

        results = soundClipManager.getSoundClips(loop );
        assertTrue(results.size() > 0);
    }
}
