package com.suny.controller;

import com.suny.model.HostHolder;
import com.suny.model.Message;
import com.suny.model.User;
import com.suny.model.ViewObject;
import com.suny.service.MessageService;
import com.suny.service.UserService;
import com.suny.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 孙建荣 on 17-9-4.下午7:38
 */
@Controller
public class MessageController {
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    private final UserService userService;

    private final HostHolder hostHolder;


    @Autowired
    public MessageController(MessageService messageService, UserService userService, HostHolder hostHolder) {
        this.messageService = messageService;
        this.userService = userService;
        this.hostHolder = hostHolder;
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message message : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", message);
                int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user", user);
                vo.set("unread", messageService.getConversationUnreadCount(localUserId, message.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @Param("conversationId") String conversationId) {
        if (StringUtils.isBlank(conversationId)) {
            return "/msg/list";
        }
        try {
            // 得到两个账号之间的会话列表数据
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();

            // 循环遍历会话列表,每条会话数据到前端就是一个会话框
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                // 得到发送消息的用户信息
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                // 设置头像以及用户ID
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                // 把每一条消息放到消息集合里面去
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取消息详情失败" + e.getMessage());
        }
        // 这里还应该把数据库里面会话的阅读状态设置为已经阅读,也就是has_read修改为1
        messageService.updateMessageReadStatus(conversationId);
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName, @RequestParam("content") String content) {

        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "未登录");
            }
            User user = userService.selectByName(toName);
            if (user == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }
            Message message = new Message();
            message.setContent(content);
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setCreateDate(new Date());
            int fromId = hostHolder.getUser().getId();
            int toId = user.getId();
            message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));

            // 这里发送消息应该有这样一个逻辑,发送消息的一方发送消息后消息肯定是被阅读了的,不可能说自己发送的消息还没有被阅读
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("增加站内信失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "插入站内信失败");
        }
    }


    @RequestMapping(path = {"/msg/jsonAddMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setContent(content);
            message.setFromId(fromId);
            message.setToId(toId);
            message.setCreateDate(new Date());
            message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.addMessage(message);
            return WendaUtil.getJSONString(message.getId());
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "插入评论失败");
        }
    }


}


















