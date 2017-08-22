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
import org.displaytag.tags.TableTagParameters;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;
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
import com.beathive.webapp.form.LoopForm;
import com.beathive.webapp.form.ShopReportForm;
import com.beathive.webapp.form.StoreForm;
import com.beathive.webapp.form.TrackpackForm;
import com.beathive.webapp.form.TrackpackQueryForm;
import com.beathive.webapp.helper.PaginatedListFactory;

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
 * @struts.action name="storeForm" path="/producer/reports" scope="request"
 *  validate="false" parameter="method" input="mainMenu" roles="admin,producer"
 *
 * @struts.action-forward name="reportsHome" path="/WEB-INF/pages/producer/reports/home.jsp"
 * @struts.action-forward name="salesForm" path="/WEB-INF/pages/producer/reports/salesForm.jsp"
 * @struts.action-forward name="salesResults" path="/WEB-INF/pages/producer/reports/salesResults.jsp"
 */
public final class ReportAction extends BaseAction {

	
	
	
	/**
	 * Below used for producer reports
	 */
	
	public ActionForward sales(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		
		if (!request.getRequestURI().contains("/admin/") && ! request.isUserInRole("admin") && request.getMethod().equalsIgnoreCase("GET")){
			return mapping.findForward("salesForm");
		}
		ShopReportForm shopReportForm = (ShopReportForm)  form;
		ShopReport salesReportPager = new ShopReport();
		BeanUtils.copyProperties(salesReportPager , shopReportForm);
		if (StringUtils.isBlank(shopReportForm.getSortCriterion())){
			salesReportPager.setSortCriterion("id");
		}
		session.setAttribute("salesReportPager" , salesReportPager);
    	PaginatedListFactory paginatedListFactory = (PaginatedListFactory)getBean("paginatedListFactory");
    	salesReportPager  = (ShopReport)paginatedListFactory.getNamedPaginatedList(request , "salesReportPager" , true);

    	
    	PurchaseManager purchaseManager = (PurchaseManager) getBean("purchaseManager");
    	if (request.isUserInRole("admin") && request.getRequestURI().contains("/admin/")){
    		String exp = request.getParameter(TableTagParameters.PARAMETER_EXPORTING);
    		purchaseManager.getSales(salesReportPager, (exp ==null));
    	}else{
    		log.debug("getting summary");
    		purchaseManager.getSummarizeSalesInRange(salesReportPager);
    	}
		
		return mapping.findForward("salesResults");
		
	}
	
	
	
	/**
	 * Takes the user to reports home page
	 */
    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request,
    		HttpServletResponse response)
    throws Exception {
    	return mapping.findForward("salesForm");
//    	return sales(mapping, form, request, response);
    }

}
