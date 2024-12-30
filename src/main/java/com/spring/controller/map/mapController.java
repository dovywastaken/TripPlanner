package com.spring.controller.map;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;


import com.spring.service.map.mapService;




@Controller
@RequestMapping("/Maps")
public class mapController {
	@Autowired
	mapService mapService;
	
	 @GetMapping
	    public String Mapss() {
	        return "post/Maps"; // JSP 파일 경로
	    }
	

	
	@GetMapping("/test")
	public String getMethodName() {
		return "post/test";
	}
	
	
}
