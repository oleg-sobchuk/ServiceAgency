package com.serviceagency.dao;

import com.serviceagency.model.Role;
import com.serviceagency.model.User;

import java.util.List;

public interface IUserDao {
    User findById(long id);
    User findByName(String name);
    List<User> getAll();
    boolean addUser(String name, String password);
    boolean deleteUser(long id);
    boolean isValid(String name, String password);
    List<String> getRoleNames(long userId);
}
