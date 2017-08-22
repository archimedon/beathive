/**
 * 
 */
package com.beathive.model;

/**
 * @author ron ron@beathive.com
 * @version 1.9 Date: May 3, 2009
 */
public class SalesSummary {

	private Type volume;
	private Type revenue;
	/**
	 * @return the volume
	 */
	public Object getVolume() {
		return volume;
	}

	/**
	 * @return the revenue
	 */
	public Object getRevenue() {
		return revenue;
	}
}
class Type{
	double loops = 0;
	double trackpacks = 0;
	double total = loops + trackpacks;
}