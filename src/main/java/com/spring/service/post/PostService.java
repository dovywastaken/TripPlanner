package com.spring.service.post;


import java.util.List;
import java.util.Map;

import com.spring.domain.Likes;
import com.spring.domain.Post;
import com.spring.domain.Tour;

public interface PostService {
	Post getPostById(int num);
    void createPost(Post post);
    void deletePost(int num);
    void updatePost(Post post);
    void incrementViewCount(int postId);
    List<Integer> incrementPostLike(Likes likes);
    int getIdisLike(String id, int num);
    int getLatestPostId(String userId);
    Map<String,Object> getMainPost(String id);
    int pageserch(int p_unique);
    void updatetour(Tour tour);
}
