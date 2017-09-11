package com.suny.controller;

import com.suny.dao.FeedDAO;
import com.suny.model.EntityType;
import com.suny.model.Feed;
import com.suny.model.HostHolder;
import com.suny.service.FeedService;
import com.suny.service.FollowService;
import com.suny.utils.JedisAdapter;
import com.suny.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙建荣 on 17-9-10.下午10:21
 */
@Controller
public class FeedController {
    private static Logger logger = LoggerFactory.getLogger(FeedController.class);

    private final FeedService feedService;

    private final FollowService followService;

    private final HostHolder hostHolder;

    private final JedisAdapter jedisAdapter;


    @Autowired
    public FeedController(FeedService feedService, FollowService followService, HostHolder hostHolder, JedisAdapter jedisAdapter) {
        this.feedService = feedService;
        this.followService = followService;
        this.hostHolder = hostHolder;
        this.jedisAdapter = jedisAdapter;
    }

    @RequestMapping(path = "/pushfeeds", method = {RequestMethod.POST, RequestMethod.GET})
    private String getPushFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTImeline(localUserId), 0, 10);
        List<Feed> feeds = new ArrayList<>();
        for (String feedId : feedIds) {
            Feed feed = feedService.getById(Integer.parseInt(feedId));
            if (feed != null) {
                feeds.add(feed);
            }
        }
        model.addAttribute("feeds", feeds);
        return "feeds";
    }

    @RequestMapping(path = "/pullfeeds", method = {RequestMethod.POST, RequestMethod.GET})
    private String getPullFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();
        if (localUserId != 0) {
            // 关注的人
            followService.getFollowers(localUserId, EntityType.ENTITY_USER, Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}


























