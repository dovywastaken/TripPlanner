package com.spring.repository.post;

import java.util.List;
import java.util.Map;

import com.spring.domain.Likes;
import com.spring.domain.Post;

public interface PostRepository  {
	 	Post getPostById(int postId);
	    void createPost(Post post);
	    void updatePost(Post post, int postId);
	    void deletePost(int postId);
	    void incrementViewCount(int postId);
	    List<Integer> incrementPostLike(Likes likes);
	    int getIdisLike(String id,int num);
	    int findLatestPostIdByUser(String userId);
	    Map<String,Object> getMainPost(String id);
}
