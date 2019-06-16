package com.serviceagency.exception;

/**
 * The DataBaseException wrap SQLException when working with DataBase and make it uncheckable.
 *
 */
public class DataBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataBaseException(String message) {
        super(message);
    }
    public DataBaseException(Throwable cause) {
        super(cause);
    }
}
