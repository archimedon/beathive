
package com.beathive.dao.hibernate;

import java.util.List;

import com.beathive.dao.hibernate.BaseDaoHibernate;
import com.beathive.model.Promo;
import com.beathive.dao.PromoDao;

import org.springframework.orm.ObjectRetrievalFailureException;

public class PromoDaoHibernate extends BaseDaoHibernate implements PromoDao {

    /**
     * @see com.beathive.dao.PromoDao#getPromos(com.beathive.model.Promo)
     */
    public List getPromos(final Promo promo) {
        return getHibernateTemplate().find("from Promo");
    }

    /**
     * @see com.beathive.dao.PromoDao#getPromo(String code)
     */
    public Promo getPromo(final Long id) {
        Promo promo = (Promo) getHibernateTemplate().get(Promo.class, id);
        if (promo == null) {
            log.warn("uh oh, promo with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(Promo.class, id);
        }

        return promo;
    }
    /**
     * @see com.beathive.dao.PromoDao#getPromo(String code)
     */
    public Promo getPromoByCode(final String code) {
    	List l = getHibernateTemplate().find("from Promo p where p.code=?", code);
    	Promo promo = null;
    	if (! l.isEmpty()){
    		promo = (Promo) l.get(0);
    	}
        return promo;
    }

    /**
     * @see com.beathive.dao.PromoDao#savePromo(Promo promo)
     */    
    public void savePromo(final Promo promo) {
        getHibernateTemplate().saveOrUpdate(promo);
    }

    /**
     * @see com.beathive.dao.PromoDao#removePromo(String code)
     */
    public void removePromo(final Long code) {
        getHibernateTemplate().delete(getPromo(code));
    }
}
