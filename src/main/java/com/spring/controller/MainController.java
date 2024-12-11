package com.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController
{
	
	@GetMapping("/")
	public String mainPage(Model model, HttpSession session) 
	{
		System.out.println("===========================================================================================");
		
		System.out.println("MainController : 프로젝트명으로 매핑되어 mainPage.jsp로 이동합니다");
		return "mainPage";
	}
	
	@GetMapping("/members/test")
	public String handleNaverLogin() {
	    System.out.println("네이버 로그인 성공?");
	    return "loginSuccess";
	}

	
	
}
