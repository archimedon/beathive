
package com.beathive.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.model.Genre;
import com.beathive.model.LabelValue;
import com.beathive.model.Loop;
import com.beathive.model.Store;
import com.beathive.model.User;
import com.beathive.service.CommentManager;
import com.beathive.service.NotFoundException;
import com.beathive.service.SoundClipManager;
import com.beathive.service.StoreManager;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.CommentForm;
import com.beathive.webapp.form.DescriptorForm;
import com.beathive.webapp.form.LoopFormSearch;
import com.beathive.webapp.form.StoreForm;
import com.beathive.webapp.helper.PaginatedListFactory;
import com.beathive.webapp.listener.UserSetupListener;

/**
 * Action class to handle CRUD on a Store object
 *
 * @struts.action name="storeForm" path="/stores" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="storeForm" path="/editStore" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="storeForm" path="/saveStore" scope="request"
 *  validate="true" parameter="method" input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit" path="/WEB-INF/pages/store/storeForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/store/storeList.jsp"
 * @struts.action-forward name="search" path="/stores.html" redirect="true"
 */
public final class StoreAction extends BaseAction {
    public ActionForward cancel(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        return mapping.findForward("search");
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'delete' method");
        }

        ActionMessages messages = new ActionMessages();
        StoreForm storeForm = (StoreForm) form;

        // Exceptions are caught by ActionExceptionHandler
        StoreManager mgr = (StoreManager) getBean("storeManager");
        mgr.removeStore(storeForm.getId());

        messages.add(ActionMessages.GLOBAL_MESSAGE,
                     new ActionMessage("store.deleted"));

        // save messages in session, so they'll survive the redirect
        saveMessages(request.getSession(), messages);

