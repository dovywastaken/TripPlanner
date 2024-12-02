package com.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.domain.Member;
import com.spring.repository.MemberRepository;


@Controller
public class MemberController 
{
	@Autowired
	private MemberRepository memberRepository;
	
	@GetMapping("/") //메인페이지로
	public String mainPage(HttpSession session, Model model) {
	    // 세션에서 "user" 객체 가져오기
	    Member user = (Member) session.getAttribute("user");
	    
	    // 세션에 user가 있다면 모델에 추가
	    model.addAttribute("user", user);
	    
	    return "mainPage"; // mainPage.jsp로 이동
	}

	
//=====================================================================================================================================================
	
	@GetMapping("/signUp") //회원가입 페이지
	public String toSignUp()
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: singUp으로 매핑되어 컨트롤러로 들어왔습니다]");
		System.out.println("loginSuccess.jsp로 이동합니다");
		
		return "signUp";
	}
	
	@PostMapping("/signUp") //회원가입
	public String fromSignUp(@ModelAttribute Member member, Model model) {
	    System.out.println("회원가입 요청 데이터: " + member);

	    // DB에 회원 저장
	    memberRepository.createMember(member);

	    // 성공 시 리다이렉션
	    return "redirect:/";
	}
	
//=====================================================================================================================================================
	
	@GetMapping("/signIn") //로그인 페이지로
	public String toLoginPage() 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: signIn으로 매핑되어 컨트롤러로 들어왔습니다]");
		System.out.println("login.jsp로 이동합니다");
		
		return "login";
	}
	
	
	@PostMapping("/signIn")
	public String fromLoginPage(@ModelAttribute Member member, HttpSession session, Model model) {
	    Member dbMember = memberRepository.findById(member.getId());

	    if (dbMember != null && dbMember.getPw().equals(member.getPw())) {
	        session.setAttribute("user", dbMember); // 세션에 사용자 정보 저장
	        return "redirect:/"; // 로그인 성공 시 메인 페이지로 리다이렉션
	    } else {
	        model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
	        return "login"; // 로그인 실패 시 다시 로그인 페이지로
	    }
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); // 세션 무효화
	    return "redirect:/"; // 메인 페이지로 리다이렉션
	}
	
	//=====================================================================================================================================================
	
	@GetMapping("/updateMember")
	public String toUpdateMember(HttpSession session) 
	{
		System.out.println(session.getAttribute("user"));
		return "updateUser";
	}
	
	@PostMapping("/updateMember")
	public String fromUpdateMember(HttpSession session) 
	{
		System.out.println("");
		System.out.println(session.getAttribute("user"));
		return "redirect:/";
	}
	
}
