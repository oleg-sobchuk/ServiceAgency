package com.serviceagency.serviceImpl;

import com.serviceagency.dao.IOrderDao;
import com.serviceagency.daoJdbcSqlImpl.OrderDaoImpl;
import com.serviceagency.enums.OrderStatus;
import com.serviceagency.model.Order;
import com.serviceagency.services.IOrderService;

import java.util.List;

public class OrderServiceImpl implements IOrderService {
    private IOrderDao orderDao = new OrderDaoImpl();

    @Override
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return orderDao.findByUserId(userId);
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return orderDao.findByOrderStatus(orderStatus);
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean add(Order order) {
        return orderDao.add(order);
    }

    @Override
    public boolean delete(Order order) {
        return orderDao.delete(order);
    }

    @Override
    public boolean update(Order order) {
        return orderDao.update(order);
    }
}
