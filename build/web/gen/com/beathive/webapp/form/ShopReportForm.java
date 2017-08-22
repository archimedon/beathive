package com.beathive.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.beathive.webapp.form.BaseForm;

/**
 * RBD note: xdoclet template modified to skip User fields in POJO
 *
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="shopReportForm" 
 */
public class ShopReportForm
    extends    BaseForm
    implements java.io.Serializable
{

    protected String storeId;

    protected String startDate;

    protected String endDate;

    protected String loopVolume;

    protected String trackpackVolume;

    protected String totalLoopYeild;

    protected String totalTrackpackYeild;

    protected boolean groupByClip;

    protected String firstRecordIndex;

    protected String totalPages;

    protected String relTotal;

    protected String viewerId;

    protected String fullListSize;

    protected transient java.util.List list = new java.util.ArrayList();

    protected String objectsPerPage;

    protected String pageNumber;

    protected String searchId;

    protected String sortCriterion;

    protected String sortDirection;

    protected String sortBy;

    /** Default empty constructor. */
    public ShopReportForm() {}

    public String getStoreId()
    {
        return this.storeId;
    }
   /**
    */

    public void setStoreId( String storeId )
    {
        this.storeId = storeId;
    }

    public String getStartDate()
    {
        return this.startDate;
    }
   /**
    */

    public void setStartDate( String startDate )
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return this.endDate;
    }
   /**
    */

    public void setEndDate( String endDate )
    {
        this.endDate = endDate;
    }

    public String getLoopVolume()
    {
        return this.loopVolume;
    }
   /**
    */

    public void setLoopVolume( String loopVolume )
    {
        this.loopVolume = loopVolume;
    }

    public String getTrackpackVolume()
    {
        return this.trackpackVolume;
    }
   /**
    */

    public void setTrackpackVolume( String trackpackVolume )
    {
        this.trackpackVolume = trackpackVolume;
    }

    public String getTotalLoopYeild()
    {
        return this.totalLoopYeild;
    }
   /**
    */

    public void setTotalLoopYeild( String totalLoopYeild )
    {
        this.totalLoopYeild = totalLoopYeild;
    }

    public String getTotalTrackpackYeild()
    {
        return this.totalTrackpackYeild;
    }
   /**
    */

    public void setTotalTrackpackYeild( String totalTrackpackYeild )
    {
        this.totalTrackpackYeild = totalTrackpackYeild;
    }

    public boolean getGroupByClip()
    {
        return this.groupByClip;
    }
   /**
    */

    public void setGroupByClip( boolean groupByClip )
    {
        this.groupByClip = groupByClip;
    }

    public String getFirstRecordIndex()
    {
        return this.firstRecordIndex;
    }
   /**
    */

    public void setFirstRecordIndex( String firstRecordIndex )
    {
        this.firstRecordIndex = firstRecordIndex;
    }

    public String getTotalPages()
    {
        return this.totalPages;
    }
   /**
    */

    public void setTotalPages( String totalPages )
    {
        this.totalPages = totalPages;
    }

    public String getRelTotal()
    {
        return this.relTotal;
    }
   /**
    */

    public void setRelTotal( String relTotal )
    {
        this.relTotal = relTotal;
    }

    public String getViewerId()
    {
        return this.viewerId;
    }
   /**
    */

    public void setViewerId( String viewerId )
    {
        this.viewerId = viewerId;
    }

    public String getFullListSize()
    {
        return this.fullListSize;
    }
   /**
    */

    public void setFullListSize( String fullListSize )
    {
        this.fullListSize = fullListSize;
    }

    public java.util.List getList()
    {
        return this.list;
    }
   /**
    */

    public void setList( java.util.List list )
    {
        this.list = list;
    }

    public String getObjectsPerPage()
    {
        return this.objectsPerPage;
    }
   /**
    */

    public void setObjectsPerPage( String objectsPerPage )
    {
        this.objectsPerPage = objectsPerPage;
    }

    public String getPageNumber()
    {
        return this.pageNumber;
    }
   /**
    */

    public void setPageNumber( String pageNumber )
    {
        this.pageNumber = pageNumber;
    }

    public String getSearchId()
    {
        return this.searchId;
    }
   /**
    */

    public void setSearchId( String searchId )
    {
        this.searchId = searchId;
    }

    public String getSortCriterion()
    {
        return this.sortCriterion;
    }
   /**
    */

    public void setSortCriterion( String sortCriterion )
    {
        this.sortCriterion = sortCriterion;
    }

    public String getSortDirection()
    {
        return this.sortDirection;
    }
   /**
    */

    public void setSortDirection( String sortDirection )
    {
        this.sortDirection = sortDirection;
    }

    public String getSortBy()
    {
        return this.sortBy;
    }
   /**
    */

    public void setSortBy( String sortBy )
    {
        this.sortBy = sortBy;
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-ShopReportForm.java 
           containing the additional code and place it in your metadata/web directory.
        */
    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *                                                javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // reset any boolean data types to false

        this.groupByClip = false;

    }

}
