package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Member;
import com.spring.service.MemberService;

@Controller
@RequestMapping("/admin")
public class AdminController 
{
    @Autowired
    private MemberService memberService;

    @GetMapping("/dashboard")
    public String toAdminPage(@RequestParam(value = "page", defaultValue = "1") int page,
    							@RequestParam(value="keyword", required = false) String keyword, Model model) 
    {
    	System.out.println("===========================================================================================");
    	System.out.println("AdminController : admin/dashboard(GET)으로 매핑되었습니다");
        int limit = 50; // 한 페이지에 표시할 회원 수
        int offset = (page - 1) * limit;
        List<Member> memberList = memberService.readAllMemberPaging(limit, offset);
        
        int totalMemberCount = memberService.getTotalMemberCount(keyword);
        int totalPages = (int) Math.ceil((double) totalMemberCount / limit);

        model.addAttribute("memberList", memberList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        
        System.out.println("admin.jsp로 이동합니다");
        return "admin";
    }
    
    @GetMapping("/search")
    @ResponseBody
    public List<Member> searchMember(@RequestParam("keyword") String keyword, 
                                      @RequestParam(value = "page", defaultValue = "1") int page, Model model) 
    {
        System.out.println("===========================================================================================");
        System.out.println("AdminController : admin/search(GET)으로 매핑되었습니다");

        int limit = 50; // 한 페이지에 표시할 회원 수
        int offset = (page - 1) * limit; // 페이지 번호에 따라 offset 계산
        
        List<Member> searchResults = memberService.searchMember(keyword, limit, offset); // 페이징 처리된 검색 결과
        if (searchResults.isEmpty()) {
            searchResults = memberService.readAllMemberPaging(limit, offset); // 검색결과가 없으면 페이징 처리된 전체 회원 목록
        }
        
        int totalMemberCount = memberService.getTotalMemberCount(keyword);
        int totalPages = (int) Math.ceil((double) totalMemberCount / limit);
        
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        System.out.println("검색값이 없으면 페이징 처리된 모든 회원을 조회합니다");
        return searchResults;
    }
}

