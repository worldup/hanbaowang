package com.upbest.mvc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IExamSerivce;
import com.upbest.pageModel.Json;

@Controller
@RequestMapping("/api/exam")
public class BExamAPIController {
    @Autowired
    private IExamSerivce service;
    
    public static final String VERIFY_SUCCESS = "OK";
    public static final String VERIFY_NULL = "相关参数为空";
    public static final String PARAM_ILLEGAL = "参数非法";

    private static final Logger logger = LoggerFactory.getLogger(BeaconAPIController.class);
    
    
    @RequestMapping(value = "/securi_get")
    @ResponseBody
    public Json get(HttpServletRequest req) {
        Json result = new Json();
        result.setObj(service.findById());
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
    
}
