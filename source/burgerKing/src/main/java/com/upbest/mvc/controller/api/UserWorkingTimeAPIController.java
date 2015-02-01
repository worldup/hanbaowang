package com.upbest.mvc.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.upbest.mvc.service.IUserWorkingTimeService;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lili on 2015/2/1.
 */
@Controller
@RequestMapping("/api/userWorkingTime")
public class UserWorkingTimeAPIController {
    @Autowired
    private IUserWorkingTimeService iUserWorkingTimeService;
    @RequestMapping(value = "/securi_add")
    @ResponseBody
    public Json addUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();

        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setMsg("保存成功");
        result.setSuccess(true);
        result.setObj(null);
        return result;

    }
    @RequestMapping(value = "/securi_query")
    @ResponseBody
    public Json queryUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();

        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setMsg("查询成功");
        result.setSuccess(true);
        result.setObj(null);
        return result;

    }
    @RequestMapping(value = "/securi_del")
    @ResponseBody
    public Json delUserWorkingTime(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();

        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setMsg("删除成功");
        result.setSuccess(true);
        result.setObj(null);
        return result;

    }
}
