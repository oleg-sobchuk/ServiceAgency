package com.serviceagency.DaoJdbcSqlImpl;

import com.serviceagency.Dao.IRoleDao;
import com.serviceagency.Model.Role;
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

public class RoleDaoImpl implements IRoleDao {

    private static final Logger logger = LogManager.getLogger(RoleDaoImpl.class);
    private final String SQL_EXCEPTION_MESSAGE = "SQL Exception ";

    @Override
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM role ");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setRoleName(resultSet.getString("name"));
                roles.add(role);
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return roles;
    }

    @Override
    public Role findById(long id) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM role WHERE id = ? ")
        ) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Role role = new Role();
                    role.setId(resultSet.getLong("id"));
                    role.setRoleName(resultSet.getString("name"));

                    return role;
                }
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return null;
    }

    @Override
    public Role findByName(String roleName) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM role WHERE name = ? ")
        ) {
            preparedStatement.setString(1, roleName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Role role = new Role();
                    role.setId(resultSet.getInt("id"));
                    role.setRoleName(roleName);

                    return role;
                }
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return null;
    }

    @Override
    public boolean create(Role role) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO role (id, name) VALUES (NULL, ?)")
        ) {

            ps.setString(1, role.getRoleName());

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
    public boolean delete(Role role) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM role WHERE id = ? ")
        ) {
            ps.setLong(1, role.getId());

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
