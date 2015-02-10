package com.upbest.mvc.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.upbest.mvc.entity.UserWorkingLeave;
import com.upbest.mvc.service.IUserWorkingLeaveService;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import org.apache.commons.lang.ArrayUtils;
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
@RequestMapping("/api/userWorkingTime")
public class UserWorkingLeaveAPIController {
    @Autowired
    private IUserWorkingLeaveService iUserWorkingTimeService;
    @RequestMapping(value = "/securi_add")
    @ResponseBody
    public Json addUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        try{
            String entity= o.getString("entity");
            Gson gson=new Gson();
            UserWorkingLeave workingLeave= gson.fromJson(entity, UserWorkingLeave.class);
            iUserWorkingTimeService.addUserWorkingLeave(workingLeave);
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
    @RequestMapping(value = "/securi_query")
    @ResponseBody
    public Json queryUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        try {

        Integer userId = o.getInteger("userId");
        Date startTime = o.getDate("startTime");
        Date endTime = o.getDate("endTime");
        List<UserWorkingLeave> userWorkingLeaves = iUserWorkingTimeService.queryUserWorkingLeave(userId, startTime, endTime);
        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setMsg("查询成功");
        result.setSuccess(true);
        result.setObj(userWorkingLeaves);
       }catch (Exception e){

        result.setCode(com.upbest.mvc.constant.Constant.Code.ILLEGAL_CODE);
        result.setMsg("查询失败"+e.getMessage());
        result.setSuccess(false);
        }
        return result;

    }
    @RequestMapping(value = "/securi_del")
    @ResponseBody
    public Json delUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        try{
            String ids= o.getString("ids");
            String [] idArr=StringUtils.split(",");
            List<Integer> idlist=new ArrayList();
            if(ArrayUtils.isNotEmpty(idArr)){
                for(String id:idArr){
                    idlist.add(Integer.valueOf(id));
                }

            }
            iUserWorkingTimeService.delUserWorkingLeaveBatch(idlist);
            result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
            result.setMsg("删除成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setCode(com.upbest.mvc.constant.Constant.Code.ILLEGAL_CODE);
            result.setMsg("删除失败"+e.getMessage());
            result.setSuccess(false);
    }

        return result;

    }
}
