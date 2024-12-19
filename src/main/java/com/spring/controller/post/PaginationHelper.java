package com.spring.controller.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class PaginationHelper {
	
	public ArrayList<Integer> getTotalPages(int totalPosts, int pageSize) {
	  
	    int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
	    
	    ArrayList<Integer> pageList = new ArrayList<>();
	    
	    for (int i = 1; i <= totalPages; i++) {
	        pageList.add(i);
	    }
	    
	    return pageList;  
	}
	    
	   public ArrayList<Integer> getpostnumber(int totalPosts,int page,int pageSize){
		   ArrayList<Integer> postNumbers = new ArrayList<>();
		  
		   int startPost = totalPosts - (page - 1) * pageSize; 
		   int endPost = startPost - pageSize + 1;

		    for (int i = startPost; i >= endPost && i > 0; i--) {
		        postNumbers.add(i);
		    }
		   return postNumbers;
	   }
	   
	   
	    // 새로운 페이지네이션 계산 메서드
	    public Map<String, Object> getPagination(int currentPage, int totalPosts, int pageSize, int groupSize) {
	        Map<String, Object> pagination = new HashMap<>();

	        // 전체 페이지 수
	        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

	        // 현재 페이지 그룹
	        int currentGroup = (currentPage - 1) / groupSize + 1;

	        // 시작 페이지와 끝 페이지 계산
	        int startPage = (currentGroup - 1) * groupSize + 1;
	        int endPage = Math.min(startPage + groupSize - 1, totalPages);

	        // 이전, 다음 그룹 존재 여부
	        boolean hasPrevGroup = startPage > 1;
	        boolean hasNextGroup = endPage < totalPages;

	        // 데이터 추가
	        pagination.put("startPage", startPage);
	        pagination.put("endPage", endPage);
	        pagination.put("currentPage", currentPage);
	        pagination.put("totalPages", totalPages);
	        pagination.put("hasPrevGroup", hasPrevGroup);
	        pagination.put("hasNextGroup", hasNextGroup);

	        return pagination;
	    }
	
	
}
