package com.suny.service;

import com.suny.dao.QuestionDAO;
import com.suny.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by 孙建荣 on 17-9-1.上午10:15
 */
@Service
public class QuestionService {

    private final QuestionDAO questionDAO;

    private final SensitiveService sensitiveService;

    @Autowired
    public QuestionService(QuestionDAO questionDAO, SensitiveService sensitiveService) {
        this.questionDAO = questionDAO;
        this.sensitiveService = sensitiveService;
    }


    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // 敏感词审查
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public List<Question> getLatestQuestion(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int updateCommentCount(int id, int count) {
        return questionDAO.updateCommentCount(id, count);
    }
}














