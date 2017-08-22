package com.beathive;

import java.util.List;
 
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import com.beathive.model.User;
 
 
/** A convienience Paginated List for Pagination using Display Tag
 * </code>com.gorti.project.web.ui.action.IExtendedPaginatedList</code> extends <code>org.displaytag.pagination.PaginatedList</code>
 * @author Ram Gorti
 *
 */
public interface ExtendedPaginatedList extends PaginatedList {
 
    /** Request params as constants.  */
    public interface IRequestParameters{
        String SORT = "sort";
        String PAGE = "page";
        String ASC = "asc";
        String DESC = "desc";
        String SIZE = "size";
        String DIRECTION = "dir";
    }
 
    /** Set the default page size **/
    int DEFAULT_PAGE_SIZE = Constants.DEFAULT_PAGE_SIZE;
 

    /**  Set the Total - total number of records or rows (e.g 10,000 rows found with the query) **/
    void setFullListSize(int total);
 
    /**  set the Page Size - to display the required number of rows (e.g 25 rows out of 10,000)  **/
    void setObjectsPerPage(int objectsPerPage);

	/**
	 * Complements PaginatedList
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber);

    /**
     * Complements PaginatedList
     */
    public void setSearchId(String searchId);

    /**  Set the sort Direction  -  asc or dsc **/
    void setSortDirection(SortOrderEnum sortOrderEnum);
 
    /** set sort criterion **/
    void setSortCriterion(String sortCriterion);


    
    /** get the first record index **/
    int getFirstRecordIndex();
 

    /**
     * returns the number of pages
     * @return 
     */
    public int getTotalPages();
    
	/**
	 * Returns a secondary total if any (here primarily for loop vs trackpack)
	 * @return the relTotal
	 */
	public int getRelTotal();

	/**
	 * @param relTotal the relTotal to set
	 */
	public void setRelTotal(int relTotal);


	public void setViewer(User viewer);
    
    
	public User getViewer();

	public Long getViewerId();

	/**
	 * @param results
	 */
	public void setList(List results);
	
	/**
	 * Clears the inner list
	 */
	public void clear();
}