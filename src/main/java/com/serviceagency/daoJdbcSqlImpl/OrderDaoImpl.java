package com.serviceagency.daoJdbcSqlImpl;

import com.serviceagency.dao.IOrderDao;
import com.serviceagency.enums.OrderStatus;
import com.serviceagency.model.Order;
import com.serviceagency.dataSource.DBCPDataSource;
import com.serviceagency.exception.DataBaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements IOrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);
    private final String SQL_EXCEPTION_MESSAGE = "SQL Exception ";

    @Override
    public Order findById(long id) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM order_table WHERE id = ? ")
        ){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                List<Order> orders = getOrders(resultSet);
                if (!orders.isEmpty()) {
                    return orders.get(0);
                }
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return null;
    }

    @Override
    public List<Order> findByUserId(long userId) {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM order_table WHERE user_id = ? ")
        ){
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                orders = getOrders(resultSet);
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return orders;
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM order_table WHERE order_status = ? ")
        ){
            preparedStatement.setString(1, orderStatus.toString());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                orders = getOrders(resultSet);
            }
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return orders;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM order_table ");
            ResultSet resultSet = preparedStatement.executeQuery()
        ){
            orders = getOrders(resultSet);
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersPage(int pageNum, int pageSize) {
        List<Order> orders = new ArrayList<>();
        String sql = String.format("SELECT * FROM order_table ORDER BY add_date DESC LIMIT %d OFFSET %d", pageSize, (pageNum-1)*pageSize);
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ){
            orders = getOrders(resultSet);
        }catch (SQLException e){
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return orders;
    }

    @Override
    public boolean add(Order order) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO order_table " +
                    "(id, user_id, device_desc, malfunc_desc, add_date, update_date, order_status, note, price) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, 0)")
        ){

            ps.setLong(1, order.getUserId());
            ps.setString(2, order.getDeviceDescription());
            ps.setString(3, order.getMalfunctionDescription());
            ps.setObject(4, order.getAddDate());
            ps.setObject(5, order.getUpdateDate());
            ps.setString(6, order.getOrderStatus().toString());
            ps.setString(7, order.getNote());
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
    public boolean delete(Order order) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM order_table WHERE id = ? ")
        ){
            ps.setLong(1, order.getId());

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
    public boolean update(Order order) {
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE order_table SET " +
                    "user_id = ?, device_desc = ?, malfunc_desc = ?, add_date = ?" +
                    ", update_date = ?, order_status = ?, note = ?, price = ? " +
                    "WHERE id = ?")
        ){

            ps.setLong(1, order.getUserId());
            ps.setString(2, order.getDeviceDescription());
            ps.setString(3, order.getMalfunctionDescription());
            ps.setObject(4, order.getAddDate());
            ps.setObject(5, order.getUpdateDate());
            ps.setString(6, order.getOrderStatus().toString());
            ps.setString(7, order.getNote());
            ps.setInt(8, order.getPrice());
            ps.setLong(9, order.getId());
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

    private List<Order> getOrders(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong("id"));
            order.setUserId(resultSet.getLong("user_id"));
            order.setDeviceDescription(resultSet.getString("device_desc"));
            order.setMalfunctionDescription(resultSet.getString("malfunc_desc"));
            order.setAddDate(resultSet.getObject("add_date", LocalDateTime.class));
            order.setUpdateDate(resultSet.getObject("update_date", LocalDateTime.class));
            order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
            order.setNote(resultSet.getString("note"));
            order.setPrice(resultSet.getInt("price"));
            orders.add(order);
        }
        return orders;
    }
}
