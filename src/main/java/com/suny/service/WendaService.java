package com.suny.service;

import org.springframework.stereotype.Service;

/**
 * Created by 孙建荣 on 17-9-1.下午10:45
 */
@Service
public class WendaService {

    public String getMessage(int userId) {
        return "HelloMessage" + String.valueOf(userId);
    }
}
