/**
 * 
 */
package com.beathive.webapp.filter;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.ui.rememberme.RememberMeProcessingFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beathive.Constants;
import com.beathive.model.CartItem;
import com.beathive.model.SoundClip;
import com.beathive.model.User;
import com.beathive.service.CartManager;
import com.beathive.service.NotFoundException;
import com.beathive.service.StoreManager;
import com.beathive.service.UserExistsException;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.AudioFileForm;
import com.beathive.webapp.form.GenreForm;
import com.beathive.webapp.form.LoopFormCart;
import com.beathive.webapp.form.SoundClipForm;
import com.beathive.webapp.helper.Cart;

/**
 * @author ron
 *
 */
public class UserPrefRememberMeFilter extends RememberMeProcessingFilter {
	protected final Log log = LogFactory.getLog(getClass());
	private UserManager userManager;
	private CartManager cartManager;

	public UserPrefRememberMeFilter(){
		super();
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	protected  void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult){
    	HttpSession session = request.getSession();


		if (authResult != null){
	    	User user = userManager.getActiveUser(((User)  authResult.getPrincipal()).getId());
	    	doLogin(user , session);
		}
	}
	
	/**
	 * @param user
	 * @param request
	 */
	private void doLogin(User user, HttpSession session) {
		Date d = user.getLastLogin();
		session.setAttribute("lastlogin", d);
		user.setLastLogin(new Date());
		Cart cart = (Cart)session.getAttribute(Constants.USER_CART);
		if (cart == null){
			cart = new Cart();
			session.setAttribute(Constants.USER_CART , cart);
		}

		List cartList = cart.getList();
		if (cartList==null){
			cartList = Collections.synchronizedList(new LinkedList<SoundClipForm>());
			cart.setList(cartList);
		}
		
		List<CartItem> cartitems = user.getCartItems();
		try {
			if(cartitems != null && (! cartitems.isEmpty())){
				AuthenticationProcessingLastLoginFilter.loadCartItems(cartList , cartitems , cartManager);
			}
		}catch(Exception e1){
			log.error(e1 , e1.getCause());
		}
		
		try {
			userManager.saveUser(user);
		} catch (UserExistsException e) {
			log.error(e);
		}
	}

	public static LoopFormCart convertLoop(SoundClip clip , String fileId) throws Exception{
		LoopFormCart clipForm = (LoopFormCart)ConvertUtil.convert(clip , LoopFormCart.class);
		ConvertUtil.convertNamedList(clipForm , "audioFormat" , AudioFileForm.class);
		ConvertUtil.convertNamedList(clipForm , "genre" , GenreForm.class);
		((LoopFormCart)clipForm).setFileId(fileId);
		return clipForm;
	}

	
	
	/**
	* Lookup and set a menu of the users stores in the session
	* @param user
	* @param session
	*/
	synchronized private void setDerivedMenus(User user, HttpSession session) {
		
		List storeMenu = new LinkedList();
		WebApplicationContext wctx = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
		StoreManager stmgr = (StoreManager)wctx.getBean("storeManager");
		
		try {
			AuthenticationProcessingLastLoginFilter.doProducerView(stmgr.getUserStores(user.getUsername()), user, session);
		} catch (NotFoundException e) {
			log.error(e);
		}
	}

	private boolean isProducer(Authentication auth) {
		
	 if (auth != null){
	     GrantedAuthority[] roles = auth.getAuthorities();
	     for(GrantedAuthority role : roles){
	     	if (role.getAuthority().equals("producer")){
	     		return true;
	     	}
	     }
	 }
	 return false;
	}

	/**
	 * @return the cartManager
	 */
	public CartManager getCartManager() {
		return cartManager;
	}

	/**
	 * @param cartManager the cartManager to set
	 */
	public void setCartManager(CartManager cartManager) {
		this.cartManager = cartManager;
	}

}
