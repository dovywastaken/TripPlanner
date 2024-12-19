package com.spring.service.post;


import com.spring.domain.Post;

public interface PostService {
	 	Post getPostById(int num);
	    void createPost(Post post);
	    void deletePost(int num);
	    void updatePost(Post post, int num);
	    void incrementViewCount(int postId);
	    void incrementPostLike(int postId);

}
