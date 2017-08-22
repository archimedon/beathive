package com.beathive.webapp.action;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.ControllerSupport;

import com.beathive.model.LabelValue;
import com.beathive.model.Store;
import com.beathive.model.User;
import com.beathive.service.LookupManager;
import com.beathive.service.NotFoundException;
import com.beathive.service.SoundClipManager;
import com.beathive.service.StoreManager;
import com.beathive.service.UserManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.AudioFileForm;
import com.beathive.webapp.form.LoopForm;
import com.beathive.webapp.form.StoreForm;
import com.beathive.webapp.form.TrackpackForm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;


/**
 * ProducerWorksController class.  This class is dispalys a Producers Maintenance page
 * /WEB-INF/pages/producer/view/assets.jsp a particular tile (JSP page).
 * 
 * <p><a href="ProducerWorksController.java.do"><i>View Source</i></a></p>
 *
 * @version $Revision: 1.4 $ $Date: 2004/05/25 06:27:22 $
 */
public final class ProducerWorksController extends ControllerSupport {
	private static Log log = LogFactory.getLog(ProducerWorksController.class);

    //~ Methods ================================================================
    /**
     *
     * @param tilesContext Current tile context
     * @param request Current request
     * @param response Current response
     * @param servletContext Current Servlet Context
     */
    public void execute(ComponentContext tilesContext,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ServletContext servletContext)
      throws Exception {
    	String cmd = request.getParameter("cmd");
    	if (StringUtils.isNotBlank(cmd)){
    		try{
	    		// get audio files for specific clip
	    		if (cmd.equalsIgnoreCase("listClipAudio")){
	    			getClipsAudio(tilesContext, request, response, servletContext);
	    		}
	    		//get producer's shops menu
	    		if (cmd.equalsIgnoreCase("producerShops")){
	    			getProducerShopMenu(tilesContext, request, response, servletContext);
	    		}
	    		// get this shops raw audio files
	    		if (cmd.equalsIgnoreCase("listUnpackedAudio")){
	    			getUnpackedAudio(tilesContext, request, response, servletContext);
	    		}
    		}catch(Exception e){
    			log.error(e);
    		}
    	}
    }

	/**
	 * If 'storeMenu' does not exist in the session, create a LabelValue list of a producer's shops
	 * put list in session.
	 * 
	 * @param tilesContext
	 * @param request
	 * @param response
	 * @param servletContext
	 * @throws NotFoundException if user does not have shop
	 */
	private void getProducerShopMenu(ComponentContext tilesContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws NotFoundException {

		List storeMenu = (List)request.getSession().getAttribute("storeMenu");
        if (storeMenu == null){
        	WebApplicationContext wctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        	StoreManager stmgr = (StoreManager)wctx.getBean("storeManager");

        	storeMenu = new LinkedList();

        	List<Store> shops = stmgr.getUserStores(request.getRemoteUser());
        	
        	if (shops!=null && ! shops.isEmpty()){
        		for(Store g : shops){
        			String gid = g.getId().toString();
        			storeMenu.add(new LabelValue(g.getName() , gid));
        		}
        		request.getSession().setAttribute("storeMenu", storeMenu);
        	}
        }
	}
	/**
	 * 
	 * Expects {@link StoreForm} in the session. Uses 'storeForm.id' to lookup the shops audio files.
	 * Sets 'unpackedAudioFiles' in the request. Note these audio files are not yet associated with
	 * {@link Soundclip}s.
	 * 
	 * @param tilesContext
	 * @param request
	 * @param response
	 * @param servletContext
	 * @throws Exception 
	 */
	private void getUnpackedAudio(ComponentContext tilesContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception {

		WebApplicationContext wctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		LookupManager lookupMgr = (LookupManager) wctx.getBean("lookupManager");
		List files = null;


		StoreForm storeForm = (StoreForm)request.getSession().getAttribute("storeForm");
		if(storeForm!=null){
			if (StringUtils.isNumeric(storeForm.getId())){
				Long storeId = new Long(storeForm.getId());
		
				String username = request.getRemoteUser();
				StoreManager umgr = (StoreManager) wctx.getBean("storeManager");
				// User u = umgr.getUserByUsername(username);
				List shops = umgr.getUserStores(username);
				
				if (shops != null){
					Store store = null;
					
					for (int i = 0 ; i < shops.size(); i++){
						if (((Store) shops.get(i)).getId().equals(storeId)){
							store = (Store) shops.get(i);
							break;
						}
					}
					
					if (store != null){
						files = lookupMgr.getProducerUnboundAudio(storeId.toString());
						if (files != null && (!files.isEmpty())) {
							files = (List) ConvertUtil.convertList(files, AudioFileForm.class);
							request.setAttribute("unpackedAudioFiles", files);
						}
						store = null;
						shops = null;
					}
				}
			}
		}
	}

	/**
	 * put 'clipAudioFiles', for clip found using req-parameter 'clipId', in the servletRequest
	 * 
	 * @throws Exception 
	 * 
	 */
	private void getClipsAudio(ComponentContext tilesContext,
            HttpServletRequest request,
            HttpServletResponse response,
            ServletContext servletContext) throws Exception {
		
		WebApplicationContext wctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	LookupManager lookupMgr = (LookupManager)wctx.getBean("lookupManager");
    	List files = null;
		String clipId = request.getParameter("clipId");
		if (StringUtils.isNotBlank(clipId)){
			files = lookupMgr.getClipAudio(clipId);

			if (files!=null && (!files.isEmpty())){
				files = (List) ConvertUtil.convertList(files, AudioFileForm.class);
				request.setAttribute("clipAudioFiles", files);
			}
		}
	}
}
