package com.upbest.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lili on 15-1-11.
 */
@Controller
@RequestMapping("/toolbox")
public class ToolboxController {
    @RequestMapping("/index")
    public String index(Model model) throws Exception {
        model.addAttribute("current", "toolbox");

        return "toolbox/toolboxmain";
    }
}
