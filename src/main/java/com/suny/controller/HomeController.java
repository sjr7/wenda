package com.suny.controller;

import com.suny.model.*;
import com.suny.service.CommentService;
import com.suny.service.FollowService;
import com.suny.service.QuestionService;
import com.suny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制项目的主页面相关类
 * Created by 孙建荣 on 17-9-1.上午10:21
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final QuestionService questionService;

    private final UserService userService;

    private final FollowService followService;

    private final HostHolder hostHolder;

    private final CommentService commentService;

    @Autowired
    public HomeController(QuestionService questionService, UserService userService, FollowService followService, HostHolder hostHolder, CommentService commentService) {
        this.questionService = questionService;
        this.userService = userService;
        this.followService = followService;
        this.hostHolder = hostHolder;
        this.commentService = commentService;
    }

    /**
     * 私有的获取问题列表方法
     *
     * @param userId 用户的id
     * @param offset 从数据库的第几条开始查询
     * @param limit  限制查询几条数据
     * @return 查询出来的问题集合
     */
    private List<ViewObject> getQuestion(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestion(userId, offset, limit);
        List<ViewObject> objectList = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            viewObject.set("user", userService.getUser(question.getUserId()));
            objectList.add(viewObject);
        }
        return objectList;
    }


    /**
     * 项目主页面,默认获取前10条问题列表
     *
     * @param model 存放数据模型
     * @param pop   如果为0则查询所有问题,否则就查询指定用户问题列表
     * @return 对应的主页面
     */
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getQuestion(0, 0, 10));
        return "index";
    }

    /**
     * 根据用户的id查询用户的问题列表
     *
     * @param model  存放数据模型
     * @param userId 指定用户的id
     * @return 查询出来的结果页面
     */
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestion(userId, 0, 10));
        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";
    }

}
















