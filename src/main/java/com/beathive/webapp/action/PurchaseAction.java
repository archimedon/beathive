package com.beathive.webapp.action;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.beathive.Constants;
import com.beathive.model.Address;
import com.beathive.model.AudioFile;
import com.beathive.model.ClipToZip;
import com.beathive.model.PaymentExecutor;
import com.beathive.model.Promo;
import com.beathive.model.Purchase;
import com.beathive.model.PurchaseItem;
import com.beathive.model.SoundClip;
import com.beathive.model.Trackpack;
import com.beathive.model.User;
import com.beathive.model.Zip;
import com.beathive.service.CartManager;
import com.beathive.service.MailEngine;
import com.beathive.service.Manager;
import com.beathive.service.PromoManager;
import com.beathive.service.PurchaseManager;
import com.beathive.service.ServiceException;
import com.beathive.service.SoundClipManager;
import com.beathive.service.UserManager;
import com.beathive.service.ZipManager;
import com.beathive.service.commerce.CcReply;
import com.beathive.service.commerce.CcRequest;
import com.beathive.service.commerce.ChargeManager;
import com.beathive.service.commerce.impl.CaptureRequest;
import com.beathive.service.commerce.impl.CcRequestImpl;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.AudioFileForm;
import com.beathive.webapp.form.BillingForm;
import com.beathive.webapp.form.GenreForm;
import com.beathive.webapp.form.LoopForm;
import com.beathive.webapp.form.LoopFormCart;
import com.beathive.webapp.form.PaypalForm;
import com.beathive.webapp.form.SoundClipForm;
import com.beathive.webapp.form.TrackpackFormCart;
import com.beathive.webapp.helper.Cart;
import com.beathive.webapp.helper.SalesDiscount;
import com.beathive.webapp.util.RequestUtil;

/**
 * <p>
 * <a href="PurchaseAction.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison</a>
 * @version $Revision: 1.12 $ $Date: 2004/05/25 06:27:22 $
 * 
 * 
 * @struts.action name="billingForm" path="/cart/authPurchase" scope="request"
 *                validate="true" input="edit" parameter="action"
 * 
 * @struts.action name="billingForm" path="/cart/checkout" scope="request"
 *                validate="false" input="edit" parameter="action"
 * 
 * 
 * @struts.action-forward name="receipt" path="/cart/receipt.jsp"
 *                        redirect="true"
 * @struts.action-forward name="edit" path="/user/index.jsp?t=account"
 *                        redirect="false"
 * @struts.action-forward name="billingInfo" path="/user/editAccount.do"
 *                        redirect="true"
 * @struts.action-forward name="viewcart" path=".cartView"
 * @struts.action-forward name="confirm" path="/cart/confirmPurchase.jsp"
 *                        redirect="true"
 * 
 */
