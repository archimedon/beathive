/**
 * 
 */
package com.beathive.model;

import java.util.Date;

/**
 * @author ron ron@beathive.com
 * @version 1.9 Date: Jul 9, 2009
 * hibernate.class table="purchase_item"
 */
public class PurchaseItemEx extends PurchaseItem {
	private String email;
	private Date created;

	/**
	 * @return the producerEmail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param producerEmail the producerEmail to set
	 */
	public void setEmail(String producerEmail) {
		this.email = producerEmail;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
}
