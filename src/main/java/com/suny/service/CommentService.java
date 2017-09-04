package com.suny.service;

import com.suny.dao.CommentDAO;
import com.suny.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 孙建荣 on 17-9-4.上午8:39
 */
@Service
public class CommentService {

    private final CommentDAO commentDAO;

    @Autowired
    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public List<Comment> getCommentsByEntity(int entity, int entityType) {
        return commentDAO.selectByEntity(entity, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }

    public void deleteComment(int entityId, int entityType) {
        // 状态为1则表示被逻辑删除
        commentDAO.updateStatus(entityId, entityType, 1);
    }

}
















