package com.spring.controller.main;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.controller.post.DateFormatter;
import com.spring.domain.Member;
import com.spring.domain.Post;
import com.spring.domain.Tour;
import com.spring.service.post.BoardService;
import com.spring.service.post.PostService;

@Controller
public class MainController
{
	@Autowired
	PostService postService;
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/") // 메인 페이지 뷰를 띄워주는 메서드
	public String mainPage(Model model, HttpSession session) {
	    System.out.println("===========================================================================================");
	    Member member=new Member();
	    model.addAttribute("member",member);
	    // 세션이 존재하는 경우 처리
	    if (session != null) {
	        // 세션에서 사용자 정보를 가져옴
	        Member member2 = (Member) session.getAttribute("user");
	        if (member2 != null) {
	            model.addAttribute("member", member2); // 모델에 사용자 정보 추가
	            String id = member2.getId(); // 사용자 ID

	            // 게시물 관련 데이터 준비
	            Map<String, Object> result = postService.getMainPost(id); // 게시물 데이터 가져오기
	            List<Post> posts = (List<Post>) result.get("Post"); // 게시물 리스트
	            List<String> days = new ArrayList<>(); // 게시물 날짜 리스트

	            // 게시물 날짜를 포맷팅하여 리스트에 추가
	            DateFormatter dateFormatter = new DateFormatter();
	            for (Post post : posts) {
	                String formattedDate = dateFormatter.formatPostDate(post.getPublishDate());
	                days.add(formattedDate);
	            }
	            
	            Map<String, Object> count = boardService.getMyboard(member2.getId(), 1);
	            
	            
	            System.out.println(days);
	            System.out.println(posts);
	            // 모델에 게시물과 날짜 데이터 추가
	            model.addAttribute("count",count.get("Allpostgetnum"));
	            model.addAttribute("days", days);
	            model.addAttribute("posts", posts);
	        }
	    }
	    
	    returnTourList(model);
	    System.out.println("관광지 모델 담겼는지 확인" + model.getAttribute("festival"));

	    System.out.println("MainController: 프로젝트명으로 매핑되어 mainPage.jsp로 이동합니다.");
	    return "mainPage"; // mainPage.jsp로 이동
	}
	private void returnTourList(Model model){
		
		int limit = 2; // 한 페이지당 표시할 관광지 수
	    int offset = 0;
	    // DB에서 전체 관광지 리스트를 가져옴
	    List<Tour> tourSpots = boardService.hotSpots("12",limit, offset);
	    List<Tour> festival = boardService.hotSpots("15", limit, offset);
	    List<Tour> restaurants = boardService.hotSpots("39", limit, offset);
	    
	    model.addAttribute("tourSpots",tourSpots);
	    model.addAttribute("festival",festival);
	    model.addAttribute("restaurants",restaurants);
	}
	
	
}
