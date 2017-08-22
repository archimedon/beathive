package com.beathive.webapp.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;
import com.beathive.model.AudioFile;
import com.beathive.model.Genre;
import com.beathive.model.Instrument;
import com.beathive.model.Loop;
import com.beathive.model.Price;
import com.beathive.model.SoundClip;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
import com.beathive.service.LookupManager;
import com.beathive.service.Manager;
import com.beathive.service.SoundClipManager;
import com.beathive.service.StoreManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.AudioFileForm;
import com.beathive.webapp.form.ClipDetailsForm;
import com.beathive.webapp.form.DescriptorForm;
import com.beathive.webapp.form.GenreForm;
import com.beathive.webapp.form.LoopForm;
import com.beathive.webapp.form.LoopFormSearch;
import com.beathive.webapp.form.PreferenceForm;
import com.beathive.webapp.form.SoundClipForm;
import com.beathive.webapp.form.StoreForm;
import com.beathive.webapp.form.TrackpackForm;
import com.beathive.webapp.helper.PaginatedListFactory;

import net.sf.json.JSONObject;

/**
 * Action class to handle CRUD on a Loop object
 *
 * @struts.action name="loopSearchForm" path="/component/loops" scope="request"
 *                validate="false" parameter="method" input="mainMenu"
 * @struts.action name="loopSearchForm" path="/loops" scope="request"
 *                validate="false" parameter="method" input="mainMenu"
 * @struts.action name="loopForm" path="/producer/editLoop" scope="request"
 *                validate="false" parameter="method" input="list"
 * @struts.action name="loopForm" path="/producer/saveLoop" scope="request"
 *                validate="true" parameter="method" input="edit"
 * 
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit" path="/WEB-INF/pages/loopForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/loopList.jsp"
 * @struts.action-forward name="search" path="/searchLoops.html"
 * @struts.action-forward name="audioView"
 *                        path="/WEB-INF/pages/unboundLoopList.jsp"
 */
