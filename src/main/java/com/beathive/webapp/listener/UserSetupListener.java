package com.beathive.webapp.listener;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beathive.Constants;
import com.beathive.model.LabelValue;
import com.beathive.model.Store;
import com.beathive.model.User;
import com.beathive.service.NotFoundException;
import com.beathive.service.StoreManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.StoreForm;

/**
 * UserSetupListener class 
 */
public class UserSetupListener implements HttpSessionAttributeListener {
    public static final String EVENT_KEY = HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY;
    private final transient Log log = LogFactory.getLog(UserSetupListener.class);

    /**
     * This method is designed to catch when user's login and record their name
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
            SecurityContext securityContext = (SecurityContext) event.getValue();
            makeUserEnvironment(event.getSession() ,  securityContext.getAuthentication());
        }
    }

	protected void makeUserEnvironment(HttpSession session, Authentication authResult){
    	User user = (User)  authResult.getPrincipal();

    	if (user.getPreference() != null ){
    		try {
				session.setAttribute(Constants.PREFERENCE_KEY, ConvertUtil.convert(user.getPreference()));
			} catch (Exception e) {
				log.error(e);
			}
    	}

	    if (isProducer(authResult)){
			try {
				UserSetupListener.doProducerView(user.getUsername(), session);
			} catch (NotFoundException e) {
				log.error(e);
			}
    	}
	}
	
	public static void doProducerView(String username , HttpSession session) throws NotFoundException{
		WebApplicationContext wctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(session.getServletContext());
		StoreManager stmgr = (StoreManager) wctx.getBean("storeManager");
		List<Store> shops = stmgr.getUserStores(username);
		List<LabelValue> storeMenu = new LinkedList<LabelValue>();

		if (shops != null && !shops.isEmpty()) {
			for (Store g : shops) {
				String gid = g.getId().toString();
				storeMenu.add(new LabelValue(g.getName(), gid));
			}
			Store store = (Store) shops.get(0);
			if (store != null) {
				StoreForm storeForm = new StoreForm();
				try {
					BeanUtils.copyProperties(storeForm, store);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				session.setAttribute("storeForm", storeForm);
			}
			session.setAttribute("storeMenu", storeMenu);
		}
	}

	private boolean isProducer(Authentication auth) {
		return (auth != null) ? hasRole(auth, "producer") : false;
	}
    
	private boolean hasRole(Authentication auth , String rolename) {
		if (auth != null){
			GrantedAuthority[] roles = auth.getAuthorities();
			for(GrantedAuthority role : roles){
				if (role.getAuthority().equals(rolename)){
					return true;
				}
			}
		}
		return false;
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
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    /**
     * Needed for Acegi Security 1.0, as it adds an anonymous user to the session and
     * then replaces it after authentication. http://forum.springframework.org/showthread.php?p=63593
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
            SecurityContext securityContext = (SecurityContext) event.getValue();
        	makeUserEnvironment(event.getSession() ,  securityContext.getAuthentication());
        }
    }
}
