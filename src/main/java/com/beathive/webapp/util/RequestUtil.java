package com.beathive.webapp.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;


/**
 * Convenience class for setting and retrieving cookies.
 */
public class RequestUtil {

	private transient static Log log = LogFactory.getLog(RequestUtil.class);
    
    /**
     * Attempts to determine the platform from header('user_agent')
     * @param req
     * @return
     */
    public static boolean is_pc_platform(HttpServletRequest req) {
      boolean retval = false;
      if ( req == null )  {
	  	  String agent = req.getHeader("USER-AGENT");
	      if ( agent != null) {
	    	  agent = agent.toLowerCase();
	    	  retval = ((agent.indexOf("windows") != -1) || (agent.indexOf("win95") != -1));
	      }
  	  }
      return retval;
  	}

    /**
     * Convenience method to set a cookie
     *
     * @param response
     * @param name
     * @param value
     * @param path
     */
    public static void setCookie(HttpServletResponse response, String name,
                                 String value, String path) {
        if (log.isDebugEnabled()) {
            log.debug("Setting cookie '" + name + "' on path '" + path + "'");
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setPath(path);
        cookie.setMaxAge(3600 * 24 * 30); // 30 days

        response.addCookie(cookie);
    }

    /**
     * Convenience method to get a cookie by name
     *
     * @param request the current request
     * @param name the name of the cookie to find
     *
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null) {
            return returnCookie;
        }

        for (int i = 0; i < cookies.length; i++) {
            Cookie thisCookie = cookies[i];

            if (thisCookie.getName().equals(name)) {
                // cookies with no value do me no good!
                if (!thisCookie.getValue().equals("")) {
                    returnCookie = thisCookie;

                    break;
                }
            }
        }
        return returnCookie;
    }

    /**
     * Convenience method for deleting a cookie by name
     *
     * @param response the current web response
     * @param cookie the cookie to delete
     * @param path the path on which the cookie was set
     */
    public static void deleteCookie(HttpServletResponse response,
                                    Cookie cookie, String path) {
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }
    
    /**
     * Convenience method to get the application's URL based on request
     * variables.
     */
    public static String getAppURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
    	int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getContextPath());
        return url.toString();
    }

	public static String getPartialPath(HttpServletRequest aRequest,
			String returnUri) {
		String ctxpath = aRequest.getContextPath();
		int len = ctxpath.length();
		int ptr = returnUri.indexOf(ctxpath);
		return returnUri.substring(ptr + len);
	}

	/**
	 * create a random string with the given length
	 * @param length
	 * @return a random string
	 */
	public static String makeRandString(int length) {

		char[] byteOut = new char[length];
		try {
			Random rand = new Random((new Date()).getTime());
			byte[] byteAry = new byte[length * 6];
			rand.nextBytes(byteAry);
			int g = 0;
			int y = 0;
			int val = -1;
			while (y < byteOut.length) {
				if (g == byteAry.length) {
					g = 0;
				}
				val = Math.abs(byteAry[g]);
				if (isASCII(val)) {
					byteOut[y++] = (char) val;
				}
				g++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(byteOut);

	}
	
	public static String generatePassword() {
	
		char[] byteOut = new char[16];
		try {
			Random rand = new Random((new Date()).getTime());
			byte[] byteAry = new byte[100];
			rand.nextBytes(byteAry);
			int g = 0;
			int y = 0;
			int val = -1;
			while (y < byteOut.length) {
				if (g == byteAry.length) {
					g = 0;
				}
				val = Math.abs(byteAry[g]);
				if (isASCII(val)) {
					byteOut[y++] = (char) val;
				}
				g++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(byteOut);
	
	}

	public static boolean isASCII(int val){
		int nine = 57;
		int zero = 48;
		int z = 122;
		int a = 97;
		int Z=90;
		int A = 65;
		return (val >= zero && val <= nine) ? true :
					( (val >= a && val <= z) ? true:
						((val >=A && val <=Z)? true : false));
	}
	
	
	public static String makeUniqueFileName(File fdir, String fname) {
		
		String ext ="";
		String prefix = fname;
		int ptr = fname.lastIndexOf(".");
		if (ptr > -1) {
			prefix = fname.substring(0,ptr);
			ext = fname.substring(ptr +1);
		}
		return makeUniqueFileName(fdir, prefix, ext);
	}
	/**
	 * @param fdir
	 * @param dateStr
	 * @param fileExt
	 * @return
	 */
	public static String makeUniqueFileName(File fdir, String prefix, String fileExt) {
		
		final Pattern pattern = Pattern.compile(prefix + ".*\\." +fileExt);
	    String[] children = fdir.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return pattern.matcher(name).matches();
			}
	    });
	    if (children!=null && children.length>0) {
	    	prefix += "_"+ children.length;
	    }
	    return prefix + "." + fileExt;
	}

	/**
	 * Write InputStream to File
	 * @param in
	 * @param fout
	 * @return
	 * @throws IOException
	 */
	public static File fwrite(InputStream in, File fout) throws IOException{

		if (! fout.getParentFile().exists()) {
			fout.getParentFile().mkdirs();
		}
		BufferedInputStream bufin = new BufferedInputStream(in);
		FileOutputStream out = new FileOutputStream(fout);
		int bite = -1;
		while ((bite = bufin.read())!=-1) {
			out.write(bite);
		}
		out.flush();
		out.close();
		bufin.close();
		return fout;
	}
	
    public static void writeBuf(InputStream stream , OutputStream bos) throws IOException{
    	int bytesRead = 0;
    	byte[] buffer = new byte[8192];
    	
    	while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
    		bos.write(buffer, 0, bytesRead);
    	}

    }

	/**
	 * Write InputStream to File
	 * @param in
	 * @param fout
	 * @return
	 * @throws IOException
	 */
	public static File fwrite(String in, File fout) throws IOException{
		
		if(! fout.getParentFile().exists()){
			fout.getParentFile().mkdirs();
		}
		StringReader bufin = new StringReader(in);
		FileOutputStream out = new FileOutputStream(fout);
		int bite = -1;
		while((bite = bufin.read())!=-1){
			out.write(bite);
		}
		out.flush();
		out.close();
		bufin.close();
		return fout;
	}
	
    /**
     * Builds a query string from a given map of parameters
     *
     * @param m A map of parameters
     * @param ampersand String to use for ampersands (e.g. "&" or "&amp;" )
     *
     * @return query string (with no leading "?")
     */
    public static StringBuffer createQueryStringFromMap(Map m, String ampersand) {
        StringBuffer aReturn = new StringBuffer("");
        Set aEntryS = m.entrySet();
        Iterator aEntryI = aEntryS.iterator();

        while (aEntryI.hasNext()) {
            Map.Entry aEntry = (Map.Entry) aEntryI.next();
            Object o = aEntry.getValue();

            if (o == null) {
                append(aEntry.getKey(), "", aReturn, ampersand);
            } else if (o instanceof String) {
                append(aEntry.getKey(), o, aReturn, ampersand);
            } else if (o instanceof String[]) {
                String[] aValues = (String[]) o;

                for (int i = 0; i < aValues.length; i++) {
                    append(aEntry.getKey(), aValues[i], aReturn, ampersand);
                }
            } else {
                append(aEntry.getKey(), o, aReturn, ampersand);
            }
        }
        return aReturn;
    }

    /**
     * Appends new key and value pair to query string
     *
     * @param key parameter name
     * @param value value of parameter
     * @param queryString existing query string
     * @param ampersand string to use for ampersand (e.g. "&" or "&amp;")
     *
     * @return query string (with no leading "?")
     */
    private static StringBuffer append(Object key, Object value,
                                       StringBuffer queryString,
                                       String ampersand) {
        if (queryString.length() > 0) {
            queryString.append(ampersand);
        }

        TagUtils tagUtils = TagUtils.getInstance();

        // Use encodeURL from Struts' RequestUtils class - it's JDK 1.3 and 1.4 compliant
        queryString.append(tagUtils.encodeURL(key.toString()));
        queryString.append("=");
        queryString.append(tagUtils.encodeURL(value.toString()));

        return queryString;
    }

	/**
	 * Return a queryString even if from a POST. Blank if no params.
	 * @param request
	 * @return
	 */
	public static String getQueryString(HttpServletRequest request) {
		String str = "";
		Map m = request.getParameterMap();
		if (m!=null){
			str = RequestUtil.createQueryStringFromMap(m , "&").toString();
		}
		return str;
	}

	/**
	 * Return the current URL, quesyString included even if the result of a POST
	 * @param request
	 * @return
	 */
	public static String getURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
    	int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getRequestURI());
        String qstr = RequestUtil.getQueryString(request);
        url.append((qstr.length() > 0) ? "?" + qstr : "");
        return url.toString();
	}
}

