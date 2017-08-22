package com.beathive.dao.ibatis;

import java.util.List;

import com.beathive.dao.RoleDao;
import com.beathive.model.Role;

/**
 * This class interacts with iBatis's SQL Maps for CRUD on Role objects.
 */
public class RoleDaoiBatis extends BaseDaoiBATIS implements RoleDao {

    public List getRoles(Role role) {
        return getSqlMapClientTemplate().queryForList("getRoles", null);
    }
    
    public Role getRoleByName(String name) {
        return (Role) getSqlMapClientTemplate().queryForObject("getRoleByName", name);
    }

    public void saveRole(final Role role) {
        if (role.getId() == null) {
            getSqlMapClientTemplate().update("addRole", role);
        } else {
            getSqlMapClientTemplate().update("updateRole", role);
        }
    }

    public void removeRole(String rolename) {
        getSqlMapClientTemplate().update("deleteRole", rolename);
    }

}
