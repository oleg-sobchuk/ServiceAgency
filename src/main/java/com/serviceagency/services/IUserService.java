package com.serviceagency.services;

import com.serviceagency.model.User;

import java.util.List;

public interface IUserService {
    User findById(long id);
    User findByName(String name);
    List<User> getAll();
    boolean addUser(String name, String password);
    boolean deleteUser(long id);
    boolean isValid(String name, String password);
    List<String> getRoleNames(long id);
    boolean isOnlyUserRole(long id);
}
