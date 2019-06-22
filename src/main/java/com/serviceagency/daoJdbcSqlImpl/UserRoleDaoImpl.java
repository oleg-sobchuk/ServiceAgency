package com.serviceagency.daoJdbcSqlImpl;

import com.serviceagency.dao.IUserRoleDao;
import com.serviceagency.dataSource.DBCPDataSource;
import com.serviceagency.exception.DataBaseException;
import com.serviceagency.model.UserRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDaoImpl implements IUserRoleDao {

    private static final Logger logger = LogManager.getLogger(UserRoleDaoImpl.class);
    private final String SQL_EXCEPTION_MESSAGE = "SQL Exception ";

    @Override
    public List<UserRole> getAll() {
        List<UserRole> userRoles = new ArrayList<>();
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM user_role ");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                UserRole userRole = new UserRole();
                userRole.setUserId(resultSet.getLong("user_id"));
                userRole.setRoleId(resultSet.getLong("role_id"));
                userRoles.add(userRole);
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return userRoles;
    }

    /**
     * Looking for roles that belong to user with asked "user ID"
     *
     * @param userId is user ID
     * @return list of "role IDs" or empty list if user have not additional roles
     */
    @Override
    public List<Long> findByUserId(long userId) {
        List<Long> roleIds = new ArrayList<>();
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT role_id FROM user_role WHERE user_id = ? ")
        ) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    long roleId = resultSet.getLong("role_id");
                    roleIds.add(roleId);
                }
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return roleIds;
    }

    /**
     * Looking for users that have role with asked "role ID"
     *
     * @param roleId is role ID
     * @return list of "user IDs" or empty list if asked role no one use
     */
    @Override
    public List<Long> findByRoleId(long roleId) {
        List<Long> userIds = new ArrayList<>();
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT user_id FROM user_role WHERE role_id = ? ")
        ) {
            preparedStatement.setLong(1, roleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    userIds.add(userId);
                }
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return userIds;
    }


    @Override
    public boolean add(long userId, long roleId) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO user_role (user_id, role_id) VALUES (?, ?)")
        ) {
            ps.setLong(1, userId);
            ps.setLong(2, roleId);
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            }

        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return false;
    }

    @Override
    public boolean delete(long userId, long roleId) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM user_role WHERE user_id = ? AND role_id = ? ")
        ) {
            ps.setLong(1, userId);
            ps.setLong(1, roleId);

            int i = ps.executeUpdate();

            if (i > 0) {
                return true;
            }

        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return false;
    }
}
