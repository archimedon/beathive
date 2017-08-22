package com.beathive.webapp.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.beathive.webapp.form.SoundClipForm;
import com.beathive.webapp.form.TrackpackFormCart;

/**
 * @author ron
 */
public class Cart implements Serializable{

	private static final long serialVersionUID = -5465254748360341350L;

	private List<SoundClipForm> list = Collections.synchronizedList(new LinkedList<SoundClipForm>());

	protected String promoCode;
	protected Double promoDiscount;

	public String getPromoCode() {
		return promoCode;
	}

	public Map getMap(){
		java.util.Map _m = new java.util.HashMap();
		
		for (int y = 0; y < list.size(); y++){
			com.beathive.webapp.form.SoundClipFormCart sf = (com.beathive.webapp.form.SoundClipFormCart)list.get(y);
			_m.put(sf.getId() , sf.getFileId());
		}
		return _m;
	}
	/**
	 * @param promoCode the promoCode to set
	 */
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	/**
	 * @param session
	 */
	public Cart() {
		super();
	}

	private boolean hasList(){
		return (list!=null);
	}
	public Double getTotal(){
		double tot = 0.0;
		if (hasList()){
			tot += Cart.getSoundClipTotal(list);
		}
		return new Double(tot);
	}
	
	public static Double getSoundClipTotal(List<SoundClipForm> list){
		BigDecimal tot = new BigDecimal(0.00);	
		for (SoundClipForm clip : list){
			tot = tot.add(new BigDecimal(clip.getPriceForm().getAmount()));
		}
		return tot.doubleValue(); 
	}

	public Map getTally(){
		Map pairs = new HashMap();
		int numtp = 0;
		int numloop = 0;

		if (hasList()){
			for (com.beathive.webapp.form.SoundClipForm f : this.list){
				if (f.getClass().equals(TrackpackFormCart.class)){
					numtp++;
				}else{
					numloop++;
				}
			}
		}
		pairs.put("trackpack", numtp +"");
		pairs.put("loop", numloop +"");
		return pairs;
	}

	/**
	 * @return the paginatedList
	 */
	public List<SoundClipForm> getList() {
		return list;
	}

	/**
	 * @param paginatedList the paginatedList to set
	 */
	public void setList(List<SoundClipForm> list) {
		this.list = list;
	}

	public String getClear(){
		this.promoDiscount = null;
		this.promoCode = null;
		if (hasList()){
			this.list.clear();
		}
		return "";
	}
	
	public double getDiscountedAmount(){
		double newprice = SalesDiscount.getDiscountedAmount(this.getTotal().doubleValue());
		if (promoDiscount!=null){
			newprice = newprice - (newprice * this.promoDiscount.doubleValue());
		}
		  BigDecimal decimalSubtotal = new BigDecimal(Double.toString(newprice));
		  
		  if(decimalSubtotal.precision() > 3){
			  decimalSubtotal = decimalSubtotal.setScale(2, RoundingMode.DOWN);
		  }
		
		return decimalSubtotal.doubleValue();
	}
	
	public double getDiscount(){
		return SalesDiscount.getMemberDiscount(this.getTotal().doubleValue());
	}

	/**
	 * @return the promoDiscount
	 */
	public Double getPromoDiscount() {
		return promoDiscount;
	}

	/**
	 * @param promoDiscount the promoDiscount to set
	 */
	public void setPromoDiscount(Double promoDiscount) {
		this.promoDiscount = promoDiscount;
	}
	
}
