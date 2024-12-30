package com.spring.repository.post;

import com.spring.domain.Likes;
import com.spring.domain.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private JdbcTemplate template;
    private ArrayList<Integer> checkLike;
    
    @Override
    public Post getPostById(int postId) {
        String sql = "SELECT * FROM Post WHERE p_unique = ?";
        return template.queryForObject(sql, new PostRowMapper(), postId);
    }

    @Override
    public void createPost(Post post) {
        String sql = "INSERT INTO Post (id, title, contents, publishDate, region, isPrivate, CommentIsAllowed, satisfaction,image_names) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        String imageNames = String.join(",", post.getFileImage());
        template.update(sql,
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getPublishDate(),
                post.getRegion(),
                post.getIsPrivate(),
                post.isCommentIsAllowed(),
                post.getSatisfaction(),
                imageNames
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
    public List<Integer> incrementPostLike(Likes likes) {
    	String checkSQl= "select count(*) from postLike where id=? and p_unique=?";
    	int check=template.queryForObject(checkSQl,Integer.class,likes.getId(),likes.getP_unique());
    	
    	if(check == 0) {
        String SQL = "insert postLike(id, p_unique, likesDate) values (?,?,?)";
        template.update(SQL, likes.getId(),likes.getP_unique(),likes.getLikesDate());
        String updateLikeSQL="UPDATE Post SET likes = likes + 1 WHERE p_unique = ?";
        template.update(updateLikeSQL,likes.getP_unique());
        	check=1;
    	}else {
    	    String SQL = "delete from postLike where id=? and p_unique=?";
            template.update(SQL, likes.getId(),likes.getP_unique());
            String updateLikeSQL="UPDATE Post SET likes = likes - 1 WHERE p_unique = ?";
            template.update(updateLikeSQL,likes.getP_unique());
            check= 0;
    	}
    	String postLikeSQL="SELECT likes from Post where p_unique = ?";
    	List<Integer> result = new ArrayList<>();
    	result.add(check);
    	result.add(template.queryForObject(postLikeSQL, Integer.class,likes.getP_unique()));
    	return result;
    }
    
    @Override
	public int getIdisLike(String id,int num) {
		String SQL="select count(*) from postLike where id=? and p_unique=?";
		int result= template.queryForObject(SQL, Integer.class,id,num);
		return result;
	}
	
	@Override
	public int findLatestPostIdByUser(String userId) {
        String sql = "SELECT p_unique FROM Post WHERE id = ? ORDER BY publishDate DESC LIMIT 1";
        return template.queryForObject(sql, Integer.class, userId);
    }

	@Override
	public Map<String, Object> getMainPost(String id) {
		System.out.println(id);
		PostRowMapper postRowMapper=new PostRowMapper();
		String mainSQL="SELECT * FROM POST WHERE id = ? ORDER BY publishDate DESC LIMIT 3";
		Map<String,Object> result=new HashMap<String, Object>();
		List<Post> post=template.query(mainSQL,postRowMapper,id);
		result.put("Post",post);                                                                                                     
		return result;
	}
}

