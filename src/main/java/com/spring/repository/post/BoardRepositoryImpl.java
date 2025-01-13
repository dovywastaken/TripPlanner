package com.spring.repository.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Post;
import com.spring.domain.Tour;

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
		
		String sql = "SELECT * FROM post WHERE isPrivate = 1 ORDER BY publishDate DESC, p_unique DESC LIMIT ?, ?"; //등록일 순, 고유번호의 내림차순으로 정렬한 데이터 들고옴
        String countSql = "SELECT COUNT(*) FROM post WHERE isPrivate = 1"; //게시물 총 갯수 세는 쿼리문
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
            sql = "SELECT * FROM post WHERE title LIKE ? AND isPrivate = 1 ORDER BY publishDate DESC LIMIT ?, ?";
            countSql = "SELECT COUNT(*) FROM post WHERE title LIKE ? AND isPrivate = 1";
            keyword = "%" + keyword + "%"; 
        } else if ("id".equals(type)) {
            sql = "SELECT * FROM post WHERE id = ? AND isPrivate = 1 ORDER BY publishDate DESC LIMIT ?, ?";
            countSql = "SELECT COUNT(*) FROM post WHERE id = ? AND isPrivate = 1";
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
	     
	    String getnumSQL="SELECT count(*) FROM post WHERE id=?"; 
		String myboardSQL="SELECT * FROM post WHERE id=? ORDER BY publishDate DESC LIMIT ?, ?";
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

	        String mySearchSql = "SELECT * FROM post WHERE id=? And title Like ? ORDER BY publishDate DESC LIMIT ?, ?";
	        String countSql = "SELECT COUNT(*) FROM post WHERE id=? And title LIKE ?";
	        keyword = "%" + keyword + "%"; 
	
	        List<Post> posts = template.query(mySearchSql, new PostRowMapper(),id, keyword, startIndex, pageSize);
	        int totalPosts = template.queryForObject(countSql, Integer.class,id,keyword);
	        
	        Map<String, Object> result = new HashMap<>();
	        result.put("Allpost", posts);
	        result.put("Allpostgetnum", totalPosts);
	        return result;
	}


		@Override
		public Map<String, Object> hotboardRead(int size, int page) { 
			int pageSize = size; 
	        int startIndex = (page - 1) * pageSize; 
			
			PostRowMapper postRowMapper=new PostRowMapper();
			List<Post> Allpost=new ArrayList<Post>(); //게시글 목록 저장할 리스트 객체
			
			String sql = "SELECT * FROM post where (likes * 5) + (views * 0.1) + (commentCount * 1) >= 100 ORDER BY publishDate DESC LIMIT ?, ?";
			String countSql = "SELECT count(*) FROM post where (likes * 5) + (views * 0.1) + (commentCount * 1) >= 100";
			
			Allpost=template.query(sql,postRowMapper,new Object[] {startIndex,pageSize});
			int Allpostgetnum=template.queryForObject(countSql, Integer.class); 
			
			Map<String, Object> result=new HashMap<String, Object>();
			result.put("Allpost", Allpost);
			result.put("Allpostgetnum", Allpostgetnum);

			return result;
		}
		
		@Override
		public List<Tour> hotSpots(String type, int limit, int offset) { //12 , 0로 들어옴
			String rankSql = "SELECT contentId FROM (SELECT contentId, COUNT(*) AS count FROM tour WHERE contentTypeId = ? GROUP BY contentId HAVING COUNT(*) >= 1) filtered_data ORDER BY filtered_data.count DESC LIMIT ? offset ?";
			List<String> rank = template.queryForList(rankSql, String.class, type,limit,offset);
			// 결과 담을 리스트
			List<Tour> result = new ArrayList<>();
			// 2) 위에서 얻은 contentid 리스트를 돌면서 상세 정보 조회(limit 1)
			String detailSql = "SELECT * FROM tour WHERE contentId = ? LIMIT 1";
			for (String contentId : rank) {
			    Tour tour = template.queryForObject(detailSql, new TourMapper(), contentId);
			    result.add(tour);
			}

			// 이제 result 리스트에 "순위권(contentid별) 중 1건"씩 데이터가 쌓임
			return result;
		}
		
		@Override
		public List<Map<String, Object>> getTourInfoByPostId(int p_unique) {
		    System.out.println("getTourInfoByPostId 리파지터리 함수 호출됨");
		    String sql = "select * from tour where p_unique = ?";
		    List<Map<String, Object>> tourList = template.queryForList(sql, p_unique);
		    
		    if (tourList == null) 
		    {
		        tourList = new ArrayList<>();
		    }
		    
		    System.out.println(tourList);
		    return tourList;
		}
}

