package com.serviceagency.Model;

import com.serviceagency.Enums.Status;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private String deviceDescription;
    private String malfunctionDescription;
    private Date addDate;
    private Date updateDate;
    private Status status;

    public Order(long userId, String deviceDescription, String malfunctionDescription) {
        this.userId = userId;
        this.deviceDescription = deviceDescription;
        this.malfunctionDescription = malfunctionDescription;
        Date now = new Date();
        addDate  = now;
        updateDate = now;
        status = Status.NEW;
    }

    public long getId() {
        return id;
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

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isClosed() {
        return status.isClosed();
    }
}
