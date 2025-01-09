package com.spring.controller.map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/Maps")
public class mapController {
	
	 @GetMapping
	    public String Mapss() {
	        return "post/Maps"; // JSP 파일 경로
	    }
	

	
	 @GetMapping("/map")
	    public String mapPage(@RequestParam("info") String info, Model model) {
	        model.addAttribute("info", info);
	        return "post/map"; 
	    }
	
}
