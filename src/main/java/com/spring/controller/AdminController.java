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
    
    
 // 공통된 메서드를 추출하여 페이징 처리 로직을 재사용
    private void setPagingData(Model model, int page, String keyword, int limit) {
        int offset = (page - 1) * limit;
        List<Member> memberList = keyword == null ? memberService.readAllMemberPaging(limit, offset, keyword) :
                                                    memberService.searchMember(keyword, limit, offset);
        
        int totalMemberCount = memberService.getTotalMemberCount(keyword);
        int totalPages = (int) Math.ceil((double) totalMemberCount / limit);
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
    }

    @GetMapping("/dashboard")
    public String allMember(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "keyword", required = false) String keyword, Model model) {
        int limit = 20; // 한 페이지에 표시할 회원 수
        setPagingData(model, page, keyword, limit);
        return "admin";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam String keyword, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int limit = 20; // 한 페이지에 표시할 회원 수
        setPagingData(model, page, keyword, limit);
        return "admin";
    }

}

