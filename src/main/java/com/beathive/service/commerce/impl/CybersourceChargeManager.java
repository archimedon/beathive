package com.beathive.service.commerce.impl;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.service.commerce.BillingException;
import com.beathive.service.commerce.CcReply;
import com.beathive.service.commerce.CcRequest;
import com.beathive.service.commerce.ChargeManager;
import com.cybersource.ws.client.Client;

public class CybersourceChargeManager extends com.beathive.service.impl.BaseManager
		implements ChargeManager {
    protected final Log log = LogFactory.getLog(CybersourceChargeManager.class);

	private static Properties cybProperties = new Properties();

	public CybersourceChargeManager() {
		super();
	}

	/**
	 *  Execute Transaction
	 */
	private CcReply run(CcRequest order) throws Exception {
		Map repl = null;
		try{
			repl = Client.runTransaction(order, cybProperties);
		}catch(Exception e){
			log.error(e.getMessage() , e);
			throw e;
		}
		return  new CcReplyImpl(repl);
	}

	
	/**
	 * @return Returns the merchantId.
	 */
	public String getMerchantID() {
		return cybProperties.getProperty("merchantID");
	}

	public CcReply ccAuth(CcRequest order) throws BillingException {
		order.put(CcRequest.SERVICE_AUTH, "true");
		order.remove(CcRequest.SERVICE_CAPTURE);
		order.remove(CcRequest.CAPTURE_REQUEST_ID);
		// run transaction now
		CcReply reply = null;
		try {
			reply = run(order);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BillingException("Auth failure: " + e.getCause(), e);
		}
		return reply;
	}


	public CcReply ccValidate(CcRequest credentials) throws BillingException {
		credentials.put(CcRequest.SERVICE_AUTH, "true");
		credentials.clearItems();
		credentials.addItem("0");
		try {
			return run(credentials);
		} catch (Exception e) {
			throw new BillingException("Validate failure: " + e.getCause(), e);
		}
	}

	public CcReply ccCapture(CcRequest order, String authRequestID)
			throws BillingException {

		order.put(CcRequest.CAPTURE_REQUEST_ID, authRequestID);
		return ccCapture(order);
	}

	
	public CcReply ccCapture(CcRequest order) throws BillingException {
		// run transaction now
		try {
			return run(order);
		} catch (Exception e) {
			throw new BillingException("CAPTURE failure: " + e.getCause(), e);
		}
	}

	public CcReply ccCapture(CcRequest order , CcReply authReply) throws BillingException {
		
		order.put(CcRequest.SERVICE_CAPTURE, "true");
		order.put(CcRequest.CAPTURE_REQUEST_ID , authReply.getAuthRequestID());
		// run transaction now
		try {
			return run(order);
		} catch (Exception e) {
			throw new BillingException("CAPTURE failure: " + e.getCause(), e);
		}
	}
	
	public CcReply ccSale(CcRequest order) throws BillingException {

		// add auth service
		order.put(CcRequest.SERVICE_AUTH, "true");
		// add capture service
		order.put(CcRequest.SERVICE_CAPTURE, "true");
		// remove any prior requestId - just in case
		order.remove(CcRequest.CAPTURE_REQUEST_ID);
		// run transaction now
		try {
			return run(order);
		} catch (Exception e) {
			throw new BillingException("Sale failure: " + e.getCause(), e);
		}
	}

	/**
	 * @return Returns the cybProperties.
	 */
	public Properties getCybProperties() {
		return cybProperties;
	}

	/**
	 * @param cybProperties The cybProperties to set.
	 */
	public void setCybProperties(Properties cybProperties) {
		CybersourceChargeManager.cybProperties = cybProperties;
	}
}
