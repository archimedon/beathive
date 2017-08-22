package com.beathive.webapp.helper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.PaginatedListImpl;
import com.beathive.model.User;


public class PaginatedListFactory {

    public ExtendedPaginatedList getPaginatedListFromRequest(HttpServletRequest request) {

        ExtendedPaginatedList paginatedList = new PaginatedListImpl();
        if (request != null) {
        	User user = (User)request.getUserPrincipal();
          	paginatedList.setViewer(user);
          	setDisplayParams(request , paginatedList);
        }

        return paginatedList;
    }
    
    /**
	 * @param request
	 * @param paginatedList
	 */
	private void setDisplayParams(HttpServletRequest request,
			ExtendedPaginatedList paginatedList) {
		
        String sortDir = request.getParameter(ExtendedPaginatedList.IRequestParameters.DIRECTION);

        String pageNo = request.getParameter(ExtendedPaginatedList.IRequestParameters.PAGE);

        paginatedList.setPageNumber(StringUtils.isNumeric(pageNo) ? Integer.parseInt(pageNo) : 1);
        
        paginatedList.setObjectsPerPage(
        		StringUtils.isNotBlank(request.getParameter(ExtendedPaginatedList.IRequestParameters.SIZE)) 
        		? Integer.parseInt(request.getParameter(ExtendedPaginatedList.IRequestParameters.SIZE))
        		: Constants.DEFAULT_PAGE_SIZE
        );
        
        if (StringUtils.isNotBlank(request.getParameter(ExtendedPaginatedList.IRequestParameters.SORT)))
        {
        	paginatedList.setSortCriterion(request.getParameter(ExtendedPaginatedList.IRequestParameters.SORT));
        }

        paginatedList.setSortDirection(
	    		sortDir == null || sortDir.equals("2") || sortDir.equals("desc")
	    		? SortOrderEnum.DESCENDING
	    		: SortOrderEnum.ASCENDING);
	}

	public ExtendedPaginatedList getNamedPaginatedList(HttpServletRequest request , String name , boolean bFromSession) {
		return getNamedPaginatedList(request, name, bFromSession, true);
    }

	/**
	 * @param request
	 * @param listName
	 * @param fromSession
	 * @param createList
	 * @return
	 */
	public ExtendedPaginatedList getNamedPaginatedList(
			HttpServletRequest request, String listName, boolean bFromSession,
			boolean createList) {
		ExtendedPaginatedList paginatedList = null;
		if (request != null) {
			if (bFromSession) {
				paginatedList = (ExtendedPaginatedList) request.getSession()
						.getAttribute(listName);
				if (paginatedList == null && createList) {
					paginatedList = new PaginatedListImpl();
					request.getSession().setAttribute(listName ,paginatedList );
				}
			} else {
				paginatedList = (ExtendedPaginatedList) request
						.getAttribute(listName);
				if (paginatedList == null && createList) {
					paginatedList = new PaginatedListImpl();
					request.setAttribute(listName ,paginatedList );
				}
			}
		}

		if (paginatedList != null && request != null) {
			setDisplayParams(request, paginatedList);
			if (request.getRemoteUser() !=null){
				User user = (User) (SecurityContextHolder.getContext()
						.getAuthentication()).getPrincipal();
				paginatedList.setViewer(user);
			}
		}
		return paginatedList;
	}
}
