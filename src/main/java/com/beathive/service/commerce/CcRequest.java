package com.beathive.service.commerce;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public interface CcRequest extends Map {

	public static final String SERVICE_CAPTURE_PREFIX = "ccCaptureService_";
	public static final String SERVICE_CAPTURE = SERVICE_CAPTURE_PREFIX+ "run";
	public static final String CAPTURE_REQUEST_ID = SERVICE_CAPTURE_PREFIX+ "authRequestID";
	
	public static final String SERVICE_AUTH_PREFIX = "ccAuthService_"; 
	public static final String SERVICE_AUTH = SERVICE_AUTH_PREFIX + "run";
	
	public static final String MERCHANT_REF_CODE = "merchantReferenceCode";
	
	public static final String CURRENCY_KEY = "purchaseTotals_currency";
	public static final String CURRENCY = "USD";
	public static final String CARD_PREFIX = "card_";
	public static final String BILLTO_PREFIX = "billTo_";
	public static final String ITEM_PREFIX ="item_";

	
	/**
	 * @param ipAddress The ipAddress to set.
	 */
	public void setIpAddress(String ipAddress);

	/**
	 * @param currency SYM The currency to set.
	 */
	public void setCurrency(String currency);

	/**
	 * @param merchantReferenceCode The merchantReferenceCode to set.
	 */
	public void setMerchantReferenceCode(String merchantReferenceCode);

	/**
	 * The requestId if this is a capture request
	 * 
	 * @param authRequestID
	 */
	public void setAuthRequestID(String authRequestID);
	
	
	/**
	 * uses BeanUtils.getSimpleProperty('price')
	 * @param items
	 */
	public void setItems(Collection items);
	/**
	 * Clears all unitItems
	 */
	public void clearItems();

	public void addItem(String itemprice);

	public Collection getItems();
	
	public void setRequestToken(String token);
//	public String getRequestToken();


	public Map getItemMap();

	public void setItemMap(Map itemMap);

}
