package com.spring.repository.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Comment;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
	
	 @Autowired
	    private JdbcTemplate template;

	 	@Override
	 	public List<Comment> getCommentsByPostId(int postId, int offset, int limit) {
	 		if (offset < 0) {
	 		    offset = 0;
	 		}
	     String sql = "SELECT c_unique, p_unique, id, comments, commentDate, commentLikes " +
	                  "FROM Comment WHERE p_unique = ? ORDER BY commentDate ASC LIMIT ? OFFSET ?";
	     return template.query(sql, new CommentRowMapper(), postId, limit, offset);
	 	}

	    @Override
	    public int countCommentsByPostId(int postId) {
	        String sql = "SELECT COUNT(*) FROM Comment WHERE p_unique = ?";
	        return template.queryForObject(sql, Integer.class, postId);
	    }

	    @Override
	    public Comment getCommentById(int c_unique) {
	        String sql = "SELECT c_unique, p_unique, id, comments, commentDate, commentLikes " +
	                     "FROM Comment WHERE c_unique = ?";
	        return template.queryForObject(sql, new CommentRowMapper(), c_unique);
	    }

	    @Override
	    public void insertComment(Comment comment) {
	        String sql = "INSERT INTO Comment (p_unique, id, comments, commentDate, commentLikes) VALUES (?,?,?,?,?)";
	        template.update(sql,
	                comment.getP_unique(),
	                comment.getId(),
	                comment.getComments(),
	                comment.getCommentDate(),
	                comment.getCommentLikes()
	        );
	    }

	    @Override
	    public void updateComment(int c_unique, String comments) {
	        String sql = "UPDATE Comment SET comments = ? WHERE c_unique = ?";
	        template.update(sql, comments, c_unique);
	    }

	    @Override
	    public void deleteComment(int c_unique) {
	        String sql = "DELETE FROM Comment WHERE c_unique = ?";
	        template.update(sql, c_unique);
	    }

	    @Override
	    public void incrementCommentLikes(int c_unique) {
	        String sql = "UPDATE Comment SET commentLikes = commentLikes + 1 WHERE c_unique = ?";
	        template.update(sql, c_unique);
	    }


}


