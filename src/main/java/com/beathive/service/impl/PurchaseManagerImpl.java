package com.beathive.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.dao.PurchaseDao;
import com.beathive.model.PaymentExecutor;
import com.beathive.model.Purchase;
import com.beathive.model.PurchaseItem;
import com.beathive.model.ShopReport;
import com.beathive.service.PurchaseManager;
import com.beathive.service.ServiceException;
import com.beathive.util.RandomGUID;
import com.beathive.util.StringUtil;
import org.springframework.dao.DataIntegrityViolationException;


/**
 * Implementation of PurchaseManager interface.</p>
 * 
 * <p>
 * <a href="PurchaseManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Ronald Dennison
 */
public class PurchaseManagerImpl extends BaseManager implements PurchaseManager {
    private PurchaseDao dao;

    /**
     * Set the DAO for communication with the data layer.
     * @param dao
     */
    public void setPurchaseDao(PurchaseDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.beathive.service.PurchaseManager#getPurchase(java.lang.Long)
     */
    public Purchase getPurchase(Long id) {
        return dao.getPurchase(id);
    }

    /**
     * @see com.beathive.service.PurchaseManager#getPurchases(com.beathive.model.Purchase)
     */
    public List getPurchases(Purchase order) {
        return dao.getPurchases(order);
    }



	public List getPurchases() {
		return dao.getPurchases();
	}

	/**
	 * @see com.beathive.service.PurchaseManager#savePurchase(com.beathive.model.Purchase)
	 */
	public void savePurchase(Purchase order) throws ServiceException {
        try {
            dao.savePurchase((Purchase) order);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Purchase '" + order.getId() + 
                                          "' already exists!");
        }
	}

	/**
	 * @see com.beathive.service.PurchaseManager#removePurchase(java.lang.Long)
	 */
	public void removePurchase(Long id) throws ServiceException {
        if (log.isDebugEnabled()) {
            log.debug("removing order: " + id);
        }

        dao.removePurchase(id);
	}

	public void removePurchaseItem(String username, String itemId) {
		if (log.isDebugEnabled()) {
			log.debug("removing orderitem: " + itemId);
		}
		
		dao.removePurchaseItem(username , itemId);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.PurchaseManager#getExecutor(java.lang.Long)
	 */
	public PaymentExecutor getExecutor(Long serviceId) {
		return dao.getExecutor(serviceId);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.PurchaseManager#getPurchaseItems(com.beathive.model.PurchaseItem, java.util.Map)
	 */
/*	public List getPurchaseItems(PurchaseItem purchaseItem, Map<java.util.Date , java.util.Date> range , ExtendedPaginatedList paginatedList){
		return dao.getPurchaseItems(purchaseItem, range , paginatedList);
	}
*/
	public Object getSummarizeSalesInRange(ShopReport salesReportPager){
		return dao.getSummarizeSalesInRange(salesReportPager);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.PurchaseManager#getSales(com.beathive.model.ShopReport)
	 */
	public void getSales(ShopReport salesReportPager, boolean b) {
		dao.getSales(salesReportPager , b);
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.PurchaseManager#getPendingPurchase(com.beathive.model.Purchase)
	 */
	public Purchase getPendingPurchase(Purchase purchase) {
		return dao.getPendingPurchase(purchase);
	}
}
