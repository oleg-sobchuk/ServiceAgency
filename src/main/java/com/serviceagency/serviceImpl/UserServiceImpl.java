package com.serviceagency.serviceImpl;

import com.serviceagency.dao.IUserDao;
import com.serviceagency.daoJdbcSqlImpl.UserDaoImpl;
import com.serviceagency.model.User;
import com.serviceagency.services.IUserService;

import java.util.List;

public class UserServiceImpl implements IUserService {

    private IUserDao userDao = new UserDaoImpl();

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public boolean addUser(String name, String password) {
        return userDao.addUser(name, password);
    }

    @Override
    public boolean deleteUser(long id) {
        return userDao.deleteUser(id);
    }

    @Override
    public boolean isValid(String name, String password) {
        return userDao.isValid(name, password);
    }

    @Override
    public List<String> getRoleNames(long id) {
        return userDao.getRoleNames(id);
    }

    @Override
    public boolean isOnlyUserRole(long id) {
        List<String> roles;
        roles = userDao.getRoleNames(id);
        return roles.isEmpty();
    }
}
