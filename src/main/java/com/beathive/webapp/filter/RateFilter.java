/*
 * Created on Oct 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.beathive.webapp.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import com.beathive.service.ServiceException;
import com.beathive.service.SoundClipManager;
import com.beathive.model.UserClipScore;

/**
	 * @web.filter display-name="Rate Filter" name="rateFilter"
	 */
public class RateFilter extends BaseFilter {
    private static Log log = LogFactory.getLog(RateFilter.class);

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (	StringUtils.isNotBlank(request.getParameter("score")) 
				&& StringUtils.isNotBlank(request.getParameter("userId")) 
				&& StringUtils.isNotBlank(request.getParameter("clipId"))
		){
			Long clipId = new Long(request.getParameter("clipId"));
			Long userId = new Long(request.getParameter("userId"));
			Integer score = new Integer(request.getParameter("score"));
			PrintWriter prn = response.getWriter();
			response.setContentType("text/plain");
			response.setContentLength(1);
			try {
				SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");
				UserClipScore ucs = new UserClipScore(userId,clipId, score);
				
				prn.write(mgr.rateClip(ucs) ? "1" : "0");
			} catch (Exception e) {
				log.error(e);
			}finally{
				prn.flush();
				prn.close();
			
			}
		}
		chain.doFilter(req, resp);
	}
}
