package com.beathive.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.model.User;
import com.beathive.model.UserKey;
import com.beathive.service.UserExistsException;
import com.beathive.service.UserManager;

/**
 * Looks for shop views. The session attribute, ViewCounter, handles incrementing the DB to 
 * 
 * @author Ronald Dennison
 * 
 * @web.filter display-name="EnableAccountFilter" name="enableAccountFilter"
 * @web.filter-init-param name="newUserAccessKey" value="uid"
 */
public class EnableAccountFilter extends BaseFilter {
	private static String newUserAccessKey = "uid"; // 'uid' is the  user's generated random key
	private static final Log log = LogFactory.getLog(EnableAccountFilter.class);

    
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		EnableAccountFilter.newUserAccessKey = config.getInitParameter("newUserAccessKey");
	}
	
	/**
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		UserManager baseMgr = (UserManager)getBean("userManager");
		if (request.getMethod().equalsIgnoreCase("get")){
			doNewUserKey(request, baseMgr);
		}
		chain.doFilter(req, res);
	}
	
	public void doNewUserKey(HttpServletRequest request, UserManager baseMgr){
		if (StringUtils.isNotBlank(request.getParameter(EnableAccountFilter.newUserAccessKey))){
			String activationKey = request.getParameter(EnableAccountFilter.newUserAccessKey);
			UserKey userKey = (UserKey)baseMgr.getKey(activationKey);
			if (userKey != null){
				User user = userKey.getUser();
				user.setEnabled(true);
				baseMgr.deleteKey(userKey);
				try {
					baseMgr.saveUser(user);
				} catch (UserExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				userKey = null;
				user = null;
			}
		}
	}


}