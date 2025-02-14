package com.spring.repository.post;

import com.spring.domain.Likes;
import com.spring.domain.Post;
import com.spring.domain.Tour;

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
        String sql = "SELECT * FROM post WHERE p_unique = ?";
        String updateSQL="UPDATE post set view= view+1 WHERE p_unique=?";
        template.update(updateSQL,postId);
        return template.queryForObject(sql, new PostRowMapper(), postId);
    }

    @Override
    public void createPost(Post post) {
        String sql = "INSERT INTO post (id, title, contents, publishDate, region, isPrivate, commentIsAllowed, satisfaction,image_names) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
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
    public void updatePost(Post post) {
        String sql = "UPDATE post SET title = ?, contents = ?, region = ?, isprivate = ?,commentIsAllowed=?, satisfaction = ?,image_names = ? WHERE p_unique = ?";
        String imageNames = String.join(",", post.getFileImage());
        template.update(sql,
                post.getTitle(),
                post.getContents(),
                post.getRegion(),
                post.getIsPrivate(),
                post.isCommentIsAllowed(),
                post.getSatisfaction(),
                imageNames,
                post.getP_unique()
        );
        String toursql="delete from tour WHERE p_unique = ?";
        template.update(toursql,post.getP_unique());
    }

    @Override
    public void deletePost(int postId) {
        String sql = "DELETE FROM post WHERE p_unique = ?";
        template.update(sql, postId);
        String tourdeletSQL="DELETE FROM tour where p_unique = ?";
        template.update(tourdeletSQL,postId);
    }

    @Override
    public void incrementViewCount(int postId) {
        String sql = "UPDATE post SET view = view + 1 WHERE p_unique = ?";
        template.update(sql, postId);
    }

    @Override
    public List<Integer> incrementPostLike(Likes likes) {
    	String checkSQl= "select count(*) from postLike where id=? and p_unique=?";
    	int check=template.queryForObject(checkSQl,Integer.class,likes.getId(),likes.getP_unique());
    	
    	if(check == 0) {
        String SQL = "insert postLike(id, p_unique, likesDate) values (?,?,?)";
        template.update(SQL, likes.getId(),likes.getP_unique(),likes.getLikesDate());
        String updateLikeSQL="UPDATE post SET likes = likes + 1 WHERE p_unique = ?";
        template.update(updateLikeSQL,likes.getP_unique());
        	check=1;
    	}else {
    	    String SQL = "delete from postLike where id=? and p_unique=?";
            template.update(SQL, likes.getId(),likes.getP_unique());
            String updateLikeSQL="UPDATE post SET likes = likes - 1 WHERE p_unique = ?";
            template.update(updateLikeSQL,likes.getP_unique());
            check= 0;
    	}
    	String postLikeSQL="SELECT likes from post where p_unique = ?";
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
        String sql = "SELECT p_unique FROM post WHERE id = ? ORDER BY publishDate DESC LIMIT 1";
        return template.queryForObject(sql, Integer.class, userId);
    }

	@Override
	public Map<String, Object> getMainPost(String id) {
		System.out.println("getMainPost 메서드 호출");
		System.out.println(id);
		PostRowMapper postRowMapper=new PostRowMapper();
		System.out.println("매퍼 까지는 생성");
		String mainSQL="SELECT * FROM post WHERE id = ? ORDER BY publishDate DESC LIMIT 3";
		System.out.println("SQL문 까지는 작성");
		Map<String,Object> result=new HashMap<String, Object>();
		System.out.println("MAP 객체 까지는 작성");
		List<Post> post=template.query(mainSQL,postRowMapper,id);
		System.out.println("list 객체에 post 들고온거 넣기 성공");
		result.put("Post",post);        
		System.out.println("result에 리스트 넣기 성공");
		return result;
	}

	@Override
	public int pageserch(int p_unique) {
		String pageserch= "WITH RankedPosts AS (SELECT p_unique, ROW_NUMBER() OVER (ORDER BY publishDate DESC, p_unique DESC) as row_num FROM post WHERE isPrivate = 1) SELECT CEILING(row_num / 10) as page_number FROM RankedPosts WHERE p_unique = ?";
		int rownum=template.queryForObject(pageserch, Integer.class,p_unique);
		System.out.println(rownum);
	return rownum;
	}

	@Override
	public void updatetour(Tour tour) {
			String insertSQL="INSERT INTO tour (p_unique,contentid, contenttypeid, title, firstimage, addr1, cat2, cat3, mapx, mapy, created_at) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			template.update(insertSQL,
					tour.getP_unique(),
					tour.getContentid(),
					tour.getContenttypeid(),
					tour.getTitle(),
					tour.getFirstimage(),
					tour.getAddr1(),
					tour.getCat2(),
					tour.getCat3(),
					tour.getMapx(),
					tour.getMapy(),
					tour.getCreated_at()
					);
		
		
	}
	
}


