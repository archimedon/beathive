package com.beathive.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.beathive.webapp.form.BaseForm;

/**
 * RBD note: xdoclet template modified to skip User fields in POJO
 *
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="formatForm" 
 */
public class FormatForm
    extends    DescriptorImplForm
    implements java.io.Serializable
{

    protected String id;

    protected String sortorder;

    protected String clazz;

    protected String labelKey;

    /** Default empty constructor. */
    public FormatForm() {}

    public String getId()
    {
        return this.id;
    }
   /**
    * @struts.validator type="required"
    */

    public void setId( String id )
    {
        this.id = id;
    }

    public String getSortorder()
    {
        return this.sortorder;
    }
   /**
    */

    public void setSortorder( String sortorder )
    {
        this.sortorder = sortorder;
    }

    public String getClazz()
    {
        return this.clazz;
    }
   /**
    */

    public void setClazz( String clazz )
    {
        this.clazz = clazz;
    }

    public String getLabelKey()
    {
        return this.labelKey;
    }
   /**
    * @struts.validator type="required"
    */

    public void setLabelKey( String labelKey )
    {
        this.labelKey = labelKey;
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-FormatForm.java 
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