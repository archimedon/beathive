package com.beathive;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//import javax.servlet.http.HttpServletRequest;
import org.displaytag.properties.SortOrderEnum;

import com.beathive.model.BaseObject;
import com.beathive.model.User;

/**
 * <code>com.gorti.project.web.ui.action.PaginatedListImpl</code> implemnts
 * </code>com.gorti.project.web.ui.action.IExtendedPaginatedList</code> This
 * class can be used for pagination purpose. This class depends upon
 * HttpServletRequest object. To be used by Controllers in case of Http
 * requests.
 * 
 * @author ron  
 */
public class PaginatedListImpl implements ExtendedPaginatedList ,  Serializable{

	private static final long serialVersionUID = -165528121215664235L;

	/** current page index, starts at 0 */
	private transient User viewer = new User(new Long(-1));

	private int relTotal = 0;
	
	private String[] sortBy = {"score","timesrated"};
    private int pageNumber = 1;

    /** number of results per page (number of rows per page to be displayed ) */
    private int objectsPerPage = ExtendedPaginatedList.DEFAULT_PAGE_SIZE;

    /** total number of results (the total number of rows ) */
    private int fullListSize = -1;

    /** list of results (rows found ) in the current page */
    private List list = Collections.synchronizedList(new LinkedList());

    /** default sorting order */
    private SortOrderEnum sortDirection = SortOrderEnum.ASCENDING;

    /** sort criteria (sorting property name) */
    private String sortCriterion = "score";

    /** Http servlet request * */
//    private HttpServletRequest request;

    /** default constructor * */
    public PaginatedListImpl() {
    }

    

    
    
//    /**
//     * Create <code>PaginatedListImpl</code> instance using the
//     * <code>HttpServletRequest</code> object.
//     * 
//     * @param request
//     *            <code>HttpServletRequest</code> object.
//     * @param pageSize
//     *            the page size - the total number of rows per page.
//     */
//    public PaginatedListImpl(HttpServletRequest request, int pageSize) {
//        sortCriterion = request
//                .getParameter(ExtendedPaginatedList.IRequestParameters.SORT);
//        sortDirection = ExtendedPaginatedList.IRequestParameters.DESC
//                .equals(request
//                        .getParameter(ExtendedPaginatedList.IRequestParameters.DIRECTION)) ? SortOrderEnum.DESCENDING
//                : SortOrderEnum.ASCENDING;
//        pageSize = pageSize != 0 ? pageSize : DEFAULT_PAGE_SIZE;
//        String page = request
//                .getParameter(ExtendedPaginatedList.IRequestParameters.PAGE);
//        pageNumber = page == null ? 0 : Integer.parseInt(page) - 1;
//    }
//
//    /**
//     * Create <code>PaginatedListImpl</code> instance .
//     * 
//     * @param pageSize
//     *            the page size - the total number of rows per page.
//     * @return <code>ExtendedPaginatedList</code> instance.
//     * @throws Exception -
//     *             problem while creating paginatedlist object.
//     */
//    public ExtendedPaginatedList getPaginatedListObject(int pageSize)
//            throws Exception {
//
//        if (request == null) {
//            throw new Exception(
//                    "Cannot create paginated list. Depends upon HttpServletRequest.");
//        }
//        return new PaginatedListImpl(request, pageSize);
//    }

//    /**
//     * Set the non-null <code>HttpServletRequest</code> object.
//     * 
//     * @param request
//     *            a <code>HttpServletRequest</code> object.
//     */
//    public void setRequest(HttpServletRequest request) {
//        this.request = request;
//    }
//
    
    /**
     * Gets the starting index 
     */
    public int getFirstRecordIndex() {
        return (pageNumber - 1) * objectsPerPage;
    }

    /**
     * returns the number of pages based on objectsPerPage
     * @return 
     */
    public int getTotalPages() {
    	return (int) Math.ceil(((double) fullListSize) / objectsPerPage);
    }
    
	/**
	 * Returns a secondary total if any (here primarily for loop vs trackpack)
	 * @return the relTotal
	 */
	public int getRelTotal() {
		return relTotal;
	}

	/**
	 * @param relTotal the relTotal to set
	 */
	public void setRelTotal(int relTotal) {
		this.relTotal = relTotal;
	}


	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#setViewer(com.beathive.model.User)
	 */
	public void setViewer(User viewer) {
		this.viewer = viewer;
	}
    
    
	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#getViewer()
	 */
	public User getViewer() {
		return this.viewer;
	}

	/* (non-Javadoc)
	 * @see com.beathive.QueryMeta#getViewerId()
	 */
	public Long getViewerId() {
		if (viewer !=null){
			if (viewer.getId()==null){
				return new Long(-1);
			}
		}
        if (viewer==null){
        	viewer = new User(new Long(-1));
        }
		return viewer.getId();
	}

    
    /**				========== BEGIN PaginatedList Impl ==========		**/

	
	/**
	 * Returns the size of the full list
	 */
    public int getFullListSize() {
        return (fullListSize == -1)
        		? ((list != null) ? list.size() : 0)
        		: fullListSize;
    }

    /**
     *  Returns the current partial list
     */
    public List getList() {
    	return list;
    }
    
    /**
     * Returns the number of objects per page.
     */
    public int getObjectsPerPage() {
        return objectsPerPage;
    }

    /**
     * Returns the page number of the partial list (starts from 1)
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Returns an ID for the search used to get the list.
     */
    public String getSearchId() {
        // Not implemented for now.
        // This is required, if we want the ID to be included in the paginated
        // purpose.
        return null;
    }

    /**
     *  Returns the sort criterion used to externally sort the full list
     */
    public String getSortCriterion() {
        return sortCriterion;
    }

    /**
     * Returns the sort direction used to externally sort the full list
     */
    public SortOrderEnum getSortDirection() {
        return sortDirection;
    }

    /**				========== END PaginatedList Impl ==========		**/
    
    
    
    /**				========== COMPPLEMENTARY SETTERS in extended interface ==========		**/
    public void setFullListSize(int size) {
        this.fullListSize = size;
    }

	/**
	 * Complements PaginatedList
	 * @param results the query results
	 */
    public void setList(List results) {
        this.list = results;
    }

	/**
	 * Complements PaginatedList
	 * @param objectsPerPage the number of objectsPerPage (aka pageSize)
	 */
    public void setObjectsPerPage(int pageSize) {
        this.objectsPerPage = pageSize;
    }

	/**
	 * Complements PaginatedList
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

    /**
     * Complements PaginatedList
     */
    public void setSearchId(String searchId) {
    }

    /**
     * Complements PaginatedList
     */
    public void setSortCriterion(String sortCriterion) {
    	this.sortCriterion = sortCriterion;
    }
    
    /**
     * Complements PaginatedList
     */
    public void setSortDirection(SortOrderEnum sortDirection) {
    	this.sortDirection = sortDirection;
    }





	/**
	 * @return the sortBy
	 */
	public String[] getSortBy() {
		return sortBy;
	}





	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String[] sortBy) {
		this.sortBy = sortBy;
	}





	/* (non-Javadoc)
	 * @see com.beathive.ExtendedPaginatedList#getReset()
	 */
	public void clear() {
		this.list.clear();
	}
}