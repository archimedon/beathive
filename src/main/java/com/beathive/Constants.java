package com.beathive;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.FastHashMap;
import com.beathive.model.LabelValue;


/**
 * Constant values used throughout the application.
 * 
 * <p>
 * <a href="Constants.java.do"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison </a>
 * @version $Revision: 1.4 $ $Date: 2004/05/16 02:16:42 $
 */
public class Constants {
    //~ Static fields/initializers =============================================

	/**
	 *  'userCart'
	 */
	public static final String USER_CART = "userCart";
	
	/**
	 *  'cartItemList'
	 */
	public static final String CART_ITEMS_KEY = "cartItemList";
	
    /** 
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */ 
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts.action.LOCALE";
    

    /**
     * The name of the CSS Theme setting.
     */
    public static final String CSS_THEME = "csstheme";
	
	
    /**
     * The application scoped attribute for persistence engine and class that
     * implements it
     */
    public static final String DAO_TYPE = "daoType";
    public static final String DAO_TYPE_HIBERNATE = "hibernate";

    /**
     * JNDI Name of Mail Session.  As configured in the appserver.
     */
    public static final String JNDI_MAIL = "mail/Session";

    /**
     * Default from address for e-mail messages. You should change
     * this to an e-mail address that your users can reply to.
     */
    public static final String DEFAULT_FROM = "support@beathive.com";
    public static final String DEFAULT_COMPLAINER_EMAIL = "offendedUser@beathive.com";
    public static final String DEFAULT_SUPPORT_EMAIL = "support@beathive.com";

    /** Application scoped attribute for authentication url */
    public static final String AUTH_URL = "authURL";

    /** Application scoped attributes for SSL Switching */
    public static final String HTTP_PORT = "httpPort";
    public static final String HTTPS_PORT = "httpsPort";

    /** The application scoped attribute for indicating a secure login */
    public static final String SECURE_LOGIN = "secureLogin";

    /** The encryption algorithm key to be used for passwords */
    public static final String ENC_ALGORITHM = "algorithm";

    /** A flag to indicate if passwords should be encrypted */
    public static final String ENCRYPT_PASSWORD = "encryptPassword";

    /** File separator from System properties */
    public static final String FILE_SEP = System.getProperty("file.separator");
	public static final String UPLOAD_FORM_KEY = "uploadForm";

    /** User home from System properties */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The session scope attribute under which the breadcrumb ArrayStack is
     * stored
     */
    public static final String BREADCRUMB = "breadcrumbs";

    /**
     * The session scope attribute under which the User object for the
     * currently logged in user is stored.
     */
    public static final String USER_KEY = "currentUserForm";
	public static final String PACK_INCOMPLETE_KEY = "trackpack.status.incomplete";
	public static final String PACK_COMPLETE_KEY = "trackpack.status.complete";
	public static final String PACK_FORMAT_INCOMPLETE_KEY = "trackpack.status.pack_format.incomplete";
	public static final String FORMAT_INCOMPLETE_KEY = "trackpack.status.basefile_missing";
//	public static final int MIN_LOOP_IN_PACK = 3;

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_EDIT_KEY = "userForm";
    public static final String MEMBER_KEY = "memberForm";
 
    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";
    /** session scoped attribute for storing user's session scoped values */
    public static final String USER_CONTAINER_KEY = "userContainer";
	public static final String MENU_SEARCH = "SearchCriteria";
	/** "DESCRIPTORS_KEY" */
	public static final String DESCRIPTOR_KEY = "DESCRIPTORS_KEY";
    /** request scoped attribute for form-name of cart_item_id*/
    public static final String CART_ITEM_ID_KEY = "cart_itemId";
    /** request scoped attribute for form-name of cart_item_id*/
    public static final String CART_ITEM_QTY_KEY = "cart_itemQty";
    /** request scoped attribute for form-name of cart_item_id*/
   public static final String CART_ITEM_UOM_KEY = "cart_itemUom";
    /**
     * The request scope attribute for indicating a newly-registered user 'registered'
     */
    public static final String REGISTERED = "registered";
	public static final String LOGGED_IN = "loggedInUri";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "admin";
    public static final String MEMBER_ROLE = "member";


	public static List SUPPORTED_CURRENCIES = new LinkedList();
	static {
		SUPPORTED_CURRENCIES.add(new LabelValue("AUD" , "AUD"));
		SUPPORTED_CURRENCIES.add(new LabelValue("CAD" , "CAD"));
		SUPPORTED_CURRENCIES.add(new LabelValue("EUR" , "EUR"));
		SUPPORTED_CURRENCIES.add(new LabelValue("GDP" , "GDP"));
		SUPPORTED_CURRENCIES.add(new LabelValue("USD" , "USD"));
		SUPPORTED_CURRENCIES.add(new LabelValue("YEN" , "YEN"));
	}

