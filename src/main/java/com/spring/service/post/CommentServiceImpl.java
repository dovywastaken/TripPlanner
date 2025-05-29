package com.spring.service.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Comment;
import com.spring.domain.Likes;
import com.spring.repository.post.CommentRepository;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

    @Override
    public Map<String,Object> getCommentsByPostId(int postId, int page, int size,String id) {
        int offset = (page - 1) * size;
        return commentRepository.getCommentsByPostId(postId, offset, size,id);
    }

    @Override
    public int countCommentsByPostId(int postId) {
        return commentRepository.countCommentsByPostId(postId);
    }

    @Override
    public Comment getCommentById(int c_unique) {
        return commentRepository.getCommentById(c_unique);
    }

    @Override
    public void insertComment(Comment comment) {
        commentRepository.insertComment(comment);
    }

    @Override
    public void updateComment(int c_unique, String comments) {
        commentRepository.updateComment(c_unique, comments);
    }

    @Override
    public void deleteComment(int c_unique) {
        commentRepository.deleteComment(c_unique);
    }

    @Override
    public List<Integer> incrementCommentLikes(Likes like) {
    	return commentRepository.incrementCommentLikes(like);
    }
	
}
