package com.beathive.dao;

import java.util.List;
import java.util.Map;

import com.beathive.ExtendedPaginatedList;
import com.beathive.model.PaymentExecutor;
import com.beathive.model.Purchase;
import com.beathive.model.PurchaseItem;
import com.beathive.model.ShopReport;

public interface PurchaseDao extends Dao {

	public void removePurchase(Long id);

	public void removePurchaseItem(String username, String itemId);
	
	public List getPurchases();

	public List getPurchases(Purchase order);

	public void savePurchase(Purchase order);

	public Purchase getPurchase(Long id);

	public List getUserPurchases(String username);

	/**
	 * @param serviceId
	 * @return
	 */
	public PaymentExecutor getExecutor(Long serviceId);

	public Object getSummarizeSalesInRange(ShopReport salesReportPager);

	/**
	 * @param salesReportPager
	 */
	public void getSales(ShopReport salesReportPager, boolean b);

	/**
	 * @param purchase
	 * @return
	 */
	public Purchase getPendingPurchase(Purchase purchase);
}
