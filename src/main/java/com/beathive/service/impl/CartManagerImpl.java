
package com.beathive.service.impl;

import java.util.List;

import com.beathive.dao.CartDao;
import com.beathive.model.CartItem;
import com.beathive.model.Favorite;
import com.beathive.model.SoundClip;
import com.beathive.service.CartManager;

public class CartManagerImpl extends BaseManager implements CartManager {
    private CartDao cartDao;


    /**
     * Set the Dao for communication with the data layer.
     * @param cartDao
     */
    public void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }


    /**
     * @see com.beathive.service.SoundClipManager#getSoundClip(final String clipId)
     */
    public SoundClip getCartSoundClip(final String clipId) {
        return cartDao.getSoundClip(new Long(clipId));
    }


	/* (non-Javadoc)
	 * @see com.beathive.service.CartManager#getUserFavorite(java.lang.Long, java.lang.Long)
	 */
	public Favorite getUserFavorite(String userId, String clipId) {
		return cartDao.getUserFavorite(new Long(userId) , new Long(clipId));
	}

	public Favorite deleteUserFavorite(String userId, String clipId) {
		return cartDao.deleteUserFavorite(new Long(userId) , new Long(clipId));
	}
	

	/* (non-Javadoc)
	 * @see com.beathive.service.CartManager#getUserFavorite(com.beathive.model.Favorite)
	 */
	public Favorite getUserFavorite(Favorite favorite) {
		// TODO Auto-generated method stub
		return cartDao.getUserFavorite(favorite);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.CartManager#getUserFavorite(com.beathive.model.Favorite)
	 */
	public List getUserFavorites(Long userId){
		return cartDao.getUserFavorites(userId);
	}


	/* (non-Javadoc)
	 * @see com.beathive.service.CartManager#deleteCartItem(com.beathive.model.CartItem)
	 */
	public void deleteCartItem(CartItem item) {
		cartDao.deleteCartItem(item);
	}


	/* (non-Javadoc)
	 * @see com.beathive.service.CartManager#saveCartItem(com.beathive.model.CartItem)
	 */
	public void saveCartItem(CartItem item) {
		cartDao.saveCartItem(item);
	}


	/* (non-Javadoc)
	 * @see com.beathive.service.CartManager#deleteUserCartItems(java.lang.String)
	 */
	public int deleteUserCartItems(String userId) {
		return cartDao.deleteUserCartItems(new Long(userId));
	}
	
}
