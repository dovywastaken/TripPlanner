package com.spring.repository.post;

import com.spring.domain.Likes;
import com.spring.domain.Post;
import com.spring.domain.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private JdbcTemplate template;
    
    private static final Logger logger = LoggerFactory.getLogger(PostRepositoryImpl.class);
    
    @Override
    public Post getPostById(int postId) {
        String sql = "SELECT * FROM post WHERE p_unique = ?";
        String updateSQL="UPDATE post set views = views +1 WHERE p_unique = ?";
        template.update(updateSQL,postId);
        return template.queryForObject(sql, new PostRowMapper(), postId);
    }

    @Override
    public void createPost(Post post) {
        String sql = "INSERT INTO post (id, title, contents, publishDate, isPrivate, commentIsAllowed, imageNames, nickname) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String imageNames = String.join(",", post.getFileImage());
        String title;
        template.update(sql,
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getPublishDate(),
                post.getIsPrivate(),
                post.isCommentIsAllowed(),
                imageNames,
                post.getNickname()
        );
    }

    @Override
    public void updatePost(Post post) {
        String sql = "UPDATE post SET title = ?, contents = ?, isPrivate = ?,commentIsAllowed=?, imageNames = ? WHERE p_unique = ?";
        String imageNames = String.join(",", post.getFileImage());
        template.update(sql,
                post.getTitle(),
                post.getContents(),
                post.getIsPrivate(),
                post.isCommentIsAllowed(),
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
        String sql = "UPDATE post SET views = views + 1 WHERE p_unique = ?";
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
		String SQL="select count(*) from postLike where id = ? and p_unique = ?";
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
		PostRowMapper postRowMapper=new PostRowMapper();
		String mainSQL = "SELECT * FROM post WHERE id = ? ORDER BY publishDate DESC LIMIT 3";
		Map<String,Object> result = new HashMap<String, Object>();
		List<Post> postList = template.query(mainSQL,postRowMapper,id);
		result.put("postList",postList);    
		return result;
	}
/*
	@Override
	public int pageSearch(int p_unique) 
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[PostRepository : pageSearch 메서드 호출]");
    	System.out.println("파라미터가 제대로 들어왔는지 확인하기 : "+ p_unique);
		String SQL= 
				"WITH RankedPosts AS (SELECT p_unique, ROW_NUMBER() OVER (ORDER BY publishDate DESC, p_unique DESC) as row_num FROM post WHERE isPrivate = 0) SELECT CEILING(row_num / 10) as page_number"
				+ " FROM RankedPosts WHERE p_unique = ?";
		int rownum = template.queryForObject(SQL, Integer.class,p_unique);
		System.out.println("결과 값 : " + rownum);
		System.out.println("[PostRepository : pageSearch 메서드 종료]");
	return rownum;
	}
	*/

	@Override
	public int pageSearch(int p_unique) 
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[PostRepository : pageSearch 메서드 호출]");
    	System.out.println("파라미터가 제대로 들어왔는지 확인하기 : "+ p_unique);
		String SQL = 
		    "SELECT CEILING(row_num / 10) AS page_number " +
		    "FROM ( " +
		    "  SELECT COUNT(*) + 1 AS row_num " +
		    "  FROM post " +
		    "  WHERE isPrivate = 0 " +
		    "    AND ( " +
		    "      publishDate > (SELECT publishDate FROM post WHERE p_unique = ?) " +
		    "      OR (publishDate = (SELECT publishDate FROM post WHERE p_unique = ?) AND p_unique > ?) " +
		    "    ) " +
		    ") AS t";
		System.out.println("SQL문 : " + SQL);
		int rownum = template.queryForObject(SQL, Integer.class, p_unique, p_unique, p_unique);
		System.out.println("결과 값 : " + rownum);
		System.out.println("[PostRepository : pageSearch 메서드 종료]");
	return rownum;
	}

	
	
	@Override
	public void updatetour(Tour tour) {
			String insertSQL="INSERT INTO tour (p_unique,contentId, contentTypeId, title, firstImage, addr1, cat2, cat3, mapx, mapy, created_at) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			
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
			logger.info(
        		    "addr1: " + tour.getAddr1() + ", " +                // 주소
        		    "cat2: " + tour.getCat2() + ", " +                  // 카테고리2
        		    "cat3: " + tour.getCat3() + ", " +                  // 카테고리3
        		    "contentId: " + tour.getContentid() + ", " +        // 콘텐츠 ID
        		    "contentTypeId: " + tour.getContenttypeid() + ", " +// 콘텐츠 타입 ID
        		    "createdAt: " + tour.getCreated_at() + ", " +       // 생성일시
        		    "firstImage: " + tour.getFirstimage() + ", " +      // 대표 이미지
        		    "id: " + tour.getId() + ", " +                      // 내부 ID
        		    "mapX: " + tour.getMapx() + ", " +                  // 지도 X좌표(경도)
        		    "mapY: " + tour.getMapy() + ", " +                  // 지도 Y좌표(위도)
        		    "pUnique: " + tour.getP_unique() + ", " +           // 고유 식별자
        		    "title: " + tour.getTitle()                         // 제목
        		);
	}
	
}


