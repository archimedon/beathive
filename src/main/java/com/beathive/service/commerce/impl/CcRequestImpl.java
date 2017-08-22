package com.beathive.service.commerce.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.service.commerce.CcReply;
import com.beathive.service.commerce.CcRequest;

public class CcRequestImpl extends HashMap implements CcRequest {
    protected static Log log = LogFactory.getLog(CcRequestImpl.class);


	private int itemcount = 0;
	
	public CcRequestImpl(String merchantReferenceCode){
		this();
		setMerchantReferenceCode(merchantReferenceCode);
	}
	public CcRequestImpl(){
		super();
		setCurrency( CcRequest.CURRENCY);
	}
	
	public void setAuthRequestID(String authRequestID){
		
		put( CcRequest.CAPTURE_REQUEST_ID, authRequestID );
	}

	/**
	 * @param accountNumber
	 *            The accountNumber to set.
	 */
	public void setAccountNumber(String accountNumber) {
		put(CARD_PREFIX + "accountNumber", accountNumber);
	}

	/**
	 * @param city
	 *            The city to set.
	 */
	public void setCity(String city) {
		put(BILLTO_PREFIX + "city", city);
	}

	/**
	 * @param country
	 *            The country to set.
	 */
	public void setCountry(String country) {
		put(BILLTO_PREFIX + "country", country);
	}

	/**
	 * @param currency
	 *            The currency to set.
	 */
	public void setCurrency(String currency) {
		put(CcRequest.CURRENCY_KEY  , currency);
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		put(BILLTO_PREFIX + "email", email);
	}

	/**
	 * Zero pad the single digit Month
	 * @param expirationMonth
	 *            The expirationMonth to set.
	 */
	public void setExpirationMonth(String expirationMonth) {
		if( expirationMonth != null ){
			if (expirationMonth.length() < 2){
				expirationMonth = "0"+expirationMonth;
			}
		put(CARD_PREFIX + "expirationMonth", expirationMonth);
		}
	}

	/**
	 * @param expirationYear
	 *            The expirationYear to set.
	 */
	public void setExpirationYear(String expirationYear) {
		put(CARD_PREFIX + "expirationYear", expirationYear);
	}

	/**
	 * @param firstName
	 *            The firstName to set.
	 */
	public void setFirstName(String firstName) {
		put(BILLTO_PREFIX + "firstName", firstName);
	}

	/**
	 * @param ipAddress
	 *            The ipAddress to set.
	 */
	public void setIpAddress(String ipAddress) {
		put(BILLTO_PREFIX + "ipAddress", ipAddress);
	}

	/**
	 * @param lastName
	 *            The lastName to set.
	 */
	public void setLastName(String lastName) {
		put(BILLTO_PREFIX + "lastName", lastName);
	}

	/**
	 * @param merchantReferenceCode
	 *            The merchantReferenceCode to set.
	 */
	public void setMerchantReferenceCode(String merchantReferenceCode) {
		put("merchantReferenceCode", merchantReferenceCode);
	}

	/**
	 * @param phoneNumber
	 *            The phoneNumber to set.
	 */
	public void setPhoneNumber(String phoneNumber) {
		put(BILLTO_PREFIX + "phoneNumber", phoneNumber);
	}

	/**
	 * @param postalCode
	 *            The postalCode to set.
	 */
	public void setPostalCode(String postalCode) {
		put(BILLTO_PREFIX + "postalCode", postalCode);
	}

	/**
	 * @param state
	 *            The state to set.
	 */
	public void setState(String state) {
		put(BILLTO_PREFIX + "state", state);
	}
	
	public void setProvince(String province) {
		if (!containsKey(BILLTO_PREFIX + "state")){
			put(BILLTO_PREFIX + "state", province);			
		}else{
			put(BILLTO_PREFIX + "province", province);			
		}
	}

	/**
	 * @param street1
	 *            The street1 to set.
	 */
	public void setStreet1(String street1) {
		put(BILLTO_PREFIX + "street1", street1);
	}

	public void setStreet2(String street2) {
		put(BILLTO_PREFIX + "street2", street2);
	}
	
	/**
	 * uses BeanUtils to extract a property named 'price' as a String
	 * @param items
	 */
	public void setItems(Collection items){
		clearItems();
		Iterator it = items.iterator();
		String price =  null;
		Object o = null;
		while(it.hasNext()){
			try {
				o = it.next();
				if (o instanceof String){
					addItem((String)o);
				}else{
				price = BeanUtils.getSimpleProperty(o , "price");
				addItem(price);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		
	}
	/**
	 * Clears all unitItems
	 */
	public void clearItems() {
		if (itemcount > 0){
			Set rem = new HashSet();
			Iterator keys = super.keySet().iterator();
			String keyName = null;
			while(keys.hasNext()){
				keyName = (String)keys.next();
				if (StringUtils.contains(keyName , ITEM_PREFIX)){
					rem.add(keyName);
				}
			}

			keys = rem.iterator();
			while(keys.hasNext()){
				super.remove(keys.next());
			}
		
		
		}
		itemcount = 0;
	}

	/**
	 * Convience to filter blank keys && values
	 * 
	 * @param key
	 * @param val
	 */
	public void put(String key , String val){
		if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(val)){
			super.put(key, val);
		}
	}
	synchronized public void addItem(String itemprice){
		String itemPref = ITEM_PREFIX  + itemcount + "_";
		super.put(itemPref +	"unitPrice"	, itemprice);
		this.itemcount++;
	}
	
	public Collection getItems(){
		return getItemMap().values();
	}
	public Map getItemMap() {
		Iterator it = super.keySet().iterator();
		Map retItems = new HashMap();
		String itemname = "";
		while(it.hasNext()){
			itemname = (String)it.next();
			if (StringUtils.contains(itemname ,ITEM_PREFIX)){
				retItems.put(itemname , get(itemname));
			}
		}
		return retItems;
	}
	
	public void setItemMap(Map items){
		clearItems();
		putAll(items);
		itemcount=items.size();
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.service.commerce.CcRequest#setRequestToken(java.lang.String)
	 */
	public void setRequestToken(String token) {
		this.put(CcReply.REQUEST_TOKEN , token);
	}
}
