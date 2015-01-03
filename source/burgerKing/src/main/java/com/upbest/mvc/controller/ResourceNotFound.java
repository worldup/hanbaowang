package com.upbest.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resourceNotFound")
public class ResourceNotFound {
	
	@RequestMapping("/index")
	public String index(){
		return "resourceNotFound/index";
	}
}
