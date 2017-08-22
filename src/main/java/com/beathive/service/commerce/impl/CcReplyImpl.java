package com.beathive.service.commerce.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.service.commerce.CcReply;

public class CcReplyImpl extends HashMap implements CcReply {
    protected final Log log = LogFactory.getLog(CcReplyImpl.class);

	public CcReplyImpl(Map map){
		super();
		if (map != null){
			putAll(map);
		}
	}
	
	public String getAuthRequestID() {
		return (String)get(CcReply.REQUESTID_KEY);
	}

	public String getDecision() {
		return (String)get(CcReply.DECISION_KEY);
	}

	public boolean isSuccessful() {
		return (StringUtils.equalsIgnoreCase(getDecision(),CcReply.DECISION_ACCEPT));
	}
		
		/**
		 * ccreply.101=The request is missing one or more required fields.  
		 * 	Possible action: 
		 * 		See the reply fields missingField_0...N for which fields are  missing. 
		 * 		Resend the request with the complete information.
		 *
		 *	ccreply.102=One or more fields in the request contains invalid data. 
		 		Possible action:
		 			See the reply fields invalidField_0...N for which fields are invalid.  Resend the request with the correct information.
		 ccreply.240=The card type sent is invalid or does not correlate with the credit card number. Possible action: Ask your customer to verify that the card is really the type that they indicated in your Web store, then resend the request.
		 ccreply.520=The authorization request was approved by the issuing bank but declined by  CyberSource based on your Smart Authorization settings. Possible action: Do not capture the authorization without further review. Review  the ccAuthReply_avsCode, ccAuthReply_cvCode, and  ccAuthReply_authFactorCode fields to determine why CyberSource rejected the  request.
		 
		 */

	public Map getErrors() {
		Map errors = null;
		// not successful parse Errors
		if (! isSuccessful()){
			System.err.println("creating errors");
			
			errors = new HashMap();
			// check missing fields
			int reasonCode = Integer.parseInt( getReasonCode());
			 switch (reasonCode) {
			 // Missing field or fields
			 case 101: {
				 errors.put( "ccreply."+getReasonCode() ,  enumerateValues("missingField" ) );
				 break;
			 }
			 // Invalid field or fields
			 case 102: {
				 errors.put( "ccreply."+getReasonCode() ,  enumerateValues("invalidField" ) );
			 	break;
			 }
			 // Insufficient funds
			 case 204: {
				 errors.put( "ccreply."+getReasonCode() ,  null );
				 break;
			 }
			 // Add additional reason codes here that you need to handle
			 // specifically.
			 // For all other reason codes (for example, unrecognized reason
			 // codes, or codes that do not require special handling),
			 // return an empty string.
			 default: errors.put( "ccreply."+getReasonCode() ,  null );
			 }
			if (! userVerified()){
				System.err.println("AVS fail");
				if (errors == null){
					errors = new HashMap();
				}
				errors.put("ccreply." + getAVSCode() , null);
			}
		}
		return errors;
	}

	public String getAuthCode() {
		return (String)get(CcReply.AUTHCODE);
	}

	public String getReasonCode() {
		String rcode = (String)get(CcReply.REASONCODE);
		if (StringUtils.isBlank(rcode)){
			rcode = (String)get("reasonCode");
		}
		return rcode;
	}

	public String getReconciliationID() {
		return (String)get(CcReply.RECONCILIATION_ID);
	}
	public String getAuthAmount() {
		return (String)get(CcReply.AUTH_AMOUNT);
	}
	
	public String getCapturedAmount() {
		return (String)get(CcReply.CAPTURED_AMOUNT);
	}


 	private Collection enumerateValues( String fieldName ) {
System.err.println("looking for '" + fieldName + "(s)_");
 		Set errorStack = new HashSet();
	 	String key, val = "";
	 	for (int i = 0; ; ++i) {
	 		key = fieldName + "_" + i; 
	 		if (!containsKey( key )) 
	 		{ break; }
	 		val = (String) get( key ); 
	 		if (val != null) { errorStack.add( val); }
	 	}
	 	return( errorStack ); 
	 }
	public String getAVSCode() {
		// TODO Auto-generated method stub
		return (String)get(CcReply.AVSCODE);
	}
	public boolean userVerified() {
		System.err.println("getAVSCode():" + getAVSCode());
		return CcReply.passAVSCodes.contains(getAVSCode());
	}

	/* (non-Javadoc)
	 * @see com.beathive.service.commerce.CcReply#getRequestToken()
	 */
	public String getRequestToken() {
		return (String)get(CcReply.REQUEST_TOKEN);
	}

}
