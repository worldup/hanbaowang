package com.upbest.mvc.controller;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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
    @ResponseBody
    @RequestMapping(value = "/save")
    public String save(@RequestParam("data")String data){
        Gson gson=new Gson();
        List<Map<String,String>> mapList=gson.fromJson(data, List.class);
        if(CollectionUtils.isNotEmpty(mapList)){
            for(Map<String,String> map:mapList){
                map.get("radioName");
                map.get("grade");
                map.get("text");
                map.get("checked");
            }
        }
        return gson.toString();
    }
}
