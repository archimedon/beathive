package com.beathive.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.beathive.Constants;
import com.beathive.webapp.form.PreferenceForm;
import com.beathive.webapp.util.RequestUtil;

/**
 *
 * @author  Ron
 * @web.filter name="checkPreferencesFilter"
 */
public class CheckPreferencesFilter extends OncePerRequestFilter {
    private final transient Log log = LogFactory.getLog(CheckPreferencesFilter.class);

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                 FilterChain chain)
    throws IOException, ServletException {
    	HttpSession session = request.getSession();
        if (session.getAttribute(Constants.DETERMINE_FORMAT_KEY) !=null) {
    		session.removeAttribute(Constants.DETERMINE_FORMAT_KEY);
    		((PreferenceForm)session.getAttribute(Constants.PREFERENCE_KEY)).setFormat(RequestUtil.is_pc_platform(request) ? "wav" : "aif");
        }
        chain.doFilter(request, response);
    }
}
