package com.beathive.webapp.action;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.PaginatedListImpl;
import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;
import com.beathive.model.Comment;
import com.beathive.model.Loop;
import com.beathive.model.ShopReport;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
import com.beathive.model.TrackpackQuery;
import com.beathive.model.User;
import com.beathive.service.PurchaseManager;
import com.beathive.service.SoundClipManager;
import com.beathive.service.StoreManager;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.AudioFileForm;
import com.beathive.webapp.form.CommentForm;
import com.beathive.webapp.form.LoopForm;
import com.beathive.webapp.form.ShopReportForm;
import com.beathive.webapp.form.StoreForm;
import com.beathive.webapp.form.TrackpackForm;
import com.beathive.webapp.form.TrackpackQueryForm;
import com.beathive.webapp.helper.PaginatedListFactory;
import com.beathive.webapp.listener.UserSetupListener;

/**
 * Implementation of <strong>Action</strong> that initializes a producers home page.
 * Interacts with {@link StoreForm} , {@link UserManager} and {@link StoreManager} 
 * to retrieve/persist values to the database.
 *
 * <p>
 * <a href="ProducerAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author ron
 *
 * @struts.action name="storeForm" path="/producer/home" scope="session"
 *  validate="false" parameter="method" input="mainMenu" roles="admin,producer"
 *
 * @struts.action-forward name="home" path="/WEB-INF/pages/producer/home.jsp"
 * @struts.action-forward name="edit" path="/WEB-INF/pages/producer/editShopForm.jsp"
 * @struts.action-forward name="inventory" path="/WEB-INF/pages/producer/inventory.jsp"
 * @struts.action-forward name="loopList" path="/WEB-INF/pages/producer/loopList.jsp"
 * @struts.action-forward name="trackpackList" path="/WEB-INF/pages/producer/trackpackList.jsp"
 * @struts.action-forward name="reports" path="/WEB-INF/pages/producer/reports.html"
 */
public final class ProducerAction extends BaseAction {

	
	
    private ExtendedPaginatedList getPager(HttpServletRequest request){
    	ExtendedPaginatedList paginatedList = new PaginatedListImpl();
    	String sortDir = request.getParameter("idir");

    	String pageNo = request.getParameter("ipage");

    	paginatedList.setPageNumber(StringUtils.isNumeric(pageNo) ? Integer.parseInt(pageNo) : 1);

    	paginatedList.setObjectsPerPage(
    			StringUtils.isNotBlank(request.getParameter("isize")) 
    			? Integer.parseInt(request.getParameter("isize"))
    			: Constants.DEFAULT_PAGE_SIZE
    	);

    	if (StringUtils.isNotBlank(request.getParameter("isort")))
    	{
    		paginatedList.setSortCriterion(request.getParameter("isort"));
    	}

    	paginatedList.setSortDirection(
    			sortDir == null || sortDir.equals("2") || sortDir.equals("desc")
    			? SortOrderEnum.DESCENDING
    			: SortOrderEnum.ASCENDING);
    	return paginatedList;
    }
	
	/**
	 * Below used for producer reports
	 */
	
	public ActionForward viewreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		return mapping.findForward("reports");
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (request.getRequestURI().contains("Component")){
			
	        StoreManager stmgr = (StoreManager)getBean("storeManager");

	    	String clipId = request.getParameter("clipId");

    		ExtendedPaginatedList componentsPager  = getPager(request);
    		
    		Trackpack tp = (Trackpack)stmgr.getTrackpackComponents(clipId, componentsPager);
    		TrackpackForm tpclip = (TrackpackForm)ConvertUtil.convert(tp);
			ConvertUtil.convertNamedList(tpclip, "audioFormat", AudioFileForm.class);
			List innerloops = componentsPager.getList();
			
			componentsPager.setList(convertLoops(innerloops));
			
			request.setAttribute("tpclip", tpclip);
    		request.setAttribute("innerLoopList", componentsPager);
			return mapping.findForward("innerLoopList");
		}

