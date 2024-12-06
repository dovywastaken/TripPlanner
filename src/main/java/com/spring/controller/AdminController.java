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
    public String toAdminPage(Model model) 
    {
    	System.out.println("===========================================================================================");
    	System.out.println("AdminController : admin/dashboard(GET)으로 매핑되었습니다");
        List<Member> memberList = memberService.readAllMember();
        model.addAttribute("memberList", memberList);
        
        System.out.println("admin.jsp로 이동합니다");
        return "admin";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Member> searchMember(@RequestParam("keyword") String keyword) 
    {
    	System.out.println("===========================================================================================");
    	System.out.println("AdminController : admin/search(GET)으로 매핑되었습니다");
        List<Member> searchResults = memberService.searchMember(keyword);
        
        System.out.println("검색값이 없으면 전체 회원을 조회합니다");
        return searchResults.isEmpty() ? memberService.readAllMember() : searchResults;
    }
}

