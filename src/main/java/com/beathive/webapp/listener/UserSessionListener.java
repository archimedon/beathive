/**
 * 
 */
package com.beathive.webapp.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.Constants;
import com.beathive.webapp.form.PreferenceForm;
import com.beathive.webapp.helper.Cart;

/**
 * @author ron
 */
public class UserSessionListener implements HttpSessionListener {
	private static final Log log = LogFactory.getLog(UserSessionListener.class);

	/* Session Creation	Event */
	public void	sessionCreated(HttpSessionEvent	se)	{
		HttpSession sess = se.getSession();
		// add the cart
		sess.setAttribute(Constants.USER_CART , new Cart());
    	PreferenceForm form = new PreferenceForm();
		form.setHideFav(Boolean.TRUE);
		form.setHideOwned(Boolean.TRUE);
		
		sess.setAttribute(Constants.DETERMINE_FORMAT_KEY, Boolean.TRUE);
		sess.setAttribute(Constants.PREFERENCE_KEY, form);
	}

	/* Session Invalidation	Event */
	public void	sessionDestroyed(HttpSessionEvent se) {
		HttpSession sess = se.getSession();
	}
}

