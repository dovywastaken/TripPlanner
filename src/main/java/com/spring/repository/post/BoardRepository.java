package com.spring.repository.post;

import java.util.List;
import java.util.Map;
import com.spring.domain.Tour;


public interface BoardRepository {
	 Map<String, Object> AllboardRead(int page);
	 Map<String, Object> searchPosts(String type, String keyword, int page); 
	 Map<String, Object> getMyboard(String member,int page);
	 Map<String, Object> mysearchPosts(String id,String keyword, int page);
	 List<Tour> hotSpots(int limit, int offset);
}
