package com.suny.controller;

import com.suny.model.Question;
import com.suny.model.ViewObject;
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
 * Created by 孙建荣 on 17-9-1.上午10:21
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final QuestionService questionService;

    private final UserService userService;

    @Autowired
    public HomeController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    private List<ViewObject> getQuestion(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestion(userId, offset, limit);
        List<ViewObject> objectList = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUser(question.getUserId()));
            objectList.add(viewObject);
        }
        return objectList;
    }


    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value = "pop", defaultValue = "0") int top) {
        model.addAttribute("vos", getQuestion(0, 0, 10));
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestion(userId, 0, 10));
        return "index";
    }

}
















