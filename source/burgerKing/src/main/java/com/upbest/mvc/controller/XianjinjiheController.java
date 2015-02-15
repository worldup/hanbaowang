package com.upbest.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lili on 2015/2/14.
 */
@Controller
@RequestMapping(value = "/xianjinjihe/")
public class XianjinjiheController {
    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("current", "xianjinjihe");
        return "/xianjinjihe/index";
    }
    @RequestMapping(value = "/guanjianxiangpinggu")
    public String guanjianxiangpinggu() {
        return "/xianjinjihe/guanjianxiangpinggu";
    }
    @RequestMapping(value = "/cunhuoguanli")
    public String cunhuoguanli() {
        return "/xianjinjihe/cunhuoguanli";
    }
    @RequestMapping(value = "/baoxianxiangguanli")
    public String baoxianxiangguanli() {
        return "/xianjinjihe/baoxianxiangguanli";
    }
    @RequestMapping(value = "/zhuanzhuoguanli")
    public String zhuanzhuoguanli() {
        return "/xianjinjihe/zhuanzhuoguanli";
    }
    @RequestMapping(value = "/wentifenxi")
    public String wentifenxi() {
        return "/xianjinjihe/wentifenxi";
    }
}