	public static List SUPPORTED_LANGS= new LinkedList();
	static {
		// this is a shortcut. the label should be a key to be translated
		// to the current user-locale. to yeild: ex: ingles,frances,alleman
		
		// set ISO 2 letter value
		SUPPORTED_LANGS.add(new LabelValue("ENGLISH","en_US"));
		SUPPORTED_LANGS.add(new LabelValue( "FRENCH" ,"fr_FR"));
		SUPPORTED_LANGS.add(new LabelValue("GERMAN","de_DE"));
		SUPPORTED_LANGS.add(new LabelValue( "JAPANESE","ja_JP"));
	}

	
    /**
     * 'preferenceForm'
     */
    public static final String PREFERENCE_KEY = "preferenceForm";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "user";
    /**
     * The name of the Producer role, as specified in web.xml
     */
    public static final String PRODUCER_ROLE = "producer";
    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";
    /**
     * Conversion_KEY
     */
//	public static final Object CONVERSION_KEY = "Conversion_KEY";

    /**
     * Name of cookie for "Remember Me" functionality.
     */
    public static final String LOGIN_COOKIE = "sessionId";
	public static final String YES_NO = "yesnoMenu";

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";
 
    
    //MENUS 
    
    /** 
     * producer picks for context
     * value = "producerPicks"
     */
    public static final String PRODUCER_PICKS ="producerPicks";
    
    /** get list of possible AVAILABLE_FORMATS=format */
    public static final String AVAILABLE_FORMATS ="format";
    /**
     * The name of the list of all genre. A application-scoped attribute
     */
    public static final String AVAILABLE_GENRES = "genre";
    /**
     * The name of the list of all instruments. A application-scoped attribute
     */
    public static final String AVAILABLE_INSTRUMENTS = "instrument";
    /**
     * The name of the list of all Instrumentgroups (i.e instrument categories (drums,bass,Mallets). A application-scoped attribute
     */
    public static final String AVAILABLE_INSTRUMENTGROUPS = "instrumentgroup";

    /**
     * The name of the List of ALL SCALES
     */
    public static final String AVAILABLE_SCALES = "scale";
    /**
     * The name of the List of ALL TIMESIGNATURES
     */
    public static final String AVAILABLE_TIMESIGNATURES = "timesignature";
    /**
     * The List of all loops
     */
    public static final String AVAILABLE_LOOPS = "availableLoops";
    /**
     * The List of all KEYNOTES
     */
    public static final String AVAILABLE_KEYNOTES = "keynote";
    /** get list of possible Sonorities */
    public static final String AVAILABLE_PASSAGES = "passage";
    /** get list of possible Energies */
    public static final String AVAILABLE_ENERGIES = "energy";
    /** get list of possible Feels */
    public static final String AVAILABLE_FEELS = "feel";
    /** get list of possible Moods */
    public static final String AVAILABLE_MOODS = "mood";
    /** get list of possible Origins */
    public static final String AVAILABLE_ORIGINS = "origin";
    /** get list of possible Sonorities */
    public static final String AVAILABLE_SONORITIES = "sonority";
    /** get list of possible Textures */
    public static final String AVAILABLE_TEXTURES = "texture";
    /** get list of possible Tempos */
    public static final String AVAILABLE_TEMPOS = "tempo";
    /** get list of possible Prices */
    public static final String AVAILABLE_PRICES = "price";
    /** get list of possible CLIP_STATI=status */
    public static final String AVAILABLE_STATI ="status";
    /** ="timespan"*/
    public static final String AVAILABLE_TIMESPAN ="timespan";
    /** states */
    public static final String AVAILABLE_STATES = "states";

    
    //~ Object specific lists    
    /**
     * The name of the list of a Loop's genre. A request-scoped attribute
     */
    public static final String LOOP_GENRES = "clipGenres";
    /**
     * The name of the list of a Loop's instruments. A request-scoped attribute
     */
    public static final String LOOP_INSTRUMENTS = "loopInstruments";
    /**
     * The name of the list of a trackpack's genre. A request-scoped attribute
     */
    public static final String TRACKPACK_GENRES = "trackpackGenres";
    /**
     * The name of the list of a trackpack's genre. A request-scoped attribute
     */
    public static final String TRACKPACK_INSTRUMENTS = "trackpackInstruments";
    
// ~ Search related lists    
    /**
     * The name of the list of a list of soundClips. A request-scoped attribute
     */
    public static final String SOUNDCLIP_LIST = "soundClipList";
    /**
     * The name of the list of a list of trackpacks. A request-scoped attribute
     */
    public static final String TRACKPACK_LIST = "trackpackList";
    /**
     * The name of the list of a loops. A request-scoped attribute
     * 'loopList'
     */
    public static final String LOOP_LIST = "loopList";
    /**
     * The name of the list of a soundClip's instruments. A request-scoped attribute
     */
    public static final String SOUNDCLIP_INSTRUMENTS = "soundClipInstruments";
    /**
     * "requestReturnUri"
     */
	public static final String RETURN_URI = "requestReturnUri";
    /**
     * The name of the list of a soundClip's genre. A request-scoped attribute
     */
    public static final String SOUNDCLIP_GENRES = "soundClipGenres";
    /**
     *  (generic) soundclipedit Key = "soundClipFormWeb"
     */
    public static final String SOUNDCLIP_VIEW_KEY = "soundClipFormWeb";

