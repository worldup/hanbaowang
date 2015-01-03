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
import com.upbest.exception.BurgerKingException;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IBSingIfnoService;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;

@Controller
@RequestMapping(value = "/api/sign")
public class SignAPIController {
	private static final Logger logger = LoggerFactory
			.getLogger(SignAPIController.class);

	public static final String SIGN_IN_SUCCESS = "签到成功";
	public static final String SIGN_IN_ERROR = "签到失败";
	public static final String SIGN_OUT_SUCCESS = "签出成功";
	public static final String SIGN_OUT_ERROR = "签出失败";
	public static final String OPERATOR_SUCCESS = "操作成功";
	public static final String OPERATOR_ERROR = "操作失败";
	@Autowired
	protected IBSingIfnoService singSeriver;

	/**
	 * 签到
	 * 
	 * @param userName
	 * @param lng
	 * @param lat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/securi_signIn")
	public Json signIn(HttpServletRequest req) {
		Json result = new Json();

		Json j = Constant.convertJson(req);
		JSONObject o = (JSONObject) j.getObj();
		int shopId = o.getIntValue("shopId");
		int userid = o.getIntValue("userid");
		String lng = o.getString("lng");
		String lat = o.getString("lat");
		String location = o.getString("location");
		
		try {
			singSeriver.signIn(shopId,userid, lng, lat, location);
			result.setCode(Code.SUCCESS_CODE);
			result.setSuccess(true);
			result.setMsg(SIGN_IN_SUCCESS);
		} catch (BurgerKingException e) {
			result.setCode(Code.ILLEGAL_CODE);
			result.setSuccess(false);
			result.setMsg(SIGN_IN_ERROR);
		}

		return result;
	}

	/**
	 * 签到
	 * 
	 * @param userName
	 * @param lng
	 * @param lat
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/securi_signOut")
	public Json signOut(HttpServletRequest req) {
		Json result = new Json();

		Json j = Constant.convertJson(req);
		JSONObject o = (JSONObject) j.getObj();
		int userid = o.getIntValue("userid");
		int shopId = o.getIntValue("shopId");
		try {
			singSeriver.signOut(shopId,userid);
			result.setCode(Code.SUCCESS_CODE);
			result.setSuccess(true);
			result.setMsg(SIGN_OUT_SUCCESS);
		} catch (BurgerKingException e) {
			result.setCode(Code.ILLEGAL_CODE);
			result.setSuccess(false);
			result.setMsg(SIGN_OUT_ERROR);
		}

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/securi_signInfo")
	public Json signInfo(HttpServletRequest req) {
		Json result = new Json();

		Json j = Constant.convertJson(req);
		JSONObject o = (JSONObject) j.getObj();
		int userid = o.getIntValue("userid");
		//是否查询最近一次签到消息
		String isLatest=o.getString("isLatest");
		try {
			result.setObj(singSeriver.getSignInfo(userid,isLatest));
			result.setCode(Code.SUCCESS_CODE);
			result.setSuccess(true);
			result.setMsg(OPERATOR_SUCCESS);
		} catch (BurgerKingException e) {
			result.setCode(Code.ILLEGAL_CODE);
			result.setSuccess(false);
			result.setMsg(OPERATOR_ERROR);
		}

		return result;
	}

}
