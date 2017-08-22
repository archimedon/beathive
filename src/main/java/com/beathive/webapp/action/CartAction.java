/**
 * 
 */
package com.beathive.webapp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.model.CartItem;
import com.beathive.model.Loop;
import com.beathive.model.Purchase;
import com.beathive.model.SoundClip;
import com.beathive.model.Trackpack;
import com.beathive.model.User;
import com.beathive.service.CartManager;
import com.beathive.service.MailEngine;
import com.beathive.service.SoundClipManager;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.AudioFileForm;
import com.beathive.webapp.form.BillingForm;
import com.beathive.webapp.form.CartItemForm;
import com.beathive.webapp.form.GenreForm;
import com.beathive.webapp.form.LoopForm;
import com.beathive.webapp.form.LoopFormCart;
import com.beathive.webapp.form.SoundClipForm;
import com.beathive.webapp.form.SoundClipFormCart;
import com.beathive.webapp.form.TrackpackFormCart;
import com.beathive.webapp.helper.Cart;
import com.beathive.webapp.helper.PaginatedListFactory;
import com.beathive.webapp.helper.SalesDiscount;


/**
 * @author ron
 * 
 * @struts.action name="cartItemForm" path="/cart" scope="request"
 *                validate="true" input="view" parameter="method"
 *                
 * @struts.action name="billingForm" path="/cart/checkout" scope="request"
 *                validate="true" input="view" parameter="method"
 * 
 * @struts.action-forward name="view" path="/WEB-INF/pages/cart/viewCart.jsp"
 * @struts.action-forward name="success" path="/WEB-INF/pages/cart/viewCart.jsp" redirect="false"
 * 
 */
