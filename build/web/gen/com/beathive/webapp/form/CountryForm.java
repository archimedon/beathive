package com.beathive.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.beathive.webapp.form.BaseForm;

/**
 * RBD note: xdoclet template modified to skip User fields in POJO
 *
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="countryForm" 
 */
public class CountryForm
    extends    org.apache.struts.action.ActionForm
    implements java.io.Serializable
{

    protected String label;

    protected String value;

    /** Default empty constructor. */
    public CountryForm() {}

    public String getLabel()
    {
        return this.label;
    }
   /**
    */

    public void setLabel( String label )
    {
        this.label = label;
    }

    public String getValue()
    {
        return this.value;
    }
   /**
    */

    public void setValue( String value )
    {
        this.value = value;
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-CountryForm.java 
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