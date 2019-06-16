package com.serviceagency.Dao;

import com.serviceagency.Model.Role;

import java.util.List;

public interface IRoleDao {
    List<Role> getAll();
    Role findById(long id);
    Role findByName(String roleName);
    boolean create(Role role);
    boolean delete(Role role);
}
