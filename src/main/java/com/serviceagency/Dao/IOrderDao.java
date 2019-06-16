package com.serviceagency.Dao;

import com.serviceagency.Enums.OrderStatus;
import com.serviceagency.Model.Order;

import java.util.List;

public interface IOrderDao {
    Order findById(long id);
    List<Order> findByUserId(long userId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> getAll();
    boolean add(Order order);
    boolean delete(Order order);
    boolean update(Order order);
}
