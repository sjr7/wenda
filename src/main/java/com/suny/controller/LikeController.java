package com.suny.controller;

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


    @Autowired
    public LikeController(LikeService likeService, HostHolder hostHolder, CommentService commentService) {
        this.likeService = likeService;
        this.hostHolder = hostHolder;
        this.commentService = commentService;
    }

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }

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
















