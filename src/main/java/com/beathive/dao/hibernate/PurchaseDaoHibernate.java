/**
 * 
 */
package com.beathive.dao.hibernate;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.beathive.dao.PurchaseDao;
import com.beathive.dao.helper.SimpleOrderCriterion;
import com.beathive.model.PaymentExecutor;
import com.beathive.model.Purchase;
import com.beathive.model.PurchaseItem;
import com.beathive.model.PurchaseItemEx;
import com.beathive.model.ShopReport;

/**
 * @author rdennison
 */
public class PurchaseDaoHibernate extends BaseDaoHibernate implements
		PurchaseDao {
	private Log log = LogFactory.getLog(PurchaseDaoHibernate.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.persistence.PurchaseDAO#removePurchase(java.lang.Long)
	 */
	public void removePurchase(Long id) {
		Purchase order = getPurchase(id);
		getHibernateTemplate().delete(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.persistence.PurchaseDAO#getPurchases()
	 */
	public List getPurchases() {
		return getHibernateTemplate()
				.find("from purchase u order by u.created");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.persistence.PurchaseDAO#getPurchases(com.beathive.model.Purchase)
	 */
	public List getPurchases(Purchase order) {
		Example stEx = Example.create(order).excludeZeroes() // exclude zero
																// valued
																// properties
				.ignoreCase() // perform case insensitive string comparisons
				.enableLike(); // use like for string comparisons
		return getSession().createCriteria(Purchase.class).add(stEx).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.persistence.PurchaseDAO#getPurchases(com.beathive.model.Purchase)
	 */
	public Object getSummarizeSalesInRange(ShopReport salesReportPager) {
		Session session = getSession();
		List allItems = getSalesCriteria(session, salesReportPager).list();

		List[] tp_loop = split(allItems);

		int tpcnt = tp_loop[0].size();
		int loopcnt = tp_loop[1].size();

		salesReportPager.setTrackpackVolume(tpcnt);
		salesReportPager.setTotalTrackpackYeild(sum(tp_loop[0]));

		salesReportPager.setLoopVolume(loopcnt);
		salesReportPager.setTotalLoopYeild(sum(tp_loop[1]));

		salesReportPager.setFullListSize(tpcnt + loopcnt);

		Criteria crit = getSalesCriteria(session, salesReportPager);

		SortOrderEnum sortDirection = salesReportPager.getSortDirection();

		crit.addOrder(new SimpleOrderCriterion(salesReportPager
				.getSortCriterion(), sortDirection
				.equals(SortOrderEnum.ASCENDING)));

		salesReportPager.setList(allItems);

		return salesReportPager;
	}

	private void doGroup(Session session, ShopReport salesReportPager) {

		Criteria idcrit = getSalesCriteria(session, salesReportPager);
		idcrit.setProjection(Projections.projectionList().add(
			Projections.property("id")).add(
			Projections.groupProperty("clipId"))
		);
		List idList = new LinkedList();

		List tmp = idcrit.list();
		String str = "";
		java.util.Iterator it = tmp.iterator();
		while (it.hasNext()) {
			Object[] l = (Object[]) it.next();
			idList.add(l[0]);
			str += l[0] + " , " + l[1] + "\n";
		}

		salesReportPager.setFullListSize(idList.size());

		List list = getSalesCriteria(session, salesReportPager).add(
			Restrictions.in("id", idList)).setFirstResult(
			salesReportPager.getFirstRecordIndex()).setFetchSize(
			salesReportPager.getObjectsPerPage()).setMaxResults(
			salesReportPager.getObjectsPerPage()).list();
		salesReportPager.setList(list);

	}

	/**
	 * @param session
	 * @param salesReportPager
	 */
	private void putSalesCount(Session session, ShopReport salesReportPager) {
		Criteria crit = getSalesCriteria(session, salesReportPager);

		((Integer) crit.setProjection(
				Projections.projectionList().add(Projections.rowCount()))
				.list().get(0)).intValue();

		Criteria saleUnitCriteria = getSalesCriteria(session, salesReportPager);

		List<PurchaseItem> allItems = saleUnitCriteria.list();
	}

	/**
	 * @param session
	 * @return
	 */
	private Criteria getSalesCriteria(Session session,
			ShopReport salesReportPager) {
		Criteria crit = session
				.createCriteria(PurchaseItem.class)
				.add(Restrictions.eq("storeId", salesReportPager.getStoreId()))
				.createAlias("purchase", "parent")
				.add(Restrictions.between("parent.created", salesReportPager
					.getStartDate(), salesReportPager.getEndDate()));
		return crit;
	}

	/**
	 * @param allItems
	 * @return
	 */
	private List[] split(List<PurchaseItem> allItems) {
		List tp = new LinkedList();
		List loop = new LinkedList();

		for (PurchaseItem item : allItems) {
			if (item.getNet()!=null){
	
				if (item.getType().equals("Trackpack")) {
					tp.add(item);
				} else {
					loop.add(item);
				}
			}
		}
		return new List[] { tp, loop };
	}

	/**
	 * @param list
	 * @return
	 */
	private Double sum(List<PurchaseItem> list) {
		double val = 0.0;
		log.debug("sum list" + list);
		for (PurchaseItem item : list) {
			val += item.getNet().doubleValue();
		}
		return new Double(val);
	}

	public Criteria salesPreamble(Criteria saleUnitCriteria, ShopReport salesReportPager, String type) {
		saleUnitCriteria
		.add(Restrictions.eq("storeId", salesReportPager.getStoreId()))
		.add(Restrictions.eq("type", type))
		.createAlias("purchase", "parent")
		.add(
			Restrictions.between("parent.created", salesReportPager.getStartDate(), salesReportPager.getEndDate())
		);
		return saleUnitCriteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.persistence.PurchaseDAO#savePurchase(com.beathive.model.Purchase)
	 */
	public void savePurchase(final Purchase order) {
		getHibernateTemplate().saveOrUpdate(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.persistence.PurchaseDAO#getPurchase(java.lang.Long)
	 */
	public Purchase getPurchase(Long id) {
		Purchase order = (Purchase) getHibernateTemplate().get(Purchase.class, id);

		if (order == null) {
			log.warn("uh oh, user '" + id + "' not found...");
			throw new ObjectRetrievalFailureException(Purchase.class, id);
		}
		return order;
	}

	/**
	 * Delete only PREAUTH items belonging to the user specified.
	 */
	public void removePurchaseItem(String username, String itemId) {

		Criteria crit = getSession().createCriteria(PurchaseItem.class).add(
				Restrictions.eq("id", new Long(itemId))).createAlias("order",
				"o").add(Restrictions.eq("o.authCode", "PREAUTH")).add(
				Restrictions.eq("o.username", username));
		List list = crit.list();
		if (list != null && (!list.isEmpty())) {
			PurchaseItem orderItem = (PurchaseItem) list.get(0);
			getHibernateTemplate().delete(orderItem);
		}
	}

	public List getAllUserPurchases(String username) {
		Criteria crit = getSession().createCriteria(Purchase.class).addOrder(
				org.hibernate.criterion.Order.desc("created"));
		return crit.list();
	}

	public List getUserPurchases(String username) {
		Criteria crit = getSession().createCriteria(Purchase.class).add(
				Restrictions.eq("authCode", "PREAUTH")).addOrder(
				org.hibernate.criterion.Order.asc("created"));
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.dao.PurchaseDao#getExecutor(java.lang.Long)
	 */
	public PaymentExecutor getExecutor(Long serviceId) {
		return (PaymentExecutor) super.getObject(PaymentExecutor.class,
				serviceId);
	}
	
	public void getSales(ShopReport salesReportPager, boolean b) {
		int fetchSize = salesReportPager.getObjectsPerPage();
		int firstIndex = salesReportPager.getFirstRecordIndex();//.getPageNumber() - 1;
		Session s = getSession();
//		Criteria crit = s.createCriteria(PurchaseItem.class)
//		.createAlias("purchase", "bill")
//		.add(Restrictions.between("bill.created", salesReportPager.getStartDate(), salesReportPager.getEndDate()))
//		.setResultTransformer( Transformers.aliasToBean(PurchaseItemEx.class) )
//		;
		
		log.debug("salesReportPager.getStartDate(): " + salesReportPager.getStartDate());
		log.debug("salesReportPager.getEndDate(): " + salesReportPager.getEndDate());
		String querystr = "select u.email as email , sum(purch_item.price) as price, pur.created  as created ," +
		" sum( purch_item.price/pur.subTotal * (pur.total - ((pur.rateFee * pur.total) + pur.flatFee)) /2 ) as net ," +
		" purch_item.storeName" +
		" from purchase_item purch_item join purchase pur on purch_item.purchaseId=pur.id" +
		" join user_store us on purch_item.storeId=us.storeId" +
		" join app_user u on u.id=us.userId" +
		" where pur.authCode <> 'PREAUTH' and pur.created >=CAST(:begin AS DATE)  and pur.created <=CAST(:end AS DATE)" +// purch_item.paid='Y'
		((salesReportPager.getStoreId()!=null) ? " and purch_item.storeId="+salesReportPager.getStoreId() : "") +
		" group by u.email" +
		" order by " + salesReportPager.getSortCriterion() + (salesReportPager.getSortDirection().equals(SortOrderEnum.ASCENDING) ? " asc" : " desc");
		//pur.created, u.email" ;
		
		Query q= s.createSQLQuery(querystr)
		.setParameter("begin", salesReportPager.getStartDate() , Hibernate.DATE)
		.setParameter("end", salesReportPager.getEndDate() , Hibernate.DATE)
		.setResultTransformer( Transformers.aliasToBean(PurchaseItemEx.class) )
		;
		
		List iter = q.list();
		int size = iter.size();
		salesReportPager.setFullListSize(size);
		if (b){
		salesReportPager.setList(iter.subList(firstIndex,  Math.min((firstIndex + fetchSize) , size )));
		}else{
			salesReportPager.setList(iter);
		}

	}

	/* (non-Javadoc)
	 * @see com.beathive.dao.PurchaseDao#getPendingPurchase(com.beathive.model.Purchase)
	 */
	public Purchase getPendingPurchase(Purchase purchase) {
		List l = getSession().createQuery("from Purchase where id=:id and customerId=:customerId and reconciliationId is NULL and authCode='PREAUTH'")
		.setParameter("id", purchase.getId())
		.setParameter("customerId", purchase.getCustomerId())
		.list();
		if (l.isEmpty()){
			return null;
		}
		return (Purchase)l.get(0);
	}

/*
	public void getSales(ShopReport salesReportPager, boolean b) {
		int fetchSize = salesReportPager.getObjectsPerPage();
		int firstIndex = salesReportPager.getFirstRecordIndex();//.getPageNumber() - 1;
		Session s = getSession();
		log.debug("salesReportPager.getStartDate(): " + salesReportPager.getStartDate());
		log.debug("salesReportPager.getEndDate(): " + salesReportPager.getEndDate());
		String querystr = "select u.email as email , sum(purch_item.price) as amount, pur.created ," +
		" (select ( purch_i.price/pur_.subTotal * ((pur_.rateFee * pur_.total) + pur_.flatFee) / 2)" +
		" from purchase_item purch_i join purchase pur_ on pur_.id=purch_i.purchaseId where purch_i.id=purch_item.id) as net" +
		" from purchase_item purch_item join purchase pur on purch_item.purchaseId=pur.id" +
		" join user_store us on purch_item.storeId=us.storeId" +
		" join app_user u on u.id=us.userId" +
		" where pur.created=:end or pur.created=:begin or pur.created between :begin and :end " +
		((salesReportPager.getStoreId()!=null) ? " and purch_item.storeId="+salesReportPager.getStoreId() : "") +
		" group by u.email" +
		" order by pur.created, u.email" ;
		
		Query q= s.createSQLQuery(querystr)
		.setParameter("begin", salesReportPager.getStartDate() , Hibernate.DATE)
		.setParameter("end", salesReportPager.getEndDate() , Hibernate.DATE)
		.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
		
		List iter = q.list();
		int size = iter.size();
		salesReportPager.setFullListSize(size);
		if (b){
		salesReportPager.setList(iter.subList(firstIndex,  Math.min((firstIndex + fetchSize) , size )));
		}else{
			salesReportPager.setList(iter);
		}

	}
	*/
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beathive.dao.PurchaseDao#getSales(com.beathive.model.ShopReport)

	public void getSales(ShopReport salesReportPager, boolean b) {
		int fetchSize = salesReportPager.getObjectsPerPage();
		int firstIndex = salesReportPager.getFirstRecordIndex();//.getPageNumber() - 1;
		Session s = getSession();
		log.debug("salesReportPager.getStartDate(): " + salesReportPager.getStartDate());
		log.debug("salesReportPager.getEndDate(): " + salesReportPager.getEndDate());
		String querystr = "select u.email as email , sum(purch_item.price) as amount, pur.created ," +
		" (select ( purch_i.price/pur_.subTotal * ((pur_.rateFee * pur_.total) + pur_.flatFee) / 2)" +
		" from purchase_item purch_i join purchase pur_ on pur_.id=purch_i.purchaseId where purch_i.id=purch_item.id) as net" +
		" from purchase_item purch_item join purchase pur on purch_item.purchaseId=pur.id" +
		" join user_store us on purch_item.storeId=us.storeId" +
		" join app_user u on u.id=us.userId" +
		" where pur.created=:end or pur.created=:begin or pur.created between :begin and :end " +
		((salesReportPager.getStoreId()!=null) ? " and purch_item.storeId="+salesReportPager.getStoreId() : "") +
		" group by u.email" +
		" order by pur.created, u.email" ;
		
		Query q= s.createSQLQuery(querystr)
		.setParameter("begin", salesReportPager.getStartDate() , Hibernate.DATE)
		.setParameter("end", salesReportPager.getEndDate() , Hibernate.DATE);
//		.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP )
		List iter = q.list();
		int size = iter.size();
		salesReportPager.setFullListSize(size);
		if (b){
		salesReportPager.setList(iter.subList(firstIndex,  Math.min((firstIndex + fetchSize) , size )));
		}else{
			salesReportPager.setList(iter);
		}

	}
*/

/* 

 * 
 * 	public void getSales(ShopReport salesReportPager, boolean b) {
		int fetchSize = salesReportPager.getObjectsPerPage();
		int firstIndex = salesReportPager.getFirstRecordIndex();//.getPageNumber() - 1;
		Session s = getSession();
		log.debug("salesReportPager.getStartDate(): " + salesReportPager.getStartDate());
		log.debug("salesReportPager.getEndDate(): " + salesReportPager.getEndDate());
		String querystr = "select u.email as email , sum(pi.price) as amount, pur.created" +
		" from purchase_item pi join purchase pur on pi.purchaseId=pur.id" +
		" join user_store us on pi.storeId=us.storeId" +
		" join app_user u on u.id=us.userId" +
		" where pur.created=:end or pur.created=:begin or pur.created between :begin and :end " +
		((salesReportPager.getStoreId()!=null) ? " and pi.storeId="+salesReportPager.getStoreId() : "") +
		" group by u.email" +
		" order by pur.created, u.email" ;
		
		Query q= s.createSQLQuery(querystr)
		.setParameter("begin", salesReportPager.getStartDate() , Hibernate.DATE)
		.setParameter("end", salesReportPager.getEndDate() , Hibernate.DATE);
//		.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP )
		List iter = q.list();
		int size = iter.size();
		salesReportPager.setFullListSize(size);
		if (b){
		salesReportPager.setList(iter.subList(firstIndex,  Math.min((firstIndex + fetchSize) , size )));
		}else{
			salesReportPager.setList(iter);

		}

	}

 */

}
