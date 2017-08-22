/*
 * Created on Oct 5, 2004
*/
package com.beathive.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Ronald Dennison
 */
public class UserDirFilter implements Filter {
	private static final Log log = LogFactory.getLog(UserDirFilter.class);
	protected ServletContext servletctx = null;
	private static String diskName="BeatDav";

	//~ Methods
	// ================================================================


	/**
	 * Initialize controller values of filter.
	 * @throws ServletException
	 */
	public void init(FilterConfig config) throws ServletException {
		servletctx = config.getServletContext();
		servletctx.log(this.getClass().getName()+"::Started");
		
		if (StringUtils.isNotBlank(config.getInitParameter("diskName"))){
			diskName = config.getInitParameter("diskName");
		}
	}

	/**
	 * destroy any instance values other than config *
	 */
	public void destroy() {
		servletctx.log(this.getClass().getName()+"::Destroyed");
		servletctx = null;
	}
	
	
	/**
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		String url = ((HttpServletRequest) req).getRequestURI().trim();
		
		String username = ((HttpServletRequest) req).getRemoteUser();
		String ctxpath = ((HttpServletRequest) req).getContextPath();
		String newpath = ctxpath + "/" + username + "/"+diskName;
		if (!url.startsWith(newpath)){
			((HttpServletResponse)res).sendError(((HttpServletResponse)res).SC_UNAUTHORIZED);
			HttpSession session = ((HttpServletRequest) req).getSession(false);
			session.invalidate();
		}
		else{
			chain.doFilter(req, res);
		}
	}
	

}