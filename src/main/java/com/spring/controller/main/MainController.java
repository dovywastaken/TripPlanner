package com.spring.controller.main;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.domain.Member;

@Controller
public class MainController
{
	
	@GetMapping("/") //메인페이지 뷰 페이지 띄워주는 메서드
	public String mainPage(Model model, HttpSession session) //로그인 한 상태에서 메인페이지에 갔을 때가 있어 세션과 모델을 챙겨간다
	{
		System.out.println("===========================================================================================");

		Member member = new Member();
		model.addAttribute("member", member);
        
		System.out.println("MainController : 프로젝트명으로 매핑되어 mainPage.jsp로 이동합니다");
		return "mainPage";
	}
}
