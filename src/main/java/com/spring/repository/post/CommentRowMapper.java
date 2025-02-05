package com.spring.repository.post;


import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import com.spring.domain.Comment;

public class CommentRowMapper implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comment comment=new Comment();
		comment.setC_unique(rs.getInt("c_unique"));
		comment.setP_unique(rs.getInt("p_unique"));
		comment.setCommentLikes(rs.getInt("commentLikes"));
		comment.setCommentDate(rs.getTimestamp("commentDate"));
		comment.setComments(rs.getString("comments"));
		comment.setEmail(rs.getString("id"));
		return comment;
		
	}


}
