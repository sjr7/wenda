package com.suny.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by 孙建荣 on 17-9-5.下午3:56
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(JedisAdapter.class);


    private JedisPool jedisPool;


    public static void print(int index, Object obj) {
        System.out.println(String.format("第%d次:%s", index, obj.toString()));
    }

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("redis://localhost:6379/9")) {
            jedis.flushDB();
            //get srt
            jedis.set("hello", "world");
            print(1, jedis.get("hello"));
            jedis.rename("hello", "newhello");
            print(1, jedis.get("newhello"));
            jedis.setex("hello2", 1800, "world");

            //
            jedis.set("pv", "100");
            jedis.incr("pv");
            jedis.incrBy("pv", 5);
            print(2, jedis.get("pv"));

            jedis.decr("pv");
            jedis.decrBy("pv", 10);
            print(3, jedis.get("pv"));


            print(3, jedis.keys("*"));

            String listname = "list";
            jedis.del(listname);

            for (int i = 0; i < 10; i++) {
                jedis.lpush(listname, "a" + String.valueOf(i));
            }

            print(4, jedis.lrange(listname, 0, 12));
            print(4, jedis.lrange(listname, 0, 3));
            print(5, jedis.llen(listname));
            print(6, jedis.lpop(listname));
            print(5, jedis.llen(listname));
            print(7, jedis.lrange(listname, 2, 6));
            print(8, jedis.lrange(listname, 2, 6));
            print(8, jedis.lindex(listname, 3));
            print(9, jedis.linsert(listname, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
            print(9, jedis.linsert(listname, BinaryClient.LIST_POSITION.BEFORE, "a4", "bb"));


            //hash
            String userkey = "userxx";
            jedis.hset(userkey, "name", "jim");
            jedis.hset(userkey, "age", "12");
            jedis.hset(userkey, "phone", "1232132131");
            print(12, jedis.hget(userkey, "name"));
            print(13, jedis.hgetAll(userkey));
            jedis.hdel(userkey, "phone");
            print(14, jedis.hgetAll(userkey));
            print(15, jedis.hexists(userkey, "email"));
            print(16, jedis.hexists(userkey, "age"));
            print(17, jedis.hkeys(userkey));
            print(18, jedis.hvals(userkey));
            jedis.hsetnx(userkey, "school", "zju");
            jedis.hsetnx(userkey, "name", "yxy");
            print(19, jedis.hgetAll(userkey));

            // set
            String likeKey1 = "commentLike1";
            String likeKey2 = "commentLike2";

            for (int i = 0; i < 10; i++) {
                jedis.sadd(likeKey1, String.valueOf(i));
                jedis.sadd(likeKey2, String.valueOf(i * i));
            }

            print(20, jedis.smembers(likeKey1));
            print(21, jedis.smembers(likeKey2));
            print(22, jedis.sunion(likeKey1, likeKey2));
            print(23, jedis.sdiff(likeKey1, likeKey2));
            print(24, jedis.sinter(likeKey1, likeKey2));
            print(25, jedis.sismember(likeKey1, "12"));
            print(26, jedis.sismember(likeKey1, "16"));
            print(27, jedis.smembers(likeKey1));
            jedis.smove(likeKey2, likeKey1, "25");
            print(28, jedis.smembers(likeKey1));
            print(29, jedis.scard(likeKey1));


        } catch (Exception e) {
            logger.error("操作redis失败" + e.getMessage());
        }


    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
























