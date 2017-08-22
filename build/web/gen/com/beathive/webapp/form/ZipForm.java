package com.beathive.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.beathive.webapp.form.BaseForm;

/**
 * RBD note: xdoclet template modified to skip User fields in POJO
 *
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="zipForm" 
 */
public class ZipForm
    extends    BaseForm
    implements java.io.Serializable
{

    protected String downloadURL;

    protected String fileSizeH;

    protected String id;

    protected String description;

    protected String version;

    protected String path;

    protected String accessCount;

    protected String accessTime;

    protected String creationTime;

    protected String name;

    protected String purchaseId;

    protected transient java.util.Set items = new java.util.LinkedHashSet();

    protected String accessKey;

    protected String fileSize;

    protected String customerName;

    protected String customerId;

    /** Default empty constructor. */
    public ZipForm() {}

    public String getDownloadURL()
    {
        return this.downloadURL;
    }
   /**
    */

    public void setDownloadURL( String downloadURL )
    {
        this.downloadURL = downloadURL;
    }

    public String getFileSizeH()
    {
        return this.fileSizeH;
    }
   /**
    */

    public void setFileSizeH( String fileSizeH )
    {
        this.fileSizeH = fileSizeH;
    }

    public String getId()
    {
        return this.id;
    }
   /**
    */

    public void setId( String id )
    {
        this.id = id;
    }

    public String getDescription()
    {
        return this.description;
    }
   /**
    */

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getVersion()
    {
        return this.version;
    }
   /**
    */

    public void setVersion( String version )
    {
        this.version = version;
    }

    public String getPath()
    {
        return this.path;
    }
   /**
    */

    public void setPath( String path )
    {
        this.path = path;
    }

    public String getAccessCount()
    {
        return this.accessCount;
    }
   /**
    */

    public void setAccessCount( String accessCount )
    {
        this.accessCount = accessCount;
    }

    public String getAccessTime()
    {
        return this.accessTime;
    }
   /**
    */

    public void setAccessTime( String accessTime )
    {
        this.accessTime = accessTime;
    }

    public String getCreationTime()
    {
        return this.creationTime;
    }
   /**
    */

    public void setCreationTime( String creationTime )
    {
        this.creationTime = creationTime;
    }

    public String getName()
    {
        return this.name;
    }
   /**
    */

    public void setName( String name )
    {
        this.name = name;
    }

    public String getPurchaseId()
    {
        return this.purchaseId;
    }
   /**
    */

    public void setPurchaseId( String purchaseId )
    {
        this.purchaseId = purchaseId;
    }

    public java.util.Set getItems()
    {
        return this.items;
    }
   /**
    */

    public void setItems( java.util.Set items )
    {
        this.items = items;
    }

    public String getAccessKey()
    {
        return this.accessKey;
    }
   /**
    */

    public void setAccessKey( String accessKey )
    {
        this.accessKey = accessKey;
    }

    public String getFileSize()
    {
        return this.fileSize;
    }
   /**
    */

    public void setFileSize( String fileSize )
    {
        this.fileSize = fileSize;
    }

    public String getCustomerName()
    {
        return this.customerName;
    }
   /**
    */

    public void setCustomerName( String customerName )
    {
        this.customerName = customerName;
    }

    public String getCustomerId()
    {
        return this.customerId;
    }
   /**
    */

    public void setCustomerId( String customerId )
    {
        this.customerId = customerId;
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-ZipForm.java 
           containing the additional code and place it in your metadata/web directory.
        */
    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *                                                javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // reset any boolean data types to false

    }

}
