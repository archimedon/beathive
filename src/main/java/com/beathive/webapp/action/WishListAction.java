/**
 * 
 */
package com.beathive.webapp.action;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.model.Favorite;
import com.beathive.model.SoundClip;
import com.beathive.model.User;
import com.beathive.service.CartManager;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.AudioFileForm;
import com.beathive.webapp.form.CartItemForm;
import com.beathive.webapp.form.FavoriteForm;
import com.beathive.webapp.form.GenreForm;
import com.beathive.webapp.form.LoopFormCart;
import com.beathive.webapp.form.SoundClipForm;
import com.beathive.webapp.form.TrackpackFormCart;
import com.beathive.webapp.helper.Cart;
import com.beathive.webapp.helper.PaginatedListFactory;
import com.beathive.webapp.util.RequestUtil;


/**
 * @author ron
 * @struts.action name="favoriteForm" path="/user/wishList" scope="request"
 *                validate="true" input="view" parameter="method"
 * 
 * @struts.action-forward name="view" path="/WEB-INF/pages/cart/viewWishList.jsp" redirect="false"
 * @struts.action-forward name="jsreply" path="/WEB-INF/pages/cart/tojs.jsp" redirect="false"
 * 
 */
public class WishListAction extends BaseAction {
	private static Log log = LogFactory.getLog(WishListAction.class);

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'cancel' method");
		}
		return mapping.findForward("mainMenu");
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

		
		String ctype = (request.getParameter("js")!=null)
		? "text/plain;"
		: (request.getParameter("x")!=null ? "text/xml;" :  "text/html;");

		
		String redir = request.getParameter("redir");
		ActionForward af = mapping.findForward("jsreply");
		if (StringUtils.isNotBlank(redir)){
			af = new ActionForward(RequestUtil.getPartialPath(request, redir),true);
			ctype = "text/html;";
		}

		FavoriteForm favForm = (FavoriteForm)form;

		ExtendedPaginatedList pageList = retrievePager(request,Constants.USER_FAVORITES , true);
		
		UserManager umgr = (UserManager)getBean("userManager");
		Favorite fav = (Favorite)convert(favForm);
		Long userId = 	pageList.getViewerId();
		fav.setUserId(userId);
			
		

		// get the User
		User user = umgr.getUser(userId.toString());
		List favs = user.getFavorites();
		
		// TODO Would use composite id but for xdoclet's incapacity
		// to gen mapping maybe later 
		// this is required to prevent duplicates.
		if(! favs.contains(fav)){
			// finish preparing fav
			fav.setCreated(new Date());
			favs.add(fav);
			// update user
			umgr.saveUser(user);
		}
		BeanUtils.copyProperties(favForm, fav);

		updateFormBean(mapping, request, favForm);
//		}
		response.setContentType(ctype + "charset=UTF-8");
		
		return af;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FavoriteForm favForm = (FavoriteForm)form;
		
		// always a  new pager
		request.getSession().removeAttribute(Constants.USER_FAVORITES);
		// get new pager
		ExtendedPaginatedList pageList = retrievePager(request,Constants.USER_FAVORITES , true);
		
		UserManager umgr = (UserManager)getBean("userManager");
		// get the User
		User user = umgr.getUser(pageList.getViewerId().toString());
		// and favs
		List<Favorite> favs = user.getFavorites();
		List citems = new LinkedList();
		CartManager mgr = (CartManager)getBean("cartManager");

		SoundClipForm clipForm = null;
		for (Favorite fav : favs){
			// get the clip
			SoundClip clip = mgr.getCartSoundClip(fav.getClipId().toString());
			// determine clip type
			if (clip.getClass().getName().toLowerCase().indexOf("trackpack") > -1){
				// then copy it
				clipForm = (TrackpackFormCart)ConvertUtil.convert(clip , TrackpackFormCart.class);
				// set the fileId in the view
				((TrackpackFormCart)clipForm).setFileId(fav.getFileId().toString());
			}
			else{
				// then copy it
				clipForm = (LoopFormCart)ConvertUtil.convert(clip , LoopFormCart.class);
				// set the fileId in the view
				((LoopFormCart)clipForm).setFileId(fav.getFileId().toString());
			}
			// convert necessary Lists
			ConvertUtil.convertNamedList(clipForm , "audioFormat" , AudioFileForm.class);
			ConvertUtil.convertNamedList(clipForm , "genre" , GenreForm.class);
			
			// add to items
			citems.add(clipForm);
		}
		// set in pager's list
		pageList.setList(citems);

