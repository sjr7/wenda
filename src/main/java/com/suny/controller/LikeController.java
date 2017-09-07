package com.suny.controller;

import com.suny.async.EventModel;
import com.suny.async.EventProducer;
import com.suny.async.EventType;
import com.suny.model.Comment;
import com.suny.model.EntityType;
import com.suny.model.HostHolder;
import com.suny.service.CommentService;
import com.suny.service.LikeService;
import com.suny.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 孙建荣 on 17-9-6.上午8:22
 */
@Controller
public class LikeController {


    private final LikeService likeService;

    private final HostHolder hostHolder;

    private final CommentService commentService;

    private final EventProducer eventProducer;


    @Autowired
    public LikeController(LikeService likeService, HostHolder hostHolder, CommentService commentService, EventProducer eventProducer) {
        this.likeService = likeService;
        this.hostHolder = hostHolder;
        this.commentService = commentService;
        this.eventProducer = eventProducer;
    }

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        Comment comment = commentService.getCommentById(commentId);


        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(EntityType.ENTITY_COMMENT)
                .setEntityOwnerId(comment.getUserId()).setExt("questionId", String.valueOf(comment.getEntityId())));
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }


}
















