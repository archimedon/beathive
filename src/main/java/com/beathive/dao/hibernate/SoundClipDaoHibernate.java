package com.beathive.dao.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.beathive.Constants;
import com.beathive.ExtendedPaginatedList;
import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;
import com.beathive.model.Genre;

import com.beathive.model.Instrument;
import com.beathive.model.Loop;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.ScrollMode;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Example.PropertySelector;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.beathive.dao.SoundClipDao;
import com.beathive.dao.helper.SimpleOrderCriterion;
import com.beathive.model.AudioFile;
import com.beathive.model.BaseObject;
import com.beathive.model.Price;
import com.beathive.model.SoundClip;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
import com.beathive.model.User;
import com.beathive.model.UserClipScore;
import com.beathive.model.Zip;

import org.displaytag.properties.SortOrderEnum;

/**
 * @author rdennison
 */
public class SoundClipDaoHibernate extends BaseDaoHibernate implements SoundClipDao {
	
	/* (non-Javadoc)
     * @see com.beathive.dao.SoundClipDao#getSoundClip(Long)
     */
    public SoundClip getSoundClip(Long clipId) {
        User viewer = new User();
		return getSoundClip(clipId, viewer);
    }
    
	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getSoundClip(Long , User)
	 */    
    public SoundClip getSoundClip(Long clipId , User viewer) {
    	Long viewerId = getViewerId(viewer);
    	Session session = getSession();
		session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
		session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
		session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
		HibernateTemplate ht = getHibernateTemplate();
    	SoundClip clip = (SoundClip) ht.get(SoundClip.class, clipId);
    	if (clip == null) {
    		log.warn("uh oh, clip '" + clipId + "' not found...");
    		throw new ObjectRetrievalFailureException(SoundClip.class, clipId);
    	}
    	return clip;
    }
    
