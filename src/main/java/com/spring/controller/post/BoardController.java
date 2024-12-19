package com.spring.controller.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.domain.Post;
import com.spring.service.post.BoardService;

@Controller
public class BoardController {
	PaginationHelper paginationHelper=new PaginationHelper();
	
	@Autowired
	BoardService boradService;
	
	@GetMapping("/Allboard")
	public String Allboard(@RequestParam(value = "page",defaultValue = "1")int page, Model model) {
		Map<String,Object> result=boradService.AllboardRead(page);
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
	    
	    Map<String, Object> result = boradService.searchPosts(type, keyword, page);

	    
	    setBoardModelAttributes(result, page, model);
	    int totalPosts = (int) result.get("Allpostgetnum"); 
	    Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, 10, 5);
	    
	    

	 
	    model.addAttribute("type", type);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pagination", pagination);
	    return "post/SearchBoard";
	}

	
	
	private void setBoardModelAttributes(Map<String,Object> result,int page,Model model) {
		List<Post> Allpost=(List<Post>) result.get("Allpost");
		int Allpostgetnum=(int)result.get("Allpostgetnum");
		
		ArrayList<Integer> getTotalPages = paginationHelper.getTotalPages(Allpostgetnum, 10);
		ArrayList<Integer> getpostnumber = paginationHelper.getpostnumber(Allpostgetnum, page, 10);
		
		DateFormatter dateFormatter = new DateFormatter();
		ArrayList<String> date=new ArrayList<String>();
		 for (Post post : Allpost) {
		        date.add(dateFormatter.formatBoardDate(post.getPublishDate()));
		    }
		 
		 model.addAttribute("date", date);
		 model.addAttribute("getTotalPages", getTotalPages);
		 model.addAttribute("getpostnumber", getpostnumber);
		 model.addAttribute("Allpost", Allpost);
	}
	

}