		return viewreport(mapping, form, request, response);
	}
    
    public ActionForward done(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request,
    		HttpServletResponse response)
    throws Exception {
    	StoreForm storeForm = (StoreForm) form;
    	storeForm = new StoreForm();
    	updateFormBean(mapping, request, storeForm);
    	return mapping.findForward("home");
    }
    /**
     * Just forwards to editShopForm.jsp
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)throws Exception {

    	return mapping.findForward("edit");
    }
    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request,
    		HttpServletResponse response)
    throws Exception {
        StoreForm storeForm = (StoreForm) form;
        String storeIdstr = request.getParameter("storeId");
        
        StoreManager stmgr = (StoreManager)getBean("storeManager");
        if(StringUtils.isNotBlank(storeIdstr)){
	        // other wise do lookup
	        Store store = null;
	        List<Store> shops = stmgr.getUserStores(request.getRemoteUser());
	        
	        List<StoreForm> shopforms = null;
	        
	        if (shops!=null && ! shops.isEmpty()){
	        	Long storeId = new Long(storeIdstr);
				for (int i = 0 ; i < shops.size(); i++){
					if (((Store) shops.get(i)).getId().equals(storeId)){
						store = (Store) shops.get(i);
						break;
					}
				}
		        if (store!=null){
		        	BeanUtils.copyProperties(storeForm, store);
	    			ConvertUtil.convertNamedList(storeForm , "comments", CommentForm.class);
		        	request.getSession().setAttribute("storeForm" , storeForm);
		        }
	        }
        }
        else{
	        if(request.getSession().getAttribute("storeMenu") ==null){
	        	UserSetupListener.doProducerView(request.getRemoteUser(), request.getSession());
	        }
	        // just going to producer home
	        if (request.getMethod().equalsIgnoreCase("GET")){

	        	storeForm = (StoreForm)request.getSession().getAttribute("storeForm");
	        	if(storeForm != null && StringUtils.isNotBlank(storeForm.getId())){
	        		Store store = stmgr.getStore(storeForm.getId());
	        		BeanUtils.copyProperties(storeForm, store);
	        		ConvertUtil.convertNamedList(storeForm , "comments", CommentForm.class);
	        	}

	        	// return producer main
	       		return mapping.findForward("home");
	        }
        }
        return new ActionForward(request.getHeader("referer"), true);
    }
    /**
     * 
     */
    public ActionForward inventory(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    throws Exception {
    	StoreForm storeForm = (StoreForm) form;

    	if (request.getRemoteUser()==null){ response.sendRedirect("/");return null;}
   	if(request.getSession().getAttribute("storeMenu") ==null){
        	UserSetupListener.doProducerView(request.getRemoteUser(), request.getSession());
        }
    	if (request.getRequestURI().contains("Component")){
			
	        StoreManager stmgr = (StoreManager)getBean("storeManager");

	    	String clipId = request.getParameter("clipId");

    		ExtendedPaginatedList componentsPager  = getPager(request);
    		
    		Trackpack tp = (Trackpack)stmgr.getTrackpack(new Long(clipId), componentsPager);
    		TrackpackForm tpclip = (TrackpackForm)ConvertUtil.convert(tp);
			ConvertUtil.convertNamedList(tpclip, "audioFormat", AudioFileForm.class);
			List innerloops = componentsPager.getList();
			
			componentsPager.setList(convertLoops(innerloops));
			
			request.setAttribute("tpclip", tpclip);
    		request.setAttribute("innerLoopList", componentsPager);
			return mapping.findForward("innerLoopList");
		}

    	String storeid = storeForm.getId();
    	String rtype = StringUtils.isNotBlank(request.getParameter("type")) ? request.getParameter("type") : "trackpack";
    	String pageNumStr = request.getParameter("page"); //storeForm.getPage();
    	String size = request.getParameter("size");
    	
    	if (StringUtils.isNotBlank(storeid)){
    		List results = null;
	        StoreManager stmgr = (StoreManager)getBean("storeManager");
	        String pagerKey = "store" + rtype + "Pager";
	        
	        PaginatedListFactory paginatedListFactory = (PaginatedListFactory)getBean("paginatedListFactory");
	        
	        ExtendedPaginatedList paginatedList  = paginatedListFactory.getNamedPaginatedList(request , pagerKey , true);
	        
        	if (StringUtils.equals(rtype , "loop")){

		        results = stmgr.getLoops(storeid, paginatedList);
            	if (results!=null){
            		results = convertLoops(results);
            	}
        	}
        	else{

		        results = stmgr.getTrackpacks(storeid, paginatedList);
		    	if (results!=null){
		    		results = (List) ConvertUtil.convertList(results, TrackpackForm.class);
		    		TrackpackForm tpform = null;
		    		for(Iterator it = results.iterator(); it.hasNext() ; ){
		    			 tpform = (TrackpackForm)it.next();
		    			 if (tpform != null) {
		    				 List innerloops = tpform.getLoops();
		    				tpform.setLoops(convertLoops(innerloops));
		    			 }
		    			ConvertUtil.convertNamedList(tpform, "audioFormat", AudioFileForm.class);
		    		}
	        	}
        	}
        	if (results!=null){
        		paginatedList.setList(results);
        	}
        	return mapping.findForward(rtype+ "List");
        }
        String referer = request.getHeader("referer");
        if (referer!=null){
        	return new ActionForward(referer, true);
        }else{
        	return mapping.findForward("home");
        }
    }

    public ActionForward searchInventory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StoreForm storeForm = (StoreForm) form;

		if (request.getSession().getAttribute("storeMenu") == null) {
			UserSetupListener.doProducerView(request.getRemoteUser(), request
					.getSession());
		}
		if (request.getRequestURI().contains("Component")) {

			StoreManager stmgr = (StoreManager) getBean("storeManager");

			String clipId = request.getParameter("clipId");

			ExtendedPaginatedList componentsPager = getPager(request);

			Trackpack tp = (Trackpack) stmgr.getTrackpack(new Long(clipId),
					componentsPager);
			TrackpackForm tpclip = (TrackpackForm) ConvertUtil.convert(tp);
			ConvertUtil.convertNamedList(tpclip, "audioFormat",
					AudioFileForm.class);
			List innerloops = componentsPager.getList();

			componentsPager.setList(convertLoops(innerloops));

			request.setAttribute("tpclip", tpclip);
			request.setAttribute("innerLoopList", componentsPager);
			return mapping.findForward("innerLoopList");
		}

		String storeid = storeForm.getId();
		String rtype = StringUtils.isNotBlank(request.getParameter("type")) ? request
				.getParameter("type")
				: "trackpack";
		String pageNumStr = request.getParameter("page"); // storeForm.getPage();
		String size = request.getParameter("size");

		if (StringUtils.isNotBlank(storeid)) {

			List results = null;
			StoreManager stmgr = (StoreManager) getBean("storeManager");
			String pagerKey = "store" + rtype + "Pager";

			PaginatedListFactory paginatedListFactory = (PaginatedListFactory) getBean("paginatedListFactory");

			ExtendedPaginatedList paginatedList = paginatedListFactory
					.getNamedPaginatedList(request, pagerKey, true);


			Boolean f = (request.getParameter("ready")!=null) ? new Boolean(request.getParameter("ready")) : Boolean.TRUE;
			
			if (StringUtils.equals(rtype, "loop")) {

				results = stmgr.getRefPackagedLoops(storeid, paginatedList , f);
				if (results != null) {
					results = convertLoops(results);
				}
			} else {

				results = stmgr.getRefPackagedTrackpacks(storeid, paginatedList, f);
				if (results != null) {
					results = (List) ConvertUtil.convertList(results, TrackpackForm.class);

					TrackpackForm tpform = null;
					for (Iterator it = results.iterator(); it.hasNext();) {
						tpform = (TrackpackForm) it.next();
						if (tpform == null) {
						} else {
							List innerloops = tpform.getLoops();
							tpform.setLoops(convertLoops(innerloops));
						}
						ConvertUtil.convertNamedList(tpform, "audioFormat",
								AudioFileForm.class);
					}
				}
			}
			if (results != null) {
				paginatedList.setList(results);
			}
			return mapping.findForward(rtype + "List");
		}
		String referer = request.getHeader("referer");
		if (referer != null) {
			return new ActionForward(referer, true);
		} else {
			return mapping.findForward("home");
		}

	}

	/**
	 * @param request
	 * @param itemType
	 * @return
	 */
	private QueryMeta getMeta(HttpServletRequest request, String itemType) {

    	HttpSession sess = request.getSession();
    	String key = "store" + itemType + "QueryMeta";
    	QueryMeta queryMeta = (QueryMeta)sess.getAttribute(key);
    	
        if (queryMeta == null){
        	queryMeta = new QueryMetaImpl();
        	
        	// stored in session so user won't have to 
        	// select pagesize again should they return to list
        	sess.setAttribute(key , queryMeta);
        }
        
        return queryMeta;
	}

	/**
	 * @param clips
	 */
	public static List convertLoops(List<Loop> clips) {
		List<LoopForm> outl = new LinkedList();
		try {
			for(Loop loop : clips){
				LoopForm loopform = (LoopForm)ConvertUtil.convert(loop, LoopForm.class);
				ConvertUtil.convertNamedList(loopform , "audioFormat", AudioFileForm.class);
				outl.add(loopform);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outl;
	}
}
