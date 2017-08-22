/**
 * 
 */
package com.beathive.dao.helper;

import org.hibernate.criterion.Order;

/**
 * @author ron
 *
 */
public class SimpleOrderCriterion extends Order {
	public SimpleOrderCriterion(String propertyName, boolean asc) {
		super(propertyName, asc);
	}
}
