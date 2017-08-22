package com.beathive.dao.hibernate;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.beathive.ExtendedPaginatedList;
import com.beathive.dao.StoreDao;
import com.beathive.dao.helper.SimpleOrderCriterion;
import com.beathive.model.AudioFile;
import com.beathive.model.Genre;
import com.beathive.model.Instrument;
import com.beathive.model.Loop;
import com.beathive.model.SoundClip;
import com.beathive.model.Store;
import com.beathive.model.Trackpack;
import com.beathive.model.User;

public class StoreDaoHibernate extends BaseDaoHibernate implements StoreDao {

	/* (non-Javadoc)
	 * @see com.beathive.dao.StoreDao#incrementViews(java.lang.Long)
	 */
	public void incrementViews(Long shopId) {
		Session session = getSession();
		session.createSQLQuery("update store set views=views+1 where id=?" )
		.setParameter(0, shopId)
		.executeUpdate();
	}
	
	public List getUserStores(String username)  throws ObjectRetrievalFailureException{
		List l =  getSession().createCriteria(User.class)
				.add(Restrictions.eq("username",username))
				.setFetchMode("stores",FetchMode.SELECT)
				.createCriteria("stores").list();
		if (l!=null && (!l.isEmpty())){
			User u = (User)l.get(0);
			return new LinkedList(u.getStores());
		}
		return l;
	}
	/* (non-Javadoc)
	 * @see com.beathive.dao.StoreDao#getLoops(java.lang.String)
	 */
	public List getLoops(Long storeId, ExtendedPaginatedList paginatedList, Boolean readystat) {
		int fetchSize = paginatedList.getObjectsPerPage();
		int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
		Long viewerId = paginatedList.getViewerId();

		Session session = getSession();
		
		// repeat the query
		Criteria crit = session.createCriteria(Loop.class)
			.add(Restrictions.eq("storeId", storeId))
			// only single loops
			.add(Restrictions.isNull("trackpackId"));

		if (readystat!=null){
			crit.add(Restrictions.eq("ready", readystat));
		}
		// don't show status equals one. A loop with a status of 2 would be a freed component loop.
    	crit.add(Restrictions.or(
    			Restrictions.isNull("statusId"),
    			Restrictions.eq("statusId" , new Integer(2))
    		)
    	);
		
		// add order-by if given
		if (paginatedList.getSortCriterion()!=null){
			if(paginatedList.getSortCriterion().equals("ready")){
				// these are needed for "ready" to determine state
				crit.setFetchMode("genre",FetchMode.JOIN);
				crit.setFetchMode("audioFormats",FetchMode.JOIN);
				crit.setFetchMode("instrument",FetchMode.JOIN);
			}
			
			boolean dir = paginatedList.getSortDirection().equals(SortOrderEnum.ASCENDING);
			crit.addOrder(new SimpleOrderCriterion(paginatedList.getSortCriterion() , dir));
		}
		
		List loops = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    	
    	int num = 0;
    	List tmp =  new LinkedList();
    	if(loops!=null && ! loops.isEmpty()){
    		num = loops.size();
    		tmp = loops.subList(firstIndex, Math.min((firstIndex + fetchSize) , num ));
    	}
    	paginatedList.setFullListSize(num);
    	paginatedList.setList(tmp);
		return tmp;
	}
	
    public SoundClip getTrackpack(Long clipId , ExtendedPaginatedList paginatedList) {
		int fetchSize = paginatedList.getObjectsPerPage();
		int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
    	Session session = getSession();
    	Criteria crit = session.createCriteria(Trackpack.class);
    	Trackpack clip = (Trackpack) crit.add(Restrictions.eq("id", clipId))
    	.setFetchMode("loops", FetchMode.SELECT).list().get(0);

    	List loops = clip.getLoops();
		int num = (loops!=null && ! loops.isEmpty())? loops.size() : 0;
		paginatedList.setFullListSize(num);
		
		List tmp = loops.subList(firstIndex, Math.min((firstIndex + fetchSize) , num ));
		paginatedList.setList(tmp);
    	return clip;
    }