public final class PurchaseAction extends BaseAction {
	private static final String PAYPAL_VALIDATE = "https://www.paypal.com/cgi-bin/webscr";
	private static final String SELLER_IDENTITY_TOKEN = "8oYackKUjhNuoDdH5K6cjUhiUJMyrPvBKA1K4JXjFVJxjgDh6joIVZx7X60";
	/** The <code>Log</code> instance for this class */
	private static Log log = LogFactory.getLog(PurchaseAction.class);
	public static String DATE_FORMAT = "EEE_dd_MMM_yyyy-hh_mma";
	public static boolean isDebug = false;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return super.execute(mapping, form, request, response);
	}

	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillingForm billingForm = (BillingForm) form;
		// Extract attributes and parameters we will need
		String uname = null;
		// present the billing form
		if ((uname = request.getRemoteUser()) != null) {
			updateBillingForm(billingForm, uname);
		}
		return mapping.findForward("editBilling");
	}

	public ActionForward checkout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		BillingForm billingForm = (BillingForm) form;
		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();

		CcReply ccReply = (CcReply) session.getAttribute("ccReply");
		CcRequest ccRequest = (CcRequest) session.getAttribute("ccRequest");

		Cart cart = CartAction.getCart(session);

		// CartManager cartmgr = (CartManager)getBean("cartManager");
		SoundClipManager clipmgr = (SoundClipManager) getBean("soundClipManager");
		PurchaseManager purman = (PurchaseManager) getBean("purchaseManager");
		Purchase order = null;// (Purchase)session.getAttribute("order");
		/** process info **/

		// IF a purchase order DOES NOT exist in the session

		// 1) Create the purchase order

		Object[] purchaseInf = (Object[]) session.getAttribute("purchaseInf"); 

		order = (Purchase) purchaseInf[0];
		String[] aufs = (String[]) purchaseInf[1];
		// 2) confirm that the files exist
		boolean[] valid = clipmgr.filesExist(aufs);

		// create an ACCESS-KEY for unauthenticated access to created *.zip

		// create the ZIP
		String customerName = getUsername(request, billingForm);
		String zfilename = "BH-" + getDateStr();
		String destfile = clipmgr.getUsersDownloadDir() + Constants.FILE_SEP + customerName + Constants.FILE_SEP
				+ zfilename + ".zip";

		// create the ZIP
		Map<String, String> zipMap = getCartAsZipItems(zfilename, cart.getList(), clipmgr);

		File zfile = new java.io.File(destfile);
		zfile.getParentFile().mkdirs();
		clipmgr.zipem(destfile, zipMap);
		// PERSIST zip-info
		Zip zip = null;
		if (zfile.exists()) {
			zip = persistZip(order, zfile);
			// use ZIP (partial) path along with ACCESS-KEY to create a download
			// link.
			// provide user with download link
			String downUrl = "/get/" + URLEncoder.encode(customerName, "UTF-8") + "/" + zip.getAccessKey()
					+ Constants.FILE_SEP + zfile.getName();

			// save the URL
			order.setDownloadURL(downUrl);

			// CAPTURE Sale using authCode
			CcRequest capture = new CaptureRequest(ccRequest, order.getId().toString(), ccReply.getAuthRequestID());
			ChargeManager chargeMgr = (ChargeManager) getBean("chargeManager");
			// log.debug("capture: " + capture);

			CcReply captureReply = chargeMgr.ccCapture(capture);

			order.setAuthCode(ccReply.getAuthCode());

			// hold off just so we retain the real total
			if (!log.isDebugEnabled()) {
				order.setTotal(new Double(captureReply.getAuthAmount()));
			}

			order.setRequestId(captureReply.getAuthRequestID());
			order.setReconciliationId(captureReply.getReconciliationID());
			purman.savePurchase(order);

			// CCreply contains final sales info

			zip.setCustomerName(customerName);
			zip.setCustomerId(order.getCustomerId());
			zip.setDownloadURL(downUrl);
			ZipManager zman = (ZipManager) getBean("zipManager");
			// save ZIP
			zman.saveZip(zip);

			order.setZipId(zip.getId());
			// PERSIST purchase transaction
			purman.savePurchase(order);

			User user = null;
			UserManager userManager = (UserManager) getBean("userManager");
			if (billingForm.getUserId() != null) {
				user = userManager.getUser(billingForm.getUserId().toString());

				user.addArchive(zip);
				user.addPurchase(order);
				user.getCartItems().clear();
				userManager.updateUser(user);

			}

			// put in response
			session.setAttribute("downUrl", RequestUtil.getAppURL(request) + downUrl);

			// email user receipt + download instructions
			emailReceipt(order, request);

			// add complete messages
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("cart.purchase.complete"));

			// save messages in session to survive a redirect
			saveMessages(session, messages);
		}

		Map<String, Comparable> fincart = new HashMap<String, Comparable>();

		double subTotal = cart.getTotal().doubleValue();
		double subTotal2 = SalesDiscount.getDiscountedAmount(subTotal);
		double savings = subTotal - subTotal2;
		fincart.put("subTotal", cart.getTotal());
		fincart.put("savings", new Double(savings));
		fincart.put("total", SalesDiscount.getDiscountedAmount(cart.getTotal()));
		fincart.put("subTotal", cart.getTotal());
		fincart.put("promoDiscount", cart.getPromoDiscount());
		fincart.put("promoCode", cart.getPromoCode());
		fincart.put("discountedAmount", cart.getDiscountedAmount());

		Cart carttmp = new Cart();
		carttmp.getList().addAll(cart.getList());

		cart.getList().clear();

		request.getSession().setAttribute("carttmp", carttmp);
		request.getSession().setAttribute("fincart", fincart);
		// process billing info
		return mapping.findForward("purchaseComplete");
	}

	/**
	 * @param request
	 * @param billingForm
	 * @return
	 */
	private static String getUsername(HttpServletRequest request, BillingForm billingForm) {
		return ((request.getRemoteUser() != null) ? request.getRemoteUser() : billingForm.getEmail());
	}

	private Zip persistZip(Purchase order, File zfile) throws Exception {
		Zip zip = new Zip();
		zip.setPurchaseId(order.getId());
		zip.setAccessKey(RequestUtil.generatePassword());
		zip.setFileSize(zfile.length());
		zip.setPath(zfile.getAbsolutePath());
		zip.setCreationTime(new Date());
		zip.setName(zfile.getName());
		return zip;
	}

	/**
	 * @param billingForm
	 * @param request
	 * @return
	 */
	private String makeDestFile(HttpServletRequest request, String username, String downloadDir) {

		return downloadDir + Constants.FILE_SEP + username + Constants.FILE_SEP + getDateStr() + ".zip";
	}

	private static String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * @param itemList
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Map<String, String> getCartAsZipItems(List<SoundClipForm> itemList, SoundClipManager clipmgr)
			throws IllegalAccessException, InvocationTargetException {
		return getCartAsZipItems("", itemList, clipmgr);
	}

	private Map<String, String> getCartAsZipItems(String zipbase, List<SoundClipForm> itemList,
			SoundClipManager clipmgr) throws IllegalAccessException, InvocationTargetException {
		Map<String, String> zipMap = new HashMap<String, String>();
		ClipToZip z = null;
		for (SoundClipForm clipForm : itemList) {
			String clipId = clipForm.getId();
			SoundClip clip = clipmgr.getSoundClip(clipId);

			AudioFileForm af = (clip instanceof Trackpack) ? ((TrackpackFormCart) clipForm).getAudioFile()
					: ((LoopFormCart) clipForm).getAudioFile(); // getAudioFile(clip
																// , fileId);
			z = new ClipToZip(af.getFormatId());
			BeanUtils.copyProperties(z, clip);

			z.setName(clip.getName().replaceAll("[\"\\?:;\\,\\-\\*\\\\]", "_"));
			zipMap.put(zipbase + Constants.FILE_SEP + z.getName(), z.getFileRef());

			if (clip instanceof Trackpack) {
				List<SoundClip> innerloops = ((Trackpack) clip).getLoops();
				for (SoundClip iclip : innerloops) {
					z = new ClipToZip(af.getFormatId());
					BeanUtils.copyProperties(z, iclip);
					if (StringUtils.isNotBlank(z.getFileRef())) {
						zipMap.put(zipbase + Constants.FILE_SEP + z.getName(), z.getFileRef());
					}
				}
			}
		}
		return zipMap;
	}

	public static String[] getNameAndExt(String str) {
		int index = str.lastIndexOf(".");
		if (index < 1) {
			return new String[] { str, "" };
		}
		String fname = str.substring(0, index);
		String ext = str.substring(index + 1);
		return new String[] { fname, ext };
	}

	private Map<String, String> getPPCartAsZipItems(String zipbase, List<Object[]> itemList, SoundClipManager clipmgr)
			throws IllegalAccessException, InvocationTargetException {
		Map<String, String> zipMap = new HashMap<String, String>();
		ClipToZip z = null;
		for (Object[] oPair : itemList) {

			Object[] paired = new Object[2];

			String clipId = (String) oPair[0];
			AudioFileForm af = (AudioFileForm) oPair[1];
			z = new ClipToZip(af.getFormatId());
			SoundClip clip = clipmgr.getSoundClip(clipId);
			BeanUtils.copyProperties(z, clip);

			File afile = new File(af.getFileRef());
			String[] namepts = getNameAndExt(afile.getName());

			z.setName(clip.getName().replaceAll("[\"\\?:;\\,\\-\\*\\\\]", "_"));
			log.debug("z.getName()): " + z.getName());
			zipMap.put(zipbase + Constants.FILE_SEP + z.getName(), z.getFileRef());
			if (clip instanceof Trackpack) {
				List<SoundClip> innerloops = ((Trackpack) clip).getLoops();
				for (SoundClip iclip : innerloops) {
					z = new ClipToZip(af.getFormatId());
					BeanUtils.copyProperties(z, iclip);
					if (StringUtils.isNotBlank(z.getFileRef())) {
						zipMap.put(zipbase + Constants.FILE_SEP + z.getName(), z.getFileRef());
					}
				}
			}
		}
		return zipMap;
	}

	/**
	 * @param billingForm
	 * @param uname
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void updateBillingForm(BillingForm billingForm, String uname)
			throws IllegalAccessException, InvocationTargetException {
		UserManager umgr = (UserManager) getBean("userManager");
		User user = umgr.getUserByUsername(uname);
		// log.debug("found user: " + user.getUsername() + " id: " +
		// user.getId());
		Address address = user.getAddress();
		if (address == null) {
			address = new Address(user.getId());
		}
		BeanUtils.copyProperties(billingForm, address);
		BeanUtils.copyProperties(billingForm, user);
		billingForm.setUserId(user.getId());
		user = null;
	}

	public AudioFile getAudioFile(SoundClip clip, String fileid) {
		AudioFile af = null;
		Iterator<?> it = clip.getAudioFormat().iterator();
		while (it.hasNext()) {
			af = (AudioFile) it.next();
			if (af.getId().toString().equals(fileid)) {
				// log.debug("got: " + af + "\naf.fileid " + af.getFileRef()+
				// "\naf.id " + af.getId());
				return af;
			}
		}
		return null;
	}

	/**
	 * provide a populated order object bearing the cart contents
	 * 
	 * @param billingForm
	 * @param request
	 * @param cart
	 * @return
	 * @throws ServiceException
	 */
	private Object[] genPurchase(BillingForm billingForm, HttpServletRequest request, Cart cart, PurchaseManager purman,
			PaymentExecutor chargeservice) throws ServiceException {
		Object[] ret = new Object[2];
		Purchase order = new Purchase();
		ret[0] = order;
		try {
			BeanUtils.copyProperties(order, billingForm);
		} catch (Exception e1) {
			log.error(e1);
		}
		if (StringUtils.isNotBlank(cart.getPromoCode())) {
			order.setPromoCode(cart.getPromoCode());
			order.setPromoDiscount(cart.getDiscount());
		}

		// check if USER entered the code
		// also double check that we got a discount in case cart had code but no
		// value (unlikely)
		if (StringUtils.isNotBlank(order.getPromoCode())) {
			Promo promo = (Promo) ((PromoManager) getBean("promoManager")).getPromoByCode(order.getPromoCode());
			if (promo != null) {
				order.setPromoDiscount(promo.getDiscount());
			} else {
				order.setPromoCode(null);
				order.setPromoDiscount(null);
			}
		}
		// order.setId(null);
		order.setSubTotal(cart.getTotal());
		// NOTE: discount percent is divided by 100
		order.setDiscount(new Double(cart.getDiscount() / 100));
		order.setTotal(new Double(cart.getDiscountedAmount()));
		order.setCustomerName(getUsername(request, billingForm));
		order.setCustomerId(billingForm.getUserId());
		order.setIpAddress(request.getRemoteAddr());

		// authcode is now set by default in the object at instantiation
		// order.setAuthCode("PREAUTH");
		order.setCreated(new Date());
		Manager base = (Manager) getBean("manager");
		order.setExecutor(chargeservice);
		purman.savePurchase(order);

		ret[1] = addPurchaseItems(order, cart.getList());
		return ret;
	}

	private void emailReceipt(Purchase oform, HttpServletRequest request) {
		MessageResources resources = getResources(request);
		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		MailEngine mailEngine = (MailEngine) getBean("mailEngine");

		String template = "email/saleReceipt.vm";

		message.setTo(oform.getFullName() + " <" + oform.getEmail() + ">");
		message.setFrom(resources.getMessage("signup.newpassword.email.from"));

		// separate SoundClips
		List<PurchaseItem> tps = new LinkedList<PurchaseItem>();
		List<PurchaseItem> loops = new LinkedList<PurchaseItem>();

		Set<PurchaseItem> items = oform.getItems();
		for (PurchaseItem pi : items) {
			pi.setGenre(resources.getMessage(pi.getGenre()));
			if (pi.isLoop()) {
				pi.setInstrument(resources.getMessage(pi.getInstrument()));
				loops.add(pi);
			} else {
				tps.add(pi);
			}
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("receipt", oform);
		data.put("baseURL", RequestUtil.getAppURL(request));
		data.put("trackpackList", tps);
		data.put("loopList", loops);
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 6);

		DateFormat df = DateFormat.getDateInstance();

		data.put("termEndDate", df.format(cal.getTime()));
		data.put("term", "6 months");
		if (oform.getPromoDiscount() != null) {
			data.put("promoDiscount", Math.round(oform.getPromoDiscount().doubleValue() * 100) + "%");
		}
		data.put("discount", Math.round(oform.getDiscount().doubleValue() * 100) + "%");

		message.setSubject(resources.getMessage("receipt.email.subject"));

		mailEngine.sendMessage(message, template, data);
	}

	/**
	 * Convert clips to orderItems and put them in the Purchase
	 * 
	 * @param order
	 * @param clips
	 */
	private String[] addPurchaseItems(Purchase order, List<?> clips) {
		String[] filerefs = new String[clips.size()]; // trackpacks represents
														// their children
		PurchaseItem orderItem = null;
		for (int g = 0; g < clips.size(); g++) {
			orderItem = new PurchaseItem();
			if (order.getId() != null) {
				orderItem.setPurchaseId(order.getId());
			}
			SoundClipForm clif = (SoundClipForm) clips.get(g);
			boolean isTP = clif.getClass().equals(TrackpackFormCart.class);

			String type = isTP ? SoundClip.TRACKPACK_TYPE : SoundClip.LOOP_TYPE;

			AudioFileForm af = (isTP) ? ((TrackpackFormCart) clif).getAudioFile()
					: ((LoopFormCart) clif).getAudioFile();
			filerefs[g] = af.getFileRef();
			orderItem.setFileRef(af.getFileRef());
			orderItem.setSamplePath(af.getSamplePath());
			orderItem.setCustomerId(order.getCustomerId());
			orderItem.setFileId(new Long(af.getId()));
			orderItem.setFormatName(af.getFormatId());
			orderItem.setClipName(clif.getName());
			orderItem.setClipId(new Long(clif.getId()));
			orderItem.setPrice(new Double(clif.getPriceForm().getAmount()));
			orderItem.setStoreId(new Long(clif.getStoreId()));
			orderItem.setStoreName(clif.getStoreName());
			orderItem.setType(type);
			orderItem.setGenre(((GenreForm) clif.getGenre().get(0)).getLabelKey());

			try {
				if (!isTP) {
					Object tmp = PropertyUtils.getProperty(clif, "trackpack");
					if (tmp != null) {
						orderItem.setParentName(BeanUtils.getProperty(tmp, "name"));
					}
					orderItem.setInstrument(((LoopForm) clif).getInstrumentForm().getLabelKey());
				} else {
					orderItem.setNumLoops(new Integer(((TrackpackFormCart) clif).getLoops().size()));
				}
			} catch (Exception e) {
				log.error("trackpack name etc", e);
			}
			try {
				order.addItem(orderItem);
			} catch (Exception e) {
				log.error(e);
			}
		}
		return filerefs;
	}

	private CcRequest makeCcRequest(Purchase order, BillingForm billingForm) {
		CcRequest req = new CcRequestImpl();
		try {
			BeanUtils.copyProperties(req, billingForm);
		} catch (Exception e) {

		}

		PurchaseItem orderItem = null;
		Collection<PurchaseItem> items = order.getItems();
		Iterator<PurchaseItem> it = items.iterator();
		while (it.hasNext()) {
			orderItem = it.next();
			req.addItem(orderItem.getPrice().toString());
		}
		return req;
	}

	/**
	 * only presents a billingForm if the users current billingInfo is doesn't
	 * validate on the form level updateUserInfo(userForm , billingForm); double
	 * cart_total = userContainer.getCart().getSubTotal()
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auth(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering auth");

		ActionMessages errors = new ActionMessages();

		BillingForm billingForm = (BillingForm) form;
		String uname = null;
		// present the billing form
		if (request.getMethod().equalsIgnoreCase("get")) {

			if ((uname = request.getRemoteUser()) != null) {
				updateBillingForm(billingForm, uname);
			}
			return mapping.findForward("editBilling");
		}

		HttpSession session = request.getSession();

		Cart cart = CartAction.getCart(session);

		PurchaseManager omgr = (PurchaseManager) getBean("purchaseManager");

		PaymentExecutor executor = (PaymentExecutor) omgr.getExecutor(new Long(Constants.CYBERSOURCE_ID));
		Object[] purchaseInf = genPurchase(billingForm, request, cart, omgr, executor);
		Purchase order = (Purchase) purchaseInf[0];

		// save pre-authorized order
		omgr.savePurchase(order);

		Collection<PurchaseItem> items = order.getItems();
		// log.debug("order " + order);
		// create a request using the orderId as as merchant refernce code
		CcRequest req = new CcRequestImpl();
		try {
			BeanUtils.copyProperties(req, billingForm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PurchaseItem orderItem = null;
		Iterator<PurchaseItem> itmIt = items.iterator();
		while (itmIt.hasNext()) {
			orderItem = itmIt.next();
			req.addItem(orderItem.getPrice().toString());
		}

		req.setMerchantReferenceCode(order.getId().toString());

		ChargeManager chargeMgr = (ChargeManager) getBean("chargeManager");
		// authorize the charges
		CcReply ccReply = chargeMgr.ccAuth(req);

		// if successful authrization set user billinfo to reflect the changes.
		// TODO - this should really e weeded into a setup biiling page
		// not here but until...
		if (ccReply.getErrors() == null || ccReply.getErrors().isEmpty()) {

			if (request.getRemoteUser() != null) {
				UserManager umgr = (UserManager) getBean("userManager");
				User user = umgr.getUserByUsername(request.getRemoteUser());

				Address address = user.getAddress();
				if (address == null) {
					address = new Address(user.getId());
					user.setAddress(address);
					address.setUserId(user.getId());
				}
				billingForm.setUserId(user.getId());
				BeanUtils.copyProperties(address, billingForm);
				// log.debug("updating user address");

				address.setAVSCode(ccReply.getAVSCode());
			}
			req.setRequestToken(ccReply.getRequestToken());
			session.setAttribute("ccReply", ccReply);
			session.setAttribute("ccRequest", req);

			session.setAttribute("purchaseInf", purchaseInf);
			session.setAttribute("billingForm", billingForm);
			return checkout(mapping, form, request, response);
		} else {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("ccreply.error.general"));
			Map<?, ?> ccErr = ccReply.getErrors();
			Iterator<?> erit = ccErr.entrySet().iterator();
			while (erit.hasNext()) {
				Map.Entry<?, ?> entry = (Map.Entry<?, ?>) erit.next();
				String ekey = (String) entry.getKey();
				if (ekey != null) {
					Collection<?> val = (Collection<?>) entry.getValue();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ekey));
					if (val != null) {
						for (Iterator<?> it = val.iterator(); it.hasNext();) {
							errors.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("ccreply.error.detail", it.next()));
						}
					}
				}
			}
			saveErrors(request, errors);
			return mapping.findForward("editBilling");
		}

	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (request.getRequestURI().indexOf("/ppnotify") > -1) {
			return notify(mapping, form, request, response);
		}

		return info(mapping, form, request, response);
	}

	/**
	 * Accepts PayPal notification. Check for validity, that the transaction is
	 * complete, then creates the users zip file. Puts transaction data,
	 * final-cart-view in the session for the purchaseComplete page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward notify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String reason = (StringUtils.isNotBlank(request.getParameter("reason_code")))
				? request.getParameter("reason_code") : "charge";
		if (reason == "refund" || StringUtils.equals(request.getParameter("payment_status"), "Refunded")) {
			return null;
		}

		PaypalForm paypalForm = (PaypalForm) form;

		// make sure the request is coming from PayPal
		String addr = request.getRemoteAddr();

		MessageResources mr = getResources(request);

		String email = mr.getMessage("paypal.seller.email");

		if (!isValidHost(addr)) {
			log.error("INValidHost '" + addr + "'");
			return null;
		}
		if (!paypalForm.isMyEmail(email)) {
			return null;
		}

		String seam = "/tmp/" + paypalForm.getInvoice() + ".jser";

		Map ppsession = (Map) readObject(seam);

		Cart cart = (Cart) ppsession.get("cart");
		List<Object[]> cartClips = (LinkedList) ppsession.get("cartClips");

		List<SoundClipForm> cartList = new LinkedList();

		CartManager mgr = (CartManager) getBean("cartManager");
		SoundClipForm clipForm = null;
		for (Object[] oPair : cartClips) {

			Object[] paired = new Object[2];

			String clipId = (String) oPair[0];
			AudioFileForm af = (AudioFileForm) oPair[1];

			SoundClip clip = mgr.getCartSoundClip(clipId);

			if (clip.getClass().getName().toLowerCase().indexOf("trackpack") > -1) {

				clipForm = (TrackpackFormCart) ConvertUtil.convert(clip, TrackpackFormCart.class);
				((TrackpackFormCart) clipForm).setFileId(af.getId());
				ConvertUtil.convertNamedList(clipForm, "audioFormat", AudioFileForm.class);
				ConvertUtil.convertNamedList(clipForm, "genre", GenreForm.class);
			} else {
				clipForm = CartAction.convertLoop(clip, af.getId());
			}
			cartList.add(clipForm);
		}
		cart.setList(cartList);

		// Managers
		SoundClipManager clipmgr = (SoundClipManager) getBean("soundClipManager");
		UserManager userManager = (UserManager) getBean("userManager");
		PurchaseManager purchaseMgr = (PurchaseManager) getBean("purchaseManager");

		User user = userManager.getUser(paypalForm.getCustomerId());
		// if no user found
		if (user == null) {
			log.error("no user found");
			return null;
		}

		Purchase order = new Purchase();
		// set search terms
		order.setId(new Long(paypalForm.getInvoice()));
		order.setCustomerId(user.getId());

		// retrieve orders that are pending
		order = purchaseMgr.getPendingPurchase(order);
		// if order null then end
		if (order == null) {
			log.error("order null");
			return null;
		}

		String valout = "0";

		if (payPalSend(request)) {

			valout = "1";
			response.setContentType("text/plain");
			response.setContentLength(1);
			PrintWriter prn = response.getWriter();
			prn.write(valout);
			prn.flush();
			prn.close();

			// update order
			BeanUtils.copyProperties(order, paypalForm);

			/** process info **/

			String customerName = user.getUsername();
			String zfilename = "BH-" + getDateStr();

			String destfile = clipmgr.getUsersDownloadDir() + Constants.FILE_SEP + customerName + Constants.FILE_SEP
					+ zfilename + ".zip";

			// create the ZIP
			Map<String, String> zipMap = getPPCartAsZipItems(zfilename, cartClips, clipmgr);

			File zfile = new java.io.File(destfile);
			zfile.getParentFile().mkdirs();
			clipmgr.zipem(destfile, zipMap);
			// PERSIST zip-info
			Zip zip = null;

			if (zfile.exists()) {
				zip = persistZip(order, zfile);
				// use ZIP (partial) path along with ACCESS-KEY to create a
				// download link.
				// provide user with download link
				String downUrl = "/get/" + URLEncoder.encode(customerName, "UTF-8") + "/" + zip.getAccessKey()
						+ Constants.FILE_SEP + zfile.getName();

				// save the URL
				order.setDownloadURL(downUrl);
				purchaseMgr.savePurchase(order);

				zip.setCustomerName(customerName);
				zip.setCustomerId(order.getCustomerId());
				zip.setDownloadURL(downUrl);
				ZipManager zman = (ZipManager) getBean("zipManager");
				// save ZIP
				zman.saveZip(zip);

				order.setZipId(zip.getId());
				// PERSIST purchase transaction
				purchaseMgr.savePurchase(order);
				user.addArchive(zip);
				user.addPurchase(order);
				user.getCartItems().clear();
				userManager.updateUser(user);

				ppsession.put("downUrl", RequestUtil.getAppURL(request) + downUrl);

				// email user receipt + download instructions
				emailReceipt(order, request);
				ActionMessages messages = new ActionMessages();

				// add complete messages
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("cart.purchase.complete"));
				ppsession.put("messages", messages);
			}

			Map<String, Comparable> fincart = new HashMap<String, Comparable>();
			double subTotal = cart.getTotal().doubleValue();
			double subTotal2 = SalesDiscount.getDiscountedAmount(subTotal);
			double savings = subTotal - subTotal2;
			fincart.put("subTotal", cart.getTotal());
			fincart.put("savings", new Double(savings));
			fincart.put("total", SalesDiscount.getDiscountedAmount(cart.getTotal()));
			fincart.put("subTotal", cart.getTotal());
			fincart.put("promoDiscount", cart.getPromoDiscount());
			fincart.put("promoCode", cart.getPromoCode());
			fincart.put("discountedAmount", cart.getDiscountedAmount());

			Cart carttmp = new Cart();
			carttmp.getList().addAll(cart.getList());

			cart.getList().clear();

			ppsession.put("carttmp", carttmp);
			ppsession.put("fincart", fincart);
			writeObject(ppsession, "/tmp/" + paypalForm.getInvoice() + ".2.jser");
		}
		return null;
	}

	private void systemError(HttpServletRequest request, Exception e) {
		ActionErrors errors = new ActionErrors();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.general"));
		while (e != null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.detail", e.getMessage()));
			e = (Exception) e.getCause();
		}
		errors.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("checkout.errors.contactadmin", getResources(request).getMessage("feedback.admin")));
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("checkout.errors.nocharge"));
		saveMessages(request, errors);
	}

	private boolean isValidHost(String addr) {
		return addr.trim().equals("66.211.170.66");
	}

	/**
	 * Sends a validate-notify command to Paypal to comfirm the given query is
	 * valid
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ActionForward ppdt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String transactionID = request.getParameter("tx");

		Map<String, String> params = new HashMap();
		params.put("cmd", "_notify-validate");
		params.put("tx", transactionID);
		params.put("at", SELLER_IDENTITY_TOKEN);
		params.put("submit", "PDT");
		String query = RequestUtil.createQueryStringFromMap(params, "&").toString();

		URL u = new URL(PAYPAL_VALIDATE);
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());

		// post values
		pw.println(query);
		pw.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		// slurp result from PP
		String status = in.readLine();
		Map respbody = new HashMap();
		while (in.readLine() != null) {
			String[] pts = in.readLine().split("=");
			respbody.put(pts[0], URLDecoder.decode(pts[1], "UTF-8"));
		}
		in.close();

		if (status.equals("SUCCESS")) {

			PaypalForm paypalForm = new PaypalForm();
			BeanUtils.copyProperties(paypalForm, respbody);

			String seam = "/tmp/" + paypalForm.getInvoice() + ".jser";

			Map ppsession = (Map) readObject(seam);

			Cart cart = (Cart) ppsession.get("cart");
			List<Object[]> cartClips = (LinkedList) ppsession.get("cartClips");

			List<SoundClipForm> cartList = new LinkedList();

			CartManager mgr = (CartManager) getBean("cartManager");
			SoundClipForm clipForm = null;
			for (Object[] oPair : cartClips) {

				Object[] paired = new Object[2];

				String clipId = (String) oPair[0];
				AudioFileForm af = (AudioFileForm) oPair[1];

				SoundClip clip = mgr.getCartSoundClip(clipId);

				if (clip.getClass().getName().toLowerCase().indexOf("trackpack") > -1) {

					clipForm = (TrackpackFormCart) ConvertUtil.convert(clip, TrackpackFormCart.class);
					((TrackpackFormCart) clipForm).setFileId(af.getId());
					ConvertUtil.convertNamedList(clipForm, "audioFormat", AudioFileForm.class);
					ConvertUtil.convertNamedList(clipForm, "genre", GenreForm.class);
				} else {
					clipForm = CartAction.convertLoop(clip, af.getId());
				}
				cartList.add(clipForm);
			}
			cart.setList(cartList);

			// Managers
			SoundClipManager clipmgr = (SoundClipManager) getBean("soundClipManager");
			UserManager userManager = (UserManager) getBean("userManager");
			PurchaseManager purchaseMgr = (PurchaseManager) getBean("purchaseManager");

			User user = userManager.getUser(paypalForm.getCustomerId());
			// if no user found
			if (user == null) {
				log.error("no user found");
				return null;
			}

			Purchase order = new Purchase();
			// set search terms
			order.setId(new Long(paypalForm.getInvoice()));
			order.setCustomerId(user.getId());

			// retrieve orders that are pending
			order = purchaseMgr.getPendingPurchase(order);
			// if order null then end
			if (order == null) {
				return null;
			}

			String valout = "0";

			// update order
			BeanUtils.copyProperties(order, paypalForm);

			/** process info **/

			String customerName = user.getUsername();
			String zfilename = "BH-" + getDateStr();

			String destfile = clipmgr.getUsersDownloadDir() + Constants.FILE_SEP + customerName + Constants.FILE_SEP
					+ zfilename + ".zip";

			// create the ZIP
			Map<String, String> zipMap = getPPCartAsZipItems(zfilename, cartClips, clipmgr);
			File zfile = new java.io.File(destfile);
			zfile.getParentFile().mkdirs();
			clipmgr.zipem(destfile, zipMap);
			// PERSIST zip-info
			Zip zip = null;

			if (zfile.exists()) {
				zip = persistZip(order, zfile);
				// use ZIP (partial) path along with ACCESS-KEY to create a
				// download link.
				// provide user with download link
				String downUrl = "/get/" + URLEncoder.encode(customerName, "UTF-8") + "/" + zip.getAccessKey()
						+ Constants.FILE_SEP + zfile.getName();

				// save the URL
				order.setDownloadURL(downUrl);
				purchaseMgr.savePurchase(order);

				zip.setCustomerName(customerName);
				zip.setCustomerId(order.getCustomerId());
				zip.setDownloadURL(downUrl);
				ZipManager zman = (ZipManager) getBean("zipManager");
				// save ZIP
				zman.saveZip(zip);

				order.setZipId(zip.getId());
				// PERSIST purchase transaction
				purchaseMgr.savePurchase(order);

				user.addArchive(zip);
				user.addPurchase(order);
				user.getCartItems().clear();
				userManager.updateUser(user);

				ppsession.put("downUrl", RequestUtil.getAppURL(request) + downUrl);

				// email user receipt + download instructions
				emailReceipt(order, request);
				ActionMessages messages = new ActionMessages();

				// add complete messages
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("cart.purchase.complete"));
				ppsession.put("messages", messages);
			}

			Map<String, Comparable> fincart = new HashMap<String, Comparable>();
			double subTotal = cart.getTotal().doubleValue();
			double subTotal2 = SalesDiscount.getDiscountedAmount(subTotal);
			double savings = subTotal - subTotal2;
			fincart.put("subTotal", cart.getTotal());
			fincart.put("savings", new Double(savings));
			fincart.put("total", SalesDiscount.getDiscountedAmount(cart.getTotal()));
			fincart.put("subTotal", cart.getTotal());
			fincart.put("promoDiscount", cart.getPromoDiscount());
			fincart.put("promoCode", cart.getPromoCode());
			fincart.put("discountedAmount", cart.getDiscountedAmount());

			Cart carttmp = new Cart();
			carttmp.getList().addAll(cart.getList());

			cart.getList().clear();

			ppsession.put("carttmp", carttmp);
			ppsession.put("fincart", fincart);
			writeObject(ppsession, seam);
			valout = "1";
			response.setContentType("text/plain");
			response.setContentLength(1);
			PrintWriter prn = response.getWriter();
			prn.write(valout);
			prn.flush();
			prn.close();
		}
		return null;
	}

	/**
	 * Sends a validate-notify command to Paypal to comfirm the given query is
	 * valid
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private boolean payPalSend(HttpServletRequest request) throws Exception {

		String query = RequestUtil.getQueryString(request);

		// Add 'command'
		query += "&cmd=_notify-validate";

		URL u = new URL("https://www.paypal.com/cgi-bin/webscr");
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());

		// post values
		pw.println(query);
		pw.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		// slurp result from PP
		String res = in.readLine();
		in.close();

		log.debug("Result: " + res);

		return res.equals("VERIFIED");
	}

	public ActionForward ppcomplete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession sess = request.getSession();

		// make sure the request is coming from PayPal
		File f = new File("/tmp/" + request.getParameter("invoice") + ".2.jser");

		if (f.exists()) {
			Map ppsession = null;
			try {
				ppsession = (Map) readObject(f.getAbsolutePath());

				if (ppsession == null) {
					sess.setAttribute("frompp", "1");
				} else {
					sess.setAttribute(Constants.USER_CART, (Cart) ppsession.get("cart"));
					sess.setAttribute("messages", ppsession.get("messages"));
					sess.setAttribute("downUrl", ppsession.get("downUrl"));
					sess.setAttribute("carttmp", ppsession.get("carttmp"));
					sess.setAttribute("fincart", ppsession.get("fincart"));
					// process billing info
					return mapping.findForward("ppsuccess");
				}
			} catch (Exception e) {
				log.error(e);
			}
		} else {
		}
		Cart cart = CartAction.getCart(sess);
		cart.getList().clear();

		sess.setAttribute("frompp", "1");
		return new ActionForward(mapping.findForward("userDownloads").getPath() + ";jsessionid=" + sess.getId(), true);
	}

	public Object readObject(String filepath) throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream inputStream = null;
		Object obj = null;
		try {
			File file = new File(filepath);
			// Construct the ObjectInputStream object
			inputStream = new ObjectInputStream(new FileInputStream(file));

			obj = inputStream.readObject();
			file.delete();

		} catch (EOFException ex) { // This exception will be caught when EOF is
									// reached
		} finally {
			// Close the ObjectInputStream
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * @param string
	 * @param cart
	 */
	public void writeObject(Object o, String filepath) {

		ObjectOutputStream outputStream = null;

		try {
			File tmp = new File(filepath);
			outputStream = new ObjectOutputStream(new FileOutputStream(tmp));
			outputStream.writeObject(o);

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// Close the ObjectOutputStream
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Called when purchase charge is being handled by PayPal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward paypal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("application/x-json charset=UTF-8");

		Date today = new Date();
		// prepare to save the order until PP-notify
		PurchaseManager purchaseMgr = (PurchaseManager) getBean("purchaseManager");
		// paypal checkout

		HttpSession session = request.getSession();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		// get User
		User user = (User) auth.getPrincipal();
		// get user cart
		Cart cart = CartAction.getCart(session);

		// create the order
		Purchase order = new Purchase();
		// set required fields
		order.setFirstName(user.getFirstName());
		order.setLastName(user.getLastName());
		order.setMiddleName(user.getMiddleName());
		order.setEmail(user.getEmail());
		order.setCustomerName(user.getUsername());
		order.setCustomerId(user.getId());
		order.setIpAddress(request.getRemoteAddr());
		order.setCreated(today);

		order.setExecutor((PaymentExecutor) purchaseMgr.getExecutor(new Long(Constants.PAYPAL_ID)));

		if (StringUtils.isNotBlank(cart.getPromoCode())) {
			order.setPromoCode(cart.getPromoCode());
			order.setPromoDiscount(cart.getDiscount());
		}

		// check if USER entered the code
		// also double check that we got a discount in case cart had code but no
		// value (unlikely)
		if (StringUtils.isNotBlank(order.getPromoCode())) {
			Promo promo = (Promo) ((PromoManager) getBean("promoManager")).getPromoByCode(order.getPromoCode());
			if (promo != null) {
				order.setPromoDiscount(promo.getDiscount());
			} else {
				order.setPromoCode(null);
				order.setPromoDiscount(null);
			}
		}

		order.setSubTotal(cart.getTotal());
		// NOTE: discount percent is divided by 100
		order.setDiscount(new Double(cart.getDiscount() / 100));
		order.setTotal(new Double(cart.getDiscountedAmount()));

		purchaseMgr.savePurchase(order);

		String[] filerefs = addPurchaseItems(order, cart.getList());

		SoundClipManager clipmgr = (SoundClipManager) getBean("soundClipManager");

		boolean[] valid = clipmgr.filesExist(filerefs);
		String str = "";
		int i = 0;
		for (boolean f : valid) {
			str += !f ? filerefs[i++] : "";
		}

		purchaseMgr.savePurchase(order);

		Map<String, String> retvals = new HashMap<String, String>();
		if (str.length() > 0) {
			retvals.put("message", str);
		} else {
			Map ppsessionMap = new HashMap();

			List cartClips = new LinkedList();

			for (SoundClipForm clipForm : cart.getList()) {

				Object[] paired = new Object[2];

				paired[0] = clipForm.getId();
				paired[1] = (clipForm instanceof TrackpackFormCart) ? ((TrackpackFormCart) clipForm).getAudioFile()
						: ((LoopFormCart) clipForm).getAudioFile(); // getAudioFile(clip
																	// ,
																	// fileId);
				cartClips.add(paired);
			}

			ppsessionMap.put("cartClips", cartClips);
			ppsessionMap.put("cart", cart);

			// set the order id as invoice number
			retvals.put("invoice", order.getId().toString());
			// send userId in custom field
			retvals.put("custom", user.getId().toString());

			writeObject(ppsessionMap, "/tmp/" + order.getId().toString() + ".jser");
		}

		PrintWriter prn = response.getWriter();
		JSONObject json = JSONObject.fromObject(retvals);

		prn.write(json.toString());
		prn.flush();
		prn.close();
		return null;
	}

	public void deleteObject(String filepath) {

		new File(filepath).delete();

	}
}
