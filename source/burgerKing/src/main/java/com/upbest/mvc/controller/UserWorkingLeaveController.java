package com.upbest.mvc.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.entity.UserWorkingLeave;
import com.upbest.mvc.service.IUserWorkingLeaveService;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by lili on 2015/2/1.
 */
@Controller
@RequestMapping("/userWorkingTime")
public class UserWorkingLeaveController {
    @Autowired
    private IUserWorkingLeaveService iUserWorkingTimeService;
    @Autowired
    private PushMessageServiceI pushMessageService;
    @RequestMapping(value = "/add")
    @ResponseBody
    public Json addUserWorkingTime(UserWorkingLeave userWorkingTime,HttpServletRequest req) {
        Json result = new Json();
        iUserWorkingTimeService.addUserWorkingTime(Lists.newArrayList(userWorkingTime));
        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setMsg("保存成功");
        result.setSuccess(true);
        result.setObj(null);
        return result;

    }
    @RequestMapping(value = "/query")
    @ResponseBody
    public Json queryUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        iUserWorkingTimeService.queryUserWorkingTime(1,new Date(),new Date());
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();

        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setMsg("查询成功");
        result.setSuccess(true);
        result.setObj(null);
        return result;

    }
    @RequestMapping(value = "/del")
    @ResponseBody
    public Json delUserWorkingTime(HttpServletRequest req)  throws Exception{
        Json result = new Json();
        Json j = Constant.convertJson(req);
        Buser buser=new Buser();
        buser.setId(507);
        pushMessageService.push("3386","507",buser,"测试","测四",null);
        JSONObject o = (JSONObject) j.getObj();

        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setMsg("删除成功");
        result.setSuccess(true);
        result.setObj(null);
        return result;

    }
}
