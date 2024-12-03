package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Member;
import com.spring.repository.MemberRepository;
import com.spring.service.MemberService;


@Controller
public class MemberController 
{
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/") //메인페이지로
	public String mainPage(HttpSession session, Model model) 
	{
	    System.out.println("================================================================");
	    System.out.println("[MemberController: 프로젝트명으로 매핑되어 컨트롤러로 들어왔습니다]");

	    // 세션에서 "user" 객체 가져오기
	    Member user = (Member) session.getAttribute("user"); //세션 정보(로그인한 회원dto) 들고오기
	    if(user == null) session.invalidate(); //세션 정보 없으면 세션 객체 삭제?
	    model.addAttribute("user", user);
	    System.out.println("mainPage.jsp로 이동합니다");
	    
	    return "mainPage"; // mainPage.jsp로 이동
	}
	
//=====================================================================================================================================================

	@GetMapping("/signUp") //회원가입 페이지로 포워드시키기
	public String toSignUp()
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: singUp(GET)으로 매핑되어 컨트롤러로 들어왔습니다]");
		System.out.println("loginSuccess.jsp로 이동합니다");
		
		return "signUp";
	}
	
	@PostMapping("/signUp") //회원가입 받고 메인페이지로 리다렉션 시키기
	public String fromSignUp(@ModelAttribute Member member) 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: singUp(POST)으로 매핑되어 컨트롤러로 들어왔습니다]");
		System.out.println("회원가입 요청 데이터: " + member);
		memberService.createMember(member); // DB에 회원 저장
	    System.out.println("메인페이지로 리다렉션 합니다"); // 성공 시 리다이렉션
	    
	    return "signUpSuccess";
	}
	
//=====================================================================================================================================================

	@GetMapping("/signIn") //로그인 페이지로
	public String toLoginPage() 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: signIn(GET)으로 매핑되어 컨트롤러로 들어왔습니다]");
		System.out.println("login.jsp로 이동합니다");
		
		return "login";
	}
	
	
	@PostMapping("/signIn")
	public String fromLoginPage(@ModelAttribute Member member, HttpSession session, Model model) 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: signIn(POST)으로 매핑되어 컨트롤러로 들어왔습니다]");
	    Member loginMb = memberService.findById(member.getId());
	    System.out.println("로그인한 아이디 " + loginMb);
	    if (loginMb != null && loginMb.getPw().equals(member.getPw()))  //로그인 정보가 db에 있는 정보와 일치하다면
	    {
	        session.setAttribute("user", loginMb); // 세션에 로그인 정보 저장
	        System.out.println("세션에 로그인 정보를 담고 메인페이지로 리다렉션");
	        
	        return "redirect:/"; // 로그인 성공 시 메인 페이지로 리다이렉션
	    } 
	    else
	    {
	        model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
	        System.out.println("가져온 member 객체가 null 또는 정보가 일치하지 않아 다시 로그인 페이지로 포워드");
	        
	        return "login"; // 로그인 실패 시 다시 로그인 페이지로
	    }
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: logout(GET)으로 매핑되어 컨트롤러로 들어왔습니다]");
	    session.invalidate(); // 세션 무효화
	    System.out.println("로그인 세션 invalidate");
	    System.out.println("메인페이지로 리다렉션합니다");
	    
	    return "redirect:/"; // 메인 페이지로 리다이렉션
	}
	
//=====================================================================================================================================================
	
	@GetMapping("/updateMember") //업데이트 페이지로 들어오는 메서드
	public String toUpdateMember(HttpSession session) 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: updateMember(GET)으로 매핑되어 컨트롤러로 들어왔습니다]");
		System.out.println(session.getAttribute("user"));
		
		return "updateUser";
	}
	
	@PostMapping("/updateMember")
	public String fromUpdateMember(@ModelAttribute Member member, HttpSession session) //업데이트 페이지에서 폼태그 submit하면 실행되는 메서드
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: updateMember(POST)으로 매핑되어 컨트롤러로 들어왔습니다]");
		Member updatemb = (Member) session.getAttribute("user"); //세션에서 로그인한 멤버dto 가져오기
		memberService.updateMember(member); //비밀번호, 지역, 전화번호 업데이트
		
		System.out.println("정보 업데이트를 하고 메인 페이지로 리다렉션");
		return "redirect:/";
	}
	
//=====================================================================================================================================================
	
	@GetMapping("/deleteMember")
	public String toDeleteMember() 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: deleteMember(GET)으로 매핑되어 컨트롤러로 들어왔습니다]");
		System.out.println("deleteUser.jsp로 이동합니다");
		
		return "deleteUser";
	}
	
	@PostMapping("/deleteMember")
	public String fromDeleteMember(Member member, HttpSession session) 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: deleteMember(POST)으로 매핑되어 컨트롤러로 들어왔습니다]");
		
		member = (Member)session.getAttribute("user");
		
		memberService.deleteMember(member);
		session.invalidate();
		System.out.println("멤버 삭제를 하고 메인 페이지로 리다렉션");
		
		return "deleteUserSuccess";
	}
//=====================================================================================================================================================
	
	@GetMapping("/admin")
	public String toAdminPage(Model model) 
	{
		System.out.println("================================================================");
		System.out.println("[MemberController: admin(GET)으로 매핑되어 컨트롤러로 들어왔습니다]");
		List<Member> memberList = memberService.readAllMember();
		
		model.addAttribute("memberList",memberList);
		
		System.out.println("admin.jsp로 이동합니다");
		
		return "admin";
	}
	
	

	@GetMapping("/admin/sorted")
	@ResponseBody // JSON으로 응답
	public List<Member> getSortedMembers(@RequestParam String sortBy) {
	    System.out.println("================================================================");
	    System.out.println("[MemberController: admin/sorted(GET)으로 매핑되어 컨트롤러로 들어왔습니다]");
	    System.out.println("정렬 기준: " + sortBy);

	    return memberService.readAllMemberSorted(sortBy);
	}

}