    public String getClipName(Long clipId){
    	return (String) getSession().createQuery("select name from SoundClip where id=?").setParameter(1, clipId).uniqueResult();
    }
    
	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#saveSoundClip(com.beathive.model.SoundClip)
	 */
	public void saveSoundClip(SoundClip datobj) {
		getHibernateTemplate().save(datobj);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getSoundClips(com.beathive.model.SoundClip)
	 */
	public List<SoundClip> getSoundClips(SoundClip clip) {
		List<SoundClip> clips = null;
		if (clip != null){
			clips = getSoundClips(clip, new QueryMetaImpl());
		}
		return clips;
	}

	public List<SoundClip> getPaginatedTrackpacks(Loop aClip, ExtendedPaginatedList paginatedList) {
		int fetchSize = paginatedList.getObjectsPerPage();
		int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
		SortOrderEnum sortDirection = paginatedList.getSortDirection();
		boolean dir = sortDirection.equals(SortOrderEnum.ASCENDING);
		
		Session session = getSession();
		Criteria loopCrit = session.createCriteria(Loop.class);
		List<Number> userFavorites = null;
		List<Number> userItems = null;
		Long viewerId = paginatedList.getViewerId();
		if(viewerId.intValue() != -1){
			userFavorites = getUserFavorites(viewerId);
			userItems = getUserItems(viewerId);
		}
		// enable filters
		session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
		session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
		session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
		
		applyUserCriteria(aClip, loopCrit, userItems, userFavorites);
		
		doBase(aClip,loopCrit,paginatedList, session);
		activeParent(loopCrit); 
		loopCrit.add(Restrictions.eq("searchable", Boolean.TRUE));
		loopCrit.setProjection(	Projections.projectionList().add( Projections.countDistinct("id"))	);
		Integer lcnt = new Integer((Integer)loopCrit.list().iterator().next());
		paginatedList.setRelTotal(lcnt.intValue());
		
		loopCrit = session.createCriteria(Loop.class);

		
		applyUserCriteria(aClip, loopCrit, userItems, userFavorites);
		loopCrit.add(Restrictions.isNotNull("trackpackId"));
		doBase(aClip,loopCrit,paginatedList, session);

		// inner join trackpack, making sure they are ready
		loopCrit.createAlias("trackpack", "tp")
		.add(Restrictions.eq("tp.ready", Boolean.TRUE))
		.add(Restrictions.isNull("tp.statusId")	);

		loopCrit.setProjection(Projections.distinct(Property.forName("trackpackId")))
		.addOrder(new SimpleOrderCriterion("tp."+paginatedList.getSortCriterion() , dir));
		
		Set packIds = new HashSet(loopCrit.list());

		if (packIds.isEmpty()){	return new LinkedList(); 	}
		Long[] tpIdz = new Long[packIds.size()];
		packIds.toArray(tpIdz);
		
		List tps = session.createCriteria(Trackpack.class)
		.setFetchMode("genre",FetchMode.JOIN)
		.setFetchMode("audioFormats",FetchMode.JOIN)
		.setFetchMode("instrument",FetchMode.JOIN)
		.add(Restrictions.eq("ready", Boolean.TRUE))
		.add(Restrictions.isNull("statusId")	)
		.add(Restrictions.in("id",tpIdz))
		.addOrder(new SimpleOrderCriterion(paginatedList.getSortCriterion() , dir))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.list();
		
		
		int num = tps.size();
		paginatedList.setFullListSize(num);
		
		return tps.subList(firstIndex, Math.min((firstIndex + fetchSize) , num));
	}
	
	
    /**
     * @see com.beathive.dao.StoreDao#getStores(com.beathive.model.Store)
     */
    public List findStores(final Loop aClip, ExtendedPaginatedList paginatedList) {
		int fetchSize = paginatedList.getObjectsPerPage();
		int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
		Long viewerId = paginatedList.getViewerId();
		Long storeId = aClip.getStoreId();
		
		Session session = getSession();
		// get search for tp count
		Criteria loopCrit = session.createCriteria(Loop.class);

		// these are needed for "ready" to determine state
		loopCrit.setFetchMode("genre",FetchMode.JOIN);
		queryGenre(loopCrit, aClip)

		// filter out "deleted"
		.add(Restrictions.isNull("statusId"))
		.add(Restrictions.eq("ready", Boolean.TRUE))
		
		.createAlias("trackpack", "tp", CriteriaSpecification.LEFT_JOIN)
		.add(Restrictions.isNull("tp.statusId"))
		.add(Restrictions.eq("tp.ready" , Boolean.TRUE))
		.setProjection(
			Projections.projectionList()
			.add(Property.forName("storeId"))
			.add(Projections.count("id"))
			.add(Projections.groupProperty("storeId"))
		);

		List<Object[]> res = loopCrit.list();

		
		SortOrderEnum sortDirection = paginatedList.getSortDirection();

		
		Criteria shopCrit = session.createCriteria(Store.class);
		List t = new LinkedList();

		if (res==null || res.isEmpty()){
			paginatedList.setFullListSize(0);
			paginatedList.setList(t);
			return t;
		}else{
			List<Long> idz = new LinkedList();
			
			for( int y = 0 ; y < res.size(); y++){
				if (((Integer)(res.get(y))[1]).intValue() > Constants.MIN_LOOPS_SHOPDIR ){
					idz.add((Long)(res.get(y))[0]);
				}
			}
			int numshops = idz.size();
			if (numshops>0){
				Long[] shopIdz = new Long[numshops];
				idz.toArray(shopIdz);
				shopCrit.add(Restrictions.in("id",shopIdz));
				t = shopCrit.addOrder(new SimpleOrderCriterion("average" , false))
				.setFirstResult(firstIndex)
				.setFetchSize(fetchSize)
				.setMaxResults(fetchSize)
				.list();
			}
			paginatedList.setFullListSize(numshops);
		}
		
		paginatedList.setList(t);

		return t;
	}

	private int getLoopCount(Session session , Loop aClip, Collection userItems, Collection userFavorites){
		// get search for loop count
		Criteria loopCrit = loopPreamble(session , aClip, userItems, userFavorites);
		loopCrit = activeParent(loopCrit); 
		
		/** set the number of loops found */
		return ( (Integer) loopCrit
		.setProjection(
				Projections.projectionList()
				.add( Projections.rowCount())
				)
			.list().get(0) ).intValue();
}
	
	/**
	 * @param loopCrit
	 * @param clip
	 * @return
	 */
	private Criteria activeParent(Criteria loopCrit) {
		// TODO Auto-generated method stub
		return 	
		loopCrit.createAlias("trackpack", "tp", CriteriaSpecification.LEFT_JOIN)
		.add(Restrictions.or(
				Restrictions.isNull("trackpackId") ,
				Restrictions.and(
						Restrictions.eq("tp.ready", Boolean.TRUE) ,
						Restrictions.isNull("tp.statusId")
				)
			)
		);

	}

	private List getLoopIds(Session session , Loop aClip, Collection userItems, Collection userFavorites , ExtendedPaginatedList paginatedList){
		// get search for loop count
		SortOrderEnum sortDirection = paginatedList.getSortDirection();
		/** set the number of loops found */
		return (List) 
		activeParent(loopPreamble(session , aClip, userItems, userFavorites))
		.addOrder(
			new SimpleOrderCriterion(
					paginatedList.getSortCriterion() 
					, sortDirection.equals(SortOrderEnum.ASCENDING)
			)
		)
		.setProjection(Projections.distinct(Property.forName("id")))
		.list();
}
	

	private Criteria applyUserCriteria(Loop aClip ,Criteria loopCrit , Collection userItems, Collection userFavorites){
		if (aClip.getOwnedByViewer() && userItems != null){
			loopCrit.add(Restrictions.not(Restrictions.in("id" , userItems)));
			loopCrit.add(Restrictions.not(Restrictions.in("trackpackId" , userItems)));
		}

		if (aClip.isAFavorite() && userFavorites != null){
			loopCrit.add(Restrictions.isEmpty("userFavorites"));
		}
		return loopCrit;
	}
	private int getTrackpackCount(Session session , Loop aClip, Collection userItems, Collection userFavorites){
		// get search for tp count
		Criteria loopCrit = loopPreamble(session , aClip, userItems, userFavorites);
		
		// add Trackpack count
		loopCrit.add(Restrictions.isNotNull("trackpackId")).createAlias("trackpack" , "tp")
		.add(Restrictions.isNull("tp.statusId"))
		.add(Restrictions.eq("tp.ready" , Boolean.TRUE));
		
		// count TP
		return ( (Integer) loopCrit
		.setProjection(
			Projections.projectionList()
			.add( Projections.countDistinct("trackpackId"))
			)
		.list().get(0) ).intValue();
	}

	public List<Loop> getPaginatedLoops(Loop aClip, ExtendedPaginatedList paginatedList) {
		int fetchSize = paginatedList.getObjectsPerPage();
		int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
		Long viewerId = paginatedList.getViewerId();

		List<Number> userFavorites = null;
		List<Number> userItems = null;
		Session session = getSession();
		if(viewerId.intValue() != -1){
			userFavorites = getUserFavorites(viewerId);
			userItems = getUserItems(viewerId);
		}
		// enable filters
		session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
		session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
		session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
		
		Criteria loopCrit = session.createCriteria(Loop.class);
		loopCrit.add(getExample(aClip));

		// these are needed for "ready" to determine state
		loopCrit.setFetchMode("genre",FetchMode.JOIN);
		loopCrit.setFetchMode("audioFormats",FetchMode.JOIN);
		loopCrit.setFetchMode("instrument",FetchMode.JOIN);

		queryInstrument(loopCrit, aClip);
		queryGenre(loopCrit, aClip);
		queryDescriptor(loopCrit, aClip);
		queryBpm(loopCrit, aClip);
		queryPubDate(loopCrit, aClip);
		queryKeyword(loopCrit, aClip);

		loopCrit.add(Restrictions.eq("searchable", Boolean.TRUE));
		
		loopCrit.add(Restrictions.isNull("statusId"))
		.add(Restrictions.eq("ready", Boolean.TRUE))
		;
		applyUserCriteria(aClip, loopCrit, userItems, userFavorites);

		loopCrit.createAlias("trackpack", "tp", CriteriaSpecification.LEFT_JOIN)
		.add(Restrictions.or(
				Restrictions.isNull("trackpackId") ,
				Restrictions.and(
					Restrictions.eq("tp.ready", Boolean.TRUE) ,
					Restrictions.isNull("tp.statusId")
				)
			)
		);

		loopCrit.setCacheable(false);
		// get search for tp count
		
		SortOrderEnum sortDirection = paginatedList.getSortDirection();

		boolean dir = sortDirection.equals(SortOrderEnum.ASCENDING);
		
		List<Loop> loops = loopCrit.addOrder(new SimpleOrderCriterion(paginatedList.getSortCriterion() , dir))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.list();

		int num = (loops!=null && ! loops.isEmpty())? loops.size() : 0;
		paginatedList.setFullListSize(num);

		paginatedList.setRelTotal( countTP(session , aClip, userItems, userFavorites));
		if (! loops.isEmpty()){
			return loops.subList(firstIndex, Math.min((firstIndex + fetchSize) , num ));
		}
		return loops;
	}

	
	/**
	 * @param session
	 * @param clip
	 * @param userItems
	 * @param userFavorites
	 * @return
	 */
	private int countTP(Session session, Loop aClip,
			List<Number> userItems, List<Number> userFavorites) {
		Criteria loopCrit = session.createCriteria(Loop.class);
		loopCrit.add(getExample(aClip));

		// these are needed for "ready" to determine state
		loopCrit.setFetchMode("genre",FetchMode.JOIN);
		loopCrit.setFetchMode("audioFormats",FetchMode.JOIN);
		loopCrit.setFetchMode("instrument",FetchMode.JOIN);

		queryInstrument(loopCrit, aClip);
		queryGenre(loopCrit, aClip);
		queryDescriptor(loopCrit, aClip);
		queryBpm(loopCrit, aClip);
		queryPubDate(loopCrit, aClip);
		queryKeyword(loopCrit, aClip);

		
		loopCrit.add(Restrictions.isNull("statusId"))
		.add(Restrictions.eq("ready", Boolean.TRUE))
		;
		applyUserCriteria(aClip, loopCrit, userItems, userFavorites);

		loopCrit.createAlias("trackpack", "tp")
		
		.add(
				Restrictions.and(
					Restrictions.eq("tp.ready", Boolean.TRUE) ,
					Restrictions.isNull("tp.statusId")
				)
			
		);
		int retn = ( (Integer) loopCrit
				.setProjection(
						Projections.projectionList()
						.add( Projections.countDistinct("trackpackId"))
						)
					.list().get(0) ).intValue();
		
		// count TP
		return retn;

		
	
	}

	/**
	 * @param session
	 * @return
	 */
	private Criteria loopPreamble(Session session , Loop aClip, Collection userItems, Collection userFavorites) {
		Example loopEx = getExample(aClip);
		Criteria crit = session.createCriteria(Loop.class);
		crit.add(loopEx);

		// these are needed for "ready" to determine state
		crit.setFetchMode("genre",FetchMode.JOIN);
		crit.setFetchMode("audioFormats",FetchMode.JOIN);
		crit.setFetchMode("instrument",FetchMode.JOIN);

		queryInstrument(crit, aClip);
		queryGenre(crit, aClip);
		queryDescriptor(crit, aClip);
		queryBpm(crit, aClip);
		queryPubDate(crit, aClip);
		queryKeyword(crit, aClip);

		crit.add(Restrictions.eq("searchable", Boolean.TRUE));
		
		crit.add(Restrictions.isNull("statusId"))
		.add(Restrictions.eq("ready", Boolean.TRUE));
		applyUserCriteria(aClip, crit, userItems, userFavorites);

		return crit;
	}

	/**
	 * @param crit
	 * @param clip
	 */
	private void queryBpm(Criteria crit, SoundClip aClip) {
		if (aClip.getBpm()!=null){
			Integer[] range = getRange(aClip.getBpm());
		
			crit.add(Restrictions.between("bpm", range[0], range[1]));
		}
	}

	private void queryPubDate(Criteria crit, SoundClip aClip) {
		
		if (aClip.getModified()!=null){
			crit.add(Restrictions.ge("modified", aClip.getModified()));
		}
	}

	private void queryKeyword(Criteria crit, SoundClip aClip) {
		if (StringUtils.isNotBlank(aClip.getKeyword())) {
			crit.add(
					Restrictions.disjunction()
					.add(Restrictions.like("name",aClip.getKeyword(), MatchMode.ANYWHERE))
					.add(Restrictions.like("storeName",aClip.getKeyword(), MatchMode.ANYWHERE))
			);
		}
	}
	
	/**
	 * @param session
	 * @return
	 */
	private Criteria trackpackPreamble(Session session , SoundClip aClip, Collection userItems, Collection userFavorites) {
		Criteria crit = session.createCriteria(Trackpack.class);
		session.enableFilter("active");
		
		// these are needed for "ready" to determine state
		crit.setFetchMode("genre",FetchMode.JOIN);
		crit.setFetchMode("audioFormats",FetchMode.JOIN);
		
		queryDescriptor(crit, aClip);
	
		// TODO Commented out because it contradicts the notion of finding trackpack based on the genres
		// given to components
//		queryGenre(crit, aClip);
//		queryBpm(crit, aClip);
//		queryPubDate(crit, aClip);
		
		crit.add(Restrictions.eq("ready", Boolean.TRUE));
		crit.add(Restrictions.isNull("statusId"));

		applyUserCriteria((Loop)aClip, crit, userItems, userFavorites);

		crit.setCacheable(true);
		return crit;
	}
	
	/**
	 * @param crit
	 * @param clip
	 * @param lastLogin
	 */
	private void querySince(Criteria crit, Loop clip) {
		Date lastLogin = clip.getCreated();
		if (lastLogin!=null){
			crit.add(
				Restrictions.le("modified", lastLogin)
			);
		}
	}
	
	/**
	 * Does not include 'bpm', 'xxxDate' and possibly other non-String descriptors
	 * @param crit
	 * @param loop
	 */
	protected Criteria queryDescriptor(Criteria crit, SoundClip loop) {
		String[] descr = { "energy", "feel", "keynote", "mood", "origin",
				"passage", "scale", "sonority", "texture", "timesignature" };
		int i = 0;
		String tempStr = null;
		for (i = 0; i < descr.length; i++) {
			try {
				tempStr = (String) BeanUtils.getProperty(loop, descr[i]);
				//log.debug("descr[i]:tempStr -> " + descr[i] + ":" + tempStr);
			} catch (Exception e) {
				log.info("No such Descriptor property or some such:" + descr[i], e);
			}
			if (StringUtils.isNotEmpty(tempStr)) {
				crit.add(Restrictions.eq(descr[i], tempStr));
			}
		}
		if (loop.getCreated() !=null) {
			//log.debug("finding loops created since: " + loop.getCreated());
			crit.add(Restrictions.ge("created", loop.getCreated()));
		}
		return crit;
	}

	/**
	 * @param bpm
	 * @return
	 */
	private static Integer[] getRange(Integer val){
		Integer lowerlimit = new Integer(79);
		Integer upperlimit = new Integer(130);
		Integer midlimit = new Integer(105);
		
		// if tmpBpm is less or equal to lower bound
		int handle = val.compareTo(midlimit);
		if (handle < 0) {
			return new Integer[]{new Integer(0) ,lowerlimit};
		} else if (handle == 0) {
			return new Integer[]{lowerlimit,upperlimit};
		} else { //if (handle > 0) {
			return new Integer[]{upperlimit , new Integer(10000)};
		}
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getSoundClips(com.beathive.model.SoundClip, com.beathive.model.User)
	 */
	public List<SoundClip> getSoundClips(SoundClip aClip, QueryMeta params) {

		int fetchSize = params.getFetchSize();
		int firstIndex = params.getCurrIndex();
		boolean isLoop = aClip.getClass().equals(Loop.class);
		Long viewerId = getViewerId(params.getViewer());

		// make sure searchable required
		Example example = getExample(aClip);
		Session session = getSession();

		Criteria crit = session.createCriteria(SoundClip.class);
		
		crit.add(example);
		
		if (aClip.getBpm()!=null){
			Integer[] range = getRange(aClip.getBpm());
		
			crit.add(Restrictions.between("bpm", range[0], range[1]));
		}
		queryGenre(crit , (SoundClip)aClip);
		queryDescriptor(crit, aClip);

		if (isLoop){ queryInstrument(crit , (Loop)aClip); }
		
		// enable filters
		session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
		session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
		session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
		crit = session.createCriteria(aClip.getClass());
		crit.add(getExample(aClip))
		.add(Restrictions.eq("searchable", new Boolean(true)));
		queryDescriptor(crit, aClip);

		queryDescriptor(crit, aClip);
		queryGenre(crit , aClip).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if (isLoop){ 		queryInstrument(crit , (Loop)aClip); }
		crit.addOrder(Order.desc("score")).setFirstResult(firstIndex).setFetchSize(fetchSize).setMaxResults(fetchSize);

		return crit.list();
		
	}

		/*
		 =>"" "instrument_id"
		{"id" , "type" , "storeId" , "name" , "modified" , "created" , "statusId" , "currencyCode" , "amount" ,
		"searchable" , "keyword" , "bpm" , "keynote" , "scale" , "timesignature" , "passage" , "energy" , 
		"feel" , "mood" , "origin" , "sonority" , "texture" , "trackpackId" , "instrument_id" };
		*/		
	private Example getExample(SoundClip clip){
		Example example = Example.create( clip);
		example
	    .excludeZeroes()           //exclude zero valued properties
//	    .ignoreCase()              //perform case insensitive string comparisons
//	    .enableLike(MatchMode.ANYWHERE)
	    ;
	
		// properties to exclude from the example
		String[] excl = {
				"aFavorite","audioFormat","avgScore","created","genre","bpm"
				,"id","modified","name","ownedByViewer"
				,"price","statusId","timesrated","type","ready","searchable"
				// will do shop specific searches,"searchable"
				//,"storeId"
				// loop
				,"instrument","trackpack"
				// methods"producer","instrumentId", "trackpackId" 
				, "numFormats","userClipScore","userFavorites","userItems","viewerScore"
				,"keyword"
				// descriptor methods
				,"descriptors"
//				, "energy" , "feel", "keynote", "mood" , "origin", "passage","score" , "sonority" , "texture", "timesignature", "scale"	
				};
	
		// exclude properties			
		for (int y = 0; y < excl.length; y++){
			example.excludeProperty(excl[y]);
		}
		return example;
	}

	// doesn't have instrument or a TP id and such
	private Example getTrackpackExample(SoundClip clip){
		Example example = Example.create( clip);
		example
		.excludeZeroes()           //exclude zero valued properties
//	    .ignoreCase()              //perform case insensitive string comparisons
//	    .enableLike(MatchMode.ANYWHERE)
		;
		
		// properties to exclude from the example
		String[] excl = {
				"aFavorite","audioFormat","avgScore","created","genre","bpm"
				,"id","modified","name","ownedByViewer"
				,"price","statusId","timesrated","type","ready"
				, "numFormats","userClipScore","userFavorites","userItems","viewerScore"
				,"keyword"
				// descriptor methods
				,"descriptors"
//				, "energy" , "feel", "keynote", "mood" , "origin", "passage","score" , "sonority" , "texture", "timesignature", "scale"	
		};
		
		// exclude properties			
		for (int y = 0; y < excl.length; y++){
			example.excludeProperty(excl[y]);
		}
		return example;
	}
	
	public void saveUserClipScore(final UserClipScore usc) {
		getHibernateTemplate().save(usc);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getTopTenClips(com.beathive.model.SoundClipQuery, int)
	 * 
	 * TODO Problem getting loops that aren't related by trackpack while still getting TPs
	 * Don't know how to efficiently do a look behind to make sure s.trackpackId is unique in the first select.
	 * May have to split into 5 TP and 5 single loops
	 */
	public List getTopTenClips(QueryMeta qm , int interval) {
		Session session = getSession();
		int fetchSize = qm.getFetchSize();
		int span = interval;
		if (span < 1){
			span=30;
		}
		String sql1 = "select distinct(pui.clipId) from purchase_item pui " +
				"join purchase b  on pui.purchaseId=b.id " +
				"join soundclip s on s.id=clipId " +
				"where " +
				"s.searchable='Y' " +
				"and b.created >= (now() - interval "+span + " day) "+
				"group by (pui.clipId) " +
				"order by count(pui.clipId) desc limit " + fetchSize;
		
		List clipIds = session.createSQLQuery(sql1).list();
		//log.debug("yeilds: " + clipIds);
		if (clipIds ==null ||  clipIds.isEmpty()){
			return new LinkedList();
		}
		else{
		/* need to figure out why Hibernate is returning my IDs as BigInterger instead of Long*/
			//log.debug(clipIds);
			BigInteger obj = null;
			for (int g = 0 ; g < clipIds.size(); g++){
				obj = (BigInteger)clipIds.get(g);
				clipIds.set(g, new Long(obj.longValue()));
			}
			/* Bug above **/
			Long viewerId = getViewerId(qm.getViewer());
			session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
			session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
			session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
			return session.createCriteria(SoundClip.class)
				.add(Restrictions.isNull("statusId"))
				.add(Restrictions.eq("ready", Boolean.TRUE))
				.add(Restrictions.in("id", clipIds))
				.addOrder(Order.desc("score"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		}
	}

	/**
	 * @param viewer
	 * @return
	 */
	private Long getViewerId(User viewer) {
        if (viewer==null){
        	viewer = new User();
        }
		if (viewer.getId()==null){
			viewer.setId(new Long(-1));
		}
		return viewer.getId();
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getNewestClips(com.beathive.model.SoundClipQuery)
	 */
	public List getNewestTrackpacks(QueryMeta params) {
		Session session = getSession();
		Long viewerId = getViewerId(params.getViewer());
		session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
		session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
		session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
		session.enableFilter("active");
		
		return session.createCriteria(Trackpack.class).setFetchSize(params.getFetchSize())
				.add(Restrictions.isNull("statusId"))
		.add(Restrictions.eq("ready", Boolean.TRUE))

			.setMaxResults(params.getFetchSize()).addOrder(Order.desc("created"))
			.list();
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getRandomShop()
	 */
	public Store getRandomShop() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getTopShop(com.beathive.model.User)
	 */
	public List getTopShop(User viewer) {
		// TODO Auto-generated method stub
		return null;
	}


	protected Criteria queryGenre(Criteria crit, SoundClip clip){
		List genres = clip.getGenre();
		if (genres != null && (!genres.isEmpty())){
			//log.debug("cg.id:" +  ((Genre)genres.get(0)).getId());
			crit.setFetchMode("genre",FetchMode.JOIN);
			crit.createAlias("genre", "cg")
			.add(Restrictions.eq("cg.id", ((Genre)genres.get(0)).getId()));
		}
		return crit;
	}
	
	
	/**
	 * Only one Format in the query
	 * @param crit
	 * @param clip
	 * @return
	 */
	protected Criteria queryFormat(Criteria crit, SoundClip clip){
		Set<AudioFile> frmts = clip.getAudioFormat();
		if (frmts != null && (!frmts.isEmpty())){
			String clipFormat = frmts.iterator().next().getFormatId();
			crit.createAlias("audioFormat", "af")
			.add(Restrictions.eq("af.formatId", clipFormat));
		}
		return crit;
	}
	
	/**
	 * Check the Loop for a instrument. A group is only looked-up in 
	 * the abscence of a specific instrument_id
	 * the check 
	 * @param crit
	 * @param clip
	 */
	protected Criteria queryInstrument(Criteria crit, Loop loop){
		Instrument instrument = loop.getInstrument();
		if (instrument != null){
			crit.setFetchMode("instrument",FetchMode.JOIN);
			Long instrument_id = instrument.getId();
			Long group_id = instrument.getGroupId();
			//log.debug("instrument_id: " + instrument_id );
			
			if (instrument_id != null){
				//log.debug("instrument_id " + instrument_id);
				crit.add(Restrictions.eq("instrumentId", instrument_id));
			} 
			else if (group_id != null){
				crit.createAlias("instrument", "instrum").add(Restrictions.eq("instrum.groupId", group_id));//.setFetchMode("instrumentgroup" , FetchMode.SELECT)
			}
		}		
		return crit;
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#updateAudio(com.beathive.model.AudioFile)
	 */
	public void updateAudio(Map filemap) {
		String clipId = (String)filemap.get("clipId");
		String formatId = (String)filemap.get("formatId");
		if (clipId != null){
			SoundClip soundClip = getSoundClip(new Long(clipId));
			if (soundClip!=null){
				boolean found = false;
			//	AudioFile file = new AudioFile();
			//	BeanUtils.copyProperties(file , filemap);
				Set<AudioFile> frms  = soundClip.getAudioFormat();
				
				try {
					for (AudioFile file : frms){
						if (file.getFormatId().equals(formatId)){
							//file.getClipId();
							Long id = file.getId();
							Long stid = file.getStoreId();
							
							BeanUtils.copyProperties(file , filemap);
							file.setId(id);
							file.setStoreId(stid);
							
							found = true;
							break;
						}
					}
					if (!found){
						AudioFile file = new AudioFile();
						BeanUtils.copyProperties(file , filemap);

						getHibernateTemplate().save(file);
						
						soundClip.addAudioFile(file);
					}
					getHibernateTemplate().update(soundClip);	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	public AudioFile getAudioFile(Long fileId) {
		return (AudioFile)getHibernateTemplate().get(AudioFile.class, fileId);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#hasFile(java.lang.String)
	 */
	public String hasFile(String checkSum) {
		String resp = null;
		Criteria criteria = getSession().createCriteria(SoundClip.class);
		List clips = criteria
		.createAlias("audioFormat", "format")
		.add(Restrictions.eq("format.checkSum",checkSum ))
		.list();
		if (clips!=null && ! clips.isEmpty()){
			resp = ((SoundClip)clips.get(0)).getName();
		}
		return resp;
	}

	/**
	 * Returns if Clip was affected
	 * @param ucr
	 * @return
	 */
	public boolean rateClip(UserClipScore ucr){
		boolean retv = false;
		try{
			getHibernateTemplate().save(ucr);
			retv= true;
		}catch(org.hibernate.exception.ConstraintViolationException e){
			log.error(e);
		}
		return retv;
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getTrackpackComponents(Long, com.beathive.ExtendedPaginatedList)
	 */
	public List getTrackpackComponents(Long clipId,	ExtendedPaginatedList paginatedList) {
		int fetchSize = paginatedList.getObjectsPerPage();
		int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
		Long viewerId = paginatedList.getViewerId();
		SortOrderEnum sortDirection = paginatedList.getSortDirection();
		
		boolean dir = sortDirection.equals(SortOrderEnum.ASCENDING);

		Session session = getSession();
		session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
		session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
		session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
		if(viewerId.intValue() != -1){
			// enable filters
			
		}
		
		
		Criteria crit = session.createCriteria(Loop.class);
		crit.add(Restrictions.eq("trackpackId", clipId));
		// these are needed for "ready" to determine state
		crit.setFetchMode("genre",FetchMode.JOIN)
		.setFetchMode("audioFormats",FetchMode.JOIN)
		.setFetchMode("instrument",FetchMode.JOIN)
		.add(Restrictions.isNull("statusId"))
		.add(Restrictions.eq("ready", Boolean.TRUE))		
		.addOrder(new SimpleOrderCriterion(paginatedList.getSortCriterion() , dir))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List loops = crit.list();
		
		int num = (loops!=null && ! loops.isEmpty())? loops.size() : 0;
		paginatedList.setFullListSize(num);

		
		List tmp = loops;
		if (! loops.isEmpty()){
			tmp = loops.subList(firstIndex, Math.min((firstIndex + fetchSize) , num ));
		}
		
		return tmp;
	}

		

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#getCounts(com.beathive.model.Loop, com.beathive.ExtendedPaginatedList)
	 */
	public Integer[] getCounts(Loop aClip,
			ExtendedPaginatedList paginatedList) {


		Session session = getSession();
		Criteria loopCrit = session.createCriteria(Loop.class);
		List<Number> userFavorites = null;
		List<Number> userItems = null;
		Long viewerId = paginatedList.getViewerId();
		if(viewerId.intValue() != -1){
			userFavorites = getUserFavorites(viewerId);
			userItems = getUserItems(viewerId);
			// enable filters
			session.enableFilter("thisUsersFavorite").setParameter("viewerId", viewerId);
			session.enableFilter("thisUsersScore").setParameter("viewerId", viewerId);
			session.enableFilter("thisUserOwns").setParameter("viewerId", viewerId);
			
			applyUserCriteria(aClip, loopCrit, userItems, userFavorites);
		}


		
		doBase(aClip,loopCrit,paginatedList, session);
		activeParent(loopCrit);
		loopCrit.add(Restrictions.eq("searchable", Boolean.TRUE));

		loopCrit.setProjection(	Projections.projectionList().add( Projections.countDistinct("id"))	);
		Integer lcnt = new Integer((Integer)loopCrit.list().iterator().next());
		
		
		loopCrit = session.createCriteria(Loop.class);
		if(viewerId.intValue() != -1){	applyUserCriteria(aClip, loopCrit, userItems, userFavorites);}
		doBase(aClip,loopCrit,paginatedList, session);
		loopCrit.add(Restrictions.isNotNull("trackpackId")).createAlias("trackpack" , "tp")
		.add(Restrictions.isNull("tp.statusId"))
		.add(Restrictions.eq("tp.ready" , Boolean.TRUE));
		
		loopCrit.setProjection(		Projections.projectionList().add( Projections.countDistinct("trackpackId"))			);
		Integer tpcnt = new Integer((Integer)loopCrit.list().iterator().next());
		return new Integer[]{lcnt ,tpcnt} ;
	}

	
	/**
	 * @param clip
	 * @param paginatedList
	 * @return
	 */
	private Criteria doBase(Loop aClip, Criteria loopCrit , ExtendedPaginatedList paginatedList , Session session) {
		loopCrit.add(getExample(aClip));

		// these are needed for "ready" to determine state
		loopCrit.setFetchMode("genre",FetchMode.JOIN);
		loopCrit.setFetchMode("audioFormats",FetchMode.JOIN);
		loopCrit.setFetchMode("instrument",FetchMode.JOIN);
//		crit.setFetchMode("trackpack",FetchMode.JOIN);
		// "select s.* from soundclip s left join soundclip tp on s.trackpackId=tp.id where s.type='Loop' 
		// and s.ready='Y' and s.statusId is NULL and ( s.trackpackId is NULL or (tp.statusId is NULL and tp.ready='Y'));"
		queryInstrument(loopCrit, aClip);
		queryGenre(loopCrit, aClip);
		queryDescriptor(loopCrit, aClip);
		queryBpm(loopCrit, aClip);
		queryPubDate(loopCrit, aClip);
		queryKeyword(loopCrit, aClip);
		
		loopCrit.add(Restrictions.isNull("statusId"))
		.add(Restrictions.eq("ready", Boolean.TRUE))
		;
		return loopCrit;
	}

	/**
	 * @param viewerId
	 * @return
	 */
	private List<Number> getUserItems(Long viewerId) {
		int i=0;
		List<Number> userItems = getSession().createSQLQuery("select pi.clipId from purchase_item pi where pi.customerId=?")
		.setParameter(0 , viewerId).list();
		if (userItems.isEmpty()){return null;}
		for(Number bint : userItems){
			userItems.set(i++, new Long(bint.intValue()));
		}
		return userItems;
	}

	/**
	 * @param viewerId
	 * @return
	 */
	private List<Number> getUserFavorites(Long viewerId) {
		return getHibernateTemplate().find("select f.clipId from Favorite f where f.userId=?" , viewerId);
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.SoundClipDao#removeSoundClip(java.lang.Long)
	 */
	public void removeSoundClip(Map settings) {
		Long clipId = (Long)settings.get("id");
		boolean deep = ((Boolean)settings.get("deep")).booleanValue();
		Long storeId = (Long)settings.get("storeId");
		
		HibernateTemplate ht = getHibernateTemplate();
		// get the clip
		SoundClip clip = (SoundClip)ht.get(SoundClip.class, clipId);
		
		if(! clip.getStoreId().equals(storeId)){
			log.error("no match: " + storeId +"= " + clip.getStoreId());
			throw new ObjectRetrievalFailureException(SoundClip.class, clipId);
		}
		// determine type
		boolean isTP = clip.getClass().equals(Trackpack.class);
		// if a sold item plan to store for the quarter
		boolean beenSold = !(clip.getTimesSold()== null || clip.getTimesSold().intValue() < 1);
		Integer clipStatusId = new Integer(1);

		// if it's a loop, easy just check sales stat or delete it
		if (isTP){
			List<Loop> loops = ((Trackpack)clip).getLoops();

			if (beenSold){
				// these are sold items change their status 
				clip.setStatusId(clipStatusId);
				// if we're deleting the components
				if (deep){
					for (Loop l : loops){
						l.setStatusId(clipStatusId);
						l.setTrackpackId(null);
					}
				}else{
					for (Loop l : loops){
						l.setTrackpackId(null);
						l.setSearchable(true);
					}
				}
			}else{
				if (deep){
					for (Loop l : loops){
						ht.delete(l);
					}
					ht.delete(clip);
				}else{
					for (Loop l : loops){
						l.setTrackpackId(null);
						l.setSearchable(true);
					}
					ht.delete(clip);
				}
			}
		}else{
			if (beenSold){
				clip.setStatusId(clipStatusId);
			}else{
				ht.delete(clip);
			}
		}
	}
}
