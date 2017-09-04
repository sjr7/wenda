package com.suny.controller;

import com.suny.model.Comment;
import com.suny.model.EntityType;
import com.suny.model.HostHolder;
import com.suny.service.CommentService;
import com.suny.service.QuestionService;
import com.suny.service.SensitiveService;
import com.suny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * Created by 孙建荣 on 17-9-4.下午4:44
 */
@Controller
public class CommentController {
    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final HostHolder hostHolder;
    private final UserService userService;
    private final CommentService commentService;
    private final QuestionService questionService;
    private final SensitiveService sensitiveService;

    @Autowired
    public CommentController(HostHolder hostHolder, UserService userService, CommentService commentService, QuestionService questionService, SensitiveService sensitiveService) {
        this.hostHolder = hostHolder;
        this.userService = userService;
        this.commentService = commentService;
        this.questionService = questionService;
        this.sensitiveService = sensitiveService;
    }

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            // 过滤content
            Comment comment = new Comment();
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                // 在我的数据库里面10000用户为管理员,这里起到一个匿名用户的作用
                comment.setUserId(10000);
            }
            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setCreateDate(new Date());
            comment.setStatus(0);

            commentService.addComment(comment);

            // 更新题目里面的评论数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);
            //异步
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }
}














