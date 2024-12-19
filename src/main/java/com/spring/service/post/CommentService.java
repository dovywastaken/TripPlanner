package com.spring.service.post;

import java.util.List;

import com.spring.domain.Comment;

public interface CommentService {
	List<Comment> getCommentsByPostId(int postId, int page, int size);
    int countCommentsByPostId(int postId);
    Comment getCommentById(int c_unique);
    void insertComment(Comment comment);
    void updateComment(int c_unique, String comments);
    void deleteComment(int c_unique);
    void incrementCommentLikes(int c_unique);
}