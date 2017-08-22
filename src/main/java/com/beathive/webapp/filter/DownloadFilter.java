package com.beathive.webapp.filter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.model.User;
import com.beathive.model.UserKey;
import com.beathive.model.Zip;
import com.beathive.service.UserExistsException;
import com.beathive.service.UserManager;
import com.beathive.service.ZipManager;
import com.beathive.webapp.util.RequestUtil;

import java.util.Date;
import java.util.regex.Pattern;
/**
 * Looks for shop views. The session attribute, ViewCounter, handles incrementing the DB to 
 * 
 * @author Ronald Dennison
 * 
 * @web.filter display-name="DownloadFilter" name="downloadFilter"
 * @web.filter-init-param name="varname" value="zid"
 */
public class DownloadFilter extends BaseFilter {
	private static final Log log = LogFactory.getLog(DownloadFilter.class);
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		String uri = ((HttpServletRequest) req).getRequestURI();
		String[] vals = uri.split(Pattern.quote("/"));
		String activationKey = vals[3];  //((HttpServletRequest) req).getParameter(this.varname);
		if (StringUtils.isNotBlank(activationKey)){
			ZipManager zipManager = (ZipManager)getBean("zipManager");
			Zip z = (Zip)zipManager.getZipByKey(activationKey);
			if (z != null){
				writeZip(z.getPath() , res);
				int cnt = z.getAccessCount().intValue();
				z.setAccessCount(new Integer(++cnt));
				z.setAccessTime(new Date());
				zipManager.saveZip(z);
			}
		}
	}
	
	public void writeZip(String filePath, ServletResponse res) throws IOException, ServletException {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {

			URL url = new URL("file://" + filePath);
			URLConnection urlc = url.openConnection();
			int length = urlc.getContentLength();
			// find the right MIME type and set it as contenttype
			res.setContentType("application/x-zip-compressed");
			res.setContentLength(length);

			
			// Use Buffered Stream for reading/writing.
			bis = new BufferedInputStream(urlc.getInputStream());
			bos = new BufferedOutputStream(res.getOutputStream());

			RequestUtil.writeBuf(bis , bos);
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().print("Error Streaming the Data");
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.flush();
				bos.close();
			}
		}
		
	}


}