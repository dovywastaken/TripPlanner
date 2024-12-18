package com.spring.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
	MailSender sender; //메일 서버에 접근해서 메일을 보내기 위해 사용하는 객체

    //회원가입 창 이동
    @GetMapping("/signUp") 
    public String toSignUp(Model model) { 
        // 회원 가입 폼 페이지로 이동
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signUp(GET)으로 매핑되어 signUp.jsp로 이동합니다");
        model.addAttribute("member", new Member()); //폼 태그에서 modelAttribute="member"를 사용하고 있기 때문에 멤버 객체를 들고 가야한다
        return "signUp";
    }

    //AJAX 요청 아이디 중복 확인 함수
    @GetMapping("/checkDuplicate") 
    @ResponseBody
    public Map<String, Boolean> checkDuplicate(@RequestParam String field, @RequestParam String value) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : /members/checkDuplicate(GET)으로 매핑되었습니다");

        boolean isAvailable = memberService.checkUp(field, value);

        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);

        return response; // JSON 반환
    }

    //회원가입 폼전송
    @PostMapping("/signUp") 
    public String fromSignUp(@ModelAttribute("member") Member member, @RequestParam String emailId, @RequestParam String emailDomain) {
        // 회원 가입 요청 처리
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signUp(POST)으로 매핑");
        member.setEmail(emailId, emailDomain);
        
        System.out.println("컨트롤러까지 결합된 이메일이 들어옴 "+member.getEmail());
        String phone = member.getPhone1();
        String[] phoneList = phone.split("-");
        
        for(int i=1; i<=phoneList.length; i++) 
        {
        	member.setPhone1(phoneList[0]);
            member.setPhone2(phoneList[1]);
            member.setPhone3(phoneList[2]);
        }
        
        memberService.createMember(member);
        System.out.println("메인페이지로 리다렉션");
        return "redirect:/";
    }

    //로그인 창 이동
    @GetMapping("/signIn") 
    public String toLoginPage(Model model) {
        // 로그인 페이지로 이동
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signIn(GET)으로 매핑되어 signIn.jsp로 이동합니다");
        model.addAttribute("member", new Member());
        return "signIn";
    }

    //로그인 폼 전송
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

    // 로그아웃 처리
    @GetMapping("/signOut")
    public String signOut(HttpSession session) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signOut(GET)으로 매핑되었습니다");
        session.invalidate();
        System.out.println("메인페이지로 리다렉션");
        return "redirect:/";
    }
    
    // 회원 정보 수정 페이지로 이동
    @GetMapping("/myPage")
    public String toMemberInfo(Model model) {
        
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/myPage(GET)으로 매핑되어 memberInfo.jsp로 이동합니다");
        model.addAttribute("member", new Member());
        return "myPage";
    }

    //AJAX 요청 이메일 전송 메서드
    @GetMapping("/email")
    @ResponseBody
    public Map<String, String> emailSend(HttpSession session) 
    {
    	Map<String, String> response = new HashMap<>();
    	
    	Member member = (Member)session.getAttribute("user");
    	
    	try 
    	{
    		String host = "http://localhost:8080/TripPlanner/members/emailCheck";
    		String from = "larrydaniels751@gmail.com";
    		String to = member.getEmail();
    		String who = member.getId();
    		String content = "클릭하여 이메일 인증을 완료해주십시요\n" + host + "?userID=" + who;
    		
    		SimpleMailMessage message = new SimpleMailMessage();
    		message.setTo(to);
    		message.setText(content);
    		message.setFrom(from);
    		sender.send(message);
    		
    		response.put("status", "success");
            response.put("message", "이메일이 성공적으로 전송되었습니다.");
    	}
    	catch(Exception e) 
    	{
    		response.put("status", "fail");
    		response.put("message", "이메일 전송에 실패했습니다.");
    	}
    	
		return response;
    }
    
    //인증 이메일 클릭 시 요청되는 메서드
    @GetMapping("/emailCheck")
    public String emailVerify(@RequestParam("userID")String userId, HttpSession session)
    {
    	System.out.println("===========================================================================================");
        System.out.println("MemberController : members/emailCheck 로 매핑");
    	Member member = (Member)session.getAttribute("user"); //현재 로그인된 계정 정보
    	String idCheck = userId; //이메일 링크 끝에 있는 아이디
    	String id = member.getId(); //현재 로그인된 계정의 아이디
    	
    	if(idCheck.equals(id)) 
    	{
    		memberService.updateEmail(id);
            Member mb = memberService.findById(id);
            System.out.println(mb.getEmailCheck());
            
            session.setAttribute("user", mb);
    	}
    	else 
    	{
    		System.out.println("아이디와 요청한 이메일의 아이디가 일치하지 않습니다");
    	}
    	
    	return "redirect:/members/myPage";
    }
    
    //회원 정보 수정 페이지로 이동
    @GetMapping("/updateMember")
    public String toUpdateMember(Model model) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/updateMember(GET)으로 매핑되어 updateMember.jsp로 이동합니다");
        model.addAttribute("member", new Member());
        return "updateMember";
    }
    
    // 회원 정보 수정 요청 처리
    @PostMapping("/updateMember")
    public String fromUpdateMember(@ModelAttribute("member") Member member,@RequestParam String emailId, 
    								@RequestParam String emailDomain, HttpSession session, Model model) 
    {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/updateMember(POST)으로 매핑되었습니다");
        member.setEmail(emailId, emailDomain);
        
        System.out.println("컨트롤러까지 결합된 이메일이 들어옴 "+ member.getEmail());
        String phone = member.getPhone1();
        String[] phoneList = phone.split("-");
        
        for(int i=1; i<=phoneList.length; i++) 
        {
        	member.setPhone1(phoneList[0]);
            member.setPhone2(phoneList[1]);
            member.setPhone3(phoneList[2]);
        }
        
        memberService.updateMember(member);
        String id = (String) member.getId();
        Member mb = memberService.findById(id);
        System.out.println(mb);
        
        session.setAttribute("user", mb);

        System.out.println("updateMemberComplete 메서드로 리다렉션");
        return "redirect:/";
    }
 
    //비밀번호 변경 페이지 이동
    @GetMapping("/pwChange")
    public String toPwChange(Model model) 
    {
    	System.out.println("===========================================================================================");
        System.out.println("MemberController : /members/toPwChange(GET)으로 매핑되었습니다");
    	model.addAttribute("member", new Member());
    	
    	
    	System.out.println("pwChange.jsp로 이동합니다");
    	return "pwChange";
    }
    
    //비밀번호 변경 처리
    @PostMapping("/pwChange")
    public String fromPwChange(@ModelAttribute("member") Member member, Model model, HttpSession session) 
    {
    	System.out.println("===========================================================================================");
        System.out.println("MemberController : /members/toPwChange(POST)으로 매핑되었습니다");
        Member mb = (Member)session.getAttribute("user");
        String id = mb.getId();
        String pw = member.getPw();
        
        System.out.println("기존 비밀번호 " + mb.getPw() + "에서 "+ member.getPw() + "로 수정합니다");
    	memberService.updatePw(pw,id);
        
        System.out.println("메인페이지로 리다렉션합니다");
    	return "redirect:/";
    }
    
    //입력한 비밀번호와 실제 비밀번호 일치 여부 확인 메서드
    @ResponseBody
    @GetMapping("/checkCurrentPw")
    public Map<String, String> checkCurrentPw(@RequestParam(value = "value", required = false, defaultValue = "") String value, HttpSession session)
    {
    	System.out.println("===========================================================================================");
        System.out.println("MemberController : members/checkCurrentPw 로 AJAX 매핑");
        System.out.println(value);
    	Map<String, String> response = new HashMap<>();
    	Member member = (Member)session.getAttribute("user");
    	String pw = member.getPw();
    	System.out.println(pw);
    	if(pw.equals(value)) {response.put("true", "true");}else if(value.equals("")) {response.put("none", "none");}else {response.put("false", "false");}
    	System.out.println(response);
    	
    	return response;
    }
    
    //회원 탈퇴페이지 이동
    @GetMapping("/deleteMember")
    public String toDeleteMember(HttpSession session, Model model) 
    {
    	System.out.println("===========================================================================================");
        System.out.println("MemberController : members/deleteMember(GET)으로 매핑되었습니다");
        Member member = (Member) session.getAttribute("user");
        model.addAttribute("member", member);
        System.out.println("deleteMember.jsp로 이동합니다");
        
        return "deleteMember";
    }

    //회원 탈퇴 처리
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
