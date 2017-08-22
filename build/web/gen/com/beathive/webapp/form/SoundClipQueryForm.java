package com.beathive.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.beathive.webapp.form.BaseForm;

/**
 * RBD note: xdoclet template modified to skip User fields in POJO
 *
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="soundClipQueryForm" 
 */
public class SoundClipQueryForm
    extends    BaseForm
    implements java.io.Serializable
{

    protected String queryClass;

    protected InstrumentForm instrument = new InstrumentForm();

    /** Default empty constructor. */
    public SoundClipQueryForm() {}

    public String getQueryClass()
    {
        return this.queryClass;
    }
   /**
    */

    public void setQueryClass( String queryClass )
    {
        this.queryClass = queryClass;
    }

    public InstrumentForm getInstrumentForm()
    {
        return this.instrument;
    }
   /**
    * @struts.validator
    */

    public void setInstrumentForm( InstrumentForm instrument )
    {
        this.instrument = instrument;
    }

    /** 
     *  Getter/Setter pair so BeanUtil.copyProperties(dest, orig) will work 
     *  Any properties modified in the web tier should use the get/setInstrumentForm 
     *  methods.
     */
    public com.beathive.model.Instrument getInstrument() throws Exception
    {
        return (com.beathive.model.Instrument) com.beathive.util.ConvertUtil.convert(this.instrument);
    }

    public void setInstrument(com.beathive.model.Instrument instrument) throws Exception
    {
        setInstrumentForm((InstrumentForm) com.beathive.util.ConvertUtil.convert(instrument));
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-SoundClipQueryForm.java 
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