    public Trackpack getTrackpackComponents(final Long clipId , ExtendedPaginatedList paginatedList) {
    	int fetchSize = paginatedList.getObjectsPerPage();
    	int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
    	Session session = getSession();
		SortOrderEnum sortDirection = paginatedList.getSortDirection();

		boolean dir = sortDirection.equals(SortOrderEnum.ASCENDING);

    	Criteria crit = session.createCriteria(Loop.class);
    	List loops = crit.add(Restrictions.eq("trackpackId", clipId))
    	.add(Restrictions.or(
    			Restrictions.eq("statusId" , new Integer(2)),
    			Restrictions.isNull("statusId")
    		)
    	)
		.setFetchMode("genre",FetchMode.SELECT)
		.setFetchMode("audioFormats",FetchMode.SELECT)
		.setFetchMode("instrument",FetchMode.SELECT)
		.addOrder(new SimpleOrderCriterion(paginatedList.getSortCriterion() , dir))
		.list();
    	
    	int num = (loops!=null && ! loops.isEmpty())? loops.size() : 0;
    	paginatedList.setFullListSize(num);
    	
    	List tmp = loops.subList(firstIndex, Math.min((firstIndex + fetchSize) , num ));
    	paginatedList.setList(tmp);
    	return (Trackpack)getHibernateTemplate().get(Trackpack.class , clipId);
    }
    
	/* (non-Javadoc)
	 * @see com.beathive.dao.StoreDao#getTrackpacks(java.lang.String)
	 */
	public List getTrackpacks(Long storeId, ExtendedPaginatedList paginatedList, Boolean readystat) {
    	int fetchSize = paginatedList.getObjectsPerPage();
    	int firstIndex = paginatedList.getFirstRecordIndex();//.getPageNumber() - 1;
    	Session session = getSession();
    	
    	// filters component-loops from TPs that were previously deleted
		session.enableFilter("active");

		SortOrderEnum sortDirection = paginatedList.getSortDirection();
		boolean dir = sortDirection.equals(SortOrderEnum.ASCENDING);
		
		Criteria crit = session.createCriteria(Trackpack.class);
		if (readystat!=null){
			crit.add(Restrictions.eq("ready", readystat));
		}
    	List loops = crit.add(Restrictions.eq("storeId", storeId))
    	
    	.add(Restrictions.or(
    			Restrictions.eq("statusId" , new Integer(2)),
    			Restrictions.isNull("statusId")
    		)
    	)
 //   	.setCacheable(true)
		.setFetchMode("genre",FetchMode.JOIN)
		.setFetchMode("audioFormats",FetchMode.JOIN)
		.setFetchMode("instrument",FetchMode.JOIN)
		.setFetchMode("loops", FetchMode.JOIN)
//		.addOrder(Order.asc("ready"))
		.addOrder(new SimpleOrderCriterion(paginatedList.getSortCriterion() , dir))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)

//		.setFetchMode("instrument",FetchMode.JOIN)
		.list();
    	
    	int num = (loops!=null && ! loops.isEmpty())? loops.size() : 0;
    	paginatedList.setFullListSize(num);
    	
    	List tmp = loops.subList(firstIndex, Math.min((firstIndex + fetchSize) , num ));
    	paginatedList.setList(tmp);

