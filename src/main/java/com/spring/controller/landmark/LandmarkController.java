package com.spring.controller.landmark;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/landmark")
public class LandmarkController {


    @GetMapping("/list")
    public String toLandmarkList() {

        return "landmark/landmarkList";
    }
    
    
    @GetMapping("/apiData")
    @ResponseBody
    public String takeApiData() 
    {
    	
    	
    	
    	return "landmark/landmarkList";
    }
    
    

    @GetMapping("/list/detail")
    public String toDetailedPage() {
        return "landmark/landmarkListDetail";
    }
    
    
    
}
