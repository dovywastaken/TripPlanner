package com.spring.service.post;

import java.util.Map;


public interface BoardService {
    Map<String, Object> AllboardRead(int page); // 전체 게시판 조회
    Map<String, Object> searchPosts(String type, String keyword, int page); // 검색 게시판 조회
}