    /**
     *  (generic) soundclipedit Key = "soundClipFormEx"
     */
    public static final String SOUNDCLIP_EDIT_KEY = "soundClipFormEx";

    /**
     * The request scope attribute under which an editable trackpack form is stored
     */
    public static final String TRACKPACK_EDIT_KEY = "trackpackFormEx";

    public static final String TRACKPACK_SEARCH_KEY = "trackpackFormWeb";  
    /**
     * The request scope attribute under which an editable loop form is stored
     */
    public static final String LOOP_EDIT_KEY = "loopFormEx";

    public static final String INSTRUMENTGROUP_EDIT_KEY = "instrumentgroupFormEx";
    
    public static final String INSTRUMENT_EDIT_KEY = "instrumentFormEx";

    public static final String GENRE_EDIT_KEY = "genreFormEx";
    /**
     * The request scope attribute under which an editable loop form is stored
     * value=loopSearchList 
     */
    public static final String LOOP_SEARCH_LIST = "loopSearchList";
	public static final String KEYED_MENU_KEY = "keyedMenu";
	public static final String AVAILABLE_KEYED_DESCRIPTORS = "availableDescriptors";
    /** value=loopFormWeb */
    public static final String LOOP_SEARCH_KEY = "loopFormWeb";
    /** value=loopFormEx */
    public static final String LOOP_DETAIL_FORM = LOOP_EDIT_KEY;

    
    // the tag names used to describe model elements in XML   
    public static final String xSCOREBAG	= "scores"; 
    public static final String xCLIPSCORE	= "clipscore"; 
    public static final String xRESULTS	= "results"; 
    public static final String xSOUNDCLIP	= "soundclip"; 
    public static final String xGENREBAG	= "genre"; 
    public static final String xGENRE		= "genre"; 
    public static final String xPRODUCERPICKS	= "producerPicks"; 
    public static final String xDESCRIPTORBAG = "descriptors"; 
    public static final String xDESCRIPTOR	= "descriptor"; 

    public static final String xFORMAT	= "format"; 
    public static final String xFORMATBAG = "format"; 
    public static final String xINSTRUMENT	= "instrument"; 
    public static final String xINSTRUMENTBAG = "instruments";
    
	// the key to the rates in the Context
	public static final String EXCHANGE_RATES = "EXCHANGE_RATES";
	public static final int FETCH_SIZE = 10; // size used for hibernate queries
//	public static final float MIN_SAMPLE_RATE = (float) 44100.0;
//	public static final int MIN_SAMPLE_SIZE = 16;
	public static final String DEFAULT_FORMAT = "aif";
	public static final String DEFAULT_CURRENCY = "USD";
	public static final String DEFAULT_LANG = "en";
	public static final String TRACKPACK_COUNT = "packcount";
	public static final String LOOP_COUNT = "loopcount";
	public static final String TRACKPACK_NODE = "trackpackNode";
	public static final String LOOP_NODE = "loopNode";
	public static final String DEFAULT_LOCALE = "en_US";
	public static final String BUNDLE_KEY = "ApplicationResources";
	public static final String INITIAL_RATE_WEIGHT = "3";
	public static final String LOOP_FILE_PREFIX = "BH-";

