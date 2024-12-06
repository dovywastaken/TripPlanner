package com.spring.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.domain.Member;
import com.spring.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/signUp")
    public String toSignUp(Model model) {
        // 회원 가입 폼 페이지로 이동
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signUp(GET)으로 매핑되어 signUp.jsp로 이동합니다");
        model.addAttribute("member", new Member());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String fromSignUp(@ModelAttribute("member") Member member) {
        // 회원 가입 요청 처리
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signUp(POST)으로 매핑");
        memberService.createMember(member);
        System.out.println("메인페이지로 리다렉션");
        return "redirect:/";
    }

    @GetMapping("/signIn")
    public String toLoginPage(Model model) {
        // 로그인 페이지로 이동
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signIn(GET)으로 매핑되어 signIn.jsp로 이동합니다");
        model.addAttribute("member", new Member());
        return "signIn";
    }

    @PostMapping("/signIn")
    public String fromLoginPage(@ModelAttribute("member") Member member, HttpSession session, Model model) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signIn(POST)으로 매핑되었습니다");
        
        Member loginMb = memberService.findById(member.getId());
        if (loginMb != null && loginMb.getPw().equals(member.getPw())) {
            session.setAttribute("user", loginMb);
            System.out.println("메인페이지로 리다렉션");
            return "redirect:/";
        } else {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
            System.out.println("로그인 정보가 맞지 않아 signIn.jsp로 이동합니다");
            return "signIn";
        }
    }


    @GetMapping("/signOut")
    public String signOut(HttpSession session) {
        // 로그아웃 처리
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signOut(GET)으로 매핑되었습니다");
        session.invalidate();
        System.out.println("메인페이지로 리다렉션");
        return "redirect:/";
    }

    @GetMapping("/updateMember")
    public String toUpdateMember(Model model) {
        // 회원 정보 수정 페이지로 이동
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/updateMember(GET)으로 매핑되어 updateMember.jsp로 이동합니다");
        model.addAttribute("member", new Member());
        return "updateMember";
    }

    @PostMapping("/updateMember")
    public String fromUpdateMember(@ModelAttribute("member") Member member) {
        // 회원 정보 수정 요청 처리
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/updateMember(POST)으로 매핑되었습니다");
        memberService.updateMember(member);
        System.out.println("메인페이지로 리다렉션");
        return "redirect:/";
    }

    @GetMapping("/deleteMember")
    public String toDeleteMember() {
        // 회원 탈퇴 페이지로 이동
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/deleteMember(GET)으로 매핑되어 deleteMember.jsp로 이동합니다");
        return "deleteMember";
    }

    @PostMapping("/deleteMember")
    public String fromDeleteMember(HttpSession session) {
        // 회원 탈퇴 요청 처리
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/deleteMember(POST)으로 매핑되었습니다");
        Member member = (Member) session.getAttribute("user");
        memberService.deleteMember(member);
        session.invalidate();
        System.out.println("deleteMemberSuccess.jsp로 이동합니다");
        return "deleteMemberSuccess";
    }
}
