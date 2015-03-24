package com.upbest.mvc.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.entity.UserWorkingLeave;
import com.upbest.mvc.service.IUserWorkingLeaveService;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        iUserWorkingTimeService.addUserWorkingLeave(userWorkingTime);
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
        iUserWorkingTimeService.queryUserWorkingLeave(1,new Date(),new Date());
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
    @RequestMapping(value = "/securi_add12")
    @ResponseBody
    public Json addUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        try{
            Integer userId=  509;
            Integer nonworkingtype=1;
            String date="2015-01-02;2015-01-02;2015-01-03";
            String action="0";
            String dateArr[]= StringUtils.split(date, ";");
            List<UserWorkingLeave> list=new ArrayList();
            for(String tempDate:dateArr){
                UserWorkingLeave leave=new UserWorkingLeave();
                leave.setUserId(userId);
                leave.setDay(tempDate);
                leave.setNonworkingType(nonworkingtype);
                list.add(leave);
            }
            //取消
            if("0".equals(action)){
                iUserWorkingTimeService.deleteUserWorkingLeave(list);
            }
            //添加
            else if("1".equals(action)){
                iUserWorkingTimeService.addUserWorkingLeave(list);
            }

            result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
            result.setMsg("保存成功");
            result.setSuccess(true);
        }catch (Exception e){

            result.setCode(com.upbest.mvc.constant.Constant.Code.ILLEGAL_CODE);
            result.setMsg("保存失败"+e.getMessage());
            result.setSuccess(false);
        }

        return result;

    }
}
