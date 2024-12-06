package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController
{
	@GetMapping("/")
	public String mainPage() 
	{
		System.out.println("===========================================================================================");
    	System.out.println("MainController : 프로젝트명으로 매핑되어 mainPage.jsp로 이동합니다");
		return "mainPage";
	}
}
