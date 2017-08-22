package com.beathive.service;

import java.util.List;

import com.beathive.model.CartItem;
import com.beathive.model.Favorite;
import com.beathive.model.SoundClip;

public interface CartManager extends Manager{
    
    /**
     * @param String userId
     * @return User
     */
    public SoundClip getCartSoundClip(String clipId);
    
    
	public Favorite getUserFavorite(String userId, String clipId);
	
	public Favorite deleteUserFavorite(String userId, String clipId);
	
	public Favorite getUserFavorite(Favorite favorite);
    
	public List getUserFavorites(Long userId);


	/**
	 * @param remove
	 */
	public void deleteCartItem(CartItem item);
	
	
	public int deleteUserCartItems(String userId);


	/**
	 * @param item
	 */
	public void saveCartItem(CartItem item);
}
