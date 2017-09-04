package com.suny.service;

import com.suny.dao.MessageDAO;
import com.suny.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 孙建荣 on 17-9-4.下午7:36
 */
@Service
public class MessageService {

    private final MessageDAO messageDAO;
    private final SensitiveService sensitiveService;

    @Autowired
    public MessageService(MessageDAO messageDAO, SensitiveService sensitiveService) {
        this.messageDAO = messageDAO;
        this.sensitiveService = sensitiveService;
    }


    public int addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConvesationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }

}
