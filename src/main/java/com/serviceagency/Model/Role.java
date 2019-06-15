package com.serviceagency.Model;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
