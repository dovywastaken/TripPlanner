package com.spring.repository.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Post;
@Repository
public class BoardRepositoryImpl implements BoardRepository {
	
	@Autowired
    private JdbcTemplate template;
	@Override
	public Map<String, Object> AllboardRead(int page) {
		int pageSize = 10; 
        int startIndex = (page - 1) * pageSize; 
		
		PostRowMapper postRowMapper=new PostRowMapper();
		List<Post> Allpost=new ArrayList<Post>();
		
		String sql = "SELECT * FROM Post WHERE isprivate = 1 ORDER BY publishdate DESC, p_unique DESC LIMIT ?, ?";
        String countSql = "SELECT COUNT(*) FROM Post WHERE isprivate = 1";
		Allpost=template.query(sql,postRowMapper,new Object[] {startIndex,pageSize});
		int Allpostgetnum=template.queryForObject(countSql, Integer.class);
		System.out.println("Executing SQL: " + sql);
	    System.out.println("startIndex: " + startIndex + ", pageSize: " + pageSize);
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("Allpost", Allpost);
		result.put("Allpostgetnum", Allpostgetnum);

		return result;
	}
	
	@Override
    public Map<String, Object> searchPosts(String type, String keyword, int page) {
        int pageSize = 10; 
        int startIndex = (page - 1) * pageSize; 

        String sql = "";
        String countSql = "";

        if ("title".equals(type)) {
            sql = "SELECT * FROM Post WHERE title LIKE ? AND isprivate = 1 ORDER BY publishdate DESC LIMIT ?, ?";
            countSql = "SELECT COUNT(*) FROM Post WHERE title LIKE ? AND isprivate = 1";
            keyword = "%" + keyword + "%"; 
        } else if ("id".equals(type)) {
            sql = "SELECT * FROM Post WHERE id = ? AND isprivate = 1 ORDER BY publishdate DESC LIMIT ?, ?";
            countSql = "SELECT COUNT(*) FROM Post WHERE id = ? AND isprivate = 1";
        }

        List<Post> posts = template.query(sql, new PostRowMapper(), keyword, startIndex, pageSize);
        int totalPosts = template.queryForObject(countSql, Integer.class, keyword);

        
        Map<String, Object> result = new HashMap<>();
        result.put("Allpost", posts);
        result.put("Allpostgetnum", totalPosts);
        return result;
    }
}


