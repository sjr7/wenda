package com.suny.dao;

import com.suny.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 孙建荣 on 17-8-31.上午11:26
 */
@Mapper
public interface QuestionDAO {

    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title, content, user_id, created_date, comment_count";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    /**
     * 添加问题
     *
     * @param question 问题的详情
     * @return 插入成功的条数
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title}, #{content}, #{userId}, #{createdDate}, #{commentCount}"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
}
