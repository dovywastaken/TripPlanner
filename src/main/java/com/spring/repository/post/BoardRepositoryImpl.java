package com.spring.repository.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Member;
import com.spring.domain.Post;
import com.spring.domain.Tour;
import com.spring.repository.member.MemberMapper;
@Repository
public class BoardRepositoryImpl implements BoardRepository {
	
	@Autowired
    private JdbcTemplate template;
	@Override
	public Map<String, Object> AllboardRead(int page) { //공개 게시글 페이지네이션 및 데이터 반환 메서드
		int pageSize = 10; //총 10개의 글을 불러오는데
        int startIndex = (page - 1) * pageSize; //페이지네이션 시작점
		
		PostRowMapper postRowMapper=new PostRowMapper();
		List<Post> Allpost=new ArrayList<Post>(); //게시글 목록 저장할 리스트 객체
		
		String sql = "SELECT * FROM Post WHERE isprivate = 1 ORDER BY publishdate DESC, p_unique DESC LIMIT ?, ?"; //등록일 순, 고유번호의 내림차순으로 정렬한 데이터 들고옴
        String countSql = "SELECT COUNT(*) FROM Post WHERE isprivate = 1"; //게시물 총 갯수 세는 쿼리문
		Allpost=template.query(sql,postRowMapper,new Object[] {startIndex,pageSize}); // 실제 게시글을 리스트화 시킨것
		int Allpostgetnum=template.queryForObject(countSql, Integer.class); //게시글이 총 몇개 있는지를 알아낸 것
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
	@Override
	public Map<String, Object> getMyboard(String member, int page) {
		 int pageSize = 10; 
	     int startIndex = (page - 1) * pageSize; 
	     Map<String, Object>result = new HashMap<String,Object>();
	     
	    String getnumSQL="SELECT count(*) FROM POST WHERE id=?"; 
		String myboardSQL="SELECT * FROM Post WHERE id=? ORDER BY publishdate DESC LIMIT ?, ?";
		List<Post> Allpost=template.query(myboardSQL, new PostRowMapper(),member, startIndex, pageSize);
		int totalPosts=template.queryForObject(getnumSQL, Integer.class,member);
		result.put("Allpost", Allpost);
		result.put("Allpostgetnum",totalPosts);
		return result;
	}

	@Override
	public Map<String, Object> mysearchPosts(String id,String keyword, int page) {
		   int pageSize = 10; 
	        int startIndex = (page - 1) * pageSize; 

	        
	        String mySearchSql = "SELECT * FROM Post WHERE id=? And title Like ? ORDER BY publishdate DESC LIMIT ?, ?";
	        String countSql = "SELECT COUNT(*) FROM Post WHERE id=? And title LIKE ?";
	        keyword = "%" + keyword + "%"; 
	

	        List<Post> posts = template.query(mySearchSql, new PostRowMapper(),id, keyword, startIndex, pageSize);
	        int totalPosts = template.queryForObject(countSql, Integer.class,id,keyword);

	        
	        Map<String, Object> result = new HashMap<>();
	        result.put("Allpost", posts);
	        result.put("Allpostgetnum", totalPosts);
	        return result;
	}

	@Override
	public List<Tour> hotSpots(String type, int limit, int offset) { //12 , 0로 들어옴
	    // 1. 데이터 조회
	    String sql = "SELECT * FROM tour Where contenttypeid = ? ORDER BY citation_count DESC LIMIT ? OFFSET ?";
	    System.out.println("hotspots 함수로 " + limit+  ", "+ offset + " 파라미터를 가지고 db에서 데이터 fetch 시도합니다");
	    System.out.println("[BoardRepository : hotSpots 메서드 종료]");
	    return template.query(sql, new TourMapper(), type, limit, offset);
	}
}


