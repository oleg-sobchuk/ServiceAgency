package com.serviceagency.Enums;

public enum Status {
    NEW,
    REJECTED,
    ACCEPTED,
    DONE;

    public boolean isClosed() {
        return (this == REJECTED || this == DONE);
    }
}
