package com.upbest.mvc.controller;

import com.google.gson.Gson;
import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lili on 15-1-11.
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController {
    @Autowired
    private IAreaService areaService;
    @RequestMapping("/getRootArea")
    @ResponseBody
    //获取大区，南区北区中区，所有parent为-1的区域
    public void getRootArea(HttpServletResponse response){
      List<BArea>  areaList=areaService.findByParent(-1);
        Gson gson=new Gson();
        String result=gson.toJson(areaList);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/getCityListByAreaId")
    @ResponseBody
    //获取大区，南区北区中区，所有parent为-1的区域
    public void getCityListByAreaId(Integer areaId,HttpServletResponse response){
        List<BArea>  areaList=areaService.findByParent(areaId);
        Gson gson=new Gson();
        String result=gson.toJson(areaList);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
