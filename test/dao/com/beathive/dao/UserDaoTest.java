package com.beathive.dao;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import com.beathive.Constants;
import com.beathive.model.Address;
import com.beathive.model.Favorite;
import com.beathive.model.Role;
import com.beathive.model.SoundClip;
import com.beathive.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

public class UserDaoTest extends BaseDaoTestCase {
    private UserDao dao = null;
    private RoleDao rdao = null;

    public void setUserDao(UserDao dao) {
        this.dao = dao;
    }
    
    public void setRoleDao(RoleDao rdao) {
        this.rdao = rdao;
    }

    public void testGetUserInvalid() throws Exception {
        try {
            dao.loadUserByUsername("badusername");
            fail("'badusername' found in database, failing test...");
        } catch (UsernameNotFoundException d) {
            assertTrue(d != null);
        }
    }

    public void testGetUser() throws Exception {
        User user = dao.getUser(new Long(1));

        assertNotNull(user);
        assertEquals(2, user.getRoles().size());
        assertTrue(user.isEnabled());
        log.debug("score: " + user.getClipScore());
    }

    public void testUpdateUser() throws Exception {
        User user = dao.getUser(new Long(1));

        Address address = user.getAddress();
        address.setStreet1("new address");

        dao.saveUser(user);

        assertEquals("new address", user.getAddress().getStreet1());
        
        // verify that violation occurs when adding new user with same username
        user.setId(null);

        endTransaction();

        try {
            dao.saveUser(user);
            fail("saveUser didn't throw DataIntegrityViolationException");
        } catch (DataIntegrityViolationException e) {
            assertNotNull(e);
            log.debug("expected exception: " + e.getMessage());
        }
    }

    public void testAddUserRole() throws Exception {
        User user = dao.getUser(new Long(1));
        assertEquals(2, user.getRoles().size());

        Role role = rdao.getRoleByName("junk");
        user.addRole(role);
        dao.saveUser(user);

        assertEquals(3, user.getRoles().size());

        //add the same role twice - should result in no additional role
        user.addRole(role);
        dao.saveUser(user);

        assertEquals("more than 3 roles", 3, user.getRoles().size());

        user.getRoles().remove(role);
        dao.saveUser(user);

        assertEquals(2, user.getRoles().size());
    }

    public void testAddAndRemoveUser() throws Exception {
        User user = new User("testuser");
        user.setPassword("testpass");
        user.setFirstName("Test");
        user.setLastName("Last");
        Address address = new Address();
        address.setCity("San Francisco");
        address.setProvince("COA");
        address.setCountry("USA");
        address.setPostalCode("94117");
        user.setAddress(address);
        user.setEmail("ron@beathive.com");
        
        Role role = rdao.getRoleByName(Constants.USER_ROLE);
        assertNotNull(role.getId());
        user.addRole(role);

        dao.saveUser(user);

        assertNotNull(user.getId());
        assertEquals("testpass", user.getPassword());

        dao.removeUser(user.getId());

        try {
            user = dao.getUser(user.getId());
            fail("getUser didn't throw DataAccessException");
        } catch (DataAccessException d) {
            assertNotNull(d);
        }
    }
}
