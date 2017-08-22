
package com.beathive.service.impl;

import java.util.List;

import com.beathive.service.impl.BaseManager;
import com.beathive.model.Promo;
import com.beathive.dao.PromoDao;
import com.beathive.service.PromoManager;

public class PromoManagerImpl extends BaseManager implements PromoManager {
    private PromoDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao
     */
    public void setPromoDao(PromoDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.beathive.service.PromoManager#getPromos(com.beathive.model.Promo)
     */
    public List getPromos(final Promo promo) {
        return dao.getPromos(promo);
    }

    /**
     * @see com.beathive.service.PromoManager#getPromo(Long id)
     */
    public Promo getPromo(final Long id) {
        return dao.getPromo(new Long(id));
    }
    /**
     * @see com.beathive.service.PromoManager#getPromoByCode(String code)
     */
    public Promo getPromoByCode(final String code) {
        return dao.getPromoByCode(code);
    }


    /**
     * @see com.beathive.service.PromoManager#savePromo(Promo promo)
     */
    public void savePromo(Promo promo) {
        dao.savePromo(promo);
    }

    /**
     * @see com.beathive.service.PromoManager#removePromo(String code)
     */
    public void removePromo(final Long code) {
        dao.removePromo(new Long(code));
    }
}
