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
import com.upbest.exception.FacilityNotMatchException;
import com.upbest.exception.UsernameAndPasswordNotMatchException;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IFacilityLoginService;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;

/**
 * 设备登陆接口
 * @author QunZheng
 *
 */
@Controller
@RequestMapping("/api/facilityLogin")
public class FacilityLoginController {
	
	@Autowired
	private IFacilityLoginService service;

    @Autowired
    private IBuserService userService;
	
	public static final String VERIFY_SUCCESS = "OK";
	public static final String VERIFY_NAME_PASSWORD_NOT_MATCH = "用户名密码不匹配";
	public static final String VERIFY_FACILITY_NAME_NOT_MATCH = "用户与设备不匹配";
	public static final String VERIFY_NULL= "相关参数为空";
	public static final String PARAM_ILLEGAL= "参数非法";
	
	private static final Logger logger = LoggerFactory.getLogger(FacilityLoginController.class);
	
	@RequestMapping(value="/securi_verify")
	@ResponseBody
	public Json verify(HttpServletRequest req){
		Json result = new Json();
		 Json j = Constant.convertJson(req);
		 JSONObject o = (JSONObject) j.getObj();
		 String facilityId=o.getString("facilityId");
		 String userName=o.getString("username");
		 String password=o.getString("password");
		 if(StringUtils.isBlank(facilityId)||StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
		     result.setCode(Code.NULL_CODE);
             result.setMsg(VERIFY_NULL);
             result.setObj(null); 
             result.setSuccess(false); 
             return result;
		 }
		try {
			int verifyCode = service.verify(facilityId, userName,password);
			if(verifyCode == IFacilityLoginService.SUCCESS){
				result.setCode(Code.SUCCESS_CODE);
				result.setMsg(VERIFY_SUCCESS);
				result.setObj(service.findFacilityLoginInfo(userName));
				result.setSuccess(true);
			}
		} catch (FacilityNotMatchException e) {
            logger.error(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg(VERIFY_FACILITY_NAME_NOT_MATCH);
        }catch (UsernameAndPasswordNotMatchException e) {
			logger.error(e.getMessage());
			result.setCode(Code.ILLEGAL_CODE);
			result.setSuccess(false);
			result.setMsg(VERIFY_NAME_PASSWORD_NOT_MATCH);
		} 
		
		return result;
	}
	
	   /**
     * 
     * @Title           函数名称：   list
     * @Description     功能描述：   查看门店列表
     * @param           参          数：   
     * @return          返  回   值：   Json  
     * @throws
     */
    @RequestMapping(value="/securi_list")
    @ResponseBody
    public Json list(HttpServletRequest req){
        Json result = new Json();
         Json j = Constant.convertJson(req);
         JSONObject o = (JSONObject) j.getObj();
         String userId=o.getString("userId");
         String name=o.getString("name");
         if(StringUtils.isBlank(userId)){
             result.setCode(Code.NULL_CODE);
             result.setMsg(VERIFY_NULL);
             result.setSuccess(false);
             result.setObj(null); 
             return result;
         }
         try {
             Integer buserId=DataType.getAsInt(userId);
             result.setObj(userService.getBUserList(buserId, name,"1"));
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ",e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
         return result;
    }
}
