package com.serviceagency.Model;

import com.serviceagency.Enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private String deviceDescription;
    private String malfunctionDescription;
    private LocalDateTime addDate;
    private LocalDateTime updateDate;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(long userId, String deviceDescription, String malfunctionDescription) {
        this.userId = userId;
        this.deviceDescription = deviceDescription;
        this.malfunctionDescription = malfunctionDescription;
        LocalDateTime now = LocalDateTime.now();
        addDate  = now;
        updateDate = now;
        orderStatus = OrderStatus.NEW;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getMalfunctionDescription() {
        return malfunctionDescription;
    }

    public void setMalfunctionDescription(String malfunctionDescription) {
        this.malfunctionDescription = malfunctionDescription;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isClosed() {
        return orderStatus.isClosed();
    }
}
