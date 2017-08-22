package com.beathive.service.commerce;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.beathive.Constants;

public interface CcReply extends Map {

	public static final String DECISION_ACCEPT= "ACCEPT";
	public static final String DECISION_KEY ="decision";
//	public static final String DECISION_ACCEPT ="ACCEPT";
	
	public static final String REQUESTID_KEY ="requestID";
	public static final String RECONCILIATION_ID ="ccCaptureReply_reconciliationID";
	public static final String AUTH_AMOUNT ="ccAuthReply_amount";
	public static final String CAPTURED_AMOUNT ="ccCaptureReply_amount";
	public static final String AUTHCODE ="ccAuthReply_authorizationCode";
	public static final String AVSCODE ="ccAuthReply_avsCode";
	public static final String REASONCODE ="ccAuthReply_reasonCode";
	public static final String REQUEST_TOKEN ="requestToken";

	public static List passAVSCodes =    Arrays.asList("1","2","A","C","D","G","I","J","L","M","O","P","Q","R","S","U","W","X","Y","Z");		
	

	
	
	public String getAuthRequestID();
	
	public String getAuthAmount();
	
	public String getCapturedAmount();

	public String getDecision();
	
	public boolean isSuccessful();
	
	public Map getErrors();
	
	/**
	 * Authorization code. Returned only if a value if returned by the processor.
	 * If after validate the value is 'PREAUTH'
	 */
	public String getAuthCode();
	
	public String getAVSCode();

	public String getReasonCode();

	public String getReconciliationID();
	
	public String getRequestToken();

