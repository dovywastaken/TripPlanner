package com.spring.controller.member;

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
import com.spring.service.member.MemberService;

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
        return "member/signUp";
    }

    //AJAX 요청 아이디,이메일 중복 확인 함수
    @GetMapping("/idCheckDuplicate") 
    @ResponseBody
    public Map<String, Boolean> idCheckDuplicate(@RequestParam String value) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : /members/idCheckDuplicate(GET)으로 매핑되었습니다");
        System.out.println("아이디"+value);
        
        boolean notAvailable = memberService.idCheckUp(value);

        Map<String, Boolean> response = new HashMap<>();

        response.put("notAvailable", notAvailable);
        
        return response; // JSON 반환
    }


    //AJAX 요청 이메일 중복 확인 함수
    @GetMapping("/emailCheckDuplicate") 
    @ResponseBody
    public Map<String, Boolean> emailCheckDuplicate(@RequestParam String emailId, @RequestParam String domain) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : /members/emailCheckDuplicate(GET)으로 매핑되었습니다");

        String email = emailId + "@"+ domain;
        System.out.println("뷰페이지로부터 파라미터로 받아 들고온 이메일은 "+email);
        boolean notAvailable = memberService.emailCheckUp(email);

        Map<String, Boolean> response = new HashMap<>();

        response.put("notAvailable", notAvailable);
        
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
        return "member/signIn";
    }

    //로그인 폼 전송
    @PostMapping("/signIn") 
    public String fromLoginPage(@ModelAttribute("member") Member member, HttpSession session, Model model,
                                @RequestParam(value = "dummy", required = false) String dummy) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/signIn(POST)으로 매핑되었습니다");
        
        // 로그인을 위한 회원 정보 조회
        Member loginMb = memberService.findById(member.getId());
        
        if ("1".equals(dummy)) {  // dummy가 1일 때 로그인 시도
            if (loginMb != null && loginMb.getPw().equals(member.getPw())) {
                session.setAttribute("user", loginMb);
                System.out.println("로그인 성공, 메인페이지로 리다이렉션");
                return "redirect:/"; // 로그인 성공 시 메인페이지로 리다이렉션
            } else if (member.getId() == null || member.getId().isEmpty()) {
                model.addAttribute("EmptyForm", "아이디를 입력해주세요");
                System.out.println("아이디가 빈 경우");
                return "mainPage"; // 아이디가 빈 경우 로그인 페이지로 이동
            } else if (member.getPw() == null || member.getPw().isEmpty()) {
                model.addAttribute("EmptyForm", "비밀번호를 입력해주세요");
                System.out.println("비밀번호가 빈 경우");
                return "mainPage"; // 비밀번호가 빈 경우 로그인 페이지로 이동
            } else {
                model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
                System.out.println("아이디 또는 비밀번호가 틀린 경우");
                return "mainPage"; // 아이디 또는 비밀번호가 일치하지 않는 경우 로그인 페이지로 이동
            }
        } else {  // dummy가 없을 경우, 로그인 페이지에서 정상적으로 시도
            if (loginMb != null && loginMb.getPw().equals(member.getPw())) {
                session.setAttribute("user", loginMb);
                System.out.println("로그인 성공, 메인페이지로 리다이렉션");
                return "redirect:/"; // 로그인 성공 시 메인페이지로 리다이렉션
            }else if (member.getId() == null || member.getId().isEmpty()) {
                model.addAttribute("EmptyForm", "아이디를 입력해주세요");
                System.out.println("아이디가 빈 경우");
                return "member/signIn"; // 아이디가 빈 경우 로그인 페이지로 이동
            } else if (member.getPw() == null || member.getPw().isEmpty()) {
                model.addAttribute("EmptyForm", "비밀번호를 입력해주세요");
                System.out.println("비밀번호가 빈 경우");
                return "member/signIn"; // 비밀번호가 빈 경우 로그인 페이지로 이동
            } else {
                model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
                System.out.println("아이디 또는 비밀번호가 일치하지 않는 경우");
                return "member/signIn"; // 아이디 또는 비밀번호가 일치하지 않는 경우 로그인 페이지로 이동
            }
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
    
    // 마이페이지로 이동
    @GetMapping("/myPage")
    public String toMemberInfo(Model model) {
        
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/myPage(GET)으로 매핑되어 memberInfo.jsp로 이동합니다");
        model.addAttribute("member", new Member());
        return "member/myPage";
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
    
 // 회원 정보 수정 페이지로 이동
    @GetMapping("/updateMember")
    public String toUpdateMember(HttpSession session, Model model) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/updateMember(GET)으로 매핑되어 updateMember.jsp로 이동합니다");

        // 로그인한 유저 정보 가져오기
        Member member = (Member)session.getAttribute("user");
        model.addAttribute("member", member);
        
        return "member/updateMember";
    }

    // 회원 정보 수정 요청 처리
    @PostMapping("/updateMember")
    public String fromUpdateMember(@ModelAttribute("user") Member member , HttpSession session, Model model,
                                    @RequestParam String emailId, @RequestParam String emailDomain) {
        System.out.println("===========================================================================================");
        System.out.println("MemberController : members/updateMember(POST)으로 매핑되었습니다");
        System.out.println("기존 이메일 주소 : "+ member.getEmail());
    
        
        // 이메일 설정
        member.setEmail(emailId, emailDomain);
        String id = member.getId();
        String phone = member.getPhone1();
        String[] phoneList = phone.split("-");
    	member.setPhone1(phoneList[0]);
        member.setPhone2(phoneList[1]);
        member.setPhone3(phoneList[2]);
        
        // 회원 정보 업데이트
        memberService.updateMember(member);
        Member updatedMember = memberService.findById(id);
        System.out.println("파인드바이아이디 통과 ");
        

        // 세션에 업데이트된 정보 반영
        session.setAttribute("user", updatedMember);
        System.out.println("세션 담기 통과 ");

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
    	return "member/pwChange";
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
        
        return "member/deleteMember";
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
        return "member/deleteMemberSuccess";
    }
    
}