package com.spring.service.post;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void incrementPostLike(int postId) {
        postRepository.incrementPostLike(postId);
    }
}

