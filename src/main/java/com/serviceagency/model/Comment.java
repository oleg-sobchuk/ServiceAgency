package com.serviceagency.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private long orderId;
    private LocalDateTime createDate;
    private String text;

    public Comment() {
    }

    public Comment(long userId, long orderId, String text) {
        this.userId = userId;
        this.orderId = orderId;
        this.text = text;
        createDate = LocalDateTime.now();
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
