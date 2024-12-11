package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Member;
import com.spring.service.MemberService;
import com.spring.service.MemberServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController 
{
    @Autowired
    private MemberService memberService;

    @GetMapping("/dashboard")
    public String allMember(@RequestParam(value = "page", defaultValue = "1") int page,
    							@RequestParam(value="keyword", required = false) String keyword, Model model) 
    {
    	System.out.println("===========================================================================================");
    	System.out.println("AdminController : admin/dashboard(GET)으로 매핑되었습니다");
        int limit = 20; // 한 페이지에 표시할 회원 수
        int offset = (page - 1) * limit;
        List<Member> memberList = memberService.readAllMemberPaging(limit, offset, keyword);
        
        int totalMemberCount = memberService.getTotalMemberCount(keyword);
        int totalPages = (int) Math.ceil((double) totalMemberCount / limit);

        model.addAttribute("memberList", memberList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        
        System.out.println("admin.jsp로 이동합니다");
        return "admin";
    }
    
    @GetMapping("/search")
    public String searchMember(@RequestParam String keyword, @RequestParam(value = "page", defaultValue = "1") int page, Model model) 
    {
        System.out.println("===========================================================================================");
        System.out.println("AdminController : admin/search(POST)으로 매핑되었습니다");
        int limit = 20; // 한 페이지에 표시할 회원 수
        int offset = (page - 1) * limit;
        List<Member> memberList = memberService.searchMember(keyword, limit, offset);
        
        int totalMemberCount = memberService.getTotalMemberCount(keyword);
        int totalPages = (int) Math.ceil((double) totalMemberCount / limit);
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        
        return "admin";
    }
}

