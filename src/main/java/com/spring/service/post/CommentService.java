package com.spring.service.post;

import java.util.List;
import java.util.Map;

import com.spring.domain.Comment;
import com.spring.domain.Likes;

public interface CommentService {
	Map<String,Object> getCommentsByPostId(int postId, int page, int size,String id);
    int countCommentsByPostId(int postId);
    Comment getCommentById(int c_unique);
    void insertComment(Comment comment);
    void updateComment(int c_unique, String comments);
    void deleteComment(int c_unique);
    List<Integer> incrementCommentLikes(Likes like);
  
}