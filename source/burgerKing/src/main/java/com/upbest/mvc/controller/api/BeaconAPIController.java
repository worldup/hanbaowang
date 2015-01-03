package com.upbest.mvc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.upbest.exception.FacilityNotMatchException;
import com.upbest.exception.UsernameAndPasswordNotMatchException;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IBeaconService;
import com.upbest.mvc.service.IFacilityLoginService;
import com.upbest.mvc.service.IShopStatisticService;
import com.upbest.mvc.service.IStoreService;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;

/**
 * 
 * @ClassName   类  名   称：	BeaconAPIController.java
 * @Description 功能描述：	beancon设备接口
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月26日上午9:25:34
 */
@Controller
@RequestMapping("/api/beacon")
public class BeaconAPIController {

    @Autowired
    private IBeaconService service;

    public static final String VERIFY_SUCCESS = "OK";
    public static final String VERIFY_NULL = "相关参数为空";
    public static final String PARAM_ILLEGAL = "参数非法";

    private static final Logger logger = LoggerFactory.getLogger(BeaconAPIController.class);

    /**
     * 
     * @Title 		   	函数名称：	get
     * @Description   	功能描述：	根据UUID获取门店相关信息
     * @param 		   	参          数：	
     * @return          返  回   值：	Json  
     * @throws
     */
    @RequestMapping(value = "/securi_get")
    @ResponseBody
    public Json get(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String uuid = o.getString("uuid");
        if (StringUtils.isBlank(uuid)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        result.setObj(service.findStoreByUuid(uuid));
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
}
