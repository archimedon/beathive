/*
 * Created on Jan 3, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.beathive.webapp.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.beathive.Constants;

/**
 * @author rdennison
 */
public class UserSessionShopViewListener implements HttpSessionListener {

    /**
     * Notification that a Session has been created.
     *
     * @param hse The session event
     */
    public void sessionCreated(HttpSessionEvent hse) {
    }

    /**
     * Notification that a session has been destroyed.
     *
     * @param hse The session event
     */
    public void sessionDestroyed(HttpSessionEvent hse) {
        hse.getSession().removeAttribute(Constants.USER_SHOP_SESSION_VIEWS);
    }
}