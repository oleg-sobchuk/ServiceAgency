package com.serviceagency.daoJdbcSqlImpl;

import com.serviceagency.dao.IUserDao;
import com.serviceagency.model.User;
import com.serviceagency.dataSource.DBCPDataSource;
import com.serviceagency.exception.DataBaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final String SQL_EXCEPTION_MESSAGE = "SQL Exception ";

    @Override
    public User findById(long id) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM user WHERE id = ? ")
        ){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                List<User> users = getUsers(resultSet);
                if (!users.isEmpty()) {
                    return users.get(0);
                }
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM user WHERE name = ? ")
        ){
            preparedStatement.setString(1, name);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                List<User> users = getUsers(resultSet);
                if (!users.isEmpty()) {
                    return users.get(0);
                }
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM user ")
        ){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                users = getUsers(resultSet);
                logger.info("Users read SUCCESS - read " + users.size() + "users");
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return users;
    }

    @Override
    public boolean addUser(String name, String password) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user (id, name, password) VALUES (NULL, ?, ?)")
        ){
            ps.setString(1, name);
            ps.setString(2, password);
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            }

        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(long id) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id = ? ")
        ){
            ps.setLong(1, id);

            int i = ps.executeUpdate();

            if (i > 0) {
                return true;
            }

        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return false;
    }

    @Override
    public boolean isValid(String name, String password) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM user WHERE name = ? AND password = ? ")
        ){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return true;
                }
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return false;
    }

    @Override
    public List<String> getRoleNames(long userId) {
        List<String> roles = new ArrayList<>();
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT r.name FROM user_role ur " +
                    "JOIN role r ON ur.role_id = r.id " +
                    "JOIN user u ON ur.user_id = u.id " +
                    "WHERE u.id = ? ")
        ){
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    roles.add(resultSet.getString("name"));
                }
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return roles;
    }


    private List<User> getUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
        }
        return users;
    }
}
