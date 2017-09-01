package com.suny.service;

import com.suny.dao.QuestionDAO;
import com.suny.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 孙建荣 on 17-9-1.上午10:15
 */
@Service
public class QuestionService {

    private final QuestionDAO questionDAO;

    @Autowired
    public QuestionService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public List<Question> getLatestQuestion(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

}
