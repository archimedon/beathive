/**
 * 
 */
package com.beathive.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author ron
 */
public class ViewCounter {
	private static final Log log = LogFactory.getLog(ViewCounter.class);

	private Set shops = null;
	private static StoreManager shopMgr = null;
	
	public static ViewCounter newInstance() {
		ViewCounter vc = new ViewCounter();
		vc.setShops(new HashSet());
		return vc;
	}
	
	private ViewCounter() {
		super();
	}
	
	public ViewCounter(StoreManager shopMgr) {
		this.shopMgr = shopMgr;
	}
	
	public void incrementView(String shopId) {
		if (!this.shops.contains(shopId)) {
			shops.add(shopId);
			//if first view in this session
			shopMgr.incrementViews(shopId);
		}
	}

	public StoreManager getShopMgr() {
		return shopMgr;
	}

	public void setShopMgr(StoreManager shopMgr) {
		ViewCounter.shopMgr = shopMgr;
	}

	public Set getShops() {
		return shops;
	}

	public void setShops(Set shops) {
		this.shops = shops;
	}
}
