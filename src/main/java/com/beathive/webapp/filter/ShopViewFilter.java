/*
 * Created on Oct 5, 2004
*/
package com.beathive.webapp.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beathive.Constants;
import com.beathive.service.StoreManager;
import com.beathive.service.ViewCounter;

/**
 * Looks for shop views. The session attribute, ViewCounter, handles incrementing the DB to 
 * 
 * @author Ronald Dennison
 * 
 * @web.filter display-name="ShopViewFilter" name="shopViewFilter"
 * @web.filter-init-param name="shopIdParam" value="storeId"
 */
public class ShopViewFilter extends BaseFilter {
	private static String shopIdParam = "storeId";
	private static final Log log = LogFactory.getLog(ShopViewFilter.class);
	private static StoreManager storeManager = null;

    
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		storeManager = (StoreManager)super.getBean("storeManager");
		String tmp = config.getInitParameter("shopIdParam");
		if (tmp!=null){
			this.shopIdParam = tmp;
		}
	}
	
	/**
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) req).getSession(false);
		if ((req.getRemoteAddr() != null) && (session!=null )){
			String shopId = ((HttpServletRequest) req).getParameter(this.shopIdParam);
			if(shopId!=null){
				Set shopsViewed =  (Set)session.getAttribute(Constants.USER_SHOP_SESSION_VIEWS);
				if (shopsViewed==null){
					shopsViewed =  new HashSet();
					session.setAttribute(Constants.USER_SHOP_SESSION_VIEWS, shopsViewed);
				}
				if (!shopsViewed.contains(shopId)){
					shopsViewed.add(shopId);
					//if first view in this session
					storeManager.incrementViews(shopId);
				}
			}			
		}
		chain.doFilter(req, res);
	}
	

}