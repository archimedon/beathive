/**
 * 
 */
package com.beathive.webapp.form;

/**
 * @author ron ron@beathive.com
 * @version 1.9 Date: Nov 11, 2009
 * @hibernate.class table="PaypalForm"
 */
public class PaypalForm extends BaseForm {
	private String first_name;
	private String last_name;
	private String middle_name;
	private String payer_email;

	private String business;
	private String custom;
	private String invoice;
	private String receiver_email;


	private String payment_date;
	private String mc_fee;
	private String mc_gross;
	private String mc_currency;
	// status must eq Complete
	private String payment_status;
	private String txn_id;
	// a paypal's unique id for users
	private String payer_id;
	// states if user is verified
	private String payer_status;
	private String verify_sign;

	private String num_cart_items;

	
	public String getNumItems(){
		return this.num_cart_items;
	}
	
	public boolean isPaymentComplete(){
		return "Completed".equals(this.payment_status);
	}
	
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @param payer_email the payer_email to set
	 */
	public void setPayer_email(String payer_email) {
		this.payer_email = payer_email;
	}

	/**
	 * @param business the business to set
	 */
	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * @param custom the custom to set
	 */
	public void setCustom(String custom) {
		this.custom = custom;
	}

	/**
	 * @param invoice the invoice to set
	 */
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	/**
	 * @param receiver_email the receiver_email to set
	 */
	public void setReceiver_email(String receiver_email) {
		this.receiver_email = receiver_email;
	}

	/**
	 * @param payment_date the payment_date to set
	 */
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}

	/**
	 * @param mc_fee the mc_fee to set
	 */
	public void setMc_fee(String mc_fee) {
		this.mc_fee = mc_fee;
	}

	/**
	 * @param mc_gross the mc_gross to set
	 */
	public void setMc_gross(String mc_gross) {
		this.mc_gross = mc_gross;
	}

	/**
	 * @param mc_currency the mc_currency to set
	 */
	public void setMc_currency(String mc_currency) {
		this.mc_currency = mc_currency;
	}

	/**
	 * @param payment_status the payment_status to set
	 */
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	/**
	 * @param txn_id the txn_id to set
	 */
	public void setTxn_id(String txn_id) {
		this.txn_id = txn_id;
	}

	/**
	 * @param payer_id the payer_id to set
	 */
	public void setPayer_id(String payer_id) {
		this.payer_id = payer_id;
	}

	/**
	 * @param payer_status the payer_status to set
	 */
	public void setPayer_status(String payer_status) {
		this.payer_status = payer_status;
	}

	/**
	 * @param num_cart_items the num_cart_items to set
	 */
	public void setNum_cart_items(String num_cart_items) {
		this.num_cart_items = num_cart_items;
	}
	
	/**
	 * @param middle_name the middle_name to set
	 */
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	
	public String getFirstName(){
		return this.first_name;
	}

	public String getLastName(){
		return this.last_name;
	}

	/**
	 * @return the middle_name
	 */
	public String getMiddleName() {
		return middle_name;
	}

	public String getEmail(){
		return this.payer_email;
	}

	/**
	 * The sellers email
	 * @return local email
	 */
	public boolean isMyEmail(String email){
		return this.receiver_email.equals(email);
	}

	public String getTrxnFee(){
		return this.mc_fee;
	}

	public String getMcGross(){
		return this.mc_gross;
	}

	public String getCurrency(){
		return this.mc_currency;
	}

	public String getPaymentStatus(){
		return this.payment_status;
	}

	public String getReconciliationId(){
		return this.txn_id;
	}

	public String getRequestId(){
		return this.payer_id;
	}

	public String getPayerStatus(){
		return this.payer_status;
	}
	
	/**
	 * @return The invoice-id
	 */
	public String getInvoice(){
		return this.invoice;
	}

	/**
	 * @return The user Id
	 */
	public String getCustomerId(){
		return this.custom;
	}

	/**
	 * @return the business
	 */
	public String getBusiness() {
		return business;
	}

	/**
	 * @return the verify_sign
	 */
	public String getAuthCode() {
		return verify_sign;
	}

	/**
	 * @param verify_sign the verify_sign to set
	 */
	public void setVerify_sign(String verify_sign) {
		this.verify_sign = verify_sign;
	}
}