	/** loopshops */
	public static final String STORE_LIST = "loopshops";
	public static final String BASE_LOOP_PRICE = "0.99";
	public static final String BASE_TRACKPACK_PRICE = "2.99";
	public static final String DEFAULT_BANNER = "/images/producer_banners/default_banner.jpg";
	public static final String RESULT_RENDERER_URL = "xmlRend";
	public static final String NEW_PASSWORD_URL = "/setupUser.do";

    
	public static String TRACKPACK_VIEW_NODE = "trackpackViewNode";
	public static String defaultLast = "changeName";
	public static String defaultFirst = "changeName";
	public static String defaultMiddle = "changeName";

	
	public static final String SUPPORTED_LANGS_KEY = "supportedLanguages";
	public static final String SUPPORTED_CURRENCIES_KEY = "supportedCurrencies";
	/** "/images/producer_banners" */
	public static final String BANNER_DIR =  "/images/producer_banners";
	// removed '#' from pattern. '#' causes swfgen to break
	public static final String DISALLOWED_CHARS = "[^_|\\w]+"; 
	public static final String AVAILABLE_COUNTRIES = "countries";
	public static final String AVAILABLE_CANADIAN_TERRITORIES = "candianTerritories";


	public static final String NOBODY_NAME = "NO_BODY_HAS_THIS_NAME_EVER";


	public static final String USER_SHOP_SESSION_VIEWS = "USER_SHOPVIEW_MGR";

	/** Sept 14th 2008*/
	public static final String BAD_EMAIL_ADDRESS = "INVALID";

	public static final String EMAIL_NOT_UNIQUE = "NOT_UNIQUE";

	public static final String STORE_LOOP_QUERYMETA = "storeloopQueryMeta";
	public static final String STORE_TRACKPACK_QUERYMETA = "storetrackpackQueryMeta";

	public static final int MIN_LOOP_IN_PACK = 3;
	/** 'userFavorites' **/
	public static final String USER_FAVORITES = "userFavorites";

	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String MEMBER_DISCOUNT_KEY = "member_discount";
	public static final String NON_MEMBER_DISCOUNT_KEY = "non_member_discount";

	public static final String DISCOUNT_KEY = "salesDiscount";

	public static final String MEMBER_DISCOUNT_COLORCODES = "memberDiscountColorCodes";
	public static final String NON_MEMBER_DISCOUNT_COLORCODES = "nonMemberDiscountColorCodes";

    /**
     * The request scope attribute that holds the promo form.
     */
    public static final String PROMO_KEY = "promoForm";

    /**
     * The request scope attribute that holds the promo list
     */
    public static final String PROMO_LIST = "promoList";
	
    /**
     * The request scope attribute that holds the comment form.
     */
    public static final String COMMENT_KEY = "commentForm";

    /**
     * The request scope attribute that holds the comment list
     */
    public static final String COMMENT_LIST = "commentList";

    /**
     * The request scope attribute that holds the zip form.
     */
    public static final String ZIP_KEY = "zipForm";

    /**
     * The request scope attribute that holds the zip list
     */
    public static final String ZIP_LIST = "zipList";

    /**
     * Used as the default 'authCode' in a purchase
     * 'authCode' should be updated to an actual auth or fail code
     * later 
     * "PREAUTH"
     */
	public static final String PREAUTH = "PREAUTH";

	/**
	 * The "assigned" DB id for the named "transactionFee" schedule.
	 * The values used are the registered trademarks for
	 * charge executor: "CyberSource":"1"
	 */
	public static final String CYBERSOURCE_ID = "1";
	
	/**
	 * The "assigned" DB id for the named "transactionFee" schedule.
	 * The values used are the registered trademarks for
	 * charge executor: PayPal:"2"
	 */
	public static final String PAYPAL_ID = "2";

	public static final String DETERMINE_FORMAT_KEY = "NEW_SESSION_PREF";
	public static final int MIN_LOOPS_SHOPDIR = 19;
	public static final String REDIR_SESSIONS = "jumpBackMap";


	/*
	public static final String[] FILE_EXTS = { "aif" ,  "wav" ,"rex"};
	

    public static Hashtable mimeToExt = new Hashtable();
    static {
		mimeToExt.put("audio/aiff" , "aif");
		mimeToExt.put("audio/x-aiff" , "aif");
		mimeToExt.put("audio/wav" , "wav");
		mimeToExt.put("audio/x-wav" , "wav");
		mimeToExt.put("audio/rex" , "rex");
		mimeToExt.put("audio/x-rex" , "rex");

    }
    public static Hashtable extToMime = new Hashtable();
    
    static {
    		extToMime.put("aif" , "audio/aiff");
		extToMime.put("wav" , "audio/wav");
    		extToMime.put("rex" , "audio/rex");
    }
   
*/
}
