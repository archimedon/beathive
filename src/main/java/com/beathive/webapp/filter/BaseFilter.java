package com.beathive.webapp.filter;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <p>
 * basic methods required by most Filters in this appication
 * </p>
 * 
 * <p>
 * <a href="BaseFilter.java.do"> <i>View Source </i> </a>
 * </p>
 */
public abstract class BaseFilter implements Filter {
	//~ Instance fields
	// ========================================================
	//   private Log log = LogFactory.getLog(BaseFilter.class);
	protected ServletContext servletctx = null;

	//~ Methods
	// ================================================================

	/**
	 * Initialize controller values of filter.
	 * @throws ServletException
	 */
	public void init(FilterConfig config) throws ServletException {
		servletctx = config.getServletContext();
		servletctx.log(this.getClass().getName()+"::Started");
	}

	/**
	 * destroy any instance values other than config *
	 */
	public void destroy() {

		servletctx.log(this.getClass().getName()+"::Destroyed");
		servletctx = null;
	}
	
	
	
	protected Object getBean(String name ) {
		
		return WebApplicationContextUtils.getRequiredWebApplicationContext(this.servletctx).getBean(name);
	}
/*	WebApplicationContext context = (WebApplicationContext) config
	.getServletContext()
	.getAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
*/	protected void initUser(){
		
	}

/** 
 * A stroke of laziness
 * @param l
 * @return
 */
public static boolean listNotEmpty(List l){
	return (l!=null && l.size()>0);
}

}