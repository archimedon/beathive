package com.beathive.dao.ibatis;

import java.util.List;

import com.beathive.dao.LookupDao;

/**
 * iBatis LookupDao.
 */
public class LookupDaoiBatis extends BaseDaoiBATIS implements LookupDao {

    /**
     * @see com.beathive.dao.LookupDao#getRoles()
     */
    public List getRoles() {
        logger.debug("retrieving all role names...");

        return getSqlMapClientTemplate().queryForList("getRoles", null);
    }
}
