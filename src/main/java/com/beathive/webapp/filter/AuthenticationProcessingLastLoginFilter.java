package com.beathive.webapp.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beathive.Constants;
import com.beathive.model.CartItem;
import com.beathive.model.LabelValue;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
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
import com.beathive.webapp.form.SoundClipFormCart;
import com.beathive.webapp.form.StoreForm;
import com.beathive.webapp.form.TrackpackFormCart;
import com.beathive.webapp.helper.Cart;
import com.beathive.webapp.helper.PaginatedListFactory;

public class AuthenticationProcessingLastLoginFilter extends AuthenticationProcessingFilter {
	protected final Log log = LogFactory.getLog(getClass());

	private UserManager userManager;
	private PaginatedListFactory paginatedListFactory;
	private CartManager cartManager;

	/**
	 * @return the cartManager
	 */
	public CartManager getCartManager() {
		return cartManager;
	}

	/**
	 * @param cartManager
	 *            the cartManager to set
	 */
	public void setCartManager(CartManager cartManager) {
		this.cartManager = cartManager;
	}

	/**
	 * @return the paginatedListFactory
	 */
	public PaginatedListFactory getPaginatedListFactory() {
		return paginatedListFactory;
	}

	/**
	 * @param paginatedListFactory
	 *            the paginatedListFactory to set
	 */
	public void setPaginatedListFactory(PaginatedListFactory paginatedListFactory) {
		this.paginatedListFactory = paginatedListFactory;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, Authentication authResult) throws IOException {
		HttpSession session = request.getSession();
		if (session == null) {
			log.error("session is null");

		} else {
			User user = userManager.getActiveUser(((User) authResult.getPrincipal()).getId());
			// record and put in session the user's lastlogin date
			doLogin(user, session);
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
		Cart cart = (Cart) session.getAttribute(Constants.USER_CART);
		if (cart == null) {
			cart = new Cart();
			session.setAttribute(Constants.USER_CART, cart);
		}

		List cartList = cart.getList();
		if (cartList == null) {
			cartList = Collections.synchronizedList(new LinkedList<SoundClipForm>());
			cart.setList(cartList);
		}

		List<CartItem> cartitems = user.getCartItems();
		try {
			if (cartitems != null && (!cartitems.isEmpty())) {
				loadCartItems(cartList, cartitems, cartManager);
			} else {
				log.error("cartitems is empty");
			}
		} catch (Exception e1) {
			log.error(e1, e1.getCause());
		}

		try {
			userManager.saveUser(user);
		} catch (UserExistsException e) {
			log.error(e);
		}
	}

	/**
	 * @param cartList
	 * @param cartitems
	 * @param cartManager2
	 */
	public static void loadCartItems(List cartList, List<CartItem> cartitems, CartManager cartManager2)
			throws Exception {
		SoundClipForm clipForm = null;
		SoundClip clip = null;
		for (CartItem citem : cartitems) {

			clip = cartManager2.getCartSoundClip(citem.getClipId().toString());

			if (clip.getClass().equals(Trackpack.class)) {

				clipForm = (TrackpackFormCart) ConvertUtil.convert(clip, TrackpackFormCart.class);
			} else {
				((Loop) clip).setTrackpack(null);
				clipForm = (LoopFormCart) ConvertUtil.convert(clip, LoopFormCart.class);
			}
			if (!cartList.contains(clipForm)) {
				((SoundClipFormCart) clipForm).setFileId(citem.getFileId().toString());
				ConvertUtil.convertNamedList(clipForm, "audioFormat", AudioFileForm.class);
				ConvertUtil.convertNamedList(clipForm, "genre", GenreForm.class);
				cartList.add(clipForm);
			}
		}
	}

	public static LoopFormCart convertLoop(SoundClip clip, String fileId) throws Exception {
		LoopFormCart clipForm = (LoopFormCart) ConvertUtil.convert(clip, LoopFormCart.class);
		ConvertUtil.convertNamedList(clipForm, "audioFormat", AudioFileForm.class);
		ConvertUtil.convertNamedList(clipForm, "genre", GenreForm.class);
		((LoopFormCart) clipForm).setFileId(fileId);
		return clipForm;
	}

	/**
	 * Lookup and set a menu of the users stores in the session
	 * 
	 * @param user
	 * @param session
	 */
	synchronized private void setDerivedMenus(User user, HttpSession session) {

		List storeMenu = new LinkedList();
		WebApplicationContext wctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		StoreManager stmgr = (StoreManager) wctx.getBean("storeManager");

		try {
			AuthenticationProcessingLastLoginFilter.doProducerView(stmgr.getUserStores(user.getUsername()), user,
					session);
		} catch (NotFoundException e) {
			log.error(e);
		}
	}

	public static void doProducerView(List<Store> shops, User user, HttpSession session) throws NotFoundException {
		List storeMenu = new LinkedList();

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

		if (auth != null) {
			GrantedAuthority[] roles = auth.getAuthorities();
			for (GrantedAuthority role : roles) {
				if (role.getAuthority().equals("producer")) {
					return true;
				}
			}
		}
		return false;
	}

}
