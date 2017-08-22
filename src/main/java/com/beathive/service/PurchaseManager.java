package com.beathive.service;

import java.util.List;
import java.util.Map;

import com.beathive.ExtendedPaginatedList;
import com.beathive.model.AudioFile;
import com.beathive.model.PaymentExecutor;
import com.beathive.model.Purchase;
import com.beathive.model.PurchaseItem;
import com.beathive.model.SalesSummary;
import com.beathive.model.ShopReport;
import com.beathive.model.Zip;

public interface PurchaseManager {
    //~ Methods ================================================================

	
    public Purchase getPendingPurchase(Purchase purchase);

    /**
     * Retrieves an Purchase
     *
     * @param Purchase
     * @return Purchase
     * @throws ServiceException
     */
    public Purchase getPurchase(Long id) throws ServiceException;

    /**
     * Finds orders by example
     * @param Purchase
     * @return List
     * @throws ServiceException
     */
    public List getPurchases(Purchase order);

    /**
     * All orders
     * @param Purchase
     * @return List
     * @throws ServiceException
     */
    public List getPurchases();
    
    /**
     * Save an Purchase
     *
     * @param Purchase
     * @return updated genre information
     * @throws ServiceException
     */
    public void savePurchase(Purchase order) throws ServiceException;

    /**
     * Rermove an order
     *
     * @param the genre's id
     * @throws ServiceException
     */
    public void removePurchase(Long id) throws ServiceException;

	public void removePurchaseItem(String username, String itemId);

	/**
	 * @param long1
	 * @return
	 */
	public PaymentExecutor getExecutor(Long long1);

	/**
	 * @param purchaseItem
	 * @param range
	 */

//	public List getPurchaseItems(PurchaseItem purchaseItem, Map<java.util.Date , java.util.Date> range , ExtendedPaginatedList paginatedList);

	/**
	 * @param purchaseItem
	 * @param range
	 * @param paginatedList
	 * @return
	 */
//	public SalesSummary getSummarizeSalesInRange(PurchaseItem purchaseItem,	Map range, ExtendedPaginatedList paginatedList);
	
	public Object getSummarizeSalesInRange(ShopReport salesReportPager);

	/**
	 * @param salesReportPager
	 */
	public void getSales(ShopReport salesReportPager, boolean b);	
	
	
//	public Zip packagePurchase(Purchase order  ) throws Exception;
	
	
	
	
}
