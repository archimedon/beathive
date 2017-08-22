package com.beathive.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.beathive.dao.CartDao;
import com.beathive.dao.helper.SimpleOrderCriterion;
import com.beathive.model.CartItem;
import com.beathive.model.Favorite;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.Trackpack;

/**
*/
public class CartDaoHibernate extends BaseDaoHibernate implements CartDao {
    
	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getSoundClip(Long)
	 */    
    public SoundClip getSoundClip(Long clipId) {
    	SoundClip clip = (SoundClip)getSession().createCriteria(SoundClip.class)
    	.setLockMode(LockMode.READ)
		.setFetchMode("genre",FetchMode.JOIN)
		.setFetchMode("audioFormats",FetchMode.JOIN)
		.setFetchMode("instrument",FetchMode.JOIN)
		.setFetchMode("loops",FetchMode.JOIN)
				.add(Restrictions.idEq(clipId)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list().get(0);
    	return clip;
    }

	/* (non-Javadoc)
	 * @see com.beathive.dao.CartDao#getUserFavorite(java.lang.Long, java.lang.Long)
	 */
	public Favorite getUserFavorite(Long userId, Long clipId) {
		List oneItem = getHibernateTemplate()
		.find("from Favorite fav where fav.userId=? and fav.clipId=?" , new Long[]{userId , clipId});
		return (oneItem!=null && !oneItem.isEmpty()) ? (Favorite)oneItem.get(0) : null;
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.CartDao#getUserFavorite(java.lang.Long, java.lang.Long)
	 */
	public List getUserFavorites(Long userId) {
		return getHibernateTemplate().find("from Favorite fav where fav.userId=?" , userId );
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.dao.CartDao#getUserFavorite(com.beathive.model.Favorite)
	 */
	public Favorite getUserFavorite(Favorite favorite) {
		return getUserFavorite(favorite.getUserId(), favorite.getClipId());
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.CartDao#deleteUserFavorite(java.lang.Long, java.lang.Long)
	 */
	public Favorite deleteUserFavorite(Long userId, Long clipId) {
		List oneItem = getHibernateTemplate()
			.find("from Favorite fav where fav.userId=? and fav.clipId=?" , new Long[]{userId , clipId});
		Favorite fav = (oneItem!=null && !oneItem.isEmpty()) ? (Favorite)oneItem.get(0) : null;
		if (fav != null){getHibernateTemplate().delete(fav);}
		return fav;
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.CartDao#deleteCartItem(com.beathive.model.CartItem)
	 */
	public void deleteCartItem(CartItem item) {
		int deletedEntities = getSession().createQuery( "delete from CartItem where userId=:userId and fileId=:fileId and clipId=:clipId" )
        .setLong( "userId", item.getUserId() )
        .setLong( "clipId", item.getClipId() )
        .setLong( "fileId", item.getFileId() )
        .executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.CartDao#saveCartItem(com.beathive.model.CartItem)
	 */
	public void saveCartItem(CartItem item) {
		getHibernateTemplate().save(item);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.CartDao#deleteUserCartItems(java.lang.Long)
	 */
	public int deleteUserCartItems(Long userId) {
		int deletedEntities = getSession().createQuery( "delete from CartItem where userId=:userId" )
        .setLong( "userId", userId )
        .executeUpdate();
		return deletedEntities;
	}
}
