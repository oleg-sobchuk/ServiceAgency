package com.serviceagency.Enums;

public enum OrderStatus {
    NEW,
    REJECTED,
    ACCEPTED,
    DONE;

    public boolean isClosed() {
        return (this == REJECTED || this == DONE);
    }
}