package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.cj.Session;
import com.spring.domain.Member;
import com.spring.service.MemberService;

@Controller
@RequestMapping("/admin")
public class AdminController 
{
    @Autowired
    private MemberService memberService;

    private void setPagingData(Model model, int page, String keyword, int limit) 
    {
    	System.out.println("===========================================================================================");
        System.out.println("AdminController : setPagingData 메서드 호출");
        int offset = (page - 1) * limit; //몇번 째 페이지 보여줄 지 결정하는 변수 //dashboard들어오면 일단 page는 1이라 1-0 * limit이 되면 offset이 0부터 시작임
        List<Member> memberList; //멤버 리스트 담을 리스트 객체
        if (keyword == null) //검색어 없으면 모든 페이지 출력
        {
            memberList = memberService.readAllMemberPaging(limit, offset, keyword);
        }
        else //검색어 있으면 검색어에 맞는 페이지 출력
        {
            memberList = memberService.searchMember(keyword, limit, offset);
        }

        
        int totalMemberCount = memberService.getTotalMemberCount(keyword); //키워드 값에 부합하는 데이터가 몇개 있는지 저장
        int totalPages = (int) Math.ceil((double) totalMemberCount / limit); //만약 검색어에 맞는 데이터가 10개 나오면 10/limit. //Math.ceil 함수는 소수점을 올려줌
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        System.out.println("AdminController : setPagingData 메서드 종료");
    }

    @GetMapping("/dashboard")
    public String allMember(@RequestParam(value = "page", defaultValue = "1") int page, 
				    		@RequestParam(value = "keyword", defaultValue = "") String keyword, 
				    		Model model) 
    {
    	System.out.println("===========================================================================================");
        System.out.println("AdminController : admin/dashboard 로 매핑");
    	int limit = 5; // 한 페이지에 표시할 회원 수
        setPagingData(model, page, keyword, limit);
        
        System.out.println("AdminController : admin.jsp 로 이동");
        return "admin";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam String keyword, 
    							@RequestParam(value = "page", defaultValue = "1") int page, 
    							Model model) 
    {
    	System.out.println("===========================================================================================");
        System.out.println("AdminController : admin/search 로 매핑");
    	int limit = 5; // 한 페이지에 표시할 회원 수
        setPagingData(model, page, keyword, limit);
        
        System.out.println("AdminController : admin.jsp 로 이동");
        return "admin";
    }

}

