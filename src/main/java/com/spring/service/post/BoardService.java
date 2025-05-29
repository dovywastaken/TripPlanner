package com.spring.service.post;

import java.util.Map;
import com.spring.domain.Tour;
import java.util.List;

public interface BoardService 
{
	 Map<String, Object> allBoard(int page);
	 Map<String, Object> allBoardSearch(String type, String keyword, int page); 
	 Map<String, Object> myBoard(String member,int page);
	 Map<String, Object> myBoardSearch(String id,String keyword, int page); 
	 Map<String, Object> hotBoard(int size, int page);
	 List<Tour> hotSpots(String type ,int limit, int offset);
	 List<Map<String, Object>> getTourInfoByPostId(int p_unique);
}