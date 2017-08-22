/**
 * 
 */
package com.beathive.service.commerce.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author rdennison
 * 
 */
public class CybCredentials extends HashMap {
	private static String[] billTo = {"firstName", "lastName","email", 
			"address.street1","address.street2","address.city", "address.state", "address.postalCode", "address.country", "address.phoneNumber"};
			 
	private static String[] card = {"creditCard.accountNumber", "creditCard.expirationMonth", "creditCard.expirationYear"};
	
	public CybCredentials(){
		super();
	}
	public void setCard(Object user){
		int y = 0;
		int ptr = -1;
		String prefix ="card_";
		for (; y < card.length; y++){
			ptr = -1;
			try {
				if ((ptr = card[y].lastIndexOf(".")) > -1){
					put (prefix + card[y].substring(ptr+1), BeanUtils.getNestedProperty(user ,card[y] ));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void setBillTo(Object user){
		int y = 0;
		int ptr = -1;
		String prefix ="billTo_";
		for (; y < billTo.length; y++){
			ptr = -1;
			try {
				if ((ptr = billTo[y].lastIndexOf(".")) > -1){
					put (prefix + billTo[y].substring(ptr+1), BeanUtils.getNestedProperty(user ,billTo[y] ));
				}else{
					put (prefix + billTo[y] , BeanUtils.getSimpleProperty(user , billTo[y]));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public CybCredentials(Map request){
		this();
		
		// this is your own tracking number. CyberSource recommends that you
		// use a unique one for each order.
		put( "merchantReferenceCode", "MRC-14344" );
		//put( "ccAuthService_run", "true" );
		super.putAll(request);
		// We will let the Client get the merchantID from props and insert it
		// into the request Map.
		
		
		request.put( "billTo_ipAddress", "10.7.7.7" );
		
		put( "purchaseTotals_currency", "USD" );
		// there are two items in this sample
		request.put( "item_0_unitPrice", "12.34" );
		request.put( "item_1_unitPrice", "56.78" );


	}
	
	

}
