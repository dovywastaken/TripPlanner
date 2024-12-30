package com.spring.repository.post;

import java.util.Map;


public interface BoardRepository {
	 Map<String, Object> AllboardRead(int page);
	 Map<String, Object> searchPosts(String type, String keyword, int page); 
	 Map<String, Object> getMyboard(String member,int page);
	 Map<String, Object> mysearchPosts(String id,String keyword, int page); 
}
