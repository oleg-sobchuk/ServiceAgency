package com.serviceagency.Dao;

import com.serviceagency.Model.User;

public interface IUserDao {
    User findById(long id);
    User findByName(String name);
    boolean addUser(String name, String password);
    boolean deleteUser(int id);
}
