package com.suny.utils;

/**
 * Created by 孙建荣 on 17-9-6.上午8:05
 */
public class RedisKeyUtil {

    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    // 获取粉丝
    private static String BIZ_FOLLOWER = "FOLLOWER";
    // 关注的对象
    private static String BIZ_FOLOWEE = "FOLLOWEE";

    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

    /**
     * 某个用户的粉丝key
     *
     * @param entityType 实体类型
     * @param entityId   实体ID
     * @return
     */
    public static String getFollowerKey(int entityType, int entityId) {
        return BIZ_FOLLOWER + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    /**
     * 每个用户对某类实体的关注key
     *
     * @param userId     用户的ID
     * @param entityType 实体类型
     * @return
     */
    public static String getFolloweeKey(int userId, int entityType) {
        return BIZ_FOLLOWER + SPLIT + String.valueOf(userId) + SPLIT + String.valueOf(entityType);
    }


}















