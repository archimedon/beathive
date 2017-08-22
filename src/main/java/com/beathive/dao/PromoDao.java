
package com.beathive.dao;

import java.util.List;

import com.beathive.dao.Dao;
import com.beathive.model.Promo;

public interface PromoDao extends Dao {

    /**
     * Retrieves all of the promos
     */
    public List getPromos(Promo promo);

    /**
     * Gets promo's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if 
     * nothing is found.
     * 
     * @param code the promo's code
     * @return promo populated promo object
     */
    public Promo getPromo(final Long id);
    
    public Promo getPromoByCode(final String code);

    /**
     * Saves a promo's information
     * @param promo the object to be saved
     */    
    public void savePromo(Promo promo);

    /**
     * Removes a promo from the database by id
     * @param id the promo's id
     */
    public void removePromo(final Long id);
}

