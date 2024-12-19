package com.spring.repository.post;

import com.spring.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private JdbcTemplate template;

    @Override
    public Post getPostById(int postId) {
        String sql = "SELECT * FROM Post WHERE p_unique = ?";
        return template.queryForObject(sql, new PostRowMapper(), postId);
    }

    @Override
    public void createPost(Post post) {
        String sql = "INSERT INTO Post (id, title, contents, publishDate, region, isPrivate, CommentIsAllowed, satisfaction) \r\n"
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getPublishDate(),
                post.getRegion(),
                post.getIsPrivate(),
                post.isCommentIsAllowed(),
                post.getSatisfaction()
        );
    }

    @Override
    public void updatePost(Post post, int postId) {
        String sql = "UPDATE Post SET title = ?, contents = ?, region = ?, isprivate = ?, Satisfaction = ? WHERE p_unique = ?";
        template.update(sql,
                post.getTitle(),
                post.getContents(),
                post.getRegion(),
                post.getIsPrivate(),
                post.getSatisfaction(),
                postId
        );
    }

    @Override
    public void deletePost(int postId) {
        String sql = "DELETE FROM Post WHERE p_unique = ?";
        template.update(sql, postId);
    }

    @Override
    public void incrementViewCount(int postId) {
        String sql = "UPDATE Post SET view = view + 1 WHERE p_unique = ?";
        template.update(sql, postId);
    }

    @Override
    public void incrementPostLike(int postId) {
        String sql = "UPDATE Post SET likes = likes + 1 WHERE p_unique = ?";
        template.update(sql, postId);
    }
}

