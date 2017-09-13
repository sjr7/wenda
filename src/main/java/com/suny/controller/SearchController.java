package com.suny.controller;

import com.suny.model.EntityType;
import com.suny.model.Question;
import com.suny.model.ViewObject;
import com.suny.service.FollowService;
import com.suny.service.QuestionService;
import com.suny.service.SearchService;
import com.suny.service.UserService;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙建荣 on 17-9-13.下午10:52
 */
@Controller
public class SearchController {

    private static Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;
    private final FollowService followService;
    private final UserService userService;
    private final QuestionService questionService;


    @Autowired
    public SearchController(SearchService searchService, FollowService followService, UserService userService, QuestionService questionService) {
        this.searchService = searchService;
        this.followService = followService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @RequestMapping(path = {"/search"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count, "<em>", "</em>");

            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q = questionService.getById(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getContent() != null) {
                    q.setContent(question.getContent());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                vo.set("question", vos);
                vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
                vo.set("user", userService.getUser(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (IOException e) {
            logger.error("传输流出错", e.getMessage());
        } catch (SolrServerException e) {
            logger.error("solr服务失败", e.getMessage());
        }
        return "result";
    }


}


























