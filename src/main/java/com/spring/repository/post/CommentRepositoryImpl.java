package com.spring.repository.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Comment;
import com.spring.domain.Likes;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
	
	 @Autowired
	    private JdbcTemplate template;

	 	@Override
	 	public Map<String,Object> getCommentsByPostId(int postId, int offset, int limit,String id) {
	 		if (offset < 0) {
	 		    offset = 0;
	 		}
	 	 Map<String,Object> response=new HashMap<String, Object>();
	 	 String sql = "SELECT c_unique, p_unique, id, comments, commentDate, commentLikes " +
             "FROM comments WHERE p_unique = ? ORDER BY commentDate ASC LIMIT ? OFFSET ?";
	 	 List<Comment> comments=template.query(sql, new CommentRowMapper(), postId, limit, offset);
	 	 List<Integer> isLike=new ArrayList<Integer>();
	 	 
	 	 if(id!=null) {
	 		 for(int i=0;i<comments.size();i++) {
		    	 int C_unique=comments.get(i).getC_unique();
		    	 String Likefindsql="select count(*) from commentLike where id=? and c_unique=?";
		    	 int isLikes=template.queryForObject(Likefindsql, Integer.class,id,C_unique);
		    	 isLike.add(isLikes);
	    	 }
	     }else {

	    	 for(int i=0;i<comments.size();i++) {
	    		 isLike.add(0);
		    	 } 	    	 
	     }
	 	 
	 	response.put("comments", comments);
	 	response.put("isLike", isLike);
	     return response;
	 	}

	    @Override
	    public int countCommentsByPostId(int postId) {
	        String sql = "SELECT COUNT(*) FROM comments WHERE p_unique = ?";
	        return template.queryForObject(sql, Integer.class, postId);
	    }

	    @Override
	    public Comment getCommentById(int c_unique) {
	        String sql = "SELECT c_unique, p_unique, id, comments, commentDate, commentLikes " +
	                     "FROM comments WHERE c_unique = ?";
	        return template.queryForObject(sql, new CommentRowMapper(), c_unique);
	    }

	    @Override
	    public void insertComment(Comment comment) {
	        String sql = "INSERT INTO comments (p_unique, id, comments, commentDate, commentLikes) VALUES (?,?,?,?,?)";
	        template.update(sql,
	                comment.getP_unique(),
	                comment.getEmail(),
	                comment.getComments(),
	                comment.getCommentDate(),
	                comment.getCommentLikes()
	        );
	        String commentCountsql="update post set commentCount=commentCount+1 where p_unique=?";
	        template.update(commentCountsql,comment.getP_unique());
	    }


	    @Override
	    public void updateComment(int c_unique, String comments) {
	        String sql = "UPDATE comments SET comments = ? WHERE c_unique = ?";
	        template.update(sql, comments, c_unique);
	    }

	    @Override
	    public void deleteComment(int c_unique) {
	    	String P_uniqueSql="SELECT p_unique FROM comments WHERE c_unique = ?";
	    	int Punique=template.queryForObject(P_uniqueSql,Integer.class,c_unique);
	        String sql = "DELETE FROM comments WHERE c_unique = ?";
	        template.update(sql, c_unique);
	        
	        String commentCountsql="update post set commentCount=commentCount-1 where p_unique=?";
	        template.update(commentCountsql,Punique);
	        
	    }

	    @Override
	    public List<Integer> incrementCommentLikes(Likes like) {
	    	List<Integer> result=new ArrayList<Integer>();
	    	String SQL="select count(*) from commentLikes where id=? and c_unique=?";
	    	int count=template.queryForObject(SQL, Integer.class,like.getId(),like.getC_unique());
	    	if(count==0) {
	        String insertSQL = "insert commentLikes(id,c_unique,likesDate) values(?,?,?)";
	        template.update(insertSQL,like.getId(),like.getC_unique(),like.getLikesDate());
	        String plusSQL= "update comments set commentLikes=commentLikes+1 where c_unique=? ";
	        template.update(plusSQL,like.getC_unique());
	        count=1;
	        }else {
	        String deleteSQL = "delete from commentLike where id=? and c_unique=?";
		    template.update(deleteSQL,like.getId(),like.getC_unique());
		    String minusSQL= "update comments set commentLikes=commentLikes-1 where c_unique=? and commentLikes>0";
	        template.update(minusSQL,like.getC_unique());
	        count=0;
	        }
	    	String totalcountSQL= "select commentLikes from comments where c_unique=?";
		    int totalcount=template.queryForObject(totalcountSQL,Integer.class,like.getC_unique());	
	    	result.add(count);
	        result.add(totalcount);
	    	return result;
	    }


}


