package com.upbest.mvc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbest.exception.BurgerKingException;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IAreaService;
import com.upbest.pageModel.Json;

@Controller
@RequestMapping(value = "/api/area")
public class AreaAPIController {
	private static final Logger logger = LoggerFactory
			.getLogger(AreaAPIController.class);

	@Autowired
	protected IAreaService areaService;
	
	private static final String SUCCESS = "操作成功";
	private static final String ERROR = "操作失败";
	
	/**
	 * 
	 * @param req
	 * @return	
	 */
	@ResponseBody
	@RequestMapping(value = "/securi_findRegion")
	public Json findRegion(HttpServletRequest req) {
		Json result = new Json();

		try {
			result.setObj(areaService.findAllAreaInfo());
			result.setCode(Code.SUCCESS_CODE);
			result.setSuccess(true);
			result.setMsg(SUCCESS);
		} catch (BurgerKingException e) {
			result.setCode(Code.ILLEGAL_CODE);
			result.setSuccess(false);
			result.setMsg(ERROR);
		}

		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/securi_findRegionInfo")
	public Json findRegionInfo(HttpServletRequest req) {
		Json result = new Json();

		try {
			result.setCode(Code.SUCCESS_CODE);
			result.setObj(areaService.findAllAreaInfo());
			result.setSuccess(true);
			result.setMsg(SUCCESS);
		} catch (BurgerKingException e) {
			result.setCode(Code.ILLEGAL_CODE);
			result.setSuccess(false);
			result.setMsg(ERROR);
		}

		return result;
	}
}
