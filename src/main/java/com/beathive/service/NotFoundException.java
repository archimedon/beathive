/**
 * 
 */
package com.beathive.service;

/**
 * @author ron
 *
 */
public class NotFoundException extends ServiceException {
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
