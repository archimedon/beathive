/*
 * Created on Oct 5, 2004
*/
package com.beathive.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.Constants;
import com.beathive.model.User;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.UserForm;

/**
 * This class is used to filter set banner on front page.
 * 
 * <p>
 * <a href="UserEnvFilter.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author Ronald Dennison
 * @version $Revision: 1.0 $ $Date: 2004/05/25 06:27:23 $
 * 
 * @web.filter display-name="User Environment Filter" name="userEnvFilter"
 * @web.filter-init-param name="homePageID" value="mainMenu"
 */
public class UserEnvFilter extends BaseFilter {
	private static String homeId = "mainMenu";
	private static final Log log = LogFactory.getLog(UserEnvFilter.class);

    public UserEnvFilter(){
    	super();
    }
    
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		String tmp = config.getInitParameter("homePageID");
		if (tmp!=null){
			homeId = tmp;
		}
	}
	
	/**
	 * TODO this method used to out the UserForm in the session
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO a better soln for the page-banner changing
		checkBanner((HttpServletRequest) req);
		setUser((HttpServletRequest)req);
		chain.doFilter(req, res);
	}
	

	private void checkBanner(HttpServletRequest req){
		  String uri =   ((HttpServletRequest) req).getRequestURI();
		  String ppath = uri.replaceAll(((HttpServletRequest) req).getContextPath() ,"");
		  if (ppath.indexOf(homeId) > -1 || StringUtils.isBlank(ppath)){
			  ((HttpServletRequest) req).setAttribute("homepg" , Boolean.TRUE);
		  }
		
	}
	
	private void setUser(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		if (session!=null && session.getAttribute(Constants.USER_KEY)==null && req.getRemoteUser() !=null){
			UserManager mgr = (UserManager) getBean("userManager");
			try {
				User user = (User) mgr.getUser(req.getRemoteUser());
				if (user!=null){
				UserForm userf = (UserForm) ConvertUtil.convert(user);
				ConvertUtil.convertLists(userf);
				session.setAttribute(Constants.USER_KEY, userf);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}