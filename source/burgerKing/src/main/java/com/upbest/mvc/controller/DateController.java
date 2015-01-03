package com.upbest.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/date/")
public class DateController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("current","date");
        return "/date/dateList";
    }
}
