package com.spring.repository.post;

import java.util.List;
import java.util.Map;

import com.spring.domain.Likes;
import com.spring.domain.Post;
import com.spring.domain.Tour;

public interface PostRepository  {
	 	Post getPostById(int postId);
	    void createPost(Post post);
	    void updatePost(Post post);
	    void deletePost(int postId);
	    void incrementViewCount(int postId);
	    List<Integer> incrementPostLike(Likes likes);
	    int getIdisLike(String id,int num);
	    int findLatestPostIdByUser(String userId);
	    Map<String,Object> getMainPost(String id);
	    int pageSearch(int p_unique);
	    void updatetour(Tour tour);
}
