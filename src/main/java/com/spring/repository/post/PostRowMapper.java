package com.spring.repository.post;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.spring.domain.*;
import org.springframework.jdbc.core.RowMapper;

public class PostRowMapper implements RowMapper<Post>{
	
	@Override
	public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post=new Post();
        post.setId(rs.getString("id"));
        post.setTitle(rs.getString("title"));
        post.setContents(rs.getString("contents"));
        post.setPublishDate(rs.getTimestamp("publishdate"));
        post.setView(rs.getInt("view"));
        post.setLikes(rs.getInt("likes"));
        post.setRegion(rs.getString("region"));
        post.setIsPrivate(rs.getString("isprivate"));
        post.setCommentIsAllowed(rs.getString("CommentIsAllowed"));
        post.setP_unique(rs.getInt("p_unique"));
        post.setSatisfaction(rs.getInt("Satisfaction"));
        return post;
	}



	
}
