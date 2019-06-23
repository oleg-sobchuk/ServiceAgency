package com.serviceagency.exception;

public class IllegalOrderStatusException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalOrderStatusException(String message, Throwable cause) {
        super(message, cause);
    }
    public IllegalOrderStatusException(String message) {
        super(message);
    }
    public IllegalOrderStatusException(Throwable cause) {
        super(cause);
    }
}