		return tmp;
	}
	protected Criteria queryGenre(Criteria crit, SoundClip clip){
		List genres = clip.getGenre();
		if (genres != null && (!genres.isEmpty())){
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
//			crit.createAlias("audioFormat", "af")
			crit.add(Restrictions.eq("af.formatId", clipFormat));
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
			Long instrument_id = instrument.getId();
			Long group_id = instrument.getGroupId();
			
			if (instrument_id != null){
				crit.add(Restrictions.eq("instrumentId", instrument_id));
			} 
			else if (group_id != null){
				crit.createAlias("instrument", "instrum").add(Restrictions.eq("instrum.groupId", group_id));//.setFetchMode("instrumentgroup" , FetchMode.SELECT)
			}
		}		
		return crit;
	}


    /**
     * @see com.beathive.dao.StoreDao#getStores(com.beathive.model.Store)
     */
    public List getStores(final Store store) {
        return getHibernateTemplate().find("from Store");

        /* Remove the line above and uncomment this code block if you want 
           to use Hibernate's Query by Example API.
        if (store == null) {
            return getHibernateTemplate().find("from Store");
        } else {
            // filter on properties set in the store
            HibernateCallback callback = new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Example ex = Example.create(store).ignoreCase().enableLike(MatchMode.ANYWHERE);
                    return session.createCriteria(Store.class).add(ex).list();
                }
            };
            return (List) getHibernateTemplate().execute(callback);
        }*/
    }

    /**
     * @see com.beathive.dao.StoreDao#getStore(Long id)
     */
    public Store getStore(final Long id) {
        Store store = (Store) getHibernateTemplate().get(Store.class, id);
        if (store == null) {
            log.warn("uh oh, store with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(Store.class, id);
        }

        return store;
    }

    /**
     * @see com.beathive.dao.StoreDao#saveStore(Store store)
     */    
    public void saveStore(final Store store) {
        getHibernateTemplate().saveOrUpdate(store);
    }

    /**
     * @see com.beathive.dao.StoreDao#removeStore(Long id)
     */
    public void removeStore(final Long id) {
        getHibernateTemplate().delete(getStore(id));
    }

	/**
	 * Get the number of single loops in shop.
	 * We filter out:
	 * 		deleted loops via statusId not eq 1
	 * 		only searchable loops shown
	 * 		trackpackId is NULL for single loops
	 */
	public int getNumLoops(Long storeId, Boolean readystat) {
    	Session session = getSession();
		Criteria crit = session.createCriteria(Loop.class)
		.add(Restrictions.eq("storeId", storeId))
//		.setCacheable(true)
//		.setFetchMode("genre",FetchMode.JOIN)
//		.setFetchMode("audioFormats",FetchMode.JOIN)
//		.setFetchMode("instrument",FetchMode.JOIN)
		.add(Restrictions.eq("ready", readystat));
//		if(! readystat.booleanValue()){
//			log.error("readystat: " + readystat);
//			crit.add(Restrictions.eq("searchable", readystat));
//		}
//		crit.add(Restrictions.isNull("statusId"))
		crit.add(Restrictions.or(Restrictions.isNull("statusId") , Restrictions.eq("statusId", 2)))
		// only single loops
		.add(Restrictions.isNull("trackpackId"))
		.setProjection(	Projections.projectionList().add( Projections.countDistinct("id"))	);
		Integer lcnt = new Integer((Integer)crit.list().get(0));
		return lcnt.intValue();
	}

	public int getNumSold(Long storeId, String type) {
    	Session session = getSession();
    	Query query = session.createSQLQuery("select count(p.id) from purchase_item p where p.storeId=? and p.type=?");   	
		return ((BigInteger)query.setParameter(0, storeId).setParameter(1, type).list().get(0)).intValue();
		
		
		
	}

	
	/* (non-Javadoc)
	 * @see com.beathive.dao.StoreDao#getNumTrackpacks(java.lang.Long, java.lang.Boolean)
	 */
	public int getNumTrackpacks(Long storeId, Boolean readystat) {
    	Session session = getSession();
    	
    	// filters component-loops from TPs that were previously deleted
//		session.enableFilter("active");

		Criteria crit = session.createCriteria(Trackpack.class)
   	.setCacheable(true)
		.add(Restrictions.eq("storeId", storeId))
		.setFetchMode("genre",FetchMode.JOIN)
		.setFetchMode("audioFormats",FetchMode.JOIN)
		.setFetchMode("loops", FetchMode.JOIN)
		.add(Restrictions.eq("ready", readystat))
		.add(Restrictions.or(Restrictions.isNull("statusId") , Restrictions.eq("statusId", 2)));

		crit.setProjection(	Projections.projectionList().add( Projections.countDistinct("id"))	);
		Integer lcnt = new Integer((Integer)crit.list().get(0));
		return lcnt.intValue();
	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.StoreDao#getStoreByName(java.lang.String)
	 */
	public Store getStoreByName(String storeName) {
		List list = getHibernateTemplate().find("from Store where name=?" , storeName);
		Store store = null;
		if (list != null){
			store = (Store)list.get(0);
		}
        if (store == null) {
            log.warn("uh oh, store with storeName '" + storeName + "' not found...");
            throw new ObjectRetrievalFailureException(Store.class, storeName);
        }

        return store;
	}
	


}
