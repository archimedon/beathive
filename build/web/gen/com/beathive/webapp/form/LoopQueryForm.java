package com.beathive.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.beathive.webapp.form.BaseForm;

/**
 * RBD note: xdoclet template modified to skip User fields in POJO
 *
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="loopQueryForm" 
 */
public class LoopQueryForm
    extends    LoopForm
    implements java.io.Serializable
{

    protected String queryMeta;

    protected String queryClass;

    protected boolean searchable;

    protected String trackpackId;

    protected TrackpackForm trackpack = new TrackpackForm();

    protected InstrumentForm instrument = new InstrumentForm();

    protected String instrumentId;

    protected boolean ready;

    protected String statusId;

    protected String id;

    protected String storeId;

    protected String name;

    protected String modified;

    protected String created;

    protected transient java.util.List genre = new java.util.ArrayList();

    protected transient java.util.Set audioFormat = new java.util.LinkedHashSet();

    protected PriceForm price = new PriceForm();

    protected String keyword;

    protected String numFormats;

    protected String score;

    protected String timesrated;

    protected String bpm;

    protected String keynote;

    protected String scale;

    protected String timesignature;

    protected String passage;

    protected String energy;

    protected String feel;

    protected String mood;

    protected String origin;

    protected String sonority;

    protected String texture;

    protected transient java.util.Set userClipScore = new java.util.LinkedHashSet();

    protected String viewerScore;

    protected transient java.util.Set userItems = new java.util.LinkedHashSet();

    protected boolean ownedByViewer;

    protected transient java.util.Set userFavorites = new java.util.LinkedHashSet();

    protected boolean AFavorite;

    protected transient java.util.Map descriptors = new java.util.HashMap();

    protected String storeName;

    protected String timesSold;

    /** Default empty constructor. */
    public LoopQueryForm() {}

    public String getQueryMeta()
    {
        return this.queryMeta;
    }
   /**
    */

    public void setQueryMeta( String queryMeta )
    {
        this.queryMeta = queryMeta;
    }

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

    public boolean isSearchable()
    {
        return this.searchable;
    }
   /**
    */

    public void setSearchable( boolean searchable )
    {
        this.searchable = searchable;
    }

    public String getTrackpackId()
    {
        return this.trackpackId;
    }
   /**
    */

    public void setTrackpackId( String trackpackId )
    {
        this.trackpackId = trackpackId;
    }

    public TrackpackForm getTrackpackForm()
    {
        return this.trackpack;
    }
   /**
    * @struts.validator
    */

    public void setTrackpackForm( TrackpackForm trackpack )
    {
        this.trackpack = trackpack;
    }

    /** 
     *  Getter/Setter pair so BeanUtil.copyProperties(dest, orig) will work 
     *  Any properties modified in the web tier should use the get/setTrackpackForm 
     *  methods.
     */
    public com.beathive.model.Trackpack getTrackpack() throws Exception
    {
        return (com.beathive.model.Trackpack) com.beathive.util.ConvertUtil.convert(this.trackpack);
    }

    public void setTrackpack(com.beathive.model.Trackpack trackpack) throws Exception
    {
        setTrackpackForm((TrackpackForm) com.beathive.util.ConvertUtil.convert(trackpack));
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

    public String getInstrumentId()
    {
        return this.instrumentId;
    }
   /**
    */

    public void setInstrumentId( String instrumentId )
    {
        this.instrumentId = instrumentId;
    }

    public boolean isReady()
    {
        return this.ready;
    }
   /**
    */

    public void setReady( boolean ready )
    {
        this.ready = ready;
    }

    public String getStatusId()
    {
        return this.statusId;
    }
   /**
    * @struts.validator type="required"
    * @struts.validator type="required"
    */

    public void setStatusId( String statusId )
    {
        this.statusId = statusId;
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

    public String getStoreId()
    {
        return this.storeId;
    }
   /**
    * @struts.validator type="required"
    */

    public void setStoreId( String storeId )
    {
        this.storeId = storeId;
    }

    public String getName()
    {
        return this.name;
    }
   /**
    * @struts.validator type="required"
    */

    public void setName( String name )
    {
        this.name = name;
    }

    public String getModified()
    {
        return this.modified;
    }
   /**
    */

    public void setModified( String modified )
    {
        this.modified = modified;
    }

    public String getCreated()
    {
        return this.created;
    }
   /**
    */

    public void setCreated( String created )
    {
        this.created = created;
    }

    public java.util.List getGenre()
    {
        return this.genre;
    }
   /**
    */

    public void setGenre( java.util.List genre )
    {
        this.genre = genre;
    }

    public java.util.Set getAudioFormat()
    {
        return this.audioFormat;
    }
   /**
    */

    public void setAudioFormat( java.util.Set audioFormat )
    {
        this.audioFormat = audioFormat;
    }

    public PriceForm getPriceForm()
    {
        return this.price;
    }
   /**
    * @struts.validator
    */

    public void setPriceForm( PriceForm price )
    {
        this.price = price;
    }

    /** 
     *  Getter/Setter pair so BeanUtil.copyProperties(dest, orig) will work 
     *  Any properties modified in the web tier should use the get/setPriceForm 
     *  methods.
     */
    public com.beathive.model.Price getPrice() throws Exception
    {
        return (com.beathive.model.Price) com.beathive.util.ConvertUtil.convert(this.price);
    }

    public void setPrice(com.beathive.model.Price price) throws Exception
    {
        setPriceForm((PriceForm) com.beathive.util.ConvertUtil.convert(price));
    }

    public String getKeyword()
    {
        return this.keyword;
    }
   /**
    */

    public void setKeyword( String keyword )
    {
        this.keyword = keyword;
    }

    public String getNumFormats()
    {
        return this.numFormats;
    }
   /**
    */

    public void setNumFormats( String numFormats )
    {
        this.numFormats = numFormats;
    }

    public String getScore()
    {
        return this.score;
    }
   /**
    */

    public void setScore( String score )
    {
        this.score = score;
    }

    public String getTimesrated()
    {
        return this.timesrated;
    }
   /**
    */

    public void setTimesrated( String timesrated )
    {
        this.timesrated = timesrated;
    }

    public String getBpm()
    {
        return this.bpm;
    }
   /**
    * @struts.validator type="required"
    */

    public void setBpm( String bpm )
    {
        this.bpm = bpm;
    }

    public String getKeynote()
    {
        return this.keynote;
    }
   /**
    */

    public void setKeynote( String keynote )
    {
        this.keynote = keynote;
    }

    public String getScale()
    {
        return this.scale;
    }
   /**
    */

    public void setScale( String scale )
    {
        this.scale = scale;
    }

    public String getTimesignature()
    {
        return this.timesignature;
    }
   /**
    */

    public void setTimesignature( String timesignature )
    {
        this.timesignature = timesignature;
    }

    public String getPassage()
    {
        return this.passage;
    }
   /**
    */

    public void setPassage( String passage )
    {
        this.passage = passage;
    }

    public String getEnergy()
    {
        return this.energy;
    }
   /**
    */

    public void setEnergy( String energy )
    {
        this.energy = energy;
    }

    public String getFeel()
    {
        return this.feel;
    }
   /**
    */

    public void setFeel( String feel )
    {
        this.feel = feel;
    }

    public String getMood()
    {
        return this.mood;
    }
   /**
    */

    public void setMood( String mood )
    {
        this.mood = mood;
    }

    public String getOrigin()
    {
        return this.origin;
    }
   /**
    */

    public void setOrigin( String origin )
    {
        this.origin = origin;
    }

    public String getSonority()
    {
        return this.sonority;
    }
   /**
    */

    public void setSonority( String sonority )
    {
        this.sonority = sonority;
    }

    public String getTexture()
    {
        return this.texture;
    }
   /**
    */

    public void setTexture( String texture )
    {
        this.texture = texture;
    }

    public java.util.Set getUserClipScore()
    {
        return this.userClipScore;
    }
   /**
    */

    public void setUserClipScore( java.util.Set userClipScore )
    {
        this.userClipScore = userClipScore;
    }

    public String getViewerScore()
    {
        return this.viewerScore;
    }
   /**
    */

    public void setViewerScore( String viewerScore )
    {
        this.viewerScore = viewerScore;
    }

    public java.util.Set getUserItems()
    {
        return this.userItems;
    }
   /**
    */

    public void setUserItems( java.util.Set userItems )
    {
        this.userItems = userItems;
    }

    public boolean getOwnedByViewer()
    {
        return this.ownedByViewer;
    }
   /**
    */

    public void setOwnedByViewer( boolean ownedByViewer )
    {
        this.ownedByViewer = ownedByViewer;
    }

    public java.util.Set getUserFavorites()
    {
        return this.userFavorites;
    }
   /**
    */

    public void setUserFavorites( java.util.Set userFavorites )
    {
        this.userFavorites = userFavorites;
    }

    public boolean isAFavorite()
    {
        return this.AFavorite;
    }
   /**
    */

    public void setAFavorite( boolean AFavorite )
    {
        this.AFavorite = AFavorite;
    }

    public java.util.Map getDescriptors()
    {
        return this.descriptors;
    }
   /**
    */

    public void setDescriptors( java.util.Map descriptors )
    {
        this.descriptors = descriptors;
    }

    public String getStoreName()
    {
        return this.storeName;
    }
   /**
    */

    public void setStoreName( String storeName )
    {
        this.storeName = storeName;
    }

    public String getTimesSold()
    {
        return this.timesSold;
    }
   /**
    */

    public void setTimesSold( String timesSold )
    {
        this.timesSold = timesSold;
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-LoopQueryForm.java 
           containing the additional code and place it in your metadata/web directory.
        */
    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *                                                javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // reset any boolean data types to false

        this.searchable = false;

        this.ready = false;

        this.ownedByViewer = false;

        this.AFavorite = false;

    }

}