        return mapping.findForward("search");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'edit' method");
        }
        HttpSession session = request.getSession();
        StoreForm storeForm = (StoreForm) form;

        String storeId = (storeForm!=null && storeForm.getId()!=null)? storeForm.getId().toString() :
        		(session.getAttribute("storeForm") != null && ((StoreForm)session.getAttribute("storeForm")).getId()!=null) ? ((StoreForm)session.getAttribute("storeForm")).getId().toString()
        				: null;
        	
        // if an id is passed in, look up the user - otherwise
        // don't do anything - user is doing an add
        if (storeId != null) {
            StoreManager mgr = (StoreManager) getBean("storeManager");
            Store store = mgr.getStore(storeId);
            storeForm = (StoreForm) convert(store);
            ConvertUtil.convertNamedList(storeForm , "comments" , CommentForm.class);
            updateFormBean(mapping, request, storeForm);
        }else{
        	
        	if(! storeForm.getAgreement()){
        		return mapping.findForward("agreementForm");
        	}
        	
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null ){//&& auth.getPrincipal() instanceof UserDetails) {
                User currentUser = (User) auth.getPrincipal();
                storeForm.setPaymentEmail(currentUser.getEmail());
            }
        }

        return mapping.findForward("edit");
    }

    
    public ActionForward view(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request,
    		HttpServletResponse response)
    throws Exception {
    	if (log.isDebugEnabled()) {
    		log.debug("Entering 'view' method");
    	}
    	HttpSession session = request.getSession();
    	StoreForm storeForm = (StoreForm) form;

    	String storeId = request.getParameter("storeId");
    		// if an id is passed in, look up the user - otherwise
    		// don't do anything - user is doing an add
    		if (storeId != null) {
    			StoreManager mgr = (StoreManager) getBean("storeManager");
    			Store store = mgr.getStore(storeId);
    			storeForm = (StoreForm) convert(store);
    			updateFormBean(mapping, request, storeForm);
    		}
    		return mapping.findForward("info");
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'save' method");
        }

        // Extract attributes and parameters we will need
        ActionMessages messages = new ActionMessages();
        StoreForm storeForm = (StoreForm) form;
        boolean isNew = ("".equals(storeForm.getId()) || storeForm.getId() == null);

        StoreManager mgr = (StoreManager) getBean("storeManager");
        storeForm.setComments(null);
        Store store = (Store) convert(storeForm);
        // remove comments
        Date date = new Date();
        store.setModified(date);
        if (isNew){
        	store.setCreated(date);
        	// Must be done for view "increment" to work
        	// mapping does not provide  for default value
        	store.setViews(new Integer(0));
        }else{
        	String[] commentIds = request.getParameterValues("commentIds");
        	if(commentIds!=null){
	        	CommentManager commentManager = (CommentManager) getBean("commentManager");
	        	for(int y = 0 ; y < commentIds.length; y++){
	        		store.addComment(commentManager.getComment(commentIds[y]));
	        	}
        	}
        }
        mgr.saveStore(store);

         if (isNew){
	        UserManager umgr = (UserManager)getBean("userManager");
	        
	        User user = umgr.getUserByUsername(request.getRemoteUser());
	        user.addStore(store);
	        
	        if (! request.isUserInRole(Constants.PRODUCER_ROLE)){
	        	
				umgr.saveUpgradeUser(user , Constants.PRODUCER_ROLE);
	        }
	        
	        UserSetupListener.doProducerView(request.getRemoteUser(), request.getSession());
    		
    		// add success messages
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("store.added"));

            // save messages in session to survive a redirect
            saveMessages(request.getSession(), messages);

            return new ActionForward(mapping.findForward("producerHome").getPath() , true);
        } else {
            UserSetupListener.doProducerView(request.getRemoteUser(), request.getSession());
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("store.updated"));
            
            saveMessages(request.getSession(), messages);
            return new ActionForward("/producer/editStore.html" , true);
        }
    }
	/**
     * Lookup and set a menu of the users stores in the session
	 * @param user
	 * @param session
	 */
	private void setDerivedMenus(User user, HttpSession session) {
		List storeMenu = (List)session.getAttribute("storeMenu");
        if (storeMenu == null){
        	StoreManager stmgr = (StoreManager)getBean("storeManager");

        	storeMenu = new LinkedList();

			try {
				List<Store> shops = stmgr.getUserStores(user.getUsername());
        	
	        	if (shops!=null && ! shops.isEmpty()){
	        		for(Store g : shops){
	        			String gid = g.getId().toString();
	        			storeMenu.add(new LabelValue(g.getName() , gid));
	        		}
	        		session.setAttribute("storeMenu", storeMenu);
	        	}
			} catch (NotFoundException e) {
				log.error(e);
			}
		}
		
	}

    public ActionForward search(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'search' method");
        }

        LoopFormSearch loopForm = (LoopFormSearch) form;
        Loop soundClip = new Loop();
        BeanUtils.copyProperties(soundClip , loopForm);
		String genreid = loopForm.getGenreId();
		if (StringUtils.isNotBlank(genreid)){
			soundClip.addGenre(new Genre(new Long(genreid)));
	    }

		SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");
        PaginatedListFactory paginatedListFactory = (PaginatedListFactory)getBean("paginatedListFactory");
        ExtendedPaginatedList paginatedList  = paginatedListFactory.getNamedPaginatedList(request , Constants.STORE_LIST , false);
        List list = mgr.findStores(soundClip, paginatedList);
        paginatedList.setList(list);

        putMeta(request);
        return mapping.findForward("list");
    }
    
	private void putMeta(HttpServletRequest request) {
		
		ServletContext application = super.getServlet().getServletContext();
		java.util.Map genreMap = (Map)application.getAttribute("genreMap");
		java.util.Map instrumentGroupMap = (Map)application.getAttribute("instrumentGroupMap");
		java.util.Map instrumentMap = (Map)application.getAttribute("instrumentMap");

		String[] dnames ={"bpm","origin","feel","passage","timespan"};

		int flag = -1;
		Map meta = new java.util.LinkedHashMap();
		List l = new java.util.LinkedList();
		MessageResources mr = getResources(request);

		String tmp = null;
		if ((tmp = request.getParameter("genreId")) != null){
			l.add(mr.getMessage(((DescriptorForm)genreMap.get(tmp)).getLabelKey()));
		}

		if ((tmp = request.getParameter("instrumentForm.groupId")) !=null){
			tmp = mr.getMessage(((DescriptorForm)instrumentGroupMap.get(tmp)).getLabelKey());
			l.add(tmp);
			flag = l.size() - 1;
		}

		if ((tmp = request.getParameter("instrumentForm.id")) !=null){
			tmp = mr.getMessage(((DescriptorForm)instrumentMap.get(request.getParameter("instrumentForm.id"))).getLabelKey());
			if (flag > -1){
				tmp =  l.get(flag ) + " - " + tmp;
				l.set(flag , tmp);
			}else{
				l.add(tmp);
			}
		}

		for (String nm: dnames){
			if (StringUtils.isNotBlank(tmp = request.getParameter(nm))){
				if(nm.equals("timespan")){
					if(tmp.equals("-3 MONTH")){
						tmp = mr.getMessage("label.timespan.previous_3_month");
					}
					if(tmp.equals("-1 MONTH")){
						tmp = mr.getMessage("label.timespan.previous_1_month");
					}
					if(tmp.equals("-7 DAY")){
						tmp = mr.getMessage("label.timespan.previous_1_week");
					}
				}else if(nm.equals("bpm")){
					if(tmp.equals("130")){
						tmp = mr.getMessage("label.tempo.fast");
					}
					if(tmp.equals("105")){
						tmp = mr.getMessage("label.tempo.medium");
					}
					if(tmp.equals("80")){
						tmp = mr.getMessage("label.tempo.slow");
					}
				}else{
						tmp = mr.getMessage(tmp);
				}
				meta.put(nm , tmp);
			}
		}
		Map m = new HashMap();
		m.put("requiredCriteria" , l);
		m.put("advancedCriteria", meta);
		request.setAttribute("searchmeta" , m);
	}


    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
            throws Exception {
        return search(mapping, form, request, response);
    }
}
