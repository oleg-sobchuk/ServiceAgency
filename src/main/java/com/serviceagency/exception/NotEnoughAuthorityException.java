package com.serviceagency.exception;

public class NotEnoughAuthorityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotEnoughAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotEnoughAuthorityException(String message) {
        super(message);
    }
    public NotEnoughAuthorityException(Throwable cause) {
        super(cause);
    }
}
