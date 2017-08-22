package com.beathive.service.commerce;


import com.beathive.service.commerce.BillingException;

public interface ChargeManager {


	public CcReply ccCapture(CcRequest order , String authRequestID) throws BillingException;

	/**
	 * Authorizes the charge. returns a ChargeReply.
	 * @param order
	 * @return
	 * @throws ChargeException 
	 */
	public CcReply ccAuth(CcRequest order) throws BillingException;
	
	/**
	 * Validates billing info by doing a '0' charge transaction
	 * @param credentials
	 * @return
	 */
	public CcReply ccValidate(CcRequest credentials) throws BillingException;
	
	/**
	 * Performs a sale.
	 * @param order
	 * @return
	 */
	public CcReply ccCapture(CcRequest order) throws BillingException;
	
	/**
	 * Performs a sale.
	 * @param order
	 * @return
	 */
	public CcReply ccSale(CcRequest order) throws BillingException;
	
}