//		super.updateFormBean(mapping, request, new FavoriteForm());
		return mapping.findForward("view");
	}
	
	
	public ExtendedPaginatedList retrievePager(HttpServletRequest request, String listName , boolean createList){
    	PaginatedListFactory paginatedListFactory = (PaginatedListFactory)getBean("paginatedListFactory");
    	boolean fromSession = true;
    	
    	ExtendedPaginatedList paginatedList  = paginatedListFactory.getNamedPaginatedList(request , listName , fromSession , createList);
		return paginatedList;

	}

	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		return view(mapping, form, request, response);
	}
		
	public ActionForward sub(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {


		String ctype = (request.getParameter("js")!=null)
		? "text/plain;"
		: (request.getParameter("x")!=null ? "text/xml;" :  "text/html;");

		
		String redir = request.getParameter("redir");
		ActionForward af = mapping.findForward("jsreply");
		if (StringUtils.isNotBlank(redir)){
			af = new ActionForward(RequestUtil.getPartialPath(request, redir),true);
			ctype = "text/html;";
		}

		FavoriteForm favForm = (FavoriteForm)form;

		ExtendedPaginatedList pageList = retrievePager(request,Constants.USER_FAVORITES , false);
		if (pageList.getList() ==null ||pageList.getList().isEmpty()){
			return mapping.findForward("jsreply");
		}
		Long userId = 	pageList.getViewerId();
		CartManager cartmgr = (CartManager)getBean("cartManager");
		
		Favorite fav = cartmgr.deleteUserFavorite(userId.toString(), favForm.getClipId());
		if (fav!=null){

		if (StringUtils.equalsIgnoreCase(request.getParameter("to") , "cart")){

			Cart cart = CartAction.getCart(request.getSession());
			List itemList = 	cart.getList();
			
			CartItemForm cartItemForm = new CartItemForm();
			BeanUtils.copyProperties(cartItemForm , fav);
			SoundClipForm clipForm = new SoundClipForm();
			clipForm.setId(fav.getClipId().toString());
			if (! itemList.contains(clipForm)){
				String clipId = cartItemForm.getClipId();
				SoundClip clip = cartmgr.getCartSoundClip(clipId);
				
				if (clip.getClass().getName().toLowerCase().indexOf("trackpack") > -1){
					clipForm = (TrackpackFormCart)ConvertUtil.convert(clip , TrackpackFormCart.class);
					((TrackpackFormCart)clipForm).setFileId(cartItemForm.getFileId());
					ConvertUtil.convertNamedList(clipForm , "audioFormat" , AudioFileForm.class);
					ConvertUtil.convertNamedList(clipForm , "genre" , GenreForm.class);
				}
				else{
					clipForm = convertLoop(clip, cartItemForm.getFileId());
				}
					itemList.add(clipForm);
			}
		}
		}
		
	response.setContentType(ctype + "charset=UTF-8");
	
	return af;
	}
	
	private LoopFormCart convertLoop(SoundClip clip , String fileId) throws Exception{
		LoopFormCart clipForm = (LoopFormCart)ConvertUtil.convert(clip , LoopFormCart.class);
		ConvertUtil.convertNamedList(clipForm , "audioFormat" , AudioFileForm.class);
		ConvertUtil.convertNamedList(clipForm , "genre" , GenreForm.class);
		((LoopFormCart)clipForm).setFileId(fileId);
		return clipForm;
	}



}
