package com.suny.controller;

import com.suny.model.*;
import com.suny.service.CommentService;
import com.suny.service.LikeService;
import com.suny.service.QuestionService;
import com.suny.service.UserService;
import com.suny.utils.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 孙建荣 on 17-9-3.下午6:22
 */
@Controller
public class QuestionController {

    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;
    private final HostHolder hostHolder;
    private final UserService userService;

    private final CommentService commentService;


    private final LikeService likeService;

    @Autowired
    public QuestionController(QuestionService questionService, HostHolder hostHolder, UserService userService, CommentService commentService, LikeService likeService) {
        this.questionService = questionService;
        this.hostHolder = hostHolder;
        this.userService = userService;
        this.commentService = commentService;
        this.likeService = likeService;
    }


    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {

        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        ArrayList<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            if (hostHolder.getUser() == null) {
                vo.set("liked", 0);
            } else {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }
            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments", vos);
        return "detail";
    }

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreateDate(new Date());
            question.setTitle(title);
            if (hostHolder.getUser() == null) {
                // 这里可以设置一个匿名用户,这里我使用１0000，这是我的admin用户
                question.setUserId(10000);
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if (questionService.addQuestion(question) > 0) {
                return WendaUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }
}


















