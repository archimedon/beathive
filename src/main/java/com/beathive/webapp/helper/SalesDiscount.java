/**
 * 
 */
package com.beathive.webapp.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Provide methods, user discounts and pricing model for client-side calcs and rendering.
 * 
 * @author ron
 */
public class SalesDiscount {
	
	private final Map<Double , Double> memberDiscountMap;
	
	private Map<Double , Double> nonMemberDiscountMap;
	private Map memberDiscountColorCodes;
	private Map nonMemberDiscountColorCodes;
	
	public static double[] membercostdiscount;
	public static double[] membercostfloor;
	
	public static double[] non_membercostdiscount;
	public static double[] non_membercostfloor;
	
	
	public SalesDiscount(Map<Double , Double> memberdiscs){
		super();
		this.memberDiscountMap = memberdiscs;
		this.membercostfloor = new double[memberDiscountMap.size()];
		this.membercostdiscount = new double[memberDiscountMap.size()];
		int i = 0;
		for (Map.Entry<Double , Double> entry : this.memberDiscountMap.entrySet()){
			SalesDiscount.membercostfloor[i] = entry.getKey().doubleValue();
			SalesDiscount.membercostdiscount[i++] = entry.getValue().doubleValue();
		}
	}

	public SalesDiscount(Map<Double , Double> memberdiscs , Map<Double , Double> nonmember_discs){
		this(memberdiscs);
		this.setNonMemberDiscountMap(nonmember_discs);
	}

	/**
	 * Returns the discount percent or zero if not qualified
	 * 
	 * @param amt
	 * @return
	 */
	public static double getMemberDiscount(double amt){
		int len = membercostfloor.length - 1;
		for (int y = len ; y >= 0  ; y--){
			if (amt >= membercostfloor[y]){
				return membercostdiscount[y];
			}
		}
		return 0;
	}

	/**
	 * Returns the discount percent or zero if not qualified
	 * @param amt
	 * @return
	 */
	public static double getNonMemberDiscount(double amt){
		int len = membercostfloor.length - 1;
		for (int y = len ; y >= 0  ; y--){
			if (amt >= non_membercostfloor[y]){
				return non_membercostdiscount[y];
			}
		}
		return 0;
	}

	/**
	 * @return the memberDiscountMap
	 */
	public Map<Double , Double> getMemberDiscountMap() {
		return memberDiscountMap;
	}

	/**
	 * @return the memberDiscountMap
	 */
	public Map getDiscounted() {
		return new HashMap(){
			public Object get(Object o){
				Object r = o;
				double amt = Double.parseDouble(o.toString());
				int len = membercostfloor.length - 1;
				for (int y = len ; y >= 0  ; y--){
					if (amt >= membercostfloor[y]){
						BigDecimal decimalSubtotal = new BigDecimal(o.toString());
						decimalSubtotal = decimalSubtotal.setScale(2, RoundingMode.HALF_DOWN);
						BigDecimal decimalDiscountPercent = new BigDecimal(membercostdiscount[y]/ 100);
						return decimalSubtotal.subtract(decimalSubtotal.multiply(decimalDiscountPercent)).doubleValue();
					}
				}
				return r;
			}
		};
	}

	/**
	 * Returns the discounted amount or the same value passed if discounts do no apply
	 *  otherwise
	 * @return the discountedAmount
	 */
	public static double getDiscountedAmount(double amt) {
		int len = membercostfloor.length - 1;
		for (int y = len ; y >= 0  ; y--) {
			if (amt >= membercostfloor[y]) {
				return (amt - ( membercostdiscount[y] * amt / 100 )) ;
			}
		}
		return amt;
	}

	/**
	 * @return the memberDiscountMap
	 */
	public Map getMemberDiscount() {
		return new HashMap() {
			public Object get(Object o) {
				double amt = Double.parseDouble(o.toString());
				int len = membercostfloor.length - 1;
				for (int y = len ; y >= 0  ; y--) {
					if (amt >= membercostfloor[y]) {
						return new Double(membercostdiscount[y]);
					}
				}
				return new Double(0);
			}
		};
	}

	/**
	 * @return the memberDiscountMap
	 */
	public Map getMemberColorCode() {
		
		return new HashMap() {
			public Object get(Object o) {
				Object r = o;
				int mark = -1;
				double amt = Double.parseDouble(o.toString());
				int len = membercostfloor.length - 1;
				for (int y = len ; y >= 0  ; y--) {
					if (amt >= membercostfloor[y]) {
						mark = y;
						break;
					}
				}
				if (mark == -1){
					r= "#000";
				}else{
					r = memberDiscountColorCodes.get(membercostdiscount[mark]);
				}
				return r;
			}
		};
	}

	/**
	 * @param memberDiscountMap the memberDiscountMap to set
	 */
	public void setMemberDiscountMap(Map<Double , Double> memberDiscountMap) {
		this.membercostfloor = new double[memberDiscountMap.size()];
		this.membercostdiscount = new double[memberDiscountMap.size()];
		int i = 0;
		for (Map.Entry<Double , Double> entry : this.memberDiscountMap.entrySet()){
			SalesDiscount.membercostfloor[i] = entry.getKey().doubleValue();
			SalesDiscount.membercostdiscount[i++] = entry.getValue().doubleValue();
		}
	}

	/**
	 * @return the nonMemberDiscountMap
	 */
	public Map getNonMemberDiscountMap() {
		return nonMemberDiscountMap;
	}

	/**
	 * @param nonMemberDiscountMap the nonMemberDiscountMap to set
	 */
	public void setNonMemberDiscountMap(Map<Double , Double> nonMemberDiscountMap) {
		this.nonMemberDiscountMap = nonMemberDiscountMap;
		SalesDiscount.non_membercostfloor = new double[nonMemberDiscountMap.size()];
		SalesDiscount.non_membercostdiscount = new double[nonMemberDiscountMap.size()];
		int i = 0;
		for (Map.Entry<Double , Double> entry : this.nonMemberDiscountMap.entrySet()){
			SalesDiscount.non_membercostfloor[i] = entry.getKey().doubleValue();
			SalesDiscount.non_membercostdiscount[i++] = entry.getValue().doubleValue();
		}
	}

	/**
	 * @return the memberDiscountColorCodes
	 */
	public Map getMemberDiscountColorCodes() {
		return memberDiscountColorCodes;
	}

	/**
	 * @param memberDiscountColorCodes the memberDiscountColorCodes to set
	 */
	public void setMemberDiscountColorCodes(Map memberDiscountColorCodes) {
		this.memberDiscountColorCodes = memberDiscountColorCodes;
	}

	/**
	 * @return the nonMemberDiscountColorCodes
	 */
	public Map getNonMemberDiscountColorCodes() {
		return nonMemberDiscountColorCodes;
	}

	/**
	 * @param nonMemberDiscountColorCodes the nonMemberDiscountColorCodes to set
	 */
	public void setNonMemberDiscountColorCodes(Map nonMemberDiscountColorCodes) {
		this.nonMemberDiscountColorCodes = nonMemberDiscountColorCodes;
	}
}
