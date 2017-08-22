package com.beathive.webapp.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beathive.service.ServiceException;
import com.beathive.service.SoundClipManager;
import com.beathive.model.UserClipScore;

/**
 * @author ron
 *
 */
public class RateServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(RateServlet.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
    java.io.IOException{
		if (	StringUtils.isNotBlank(request.getParameter("score")) 
				&& StringUtils.isNotBlank(request.getParameter("userId")) 
				&& StringUtils.isNotBlank(request.getParameter("clipId"))
				&& StringUtils.isNotBlank(request.getParameter("shopId"))
		){
			Long clipId = new Long(request.getParameter("clipId"));
			Long userId = new Long(request.getParameter("userId"));
			Long storeId = new Long(request.getParameter("shopId"));
			Integer score = new Integer(request.getParameter("score"));
			SoundClipManager mgr = (SoundClipManager) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("soundClipManager");
			UserClipScore ucs = new UserClipScore(userId,clipId, storeId, score);
			boolean b = mgr.rateClip(ucs);
			if (StringUtils.isBlank(request.getParameter("js"))){
				String referer = request.getHeader("Referer");
				response.sendRedirect(response.encodeRedirectURL(referer));
			}else{
				PrintWriter prn = response.getWriter();
				response.setContentType("text/plain");
				response.setContentLength(1);
				prn.write(b ? "1" : "0");
				prn.flush();
				prn.close();
			}
		}
	}
}
