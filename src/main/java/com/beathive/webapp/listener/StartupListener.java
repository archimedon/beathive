package com.beathive.webapp.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.ProviderManager;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.security.providers.rememberme.RememberMeAuthenticationProvider;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beathive.Constants;
import com.beathive.service.LookupManager;
import com.beathive.util.ConvertUtil;
import com.beathive.webapp.form.DescriptorForm;
import com.beathive.webapp.form.InstrumentForm;
import com.beathive.webapp.form.InstrumentgroupForm;
import com.beathive.webapp.helper.SalesDiscount;
import com.beathive.webapp.util.HashColumn;
import com.beathive.webapp.util.MenuHash;

/**
 * StartupListener class used to initialize settings and populate
 * application-wide drop-downs.
 * 
 * This listener is executed outside of OpenSessionInViewFilter, have to
 * explicitly initialize all loaded data on the Dao or service level to avoid
 * LazyInitializationException. Hibernate.initialize() works for this.
 */
public class StartupListener extends ContextLoaderListener implements ServletContextListener {

	private static final Log log = LogFactory.getLog(StartupListener.class);

	public static final String CHECKLIST_KEY = "checkLists";
	public static final String DROPDOWN_KEY = "dropDowns";

	public static final String[] dropdowns = { Constants.AVAILABLE_INSTRUMENTGROUPS, Constants.AVAILABLE_GENRES };// ,
																													// Constants.AVAILABLE_INSTRUMENTS};

