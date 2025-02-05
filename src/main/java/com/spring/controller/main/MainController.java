package com.spring.controller.main;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.controller.post.DateFormatter;
import com.spring.domain.Member;
import com.spring.domain.Post;
import com.spring.domain.Tour;
import com.spring.service.member.MemberService;
import com.spring.service.post.BoardService;
import com.spring.service.post.PostService;

@Controller
public class MainController
{
	@Autowired
	PostService postService;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/") // 메인 페이지 뷰를 띄워주는 메서드
	public String mainPage(Model model, HttpSession session) {
	    System.out.println("===========================================================================================");
	    Member member=new Member(); //멤버 객체 전달 안해주면 에러남
	    model.addAttribute("member",member);
	    // 세션이 존재하는 경우 처리
	    if (session != null) 
	    {
	        // 세션에서 사용자 정보를 가져옴
	        Member loginMember = (Member) session.getAttribute("user");
	        if (loginMember != null) 
	        {
	            model.addAttribute("member", loginMember); // 모델에 사용자 정보 추가
	            String id = loginMember.getEmail(); // 사용자 ID

	            // 게시물 관련 데이터 준비
	            Map<String, Object> result = postService.getMainPost(id); // 게시물 데이터 가져오기
	            List<Post> posts = (List<Post>) result.get("Post"); // 게시물 리스트
	            List<String> days = new ArrayList<String>(); // 게시물 날짜 리스트

	            // 게시물 날짜를 포맷팅하여 리스트에 추가1
	            DateFormatter dateFormatter = new DateFormatter();
	            for (Post post : posts) 
	            {
	                String formattedDate = dateFormatter.formatPostDate(post.getPublishDate());
	                days.add(formattedDate);
	            }
	            
	            Map<String, Object> count = boardService.myBoard(loginMember.getEmail(), 1);
	            
	            // 모델에 게시물과 날짜 데이터 추가
	            model.addAttribute("count",count.get("Allpostgetnum"));
	            model.addAttribute("days", days);
	            model.addAttribute("posts", posts);
	        }
	    }
	    
	    returnTourList(model);
	    hotboard(model);

	    System.out.println("MainController: 프로젝트명으로 매핑되어 mainPage.jsp로 이동합니다.");
	    return "mainPage"; // mainPage.jsp로 이동
	}
	
	private void returnTourList(Model model)
	{
		
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
	
	public void hotboard(Model model) 
	{
	    int page = 1;
	    int size = 3;
	    Map<String, Object> result = boardService.hotBoard(size, page);
	    System.out.println("인기게시글 들고왔는지 확인하기 : " + result);
	    if(result.get("allPost") != null) 
	    {
	    	model.addAttribute("result", result);
	    	// result에서 Allpost 리스트를 가져옴
		    List<Post> posts = (List<Post>) result.get("allPost");
		    List<Integer> pUniqueList = new ArrayList<>();

		    // 각 Post 객체에서 p_unique 값을 가져와서 리스트에 추가
		    for (Post post : posts) {
		        pUniqueList.add(post.getP_unique());
		    }
		    
		    
		    for (int i = 0; i < pUniqueList.size(); i++) {
		        int id = pUniqueList.get(i);
		        List<Map <String, Object>> tourList = boardService.getTourInfoByPostId(id);
		    }

		    // pUniqueList를 모델에 추가
		    model.addAttribute("pUniqueList", pUniqueList);
	    }
	    else 
	    {
	    	System.out.println("인기리스트가 없음 ");
	    }
	    

	    
	}
	
	//로그인 폼 전송
    @PostMapping("/")
    public String mainPageSignIn(@ModelAttribute("member") Member member, HttpSession session, Model model)
    {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : TripPlanner(POST)로 매핑되었습니다");
        
        // 로그인을 위한 회원 정보 조회
        Member loginMb = memberService.findById(member.getEmail());
        
        if (loginMb != null && loginMb.getPw().equals(member.getPw())) {
            session.setAttribute("user", loginMb);
            System.out.println("로그인 성공, 메인페이지로 리다이렉션");
            return "redirect:/"; // 로그인 성공 시 메인페이지로 리다이렉션
        } else if (member.getEmail() == null || member.getEmail().isEmpty()) {
            model.addAttribute("EmptyForm", "아이디를 입력해주세요");
            System.out.println("아이디가 빈 경우");
            return "mainPage"; // 아이디가 빈 경우 로그인 페이지로 이동
        } else if (member.getPw() == null || member.getPw().isEmpty()) {
            model.addAttribute("EmptyForm", "비밀번호를 입력해주세요");
            System.out.println("비밀번호가 빈 경우");
            return "mainPage"; // 비밀번호가 빈 경우 로그인 페이지로 이동
        } else {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
            System.out.println("아이디 또는 비밀번호가 틀린 경우");
            return "mainPage"; // 아이디 또는 비밀번호가 일치하지 않는 경우 로그인 페이지로 이동
        }
     }
	
}