public class CartAction extends BaseAction {
	private static Log log = LogFactory.getLog(CartAction.class);

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'cancel' method");
		}
		return mapping.findForward("mainMenu");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		return view(mapping, form, request, response);
	}

	
	/**
	 * Expects the form to be fully populated. These actions do no do BD lookups
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CartItemForm itemForm = (CartItemForm)form;
		HttpSession session = request.getSession();

		Cart cart = CartAction.getCart(session);
		SoundClipForm clipForm = null;
		if (itemForm.getType().equals("trackpack")) {
			clipForm = new TrackpackFormCart();
		}else{
			clipForm = new LoopFormCart();
		}
		clipForm.setId(itemForm.getClipId());

		int numLoops = 0 ;
		int numTrackpacks = 0;
		List cartList = cart.getList();

		if (!cartList.contains(clipForm)) {
			

			CartManager mgr = (CartManager) getBean("cartManager");

			String clipId = itemForm.getClipId();
			SoundClip clip = mgr.getCartSoundClip(clipId);

			if (clip.getClass().getName().toLowerCase().indexOf("trackpack") > -1) {
				numTrackpacks++;

				clipForm = (TrackpackFormCart) ConvertUtil.convert(clip,
						TrackpackFormCart.class);
				((TrackpackFormCart) clipForm).setFileId(itemForm.getFileId());
				
				ConvertUtil.convertNamedList(clipForm, "audioFormat",
						AudioFileForm.class);
				
				ConvertUtil
						.convertNamedList(clipForm, "genre", GenreForm.class);
			} else {
				clipForm = convertLoop(clip, itemForm.getFileId());
				numLoops++;
			}
			if (!checkFileStat(response, (SoundClipFormCart)clipForm)){
				response.setContentType("text/plain;charset=UTF-8");
				PrintWriter prn = response.getWriter();
				MessageResources resources = getResources(request);
				prn.write("{'message':'"+resources.getMessage("soundclip.error.filenotfound")+"'}");
				return null;
			}
			cartList.add(clipForm);

			// itemForm.setSoundClipForm(clipForm);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (StringUtils.isNotBlank(request.getRemoteUser())){
				Long uid = ((User) auth.getPrincipal()).getId();
				CartItem item = new CartItem();
				BeanUtils.copyProperties(item,itemForm);
				item.setUserId(uid);
				mgr.saveCartItem(item);
			}
		}
		if (StringUtils.equalsIgnoreCase(request.getParameter("rtype") , "summary")){
			
			return mapping.findForward("summary");
		}
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter prn = response.getWriter();
		prn.write(
			"{'numLoops':'" + numLoops +"',"+
			"'numTrackpacks':'" + numTrackpacks +"','cartsize':'"+ (numLoops+ numTrackpacks)+"'}");
		return null;
	}
	
	
	private boolean checkFileStat(HttpServletResponse response , SoundClipFormCart sc ) throws IOException{
		SoundClipManager clipmgr = (SoundClipManager) getBean("soundClipManager");
		String pth = sc.getAudioFile().getFileRef();
		boolean[] flag = clipmgr.filesExist(new String[]{pth});
		return flag[0];
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		Cart cart = CartAction.getCart(request.getSession());
		List<SoundClipForm> itemlist = cart.getList();
		if (itemlist != null && (! itemlist.isEmpty())){
			SoundClipFormCart tmp = (SoundClipFormCart)itemlist.get(0);
		}
		
		if (StringUtils.equalsIgnoreCase(request.getParameter("rtype") , "summary")){
			
			return mapping.findForward("summary");
		}
		Map stats = cart.getTally();
		String numLoops = (String)stats.get("trackpack") ;
		String numTrackpacks = (String)stats.get("loop") ;

		if (request.getParameter("stats")!=null){
			response.setContentType("text/plain;charset=UTF-8");
			PrintWriter prn = response.getWriter();
			prn.write(
				"{'numLoops':'" + numLoops +"',"+
				"'numTrackpacks':'" + numTrackpacks +"','cartsize':'"+ (numLoops+ numTrackpacks)+"'" 
				+ ((request.getRemoteUser()!=null) ? ",'wishsize':'"+ ((com.beathive.model.User )((org.springframework.security.providers.UsernamePasswordAuthenticationToken)((org.springframework.security.context.SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication()).getPrincipal()).getFavorites().size()+"'"
						: "") + "}");
			return null;

		}
		//	super.updateFormBean(mapping, request, new CartItemForm());
		return mapping.findForward("success");
	}
	

	/**
	 * @param cart
	 * @param mgr
	 */
	private List reloadCart(List<SoundClipForm> itemlist, CartManager mgr) {
		List l = new LinkedList();
		SoundClipForm clipForm = null;
		try{
			for (SoundClipForm itemForm : itemlist){
				
				String clipId = ((SoundClipFormCart)itemForm).getId();
				SoundClip clip = mgr.getCartSoundClip(clipId);
				
				if (clip.getClass().equals(Trackpack.class)){
	
					clipForm = (TrackpackFormCart)ConvertUtil.convert(clip , TrackpackFormCart.class);
					((TrackpackFormCart)clipForm).setFileId(((SoundClipFormCart)itemForm).getFileId());
	
					ConvertUtil.convertNamedList(clipForm , "audioFormat" , AudioFileForm.class);
					ConvertUtil.convertNamedList(clipForm , "genre" , GenreForm.class);
				}
				else{
					clipForm = convertLoop(clip, ((SoundClipFormCart)itemForm).getFileId());
				}
				l.add(clipForm);
			}
	
			return l;
		}catch(Exception e){
			log.error(e);
		}
		return l;
	}

	public static LoopFormCart convertLoop(SoundClip clip , String fileId) throws Exception{
		LoopFormCart clipForm = (LoopFormCart)ConvertUtil.convert(clip , LoopFormCart.class);
		ConvertUtil.convertNamedList(clipForm , "audioFormat" , AudioFileForm.class);
		ConvertUtil.convertNamedList(clipForm , "genre" , GenreForm.class);
		((LoopFormCart)clipForm).setFileId(fileId);
		return clipForm;
	}


	public static Cart getCart(HttpSession session){
		
		Cart cart = (Cart)session.getAttribute(Constants.USER_CART);
		if (cart == null){
			cart = new Cart();
//			cart.setDiscountSchedule((SalesDiscount)request.getSession().getServletContext().getAttribute(Constants.DISCOUNT_KEY));
			session.setAttribute(Constants.USER_CART , cart);
		}
//		boolean fromSession = true;
//		ExtendedPaginatedList paginatedList  = paginatedListFactory.getNamedPaginatedList(request , Constants.USER_CART +"Pager" , fromSession , createList);
//    	cart.setPaginatedList(paginatedList);
    	return cart;

	}
	
	public ActionForward sub(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CartItemForm itemForm = (CartItemForm)form;
		HttpSession session = request.getSession();
		
		Cart cart = CartAction.getCart(session);
		SoundClipForm cform = null;
		if (itemForm.getType().equals("trackpack")) {
			cform = new TrackpackFormCart();
		}else{
			cform = new LoopFormCart();
		}
		cform.setId(itemForm.getClipId());

		
		int numLoops = 0 ;
		int numTrackpacks = 0;
		List cartList = cart.getList();
		
		if (cartList != null && StringUtils.isNotBlank(itemForm.getClipId())) {
			if (cartList.contains(cform)) {
				
				CartManager mgr = (CartManager) getBean("cartManager");

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (StringUtils.isNotBlank(request.getRemoteUser())){
					Long uid = ((User) auth.getPrincipal()).getId();
					CartItem item = (CartItem)ConvertUtil.convert(itemForm,CartItem.class);
					item.setUserId(uid);
					mgr.deleteCartItem(item);
					UserManager userManager = (UserManager)getBean("userManager");
					User user = userManager.getActiveUser(uid);
				}
				cartList.remove(cform);
			}
		}
		if (StringUtils.equalsIgnoreCase(request.getParameter("rtype") , "summary")){
			return mapping.findForward("summary");
		}
		
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter prn = response.getWriter();
		prn.write(
			"{'numLoops':'" + numLoops +"',"+
			"'numTrackpacks':'" + numTrackpacks +"','cartsize':'"+ (numLoops+ numTrackpacks)+"'}");
		return null;
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		HttpSession session = request.getSession();
		
		CartAction.getCart(session).getClear();
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null){
			CartManager mgr = (CartManager) getBean("cartManager");
				Long uid = ((User) auth.getPrincipal()).getId();
				mgr.deleteUserCartItems(uid.toString());
				UserManager userManager = (UserManager)getBean("userManager");
				User user = userManager.getActiveUser(uid);
		}
		
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter prn = response.getWriter();
		prn.write("{'numLoops':'0','numTrackpacks':'0','cartsize':'0'}");
		prn.flush();
		prn.close();
		return null;
	}
	
	
	public ActionForward checkout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		BillingForm billingForm = (BillingForm)form;
        // Extract attributes and parameters we will need
		
		// present the billing form
		if (request.getMethod().equalsIgnoreCase("get")){
			String uname = null; 
			if ((uname = request.getRemoteUser())!=null){
				updateBillingForm(billingForm , uname);
			}
		}
		return mapping.findForward("editBilling");
	}

	/**
	 * @param billingForm
	 * @param uname
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void updateBillingForm(BillingForm billingForm, String uname) throws IllegalAccessException, InvocationTargetException {
		UserManager umgr = (UserManager)getBean("userManager");
		User user = umgr.getUserByUsername(uname);
		BeanUtils.copyProperties(billingForm , user.getAddress());
		BeanUtils.copyProperties(billingForm , user);
	}
	


}