	public static final String[] checklists = { Constants.AVAILABLE_TEMPOS, Constants.AVAILABLE_FORMATS,
			Constants.AVAILABLE_TIMESPAN, Constants.AVAILABLE_ORIGINS, Constants.AVAILABLE_PASSAGES,
			Constants.AVAILABLE_MOODS, Constants.AVAILABLE_FEELS, Constants.AVAILABLE_SONORITIES,
			Constants.AVAILABLE_ENERGIES, Constants.AVAILABLE_TIMESIGNATURES, Constants.AVAILABLE_SCALES,
			Constants.AVAILABLE_TEXTURES, Constants.AVAILABLE_KEYNOTES };

	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("initializing context...");
		}

		// call Spring's context ContextLoaderListener to initialize
		// all the context files specified in web.xml
		super.contextInitialized(event);

		ServletContext context = event.getServletContext();

		// Orion initializes Servlets before Listeners, have to check if the
		// config
		// object already exists
		Map config = (HashMap) context.getAttribute(Constants.CONFIG);

		if (config == null) {
			config = new HashMap();
		}

		if (context.getInitParameter(Constants.CSS_THEME) != null) {
			config.put(Constants.CSS_THEME, context.getInitParameter(Constants.CSS_THEME));
		}

		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

		boolean encryptPassword = false;
		try {
			ProviderManager provider = (ProviderManager) ctx.getBean("authenticationManager");
			for (Iterator it = provider.getProviders().iterator(); it.hasNext();) {
				AuthenticationProvider p = (AuthenticationProvider) it.next();
				if (p instanceof RememberMeAuthenticationProvider) {
					config.put("rememberMeEnabled", Boolean.TRUE);
				}
			}

			if (ctx.containsBean("passwordEncoder")) {
				encryptPassword = true;
				config.put(Constants.ENCRYPT_PASSWORD, Boolean.TRUE);
				String algorithm = "SHA";
				if (ctx.getBean("passwordEncoder") instanceof Md5PasswordEncoder) {
					log.error("got encoder: " + ctx.getBean("passwordEncoder").toString());
					algorithm = "MD5";
				}
				config.put(Constants.ENC_ALGORITHM, algorithm);
			}
		} catch (NoSuchBeanDefinitionException n) {
			// ignore. Only possible in testing
		}

		context.setAttribute(Constants.CONFIG, config);
		SalesDiscount discounter = null;

		/* put discount values in application view */
		if (context.getInitParameter(Constants.MEMBER_DISCOUNT_KEY) != null) {
			String mapstr = context.getInitParameter(Constants.MEMBER_DISCOUNT_KEY);
			Map discounts = new LinkedHashMap();
			// 15:10,30:12,50:15,100:18
			String[] steps = mapstr.split(",");
			for (String tmp : steps) {
				String[] nv = tmp.split(":");
				discounts.put(new Double(nv[0]), new Double(nv[1]));
			}
			discounter = new SalesDiscount(discounts);
		}

		if (discounter != null) {
			/* put discount values in application view */
			if (context.getInitParameter(Constants.NON_MEMBER_DISCOUNT_KEY) != null) {
				String mapstr = context.getInitParameter(Constants.NON_MEMBER_DISCOUNT_KEY);
				Map nonMemberDiscountMap = new LinkedHashMap();
				// 15:10,30:12,50:15,100:18
				String[] steps = mapstr.split(",");
				for (String tmp : steps) {
					String[] nv = tmp.split(":");
					nonMemberDiscountMap.put(new Double(nv[0]), new Double(nv[1]));
				}
				discounter.setNonMemberDiscountMap(nonMemberDiscountMap);
			}
			context.setAttribute(Constants.DISCOUNT_KEY, discounter);

			if (context.getInitParameter(Constants.MEMBER_DISCOUNT_COLORCODES) != null) {
				String mapstr = context.getInitParameter(Constants.MEMBER_DISCOUNT_COLORCODES);
				Map map = new LinkedHashMap();
				String[] steps = mapstr.split(",");
				for (String tmp : steps) {
					String[] nv = tmp.split(":");
					map.put(new Double(nv[0]), nv[1]);
				}
				context.setAttribute(Constants.MEMBER_DISCOUNT_COLORCODES, map);
				discounter.setMemberDiscountColorCodes(map);
			}

			if (context.getInitParameter(Constants.NON_MEMBER_DISCOUNT_COLORCODES) != null) {
				String mapstr = context.getInitParameter(Constants.NON_MEMBER_DISCOUNT_COLORCODES);
				Map map = new LinkedHashMap();
				String[] steps = mapstr.split(",");
				for (String tmp : steps) {
					String[] nv = tmp.split(":");
					map.put(new Double(nv[0]), nv[1]);
				}
				context.setAttribute(Constants.NON_MEMBER_DISCOUNT_COLORCODES, map);
				discounter.setNonMemberDiscountColorCodes(map);
			}

		}

		// output the retrieved values for the Init and Context Parameters
		if (log.isDebugEnabled()) {
			log.debug("Remember Me Enabled? " + config.get("rememberMeEnabled"));
			log.debug("Encrypt Passwords? " + encryptPassword);
			if (encryptPassword) {
				log.debug("Encryption Algorithm: " + config.get(Constants.ENC_ALGORITHM));
			}
			log.debug("Populating drop-downs...");
		}
		setupContext(context);
	}

	public static void setupContext(ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

		LookupManager lookupMgr = (LookupManager) ctx.getBean("lookupManager");

		// get list of possible roles
		context.setAttribute(Constants.AVAILABLE_ROLES, lookupMgr.getAllRoles());
		context.setAttribute(Constants.AVAILABLE_GENRES, lookupMgr.getAllGenres());
		context.setAttribute(Constants.AVAILABLE_COUNTRIES, lookupMgr.getAllCountries());
		context.setAttribute(Constants.AVAILABLE_CANADIAN_TERRITORIES, lookupMgr.getAllCanadaTerritories());
		context.setAttribute(Constants.AVAILABLE_STATES, lookupMgr.getAllStates());

		DescriptorForm tmpDesc = null;
		MenuHash chkHash = new MenuHash(checklists);
		MenuHash ddHash = new MenuHash(dropdowns);

		List descriptors = lookupMgr.getDescriptors();
		Map instrumentGroupMap = new LinkedHashMap();
		Map instrumentMap = new LinkedHashMap();
		Map genreMap = new LinkedHashMap();

		String disc = null;
		for (int j = 0; j < descriptors.size(); j++) {
			try {
				tmpDesc = (DescriptorForm) ConvertUtil.convert(descriptors.get(j));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			disc = tmpDesc.getClazz();
			try {
				if (ddHash.containsKey(disc)) {
					if (disc.equals("genre")) {
						genreMap.put(tmpDesc.getId(), tmpDesc);
					} else if (disc.equals("instrumentgroup")) {
						ConvertUtil.convertLists(tmpDesc);
						instrumentGroupMap.put(tmpDesc.getId(), tmpDesc);
						List<InstrumentForm> insts = ((InstrumentgroupForm) tmpDesc).getInstruments();
						for (InstrumentForm ist : insts) {
							instrumentMap.put(ist.getId(), ist);
						}
					}
					ddHash.add(disc, tmpDesc);
				} else if (chkHash.containsKey(disc)) {
					chkHash.add(disc, tmpDesc);
				}
			} catch (Exception e) {
				log.debug(e);
			}
		}
		context.setAttribute("genreMap", genreMap);
		context.setAttribute("instrumentMap", instrumentMap);
		context.setAttribute("instrumentGroupMap", instrumentGroupMap);

		Map hash = new HashColumn();
		hash.putAll(chkHash);
		hash.putAll(ddHash);
		context.setAttribute(CHECKLIST_KEY, chkHash);
		context.setAttribute(DROPDOWN_KEY, ddHash);
		context.setAttribute(Constants.DESCRIPTOR_KEY, hash);
		context.setAttribute(Constants.REDIR_SESSIONS, new HashMap());

		if (log.isDebugEnabled()) {
			log.debug("Drop-down initialization complete [OK]");
		}
	}
}
