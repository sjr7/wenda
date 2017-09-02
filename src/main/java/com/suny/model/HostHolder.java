package com.suny.model;

import org.springframework.stereotype.Component;

/**
 * Created by 孙建荣 on 17-9-1.下午10:38
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }


}















