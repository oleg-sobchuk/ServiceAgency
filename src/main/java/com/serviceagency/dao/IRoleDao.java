package com.serviceagency.dao;

import com.serviceagency.model.Role;

import java.util.List;

public interface IRoleDao {
    List<Role> getAll();
    Role findById(long id);
    Role findByName(String roleName);
    boolean create(Role role);
    boolean delete(Role role);
}
