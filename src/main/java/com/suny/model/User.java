package com.suny.model;

/**
 * 用户信息实体类
 * Created by 孙建荣 on 17-8-31.下午12:42
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.salt = "";
        this.headUrl = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}














