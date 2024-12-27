package com.spring.controller.landmark;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/landmark")
public class LandmarkController {

    @GetMapping("/list")
    public String toLandmarkList() {
    	
    	
        return "landmark/landmarkList";
    }
}