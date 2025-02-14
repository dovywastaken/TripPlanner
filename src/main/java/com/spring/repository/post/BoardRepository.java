package com.spring.repository.post;

import java.util.Map;
import java.util.List;
import com.spring.domain.Tour;

public interface BoardRepository {
	 Map<String, Object> AllboardRead(int page);
	 Map<String, Object> searchPosts(String type, String keyword, int page); 
	 Map<String, Object> getMyboard(String member,int page);
	 Map<String, Object> mysearchPosts(String id,String keyword, int page); 
	 Map<String, Object> hotboardRead(int size, int page);
	 List<Tour> hotSpots(String type ,int limit, int offset);
	 List<Map<String, Object>> getTourInfoByPostId(int p_unique);
}
