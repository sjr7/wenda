package com.suny.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.suny.async.EventHandler;
import com.suny.async.EventModel;
import com.suny.async.EventType;
import com.suny.model.EntityType;
import com.suny.model.Feed;
import com.suny.model.Question;
import com.suny.model.User;
import com.suny.service.FeedService;
import com.suny.service.FollowService;
import com.suny.service.QuestionService;
import com.suny.service.UserService;
import com.suny.utils.JedisAdapter;
import com.suny.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by 孙建荣 on 17-9-11.上午7:57
 */
@Component
public class FeedHandler implements EventHandler {

    private final FollowService followService;

    private final UserService userService;

    private final FeedService feedService;

    private final JedisAdapter jedisAdapter;

    private final QuestionService questionService;

    @Autowired
    public FeedHandler(FollowService followService, UserService userService, FeedService feedService, JedisAdapter jedisAdapter, QuestionService questionService) {
        this.followService = followService;
        this.userService = userService;
        this.feedService = feedService;
        this.jedisAdapter = jedisAdapter;
        this.questionService = questionService;
    }

    private String buildFeedData(EventModel model) {
        HashMap<String, String> map = new HashMap<>();
        // 触发用户都是通用的
        User actor = userService.getUser(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(model.getActorId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (model.getType() == EventType.COMMENT ||
                (model.getType() == EventType.FOLLOW && model.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = questionService.getById(model.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }


        return null;
    }

    @Override
    public void doHandle(EventModel model) {
        // 为了测试,把model的user的modelId随一下
        Random random = new Random();
        model.setActorId(1 + random.nextInt(10));

        // 构造一个新鲜事
        Feed feed = new Feed();
        feed.setCreateDate(new Date());
        feed.setType(model.getType().getValue());
        feed.setUserId(model.getActorId());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            // 不支持的feed
            return;
        }
        feedService.addFeed(feed);

        // 获取所有的粉丝
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, model.getActorId(), Integer.MAX_VALUE);

        //系统队列
        followers.add(0);

        // 给所有的粉丝推事件
        for (Integer follower : followers) {
            String tImelineKey = RedisKeyUtil.getTImeline(follower);
            jedisAdapter.lpush(tImelineKey, String.valueOf(feed.getId()));
            // 限制最长的长度,如果timeLineKey的长度过于大,就删除后面的额新鲜事

        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}























