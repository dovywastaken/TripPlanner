package com.spring.service.post;

import java.util.Map;
import com.spring.domain.Tour;
import java.util.List;

public interface BoardService {
    Map<String, Object> AllboardRead(int page); // 전체 게시판 조회
    Map<String, Object> searchPosts(String type, String keyword, int page); // 검색 게시판 조회
    Map<String, Object> getMyboard(String member,int page);
    Map<String, Object> mysearchPosts(String id,String keyword, int page); 
    Map<String, Object> hotboardRead(int size, int page);
    List<Tour> hotSpots(String type ,int limit, int offset);
    List<Map<String, Object>> getTourInfoByPostId(int p_unique);
}