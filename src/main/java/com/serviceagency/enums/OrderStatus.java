package com.serviceagency.enums;

public enum OrderStatus {
    NEW,
    REJECTED,
    ACCEPTED,
    DONE;

    public boolean isClosed() {
        return (this == REJECTED || this == DONE);
    }
}