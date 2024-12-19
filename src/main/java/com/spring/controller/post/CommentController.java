package com.spring.controller.post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.spring.domain.Comment;
import com.spring.service.post.CommentService;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public Map<String, Object> getComments(@RequestParam("postId") int postId,
                                           @RequestParam(value="page", defaultValue="1") int page) {
        int pageSize = 10;
        int totalCount = commentService.countCommentsByPostId(postId);
        int totalPage = (int)Math.ceil((double)totalCount / pageSize);

        List<Comment> comments = commentService.getCommentsByPostId(postId, page, pageSize);
        if(comments==null) {
        	comments=new ArrayList<>();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments);
        response.put("currentPage", page);
        response.put("totalPage", totalPage);
        response.put("totalCount", totalCount);

        return response;
    }

    @PostMapping
    public Map<String,Object> createComment(@RequestParam("postId") int postId,
                                            @RequestParam("id") String userId,
                                            @RequestParam("comments") String commentText) {
    	
    	Timestamp timestamp =new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setP_unique(postId);
        comment.setId(userId);
        comment.setComments(commentText);
        comment.setCommentDate(timestamp);
        comment.setCommentLikes(0);

        commentService.insertComment(comment);

        Map<String,Object> response = new HashMap<>();
        response.put("message", "success");
        return response;
    }

    @DeleteMapping("/{c_unique}")
    public Map<String,Object> deleteComment(@PathVariable int c_unique) {
        commentService.deleteComment(c_unique);
        Map<String,Object> response = new HashMap<>();
        response.put("message", "deleted");
        return response;
    }

    @PostMapping("/{c_unique}/like")
    public Map<String,Object> likeComment(@PathVariable int c_unique) {
        commentService.incrementCommentLikes(c_unique);
        Map<String,Object> response = new HashMap<>();
        response.put("message", "liked");
        return response;
    }
}

