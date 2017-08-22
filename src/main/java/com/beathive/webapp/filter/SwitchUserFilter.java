package com.beathive.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.ui.switchuser.SwitchUserProcessingFilter;

public class SwitchUserFilter extends SwitchUserProcessingFilter {
	private static String onkey;
	private static String offkey;
	
	public SwitchUserFilter(){
		super();
	}
	
	public void setSwitchUserUrl(String url){
		super.setSwitchUserUrl(url);
		onkey= url;
	}
	
	public void setExitUserUrl(String url){
		super.setExitUserUrl(url);
		offkey= url;
	}
	
	// changed from doFilter to doFilterHttp due to "unable to override final method"
	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	throws IOException, ServletException {
		//Filter request
		HttpSession session = request.getSession();
		
	    boolean isbecoming = (request.getRequestURI().indexOf(onkey) > -1);
		if ( isbecoming 
		|| (request.getRequestURI().indexOf(offkey) > -1)){
	
			if (isbecoming){
				session.setAttribute("switched", Boolean.TRUE);
			}else{
				session.removeAttribute("switched");
			}
		}
	    super.doFilterHttp(request, response,chain);
	}

}
