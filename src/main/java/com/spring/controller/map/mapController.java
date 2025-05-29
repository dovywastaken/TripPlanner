package com.spring.controller.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@PropertySource("classpath:properties/API.key.properties") 
public class mapController {
	
	private static final Logger logger = LoggerFactory.getLogger(mapController.class);
	
	
	@Value("${MapAPI.key}")
	private String mapAPIKey;
	
	
	@GetMapping("/map")
    public String map(Model model) 
 	{
		logger.info("===========================================================================================");		
	 	logger.info("mapController : map(GET)으로 매핑되었습니다");
	 	
	 	model.addAttribute("mapAPIKey", mapAPIKey);
	 	
	 	logger.info("map(GET) : API키를 가지고 맵 뷰로 이동합니다.");
        return "post/map";
    }
	

	
	@GetMapping("/map/detailed")
    public String mapPage(@RequestParam("info") String info, Model model) 
 	{
		logger.info("===========================================================================================");		
	 	logger.info("mapController : /map/detailed(GET)으로 매핑되었습니다");
	 	model.addAttribute("mapAPIKey", mapAPIKey);
        model.addAttribute("info", info);
        
        logger.info("/map/detailed(GET) : API키와 상세정보를 가지고 맵 뷰로 이동합니다.");
        return "post/detailedMap"; 
    }
	
}
