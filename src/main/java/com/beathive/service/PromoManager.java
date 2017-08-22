
package com.beathive.service;

import java.util.List;

import com.beathive.service.Manager;
import com.beathive.model.Promo;
import com.beathive.dao.PromoDao;

public interface PromoManager extends Manager {
    /**
     * Retrieves all of the promos
     */
    public List getPromos(Promo promo);

    public Promo getPromo(final Long id);
    
    /**
     * Gets promo's information based on code.
     * @param code the promo's code
     * @return promo populated promo object
     */
    public Promo getPromoByCode(final String code);
    /**
     * Saves a promo's information
     * @param promo the object to be saved
     */
    public void savePromo(Promo promo);

    /**
     * Removes a promo from the database by code
     * @param code the promo's code
     */
    public void removePromo(final Long code);
}

