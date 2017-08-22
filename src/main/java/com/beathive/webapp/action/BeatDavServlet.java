package com.beathive.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author ron
 *
 */
public class BeatDavServlet extends org.apache.jackrabbit.j2ee.RepositoryAccessServlet {
	private static final Log log = LogFactory.getLog(BeatDavServlet.class);
	public BeatDavServlet(){
		super();
	}
	public void service(HttpServletRequest req, HttpServletResponse res){
		service(req, res);
	}
	public void doPut(HttpServletRequest req, HttpServletResponse res){
		this.doPut(req, res);
	}
}
