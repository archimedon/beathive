/**
 * 
 */
package com.beathive.service.commerce;

import com.beathive.service.ServiceException;

/**
 * @author ron
 *
 */
public class BillingException extends ServiceException {

	/**
     * Constructor for ServiceException.
     */
    public BillingException() {
        super();
    }

    /**
     * Constructor for ServiceException.
     *
     * @param message
     */
    public BillingException(String message) {
        super(message);
    }

    /**
     * Constructor for ServiceException.
     *
     * @param message
     * @param cause
     */
    public BillingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for ServiceException.
     *
     * @param cause
     */
    public BillingException(Throwable cause) {
        super(cause);
    }

}
