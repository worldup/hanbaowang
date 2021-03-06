package com.upbest.mvc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IWorkService;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;

/**
 * 类型管理接口
 * @author 
 *
 */


@Controller
@RequestMapping("/api/work")
public class WorkAPIController {
    
    @Autowired
    private IWorkService service;
    
    
    public static final String VERIFY_SUCCESS = "OK";
    public static final String VERIFY_NULL = "相关参数为空";
    public static final String PARAM_ILLEGAL = "参数非法";

    private static final Logger logger = LoggerFactory.getLogger(WorkAPIController.class);
    @ResponseBody
    @RequestMapping("/securi_sendMail")
    public Json sendMail(HttpServletRequest req){

        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String userId=o.getString("userId");
        String month=o.getString("month");
        String emails=o.getString("emails");
        if (StringUtils.isBlank(userId)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        service.sendWorkPlanMailByUserIdExt(userId, month,emails);
        result.setObj(service.findWork(userId));
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
    @RequestMapping(value = "/securi_getwork")
    @ResponseBody
    public Json get(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String userId=o.getString("userId");
        if (StringUtils.isBlank(userId)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        result.setObj(service.findWork(userId));
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
    @RequestMapping(value = "/securi_getMonthlyWork")
    @ResponseBody
    public Json getMonthlyWork(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String omUserId=o.getString("omUserId");
        String ocUserId=o.getString("ocUserId");
        //month 2015-04-01
        String month=o.getString("month");
        if (StringUtils.isBlank(ocUserId)||StringUtils.isBlank(omUserId)||StringUtils.isBlank(month) ){
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
       String fileName= service.getWorkPlan4Excel(omUserId,ocUserId,month);
        result.setObj(fileName);
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
    
}
