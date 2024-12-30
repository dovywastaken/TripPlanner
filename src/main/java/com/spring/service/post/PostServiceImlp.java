package com.spring.service.post;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Likes;
import com.spring.domain.Post;
import com.spring.repository.post.PostRepository;


@Service
public class PostServiceImlp implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post getPostById(int num) {
        return postRepository.getPostById(num);
    }

    @Override
    public void createPost(Post post) {
        postRepository.createPost(post);
    }

    @Override
    public void deletePost(int num) {
        postRepository.deletePost(num);
    }

    @Override
    public void updatePost(Post post, int num) {
        postRepository.updatePost(post, num);
    }

    @Override
    public void incrementViewCount(int postId) {
        postRepository.incrementViewCount(postId);
    }

    @Override
    public List<Integer> incrementPostLike(Likes likes) {
    List<Integer> result= postRepository.incrementPostLike(likes);
       return result;
    }

	@Override
	public int getIdisLike(String id,int num) {
		return postRepository.getIdisLike(id,num);
	}
	@Override
	public int getLatestPostId(String userId) {
	        return postRepository.findLatestPostIdByUser(userId);
	    }

	@Override
	public Map<String, Object> getMainPost(String id) {
		
		return postRepository.getMainPost(id);
	}
}

