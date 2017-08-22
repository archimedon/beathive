package com.beathive.webapp.filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.Constants;
import com.beathive.model.Promo;
import com.beathive.service.PromoManager;
import com.beathive.webapp.helper.Cart;

/**
 * Looks for shop views. The session attribute, ViewCounter, handles incrementing the DB to 
 * 
 * @author Ronald Dennison
 * 
 * @web.filter display-name="PromoFilter" name="promoFilter"
 * @web.filter-init-param name="promoCodeKey" value="dcode"
 */
public class PromoFilter extends BaseFilter {
	private static String promoCodeKey = "dcode"; // 'codeKey' is the  user's generated random key
	private static final Log log = LogFactory.getLog(PromoFilter.class);
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		this.promoCodeKey = config.getInitParameter("promoCodeKey");
	}
	
	/**
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (request.getMethod().equalsIgnoreCase("get")){
			doPromo(request);
		}
		chain.doFilter(req, res);
	}
	
	public void doPromo(HttpServletRequest request){
		if (StringUtils.isNotBlank(request.getParameter(this.promoCodeKey))){
			
			String promoCode = request.getParameter(this.promoCodeKey);
			
			Cart cart = (Cart)request.getSession().getAttribute(Constants.USER_CART);
			if(cart == null){
				cart = new Cart();
				request.getSession().setAttribute(Constants.USER_CART , cart);
			}
			if (StringUtils.isBlank(cart.getPromoCode())){
				PromoManager baseMgr = (PromoManager)getBean("promoManager");
				Promo promo = (Promo) baseMgr.getPromoByCode(promoCode);
				if (promo != null){
				    // Get Date 1
					Calendar cal = Calendar.getInstance();
					Date d1 = cal.getTime();

					cal.setTime(promo.getStart());
					cal.add(Calendar.DATE, promo.getDuration());
					Date d2 =  cal.getTime();
					if (d2.after(d1)){
						cart.setPromoCode(promo.getCode());
						cart.setPromoDiscount(promo.getDiscount());
					}
				}
			}
		}
	}
	

}