	public boolean userVerified();

}
/*
Reason  Code Description 

100 Successful transaction.

101 The request is missing one or more required fields.  Possible action: See the reply fields missingField_0...N for which fields are  missing. Resend the request with the complete information.

102 One or more fields in the request contains invalid data. Possible action: See the reply fields invalidField_0...N for which fields are invalid.  Resend the request with the correct information.

104 The merchantReferenceCode sent with this authorization request matches the  merchantReferenceCode of another authorization request that you sent in the  last 15 minutes. Possible action: Resend the request with a unique merchantReferenceCode  value.


50 Error: General system failure.  See the documentation for your CyberSource client (SDK) for information about  how to handle retries in the case of system errors.

151 Error: The request was received but there was a server timeout. This error does  not include timeouts between the client and the server. Possible action: To avoid duplicating the order, do not resend the request until you  have reviewed the order status in the Business Center. See the documentation for  your CyberSource client (SDK) for information about how to handle retries in the  case of system errors.

152 Error: The request was received, but a service did not finish running in time.  Possible action: To avoid duplicating the order, do not resend the request until you  have reviewed the order status in the Business Center. See the documentation for  your CyberSource client (SDK) for information about how to handle retries in the  case of system errors.

201 The issuing bank has questions about the request. You do not receive an  authorization code programmatically, but you might receive one verbally by calling  the processor. Possible action: Call your processor or the issuing bank to possibly receive a  verbal authorization. For contact phone numbers, refer to your merchant bank  information.

202 Expired card. You might also receive this if the expiration date you provided does  not match the date the issuing bank has on file. Possible action: Request a different card or other form of payment.

203 General decline of the card. No other information provided by the issuing bank.  Possible action: Request a different card or other form of payment. 


204 Insufficient funds in the account. Possible action: Request a different card or other form of payment.

205 Stolen or lost card. Possible action: Review the customer’s information and determine if you want to  request a different card from the customer.

207 Issuing bank unavailable. Possible action: Wait a few minutes and resend the request.

208 Inactive card or card not authorized for card-not-present transactions. Possible action: Request a different card or other form of payment.


210 The card has reached the credit limit.  Possible action: Request a different card or other form of payment.

211 Invalid card verification number.  Possible action: Request a different card or other form of payment. 

221 The customer matched an entry on the processor’s negative file.  Possible action: Review the order and contact the payment processor. 

231 Invalid account number. Possible action: Request a different card or other form of payment. 

232 The card type is not accepted by the payment processor. Possible action: Request a different card or other form of payment. Also, check  with CyberSource Customer Support to make sure your account is configured  correctly.

233 General decline by the processor. Possible action: Request a different card or other form of payment.

234 There is a problem with your CyberSource merchant configuration. Possible action: Do not resend the request. Contact Customer Support to correct  the configuration problem.

235 The requested amount exceeds the originally authorized amount. Occurs, for  example, if you try to capture an amount larger than the original authorization  amount. This reason code only applies if you are processing a capture through the  API. See “Using the API for Captures and Credits” on page64. Possible action: Issue a new authorization and capture request for the new  amount.

236 Processor failure.  Possible action: Tell the customer the payment processing system is unavailable  temporarily, and to try their order again in a few minutes.

238 The authorization has already been captured. This reason code only applies if you  are processing a capture through the API. See “Using the API for Captures and  Credits” on page64. Possible action: No action required. 


239 The requested transaction amount must match the previous transaction amount.  This reason code only applies if you are processing a capture or credit through the  API. See “Using the API for Captures and Credits” on page64. Possible action: Correct the amount and resend the request.

240 The card type sent is invalid or does not correlate with the credit card number. Possible action: Ask your customer to verify that the card is really the type that  they indicated in your Web store, then resend the request.

241 The request ID is invalid. This reason code only applies when you are processing  a capture or credit through the API. See “Using the API for Captures and Credits”  on page64. Possible action: Request a new authorization, and if successful, proceed with the  capture.

242 You requested a capture through the API, but there is no corresponding, unused  authorization record. Occurs if there was not a previously successful authorization  request or if the previously successful authorization has already been used by  another capture request. This reason code only applies when you are processing a  capture through the API. See “Using the API for Captures and Credits” on page64. Possible action: Request a new authorization, and if successful, proceed with the  capture.

246 The capture or credit is not voidable because the capture or credit information has  already been submitted to your processor. Or, you requested a void for a type of  transaction that cannot be voided. This reason code applies only if you are  processing a void through the API. See “Using the API for Voids” on page75 for  information about voids. Possible action: No action required.

247 You requested a credit for a capture that was previously voided. This reason code  applies only if you are processing a void through the API. See “Using the API for  Voids” on page75 for information about voids. Possible action: No action required.

250 Error: The request was received, but there was a timeout at the payment  processor. Possible action: To avoid duplicating the transaction, do not resend the request  until you have reviewed the transaction status in the Business Center. 


520 The authorization request was approved by the issuing bank but declined by  CyberSource based on your Smart Authorization settings. Possible action: Do not capture the authorization without further review. Review  the ccAuthReply_avsCode, ccAuthReply_cvCode, and  ccAuthReply_authFactorCode fields to determine why CyberSource rejected the  request.

#  =================================  AVS response #
ccreply.A=Street address matches, but both 5-digit and 9-digit postal code do not match.
ccreply.B=Street address matches, but postal code not verified. Returned only for non-U.S.-issued Visa cards.
ccreply.C=Not verified: Street address and postal code not verified. Returned only for non-U.S.-issued Visa cards.
ccreply.D=Street address and postal code both match. Returned only for non-U.S.-issued Visa cards. Although D and M are duplicates, it is possible that you will receive both.
ccreply.E=Invalid: AVS data is invalid.
ccreply.G=Not supported: Non-U.S. issuing bank does not support AVS.
ccreply.I=Not verified: Address not verified. Returned only for non-U.S.-issued Visa cards.
ccreply.J=Match: This code is returned only if you are signed up to use AAV+ with the American Express Phoenix processor. Card member’s name, billing address, and postal code all match. Shipping information verified and chargeback protection guaranteed through the Fraud Protection Program.
ccreply.K=This code is returned only if you are signed up to use Enhanced AVS or AAV+ with the American Express Phoenix processor. Card member’s name matches. Both billing address and billing postal code do not match.
ccreply.L=This code is returned only if you are signed up to use Enhanced AVS or AAV+ with the American Express Phoenix processor. Card member’s name matches. Billing postal code matches, but billing address does not match.
ccreply.M=Match: Duplicate of D. Street address and postal code both match. Returned only for non-U.S.-issued Visa cards. Although D and M are duplicates, it is possible that you will receive both.
ccreply.N=No match: Street address, 5-digit postal code, and 9-digit postal code all do not match.
ccreply.O=This code is returned only if you are signed up to use Enhanced AVS or AAV+ with the American Express Phoenix processor. Card member’s name matches. Billing address matches, but billing postal code does not match.
ccreply.P=Postal code matches, but street address not verified. Returned only for non-U.S.-issued Visa cards.
ccreply.Q=Match: This code is returned only if you are signed up to use AAV+ with the American Express Phoenix processor. Card member’s name, billing address, and postal code all match. Shipping information verified but chargeback protection not guaranteed (Standard program).
ccreply.R=System unavailable: System unavailable.
ccreply.S=Not supported: U.S. issuing bank does not support AVS.
ccreply.U=System unavailable: Address information unavailable. Returned if the U.S. bank does not support non-U.S. AVS or if the AVS in a U.S. bank is not functioning properly.
ccreply.V=Match: This code is returned only if you are signed up to use Enhanced AVS or AAV+ with the American Express Phoenix processor. Card member name matches. Both billing address and billing postal code match.
ccreply.W=Street address does not match, but 9-digit postal code matches.
ccreply.X=Match: Exact match. Street address and 9-digit postal code both match.
ccreply.Y=Match: Exact match. Street address and 5-digit postal code both match.
ccreply.Z=Street address does not match, but 5-digit postal code matches.
ccreply.1=Not supported: CyberSource AVS code. AVS is not supported for this processor or card type.
ccreply.2=Invalid: CyberSource AVS code. The processor returned an unrecognized value for the AVS response.
 

*/