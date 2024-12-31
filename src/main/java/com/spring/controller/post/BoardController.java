package com.spring.controller.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.domain.Member;
import com.spring.domain.Post;
import com.spring.domain.Tour;
import com.spring.service.post.BoardService;

@Controller
public class BoardController {
	PaginationHelper paginationHelper=new PaginationHelper();
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/Allboard")
	public String Allboard(@RequestParam(value = "page",defaultValue = "1")int page, Model model) { //페이지 파라미터 받아오기
		Map<String,Object> result=boardService.AllboardRead(page); //
		setBoardModelAttributes(result,page,model);
		
		int totalPosts = (int) result.get("Allpostgetnum");
		Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, 10, 5);
		model.addAttribute("currentPage", page);
		model.addAttribute("pagination", pagination);
		return "post/Allboard";
	}
	
	
	@GetMapping("/board/search")
	public String searchBoard(@RequestParam(value = "page", defaultValue = "1") int page,
	                          @RequestParam("type") String type,
	                          @RequestParam("keyword") String keyword,
	                          Model model) {
	    
	    Map<String, Object> result = boardService.searchPosts(type, keyword, page);

	    
	    setBoardModelAttributes(result, page, model);
	    int totalPosts = (int) result.get("Allpostgetnum"); 
	    Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, 10, 5);
	    
	    

	 
	    model.addAttribute("type", type);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pagination", pagination);
	    return "post/SearchBoard";
	}

	
	
	private void setBoardModelAttributes(Map<String,Object> result,int page,Model model) 
	//게시물을 들고오고 게시물 수를 들고옴과 동시에 페이지네이션 데이터를 생성, 게시물 생성 날짜 포맷팅을 해줌
	{
		List<Post> Allpost=(List<Post>) result.get("Allpost"); //모든 게시글을 result에서 들고와서 리스트에 담는다
		int Allpostgetnum=(int)result.get("Allpostgetnum"); //게시글 갯수의 총 합을 result에서 들고와서 변수에 담는다
		
		ArrayList<Integer> getTotalPages = paginationHelper.getTotalPages(Allpostgetnum, 10); //페이지네이션 만드는 코드
		ArrayList<Integer> getpostnumber = paginationHelper.getpostnumber(Allpostgetnum, page, 10); //
		
		DateFormatter dateFormatter = new DateFormatter(); //게시물의 날짜를 포멧에 맞춰주는 객체
		ArrayList<String> date=new ArrayList<String>(); //
		 for (Post post : Allpost) //Allpost에 있는 요소를 순회하면서 post에 담는다
		 {
		        date.add(dateFormatter.formatBoardDate(post.getPublishDate())); 
		 }
		 
		 model.addAttribute("date", date);
		 model.addAttribute("getTotalPages", getTotalPages);
		 model.addAttribute("getpostnumber", getpostnumber);
		 model.addAttribute("Allpost", Allpost);
	}
	
	@GetMapping("/hotPlanners")
	public String toHotPlanners(@RequestParam(value = "page",defaultValue = "1")int page, Model model)
	{
		Map<String,Object> result=boardService.AllboardRead(page); //
		setBoardModelAttributes(result,page,model);
		
		int totalPosts = (int) result.get("Allpostgetnum");
		Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, 10, 5);
		model.addAttribute("currentPage", page);
		model.addAttribute("pagination", pagination);

		return "post/hotPlanners";
	}
	
	
	@GetMapping("/hotSpots")
	public String toHotSpots(@RequestParam(value = "page", defaultValue = "1") int page, Model model) //1번)
	{
		System.out.println("===========================================================================================");
        System.out.println("BoardController : hotSpots(GET)으로 매핑");
		int limit = 12; //한 페이지에 표시할 관광지 숫자
		int offset = (page - 1) * limit;
		List<Tour> tourList = boardService.hotSpots(limit, offset);
		System.out.println("관광지 정보 담겼는지 "+tourList);
		
		return "post/hotSpots";
	}

	@GetMapping("/Myboard")
	public String getMyboard(HttpSession session,Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		if(session!=null) {
			
		Member member=(Member)session.getAttribute("user");
		Map<String,Object> result=boardService.getMyboard(member.getId(),page);
		int Allpostgetnum=(int) result.get("Allpostgetnum");
		
		Map<String, Object> pagination = paginationHelper.getPagination(page, Allpostgetnum, 10, 5);
		setBoardModelAttributes(result,page,model);
		model.addAttribute("pagination",pagination);
		model.addAttribute("currentPage", page);
		return "post/Myboard";
		}else
		return "errorPage";
	}
	
	
	
	@GetMapping("/board/mysearch")
	public String mysearchBoard(@RequestParam(value = "page", defaultValue = "1") int page,
	                          @RequestParam("keyword") String keyword,
	                          HttpSession session,
	                          Model model
	                          ) {
	    if(session!=null) {
	    Member member=(Member)session.getAttribute("user");
	    String id=member.getId();
	    Map<String, Object> result = boardService.mysearchPosts(id,keyword, page);
	    setBoardModelAttributes(result, page, model);
	    int totalPosts = (int) result.get("Allpostgetnum"); 
	    Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, 10, 5);

	    model.addAttribute("keyword", keyword);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pagination", pagination);
	    return "post/MySearchBoard";
	    }else {
			return "errorPage";
		}
	}
	
}

