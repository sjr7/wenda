package com.suny.controller;

import com.suny.model.HostHolder;
import com.suny.model.Question;
import com.suny.service.QuestionService;
import com.suny.service.UserService;
import com.suny.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by 孙建荣 on 17-9-3.下午6:22
 */
@Controller
public class QuestionController {

    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;
    private final HostHolder hostHolder;
    private final UserService userService;


    @Autowired
    public QuestionController(QuestionService questionService, HostHolder hostHolder, UserService userService) {
        this.questionService = questionService;
        this.hostHolder = hostHolder;
        this.userService = userService;
    }


    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
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


















