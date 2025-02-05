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
public class BoardRepositoryImpl implements BoardRepository 
{
	
	@Autowired
    private JdbcTemplate template;
	
	@Override
	public Map<String, Object> allBoard(int page) 
	{ //공개 게시글 페이지네이션 및 데이터 반환 메서드
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[BoardRepository : allBoardRead 메서드 호출]");
		int pageSize = 10; //총 10개의 글을 불러오는데
        int offset = (page - 1) * pageSize; //페이지네이션 시작점 page의 값이 1이면 0번 글부터 2면 10번 글부터
		
		PostRowMapper postRowMapper = new PostRowMapper();
		String postListSQL = "SELECT * FROM post WHERE isPrivate = 0 ORDER BY publishDate DESC, p_unique DESC LIMIT ?, ?"; //등록일 순, 고유번호의 내림차순으로 정렬한 데이터 들고옴
		List<Post> postList = template.query(postListSQL,postRowMapper,new Object[] {offset,pageSize}); // 실제 게시글을 리스트화 시킨것
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("postList", postList);
		result.put("postSize", postList.size());
		System.out.println("[총 " + postList.size() + "개의 게시글을 반환합니다]");
		System.out.println("[BoardRepository : allBoardRead 메서드 종료]");
		
		return result;
	}
	
	@Override
    public Map<String, Object> allBoardSearch(String type, String keyword, int page) 
	{
        int pageSize = 10; 
        int offset = (page - 1) * pageSize; 

        String postListSQL = "";
        String postSizeSQL = "";

        if ("title".equals(type)) {
        	postListSQL = "SELECT * FROM post WHERE title LIKE ? AND isPrivate = 0 ORDER BY publishDate DESC LIMIT ?, ?";
        	postSizeSQL = "SELECT COUNT(*) FROM post WHERE title LIKE ? AND isPrivate = 0";
            keyword = "%" + keyword + "%"; 
        } else if ("id".equals(type)) {
        	postListSQL = "SELECT * FROM post WHERE id = ? AND isPrivate = 0 ORDER BY publishDate DESC LIMIT ?, ?";
        	postSizeSQL = "SELECT COUNT(*) FROM post WHERE id = ? AND isPrivate = 0";
        }

        List<Post> postList = template.query(postListSQL, new PostRowMapper(), keyword, offset, pageSize);
        int postSize = template.queryForObject(postSizeSQL, Integer.class, keyword);

        
        Map<String, Object> result = new HashMap<>();
        result.put("postList", postList);
        result.put("postSize", postSize);
        return result;
    }
	
	@Override
	public Map<String, Object> myBoard(String member, int page) 
	{
		int pageSize = 10; 
	    int offset = (page - 1) * pageSize; 
	    Map<String, Object>result = new HashMap<String,Object>();
	     
	    String postListSQL="SELECT * FROM post WHERE id=? ORDER BY publishDate DESC LIMIT ?, ?";
	    String postSizeSQL="SELECT count(*) FROM post WHERE id=?"; 
	    
		List<Post> postList = template.query(postListSQL, new PostRowMapper(),member, offset, pageSize);
		int postSize = template.queryForObject(postSizeSQL, Integer.class,member);
		result.put("postList", postList);
		result.put("postSize",postSize);
		
		return result;
	}

	@Override
	public Map<String, Object> myBoardSearch(String id,String keyword, int page) 
	{
		    int pageSize = 10; 
	        int offset = (page - 1) * pageSize; 

	        String postListSQL = "SELECT * FROM post WHERE id=? And title Like ? ORDER BY publishDate DESC LIMIT ?, ?";
	        String postSizeSQL = "SELECT COUNT(*) FROM post WHERE id=? And title LIKE ?";
	        keyword = "%" + keyword + "%"; 
	
	        List<Post> postList = template.query(postListSQL, new PostRowMapper(),id, keyword, offset, pageSize);
	        int postSize = template.queryForObject(postSizeSQL, Integer.class,id,keyword);
	        
	        Map<String, Object> result = new HashMap<>();
	        result.put("postList", postList);
	        result.put("postSize", postSize);
	        
	        return result;
	}


		@Override
		public Map<String, Object> hotBoard(int size, int page) 
		{ 
			int pageSize = size; 
	        int offset = (page - 1) * pageSize; 
			
			PostRowMapper postRowMapper = new PostRowMapper();
			String postListSQL = "SELECT * FROM post where (likes * 5) + (views * 0.1) + (commentCount * 1) >= 100 ORDER BY publishDate DESC LIMIT ?, ?";
			String postSizeSQL = "SELECT count(*) FROM post where (likes * 5) + (views * 0.1) + (commentCount * 1) >= 100";
			
			List<Post> postList =template.query(postListSQL,postRowMapper,offset,pageSize);
			int postSize = template.queryForObject(postSizeSQL, Integer.class); 
			
			Map<String, Object> result=new HashMap<String, Object>();
			result.put("postList", postList);
			result.put("postSize", postSize);

			return result;
		}
		
		@Override
		public List<Tour> hotSpots(String type, int limit, int offset) 
		{ //12 , 0로 들어옴
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
		public List<Map<String, Object>> getTourInfoByPostId(int p_unique) 
		{
		    System.out.println("getTourInfoByPostId 리파지터리 함수 호출됨");
		    String sql = "select * from tour where p_unique = ?";
		    List<Map<String, Object>> tourList = template.queryForList(sql, p_unique);
		    
		    if (tourList == null) 
		    {
		        tourList = new ArrayList<>();
		    }
		    
		    return tourList;
		}
}