public final class LoopAction extends BaseAction {
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("search");
	}

	public ActionForward viewTrackpack(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String clipId = request.getParameter("clipId");
		if (StringUtils.isNotBlank(clipId)) {
			ExtendedPaginatedList componentsPager = getPager(request);

			SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");

			List<Loop> innerloops = mgr.getTrackpackComponents(clipId, componentsPager);
			LoopForm loopform = null;
			if (innerloops != null && !innerloops.isEmpty()) {

				List<LoopForm> outl = new LinkedList();
				try {
					for (Loop loop : innerloops) {
						loopform = (LoopForm) ConvertUtil.convert(loop, LoopForm.class);
						// log.debug("loopform owned: " +
						// loopform.getOwnedByViewer());
						ConvertUtil.convertNamedList(loopform, "audioFormat", AudioFileForm.class);
						outl.add(loopform);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				componentsPager.setList(outl);
				request.setAttribute("component" + Constants.LOOP_LIST, componentsPager);
			}

			// MUST GET AND SET TRACKPACK AFTER GETTING LOOPS
			Trackpack pack = (Trackpack) mgr.getSoundClip(clipId);

			TrackpackForm tpform = (TrackpackForm) ConvertUtil.convert(pack, TrackpackForm.class);
			ConvertUtil.convertNamedList(tpform, "audioFormat", AudioFileForm.class);
			request.setAttribute("trackpack", tpform);
		}
		return mapping.findForward("success");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (request.getRequestURI().indexOf("viewTrackpack") > -1) {
			return viewTrackpack(mapping, form, request, response);
		}
		String clipId = request.getParameter("clipId");
		if (StringUtils.isNotBlank(clipId)) {
			ExtendedPaginatedList componentsPager = getPager(request);

			SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");
			List<Loop> innerloops = mgr.getTrackpackComponents(clipId, componentsPager);

			if (innerloops != null && !innerloops.isEmpty()) {
				List<LoopForm> outl = new LinkedList();
				try {
					for (Loop loop : innerloops) {
						// log.debug("loop owned: " + loop.getOwnedByViewer());
						LoopForm loopform = (LoopForm) ConvertUtil.convert(loop, LoopForm.class);
						ConvertUtil.convertNamedList(loopform, "audioFormat", AudioFileForm.class);
						outl.add(loopform);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				componentsPager.setList(outl);
				request.setAttribute(Constants.LOOP_LIST, componentsPager);
			}
		}
		return mapping.findForward("success");
	}

	/**
	 * A special pager used for inner table
	 * 
	 * @param request
	 * @return
	 */
	private ExtendedPaginatedList getPager(HttpServletRequest request) {
		PaginatedListFactory paginatedListFactory = (PaginatedListFactory) getBean("paginatedListFactory");
		ExtendedPaginatedList paginatedList = paginatedListFactory.getNamedPaginatedList(request, "componentsPager",
				true);
		return paginatedList;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		LoopForm loopForm = (LoopForm) form;
		HashMap hm = new HashMap();
		StoreManager stmgr = (StoreManager) getBean("storeManager");

		List<Store> shops = stmgr.getUserStores(request.getRemoteUser());
		Long actualId = null;
		List<StoreForm> shopforms = null;
		if (StringUtils.isBlank(loopForm.getStoreId())) {
			hm.put("msg", getResources(request).getMessage("storeId.required"));
			JSONObject json = JSONObject.fromObject(hm);
			response.setContentType("application/x-json");
			// Plop it in the header so prototype can grab it.
			request.setAttribute("data", json.toString());
			return null;
		}
		Long storeId = new Long(loopForm.getStoreId());

		if (shops == null) {
			hm.put("msg", getResources(request).getMessage("soundClip.access.denied"));
			JSONObject json = JSONObject.fromObject(hm);
			response.setContentType("application/x-json");

			// Plop it in the header so prototype can grab it.
			request.setAttribute("data", json.toString());
			return null;
		}
		if (shops.size() == 1) {
			actualId = shops.get(0).getId();
		} else {
			for (Store shp : shops) {
				if (shp.getId().equals(storeId)) {
					actualId = storeId;
					break;
				}
			}
		}

		hm.put("id", new Long(loopForm.getId()));
		hm.put("storeId", actualId);
		hm.put("deep", new Boolean(StringUtils.isNotBlank(request.getParameter("deep"))));

		// Exceptions are caught by ActionExceptionHandler
		SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");
		try {

			mgr.removeSoundClip(hm);
			hm.put("msg", getResources(request).getMessage("soundClip.deleted"));
		} catch (Exception e) {

			hm.put("error", e.getCause());
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("soundClip.deleted"));

		// each key from our hash map becomes a key in our JSON object
		JSONObject json = JSONObject.fromObject(hm);
		response.setContentType("application/x-json");

		// Plop it in the header so prototype can grab it.
		request.setAttribute("data", json.toString());

		return mapping.findForward("success");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'edit' method");
		}

		LoopForm loopForm = (LoopForm) form;
		ActionForward af = mapping.findForward("edit");

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		if (loopForm.getId() != null) {
			SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");
			SoundClip soundClip = mgr.getSoundClip(loopForm.getId());
			boolean istp = soundClip.getClass().equals(Trackpack.class);
			SoundClipForm clipForm = null;

			if (istp) {
				List iloops = new LinkedList(((Trackpack) soundClip).getLoops());
				convertLoops(iloops);
				clipForm = (SoundClipForm) ConvertUtil.convert(soundClip, TrackpackForm.class);
				ConvertUtil.convertNamedList(clipForm, "audioFormat", AudioFileForm.class);
				((TrackpackForm) clipForm).setLoops(iloops);
				af = mapping.findForward("editrackpack");
			} else {
				clipForm = (SoundClipForm) ConvertUtil.convert(soundClip, LoopForm.class);
				ConvertUtil.convertNamedList(clipForm, "audioFormat", AudioFileForm.class);
			}
			updateFormBean(mapping, request, clipForm);
		}
		return af;
	}

	/**
	 * @param clips
	 */
	public static List convertLoops(List clips) {
		Loop loop = null;
		try {
			for (int i = 0; i < clips.size(); i++) {
				loop = (Loop) clips.get(i);
				LoopForm loopform = (LoopForm) ConvertUtil.convert(loop, LoopForm.class);
				ConvertUtil.convertNamedList(loopform, "genre", GenreForm.class);
				ConvertUtil.convertNamedList(loopform, "audioFormat", AudioFileForm.class);
				clips.set(i, loopform);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clips;
	}

	public ActionForward details(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClipDetailsForm details = (ClipDetailsForm) form;

		String loopId = details.getLoopId();
		// log.debug(BeanUtils.describe(details));
		if (StringUtils.isNotBlank(loopId) && !details.getAttribs().isEmpty()) {
			Map data = details.getAttribs();
			SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");
			Manager baseMgr = (Manager) getBean("manager");

			SoundClip clip = mgr.getSoundClip(loopId);
			// log.debug("loop name: " + clip.getName());
			Set<Map.Entry> entries = data.entrySet();

			for (Map.Entry<String, String> entry : entries) {

				String key = entry.getKey();
				String val = entry.getValue();
				// log.debug("key: " + key +" value: " + val);
				if (key.equals("genre")) {
					String[] gids;
					if (val.indexOf(",") > -1) {

						gids = val.split(",");
					} else {
						gids = new String[] { entry.getValue() };
					}
					setClipGenres(baseMgr, clip, gids);
				} else if (key.equals("instrument.id")) {
					setInstruments(baseMgr, (Loop) clip, val);
					// PropertyUtils.setNestedProperty(clip, "instrument.id",
					// new Long(val));
				} else if (key.indexOf("price") > -1) {
					Price priceObj = new Price();
					priceObj.setAmount(new Double(val));
					PropertyUtils.setProperty(clip, "price", priceObj);
				} else {
					BeanUtils.copyProperty(clip, key, entry.getValue());
				}
			}

			clip.setModified(new Date());
			// for trackpacks ...
			if (clip.getClass().equals(Trackpack.class)) {
				Trackpack tp = (Trackpack) clip;
				// Set the status of component loops based on current state
				if (!tp.isReady()) {
					Integer clipStatusId = new Integer(2);
					List<Loop> loops = tp.getLoops();
					for (Loop l : loops) {
						l.setStatusId(clipStatusId);
					}
				}
			}

			mgr.saveSoundClip(clip);
			data.put("ready", new Boolean(clip.isReady()));
			request.setAttribute("loopdata", data);

			LoopForm lf = new LoopForm();
			BeanUtils.copyProperties(lf, clip);
			ConvertUtil.convertNamedList(lf, "audioFormat", AudioFileForm.class);

			// ConvertUtil.convertLists(lf);
			request.setAttribute("clip", lf);
			return mapping.findForward("jsdata");
		}
		return null;
	}

	/**
	 * @param clip
	 * @param gids
	 */
	private void setClipGenres(Manager baseMgr, SoundClip clip, String[] gids) {
		List genres = new LinkedList();
		for (int i = 0; i < gids.length; i++) {
			genres.add((Genre) baseMgr.getObject(Genre.class, new Long(gids[i])));
			// clip.addGenre((Genre)baseMgr.getObject(Genre.class, new
			// Long(gids[i])));
		}
		// log.debug(genres);
		clip.setGenre(genres);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'save' method");
		}

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		LoopForm loopForm = (LoopForm) form;

		// Note "setDescriptors()" is "private' in Loop making this is
		// superfluous
		loopForm.setDescriptors(null);

		boolean isNew = ("".equals(loopForm.getId()) || loopForm.getId() == null);

		SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");

		Loop loop = (Loop) convert(loopForm);
		ConvertUtil.convertLists(loop);
		Date date = new Date();
		loop.setModified(date);
		loop.setCreated(date);

		// add genres
		checkGenres(request, loop);

		// update clipFormats (file refs)
		checkAudioFiles(request, loop);

		mgr.saveSoundClip(loop);

		// add success messages
		if (isNew) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("soundClip.added"));

			// save messages in session to survive a redirect
			saveMessages(request.getSession(), messages);

			return mapping.findForward("search");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("soundClip.updated"));
			saveMessages(request, messages);

			return mapping.findForward("edit");
		}
	}

	private void setInstruments(Manager baseMgr, Loop clip, String iid) {
		Instrument instrument = (Instrument) baseMgr.getObject(Instrument.class, new Long(iid));
		if (instrument != null) {
			clip.setInstrumentId(instrument.getId());
			log.error("instr: " + instrument);
			clip.setInstrument(instrument);
		} else {
			log.error("no instr");
		}
	}

	private void checkGenres(HttpServletRequest request, SoundClip loop) {
		String[] genreIds = request.getParameterValues("clipGenres");
		Manager baseMgr = (Manager) getBean("manager");
		if (genreIds != null) {
			setClipGenres(baseMgr, loop, genreIds);
		}
	}

	/**
	 * @param request
	 * @param loop
	 */
	private void checkAudioFiles(HttpServletRequest request, SoundClip loop) {
		String[] clipAudioFiles = request.getParameterValues("clipAudioFiles");
		if (clipAudioFiles != null) {
			Manager baseMgr = (Manager) getBean("manager");
			for (int i = 0; i < clipAudioFiles.length; i++) {
				loop.addAudioFile((AudioFile) baseMgr.getObject(AudioFile.class, new Long(clipAudioFiles[i])));
			}
		}
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'search' method");
		}

		LoopFormSearch loopForm = (LoopFormSearch) form;

		if (StringUtils.isBlank(request.getParameter("instrumentForm.groupId"))
				&& StringUtils.isBlank(request.getParameter("instrumentForm.groupId"))
				&& StringUtils.isBlank(loopForm.getKeyword()) && StringUtils.isBlank(loopForm.getTrackpackId())
				&& StringUtils.isBlank(loopForm.getGenreId()) && StringUtils.isBlank(request.getParameter("storeId"))) {

			PreferenceForm prefs = (PreferenceForm) request.getSession().getAttribute(Constants.PREFERENCE_KEY);

			loopForm.setPreffrm(prefs.getFormat());
			loopForm.setAFavorite(prefs.getHideFav());
			loopForm.setOwnedByViewer(prefs.getHideOwned());
			loopForm.setSort(prefs.getSort());
			return mapping.findForward("search");
		}

		String storeId = null;
		if (request.getRequestURI().indexOf("/shop/") > -1) {
			StoreManager stmgr = (StoreManager) getBean("storeManager");
			Store store = null;
			if (StringUtils.isNotBlank(request.getParameter("storeName"))) {
				log.debug("by name store");
				store = stmgr.getStoreByName(request.getParameter("storeName"));
				storeId = store.getId().toString();
				log.debug("by name store: " + store);
			} else {
				storeId = request.getParameter("storeId");
				store = stmgr.getStore(storeId);
			}
			StoreForm strf = (StoreForm) convert(store);
			request.setAttribute("storeForm", strf);
		}

		putMeta(request);

		String rtype = StringUtils.isNotBlank(request.getParameter("type")) ? request.getParameter("type")
				: "trackpack";

		PaginatedListFactory paginatedListFactory = (PaginatedListFactory) getBean("paginatedListFactory");

		ExtendedPaginatedList paginatedList = paginatedListFactory.getNamedPaginatedList(request,
				"search" + rtype + "Pager", true);

		Loop soundClip = new Loop();
		BeanUtils.copyProperties(soundClip, loopForm);

		if (StringUtils.isNotBlank(storeId)) {
			soundClip.setStoreId(new Long(storeId));
		}
		setPubDate(soundClip, request.getParameter("timespan"));

		if (StringUtils.isNotBlank(loopForm.getFormatId())) {
			soundClip.addAudioFile(new AudioFile(loopForm.getFormatId()));
		}

		if (StringUtils.isNotBlank(loopForm.getGenreId())) {
			log.debug("genreid: " + loopForm.getGenreId());
			soundClip.addGenre(new Genre(new Long(loopForm.getGenreId())));
		}

		List<SoundClip> results = null;
		String fwrd = "trackpackList";

		if (StringUtils.equalsIgnoreCase(rtype, "trackpack")) {
			results = ((SoundClipManager) getBean("soundClipManager")).getTrackpacks(soundClip, paginatedList);
		} else {
			fwrd = "loopList";
			results = ((SoundClipManager) getBean("soundClipManager")).getPaginatedLoops(soundClip, paginatedList);
		}

		List<String> buf = new LinkedList();
		List<SoundClipForm> outp = new LinkedList();
		SoundClipForm sc = null;
		Set<GenreForm> genremap = new LinkedHashSet();
		if (results != null && !results.isEmpty()) {
			for (SoundClip mtmp : results) {
				buf.add(mtmp.getName() + " id: " + mtmp.getId() + " items: " + mtmp.getUserItems() + "\n");
				if (mtmp.getClass().equals(Trackpack.class)) {
					sc = (SoundClipForm) ConvertUtil.convert(mtmp, TrackpackForm.class);
				} else {
					sc = (SoundClipForm) ConvertUtil.convert(mtmp, LoopForm.class);
				}
				ConvertUtil.convertNamedList(sc, "genre", GenreForm.class);
				ConvertUtil.convertNamedList(sc, "audioFormat", AudioFileForm.class);
				outp.add(sc);
			}
		}
		paginatedList.setList(outp);
		request.setAttribute(Constants.LOOP_LIST, paginatedList);

		return mapping.findForward(fwrd);
	}

	/**
	 * @param request
	 */
	private void putMeta(HttpServletRequest request) {

		ServletContext application = super.getServlet().getServletContext();
		java.util.Map genreMap = (Map) application.getAttribute("genreMap");
		java.util.Map instrumentGroupMap = (Map) application.getAttribute("instrumentGroupMap");
		java.util.Map instrumentMap = (Map) application.getAttribute("instrumentMap");

		String[] dnames = { "bpm", "origin", "feel", "passage", "timespan" };

		int flag = -1;
		Map meta = new java.util.LinkedHashMap();
		List l = new java.util.LinkedList();
		MessageResources mr = getResources(request);

		String tmp = null;
		if ((tmp = request.getParameter("genreId")) != null) {
			l.add(mr.getMessage(((DescriptorForm) genreMap.get(tmp)).getLabelKey()));
		}

		if ((tmp = request.getParameter("instrumentForm.groupId")) != null) {
			tmp = mr.getMessage(((DescriptorForm) instrumentGroupMap.get(tmp)).getLabelKey());
			l.add(tmp);
			flag = l.size() - 1;
		}

		if ((tmp = request.getParameter("instrumentForm.id")) != null) {
			tmp = mr.getMessage(
					((DescriptorForm) instrumentMap.get(request.getParameter("instrumentForm.id"))).getLabelKey());
			if (flag > -1) {
				tmp = l.get(flag) + " - " + tmp;
				l.set(flag, tmp);
			} else {
				l.add(tmp);
			}
		}

		for (String nm : dnames) {
			if (StringUtils.isNotBlank(tmp = request.getParameter(nm))) {
				if (nm.equals("timespan")) {
					if (tmp.equals("-3 MONTH")) {
						tmp = mr.getMessage("label.timespan.previous_3_month");
					}
					if (tmp.equals("-1 MONTH")) {
						tmp = mr.getMessage("label.timespan.previous_1_month");
					}
					if (tmp.equals("-7 DAY")) {
						tmp = mr.getMessage("label.timespan.previous_1_week");
					}
				} else if (nm.equals("bpm")) {
					if (tmp.equals("130")) {
						tmp = mr.getMessage("label.tempo.fast");
					}
					if (tmp.equals("105")) {
						tmp = mr.getMessage("label.tempo.medium");
					}
					if (tmp.equals("80")) {
						tmp = mr.getMessage("label.tempo.slow");
					}
				} else {
					tmp = mr.getMessage(tmp);
				}
				meta.put(nm, tmp);
			}
		}
		Map m = new HashMap();
		m.put("requiredCriteria", l);
		m.put("advancedCriteria", meta);
		request.setAttribute("searchmeta", m);
	}

	/**
	 * @param soundClip
	 * @param parameter
	 */
	private void setPubDate(SoundClip soundClip, String timespan) {
		if (StringUtils.isNotBlank(timespan)) {
			String[] splt = timespan.split("\\s+");
			int amt = Integer.parseInt(splt[0].trim());
			int field = Calendar.MONTH;
			if (splt[1].equalsIgnoreCase("DAY")) {
				field = Calendar.DATE;
			} else if (splt[1].equalsIgnoreCase("MONTH")) {
				field = Calendar.MONTH;
			} else if (splt[1].equalsIgnoreCase("YEAR")) {
				field = Calendar.YEAR;
			}
			Calendar cal = Calendar.getInstance();
			cal.roll(field, amt);
			soundClip.setModified(cal.getTime());
		} else {
			soundClip.setModified(null);
		}
	}

	private void updatePrefs(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		PreferenceForm prefs = (PreferenceForm) sess.getAttribute(Constants.PREFERENCE_KEY);
		if (StringUtils.isNotBlank(request.getParameter("preffrm"))) {
			prefs.setFormat(request.getParameter("preffrm"));
		}
		prefs.setHideFav(StringUtils.isNotBlank(request.getParameter("AFavorite")));
		prefs.setHideOwned(StringUtils.isNotBlank(request.getParameter("ownedByViewer")));
		if (StringUtils.isNotBlank(request.getParameter("sort"))) {
			prefs.setSort(request.getParameter("sort"));
		}
		if (StringUtils.isNotBlank(request.getParameter("locale"))) {
			prefs.setLocale(request.getParameter("locale"));
		}
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return search(mapping, form, request, response);
	}

	private QueryMeta getMeta(HttpServletRequest request, String queryType) {
		String rtype = request.getParameter("type");

		HttpSession sess = request.getSession();
		String key = queryType + rtype + "QueryMeta";
		QueryMeta queryMeta = (QueryMeta) sess.getAttribute(key);

		if (queryMeta == null) {
			queryMeta = new QueryMetaImpl();

			// stored in session so user won't have to
			// select pagesize again should they return to list
			sess.setAttribute(key, queryMeta);
		}

		String pageNumStr = request.getParameter("page"); // storeForm.getPage();
		String size = request.getParameter("size");
		if (StringUtils.isNotBlank(pageNumStr)) {
			queryMeta.setCurrIndex(Integer.parseInt(pageNumStr));
		}
		if (StringUtils.isNotBlank(size)) {
			queryMeta.setFetchSize(Integer.parseInt(size));
		}

		return queryMeta;
	}

	/**
	 * @param request
	 * @param loop
	 */
	public ActionForward listUnbound(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storeId = request.getParameter("storeId");
		if (StringUtils.isNotBlank(storeId)) {
			LookupManager lookupMgr = (LookupManager) getBean("lookupManager");
			List files = lookupMgr.getProducerUnboundAudio(storeId);
			if (files != null && (!files.isEmpty())) {
				files = (List) ConvertUtil.convertList(files, AudioFileForm.class);
				request.getSession().setAttribute("unboundAudioFiles", files);
			} else {
				request.getSession().removeAttribute("unboundAudioFiles");
			}
		}
		return mapping.findForward("audioView");
	}

	public ActionForward listClipAudio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String clipId = request.getParameter("clipId");
		if (StringUtils.isNotBlank(clipId)) {
			LookupManager lookupMgr = (LookupManager) getBean("lookupManager");
			List files = lookupMgr.getClipAudio(clipId);
			if (files != null && (!files.isEmpty())) {
				files = (List) ConvertUtil.convertList(files, AudioFileForm.class);
				request.getSession().setAttribute("clipAudioFiles", files);
			} else {
				request.getSession().removeAttribute("clipAudioFiles");
			}
		}
		return mapping.findForward("audioView");
	}

	public ActionForward count(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'search' method");
		}

		LoopFormSearch loopForm = (LoopFormSearch) form;

		String rtype = StringUtils.isNotBlank(request.getParameter("type")) ? request.getParameter("type")
				: "trackpack";

		PaginatedListFactory paginatedListFactory = (PaginatedListFactory) getBean("paginatedListFactory");

		ExtendedPaginatedList paginatedList = paginatedListFactory.getNamedPaginatedList(request,
				"search" + rtype + "Pager", true);

		Loop soundClip = new Loop();
		BeanUtils.copyProperties(soundClip, loopForm);

		setPubDate(soundClip, request.getParameter("timespan"));

		String formatId = loopForm.getFormatId();
		if (StringUtils.isNotBlank(formatId)) {
			soundClip.addAudioFile(new AudioFile(formatId));
		}

		String genreid = loopForm.getGenreId();
		if (StringUtils.isNotBlank(genreid)) {
			soundClip.addGenre(new Genre(new Long(genreid)));
		}

		List<SoundClip> results = null;
		String fwrd = "trackpackList";
		SoundClipManager mgr = (SoundClipManager) getBean("soundClipManager");
		Integer[] count = mgr.getCounts(soundClip, paginatedList);

		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter prn = response.getWriter();
		prn.write("{'numLoops_trkr':'" + count[0] + "','numTrackpacks_trkr':'" + count[1] + "'}");

		// update prefs
		updatePrefs(request);

		return null;
	}

}
