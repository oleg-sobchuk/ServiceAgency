package com.serviceagency.serviceImpl;

import com.serviceagency.dao.IOrderDao;
import com.serviceagency.daoJdbcSqlImpl.OrderDaoImpl;
import com.serviceagency.enums.OrderStatus;
import com.serviceagency.exception.IllegalOrderStatusException;
import com.serviceagency.exception.NotEnoughAuthorityException;
import com.serviceagency.model.Order;
import com.serviceagency.services.IOrderService;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private final String lessAuthorityMessage = "User do not have enough authority to make the action.";
    private final String illegalRepairStatusMessage = "Only orders with status 'ACCEPTED' can be taken for repair.";
    private final String illegalAcceptRejectStatusMessage = "Only orders with status 'NEW' can be processed.";
    private final String illegalPriceMessage = "Price must be positive decimal and not 0.";
    private final String illegalNoteMessage = "Note must contains cause. It cant be empty.";
    private final String illegalActionMessage = "Unknown action.";


    private Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private IOrderDao orderDao = new OrderDaoImpl();


    private Order makeRepair(List<String> userRoles, String orderId) {
        Order order = orderDao.findById(Long.parseLong(orderId));
        if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
            if (userRoles.contains("MASTER")) {
                order.setOrderStatus(OrderStatus.DONE);
                order.setUpdateDate(LocalDateTime.now());
                return order;
            }else {
                logger.warn(lessAuthorityMessage);
                throw new NotEnoughAuthorityException(lessAuthorityMessage);
            }
        }else {
            logger.warn(illegalRepairStatusMessage);
            throw new IllegalOrderStatusException(illegalRepairStatusMessage);
        }
    }

    private Order makeAccept(List<String> userRoles, String orderId, String orderPrice) {
        Order order = orderDao.findById(Long.parseLong(orderId));
        int price = Integer.parseInt(orderPrice);
        if (price <= 0) {
            logger.warn(illegalPriceMessage);
            throw new IllegalArgumentException(illegalPriceMessage);
        }
        if (order.getOrderStatus() == OrderStatus.NEW) {
            if (userRoles.contains("MANAGER")) {
                order.setPrice(price);
                order.setOrderStatus(OrderStatus.ACCEPTED);
                order.setUpdateDate(LocalDateTime.now());
                return order;
            }else {
                logger.warn(lessAuthorityMessage);
                throw new NotEnoughAuthorityException(lessAuthorityMessage);
            }
        }else {
            logger.warn(illegalAcceptRejectStatusMessage);
            throw new IllegalOrderStatusException(illegalAcceptRejectStatusMessage);
        }
    }

    private Order makeReject(List<String> userRoles, String orderId, String note) {
        Order order = orderDao.findById(Long.parseLong(orderId));
        if (note.trim().isEmpty()) {
            logger.warn(illegalNoteMessage);
            throw new IllegalArgumentException(illegalNoteMessage);
        }
        if (order.getOrderStatus() == OrderStatus.NEW) {
            if (userRoles.contains("MANAGER")) {
                order.setNote(note);
                order.setOrderStatus(OrderStatus.REJECTED);
                order.setUpdateDate(LocalDateTime.now());
                return order;
            }else {
                logger.warn(lessAuthorityMessage);
                throw new NotEnoughAuthorityException(lessAuthorityMessage);
            }
        }else {
            logger.warn(illegalAcceptRejectStatusMessage);
            throw new IllegalOrderStatusException(illegalAcceptRejectStatusMessage);
        }
    }

    @Override
    public boolean makeAction(List<String> userRoles, String action, String orderId, String note, String price) {
        Order order;
        switch (action) {
            case "accept" :
                order = makeAccept(userRoles, orderId, price);
                break;
            case "reject" :
                order = makeReject(userRoles, orderId, note);
                break;
            case "repair" :
                order = makeRepair(userRoles, orderId);
                break;
            default :
                logger.warn(illegalActionMessage);
                throw new IllegalArgumentException(illegalActionMessage);
        }        
        return orderDao.update(order);
    }

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
