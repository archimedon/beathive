package com.beathive.webapp.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.beathive.model.User;


/**
 * UserCounterListener class used to count the current number
 * of active users for the applications. It does this by counting
 * user objects as they are put in Session.
 * It also exposes the user count in the servlet context.
 *
 * @author Ronald Dennison
 */
public class UserCounterListener implements ServletContextListener, HttpSessionAttributeListener {
    public static final String COUNT_KEY = "userCounter";
    public static final String USERS_KEY = "userNames";
    public static final String EVENT_KEY = HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY;
    private final transient Log log = LogFactory.getLog(UserCounterListener.class);
    private transient ServletContext servletContext;
    private int counter;
    private Set<Object> users;

    public synchronized void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        servletContext.setAttribute((COUNT_KEY), Integer.toString(counter));
    }

    public synchronized void contextDestroyed(ServletContextEvent event) {
        servletContext = null;
        users = null;
        counter = 0;
    }

    synchronized void incrementUserCounter() {
        counter = Integer.parseInt((String) servletContext.getAttribute(COUNT_KEY));
        counter++;
        servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));

        if (log.isDebugEnabled()) {
            log.debug("User Count: " + counter);
        }
    }

    synchronized void decrementUserCounter() {
        int counter =
            Integer.parseInt((String) servletContext.getAttribute(COUNT_KEY));
        counter--;

        if (counter < 0) {
            counter = 0;
        }

        servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));

        if (log.isDebugEnabled()) {
            log.debug("User Count: " + counter);
        }
    }

    synchronized void addUsername(Object user) {
        users = (Set) servletContext.getAttribute(USERS_KEY);

        if (users == null) {
            users = new HashSet();
            servletContext.setAttribute(USERS_KEY, users);
        }

        if (users.contains(user)) {
        	if (log.isDebugEnabled()) {
        		log.debug("User already logged in");
        	}
        	else {
        		users.add(user);
        		incrementUserCounter();
        	}
        }
    }

    synchronized void removeUsername(Object user) {
        users = (Set) servletContext.getAttribute(USERS_KEY);

        if (users != null) {
            users.remove(user);
            decrementUserCounter();
        }
    }

    /**
     * This method is designed to catch when user's login and record their name
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
            SecurityContext securityContext = (SecurityContext) event.getValue();
            User user = (User) securityContext.getAuthentication().getPrincipal();
            addUsername(user);
        }
    }

    private boolean isAnonymous() {
        AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null) {
            Authentication auth = ctx.getAuthentication();
            return resolver.isAnonymous(auth);
        }
        return true;
    }

    /**
     * When user's logout, remove their name from the hashMap
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
            SecurityContext securityContext = (SecurityContext) event.getValue();
            Authentication auth = securityContext.getAuthentication();
            User user = (User) auth.getPrincipal();
            removeUsername(user);
        }
    }

    /**
     * Needed for Acegi Security 1.0, as it adds an anonymous user to the session and
     * then replaces it after authentication. http://forum.springframework.org/showthread.php?p=63593
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
            SecurityContext securityContext = (SecurityContext) event.getValue();
            if (securityContext.getAuthentication() != null) {
                User user = (User) securityContext.getAuthentication().getPrincipal();
                addUsername(user);
            }
        }
    }
}
