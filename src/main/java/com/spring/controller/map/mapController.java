package com.spring.controller.map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@PropertySource("classpath:properties/API.key.properties") 
public class mapController {
	
	@Value("${MapAPI.key}")
	private String mapAPIKey;
	
	
	@GetMapping("/map")
    public String map(Model model) 
 	{
	 	System.out.println("map로 들어옴");
	 	System.out.println("apikey "+ mapAPIKey);
	 	model.addAttribute("mapAPIKey", mapAPIKey);
        return "post/map";
    }
	

	
	@GetMapping("/map/detailed")
    public String mapPage(@RequestParam("info") String info, Model model) 
 	{
		System.out.println("detailedMap으로 들어옴");
	 	System.out.println("apikey "+ mapAPIKey);
	 	model.addAttribute("mapAPIKey", mapAPIKey);
        model.addAttribute("info", info);
        
        return "post/detailedMap"; 
    }
	
}
