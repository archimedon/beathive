package com.beathive.service;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.UserDetails;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.beathive.Constants;
import com.beathive.model.Role;
import com.beathive.model.User;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class ProducerSecurityAdvice implements MethodBeforeAdvice, AfterReturningAdvice {
    public final static String ACCESS_DENIED = "Access Denied: Only administrators are allowed to modify other users.";
    protected final Log log = LogFactory.getLog(ProducerSecurityAdvice.class);
    private UserCache userCache;

    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    /**
     * Method to enforce security and only allow administrators to modify users. Regular
     * users are allowed to modify themselves.
     */
    public void before(Method method, Object[] args, Object target) throws Throwable {
        SecurityContext ctx = SecurityContextHolder.getContext();

        if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            boolean administrator = false;
            GrantedAuthority[] roles = auth.getAuthorities();
            for (int i=0; i < roles.length; i++) {
                if (roles[i].getAuthority().equals(Constants.ADMIN_ROLE)) {
                    administrator = true;
                    break;
                }
            }

            User user = (User) args[0];
            String username = user.getUsername();

            String currentUser;
            if (auth.getPrincipal() instanceof UserDetails) {
                currentUser = ((UserDetails) auth.getPrincipal()).getUsername();
            } else {
                currentUser = String.valueOf(auth.getPrincipal());
            }

            if (username != null && !username.equals(currentUser)) {
                AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
                // allow new users to signup - this is OK b/c Signup doesn't allow setting of roles
                boolean signupUser = resolver.isAnonymous(auth);
                if (!signupUser) {
                    log.info("Verifying that '" + currentUser + "' can modify '" + username + "'");
                    if (!administrator) {
                        log.warn("Access Denied: '" + currentUser + "' tried to modify '" + username + "'!");
                        throw new AccessDeniedException(ACCESS_DENIED);
                    }
                } else {
                    log.info("Registering new user '" + username + "'");
                }
            }

            // don't allow users with "user" role to upgrade to "admin" role
            else if (username != null && username.equalsIgnoreCase(currentUser) && !administrator) {

            	if (method.getName().indexOf("saveUpgradeUser") == -1){
                // get the list of roles the user is trying add
                Set userRoles = new HashSet();
                if (user.getRoles() != null) {
                    for (Iterator it = user.getRoles().iterator(); it.hasNext();) {
                        Role role = (Role) it.next();
                        userRoles.add(role.getName());
                    }
                }

                // get the list of roles the user currently has
                Set authorizedRoles = new HashSet();
                for (int i=0; i < roles.length; i++) {
                    authorizedRoles.add(roles[i].getAuthority());
                }

	                // if they don't match - access denied
	                // users aren't allowed to change their roles
	                if (!CollectionUtils.isEqualCollection(userRoles, authorizedRoles)) {
	                    log.warn("Access Denied: '" + currentUser + "' tried to change their role(s)!");
	                    throw new AccessDeniedException(ACCESS_DENIED);
	                }
                }
            }
        }
    }

    public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
    throws Throwable {
        User user = (User) args[0];

        if (userCache != null && user.getVersion() != null) {
            if (log.isDebugEnabled()) {
                log.debug("Removing '" + user.getUsername() + "' from userCache");
            }

            userCache.removeUserFromCache(user.getUsername());

            // reset the authentication object if current user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserDetails) {
                User currentUser = (User) auth.getPrincipal();
                if (currentUser.getId().equals(user.getId())) {
                    if (!currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                        userCache.removeUserFromCache(currentUser.getUsername());
                    }
                    auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
    }
}
