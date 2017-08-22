/**
 * 
 */
package com.beathive.dao;

import java.util.List;

import com.beathive.model.CartItem;
import com.beathive.model.Favorite;
import com.beathive.model.SoundClip;

/**
 * @author ron
 *
 */
public interface CartDao extends Dao {

	/**
	 * Retrieves a minimal SoundClip suitable for the cart
	 * @param clipId - the clip Id
	 * @return
	 */
	public SoundClip getSoundClip(Long clipId);

	public Favorite getUserFavorite(Long userId , Long clipId);
	
	public Favorite deleteUserFavorite(Long userId , Long clipId);
	
	public Favorite getUserFavorite(Favorite favorite);
	
	public List getUserFavorites(Long userId);

	/**
	 * @param item
	 */
	public void deleteCartItem(CartItem item);

	/**
	 * @param item
	 */
	public void saveCartItem(CartItem item);

	/**
	 * @param userId
	 * @return
	 */
	public int deleteUserCartItems(Long userId);
}