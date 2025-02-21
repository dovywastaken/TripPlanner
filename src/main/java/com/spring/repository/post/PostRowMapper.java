package com.spring.repository.post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.spring.domain.*;


import org.springframework.jdbc.core.RowMapper;

public class PostRowMapper implements RowMapper<Post>{
	
	@Override
	public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post=new Post();
		post.setP_unique(rs.getInt("p_unique"));
        post.setId(rs.getString("id"));
        post.setTitle(rs.getString("title"));
        post.setContents(rs.getString("contents"));
        post.setPublishDate(rs.getTimestamp("publishDate"));
        post.setViews(rs.getInt("views"));
        post.setLikes(rs.getInt("likes"));
        post.setIsPrivate(rs.getString("isPrivate"));
        post.setCommentIsAllowed(rs.getString("commentIsAllowed"));
        String imageNames = rs.getString("imageNames");
        post.setCommentCount(rs.getInt("commentCount"));
        post.setNickname(rs.getString("nickname"));
        
        if (imageNames != null && !imageNames.isEmpty()) 
        {
            List<String> imageNameList = Arrays.asList(imageNames.split(",")); // 쉼표로 구분된 문자열 처리
            post.setFileImage(imageNameList);
        } else {
            post.setFileImage(List.of()); // 빈 리스트 설정
        }
        
        return post;
	}



	
}
