package com.serviceagency.Dao;

import com.serviceagency.Model.User;

import java.util.List;

public interface IUserDao {
    User findById(long id);
    User findByName(String name);
    List<User> getAll();
    boolean addUser(String name, String password);
    boolean deleteUser(int id);
}
