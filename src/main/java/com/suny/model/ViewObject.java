package com.suny.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装的对象实体类
 * Created by 孙建荣 on 17-8-31.下午12:53
 */
public class ViewObject {

    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
