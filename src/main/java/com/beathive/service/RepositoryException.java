package com.beathive.service;


/**
 * A general RepositoryException that can be thrown to indicate an error
 * in business logic.
 *
 * <p>
 * <a href="RepositoryException.java.do"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison</a>
 */
public class RepositoryException extends Exception {
    //~ Constructors ===========================================================

    /**
     * Constructor for RepositoryException.
     */
    public RepositoryException() {
        super();
    }

    /**
     * Constructor for RepositoryException.
     *
     * @param message
     */
    public RepositoryException(String message) {
        super(message);
    }

    /**
     * Constructor for RepositoryException.
     *
     * @param message
     * @param cause
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for RepositoryException.
     *
     * @param cause
     */
    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
