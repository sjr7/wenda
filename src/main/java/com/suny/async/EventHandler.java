package com.suny.async;

import java.util.List;

/**
 * Created by 孙建荣 on 17-9-7.下午4:38
 */
public interface EventHandler {

    void doHandle(EventModel model);


    List<EventType> getSupportEventTypes();
}
