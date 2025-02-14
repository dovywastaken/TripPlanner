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
        post.setCommentCount(rs.getInt("commentCount"));
        String fileImageString = rs.getString("image_names"); // 데이터베이스 컬럼명 확인 필요
        if (fileImageString != null && !fileImageString.isEmpty()) {
            List<String> fileImageList = Arrays.asList(fileImageString.split(",")); // 쉼표로 구분된 문자열 처리
            post.setFileImage(fileImageList);
        } else {
            post.setFileImage(List.of()); // 빈 리스트 설정
        }
        return post;
	}



	
}
