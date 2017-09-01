package com.suny.model;

import java.util.Date;

/**
 * Created by 孙建荣 on 17-9-1.上午10:39
 */
public class LoginTicket {

    private int id;
    private int userId;
    private Date expired;
    // 0有效,1无效
    private int status;

    private String ticket;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}





















