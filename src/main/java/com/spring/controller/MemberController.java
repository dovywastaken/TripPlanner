package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Member;
import com.spring.service.MemberService;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        Member user = (Member) session.getAttribute("user");
        if (user == null) session.invalidate();
        model.addAttribute("user", user);
        
        return "mainPage";
    }

    //===================================================================================================================
    
    @GetMapping("/signUp")
    public String toSignUp() {
    	
        return "signUp";
    }

    @PostMapping("/signUp")
    public String fromSignUp(@ModelAttribute Member member) {
        memberService.createMember(member);
        
        return "signUpSuccess";
    }

    //===================================================================================================================
    
    @GetMapping("/signIn")
    public String toLoginPage() {
    	
        return "login";
    }

    @PostMapping("/signIn")
    public String fromLoginPage(@ModelAttribute Member member, HttpSession session, Model model) {
        Member loginMb = memberService.findById(member.getId());
        if (loginMb != null && loginMb.getPw().equals(member.getPw())) {
            session.setAttribute("user", loginMb);
            
            return "redirect:/";
        } else {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
            
            return "login";
        }
    }

    //===================================================================================================================
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        
        return "redirect:/";
    }
    
    //===================================================================================================================

    @GetMapping("/updateMember")
    public String toUpdateMember() {
    	
        return "updateUser";
    }

    @PostMapping("/updateMember")
    public String fromUpdateMember(@ModelAttribute Member member, HttpSession session) {
        memberService.updateMember(member);
        
        return "redirect:/";
    }
    
    //===================================================================================================================

    @GetMapping("/deleteMember")
    public String toDeleteMember() {
    	
        return "deleteUser";
    }

    @PostMapping("/deleteMember")
    public String fromDeleteMember(Member member, HttpSession session) {
        member = (Member) session.getAttribute("user");
        memberService.deleteMember(member);
        session.invalidate();
        
        return "deleteUserSuccess";
    }

    //===================================================================================================================

    @GetMapping("/admin")
    public String toAdminPage(Model model, @RequestParam(value = "sorted", defaultValue = "true") boolean sorted) {
        List<Member> memberList;

        if (sorted) {
            // 정렬된 회원 목록 가져오기
            memberList = memberService.getAllMembersSorted();
        } else {
            // 정렬되지 않은 전체 회원 목록 가져오기
            memberList = memberService.readAllMember();
        }

        model.addAttribute("memberList", memberList);
        return "admin";  // admin.jsp 페이지로 이동
    }


    @GetMapping("/admin/search")
    @ResponseBody
    public List<Member> searchMember(@RequestParam("keyword") String keyword, HttpServletRequest request) {
        List<Member> searchResults = memberService.searchMember(keyword);

        // 검색 결과가 없으면 전체 멤버 리스트 반환
        if (searchResults.isEmpty()) {
            return memberService.getAllMembersSorted();  // 전체 회원 목록을 반환
        }

        return searchResults;
    }


}
