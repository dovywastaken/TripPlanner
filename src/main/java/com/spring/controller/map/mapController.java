package com.spring.controller.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Maps")
public class mapController {
	
	 @GetMapping
	    public String Mapss() {
	        return "post/Maps"; // JSP 파일 경로
	    }
	

	
	@GetMapping("/test")
	public String getMethodName() {
		return "post/test";
	}
	
	
}
