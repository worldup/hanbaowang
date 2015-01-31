package com.upbest.mvc.controller.api;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.upbest.mvc.vo.BStsVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.service.IStatisticTaskService;
import com.upbest.mvc.service.IWorkService;
import com.upbest.mvc.vo.StsResultVO;
import com.upbest.mvc.vo.StsStoreVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;

/**
 * 
 * @ClassName   类  名   称：	StsAPIController.java
 * @Description 功能描述：	计划或事件统计完成率
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月24日下午3:33:35
 */
@Controller
@RequestMapping("/api/sts")
public class StsAPIController {
    @Autowired
    private IWorkService workService;
    @Autowired
    private IStatisticTaskService service;

    public static final String VERIFY_SUCCESS = "OK";
    public static final String VERIFY_NULL = "相关参数为空";
    public static final String PARAM_ILLEGAL = "参数非法";

    private static final Logger logger = LoggerFactory.getLogger(StsAPIController.class);

    /**
     * 
     * @Title 		   	函数名称：	get
     * @Description   	功能描述：	计划或事件统计完成率
     * @param 		   	参          数：	
     * @return          返  回   值：	Json  
     * @throws
     */
    @RequestMapping(value = "/securi_sts")
    @ResponseBody
    public Json sts(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        // 年度
        String year = o.getString("year");
        // 0:事件 1:计划
        String type = o.getString("type");
        // 0:月度 1:季度(排序方式)
        String sord = o.getString("sord");
        // 月度
        String month = o.getString("month");
        // 季度(第一季度;第二季度;第三季度;第四季度)
        String quarter = o.getString("quarter");
        // 登录用户ID
        String userId = o.getString("userId");

        if (StringUtils.isBlank(year) || StringUtils.isBlank(type) || StringUtils.isBlank(userId)) {
            return getNullResult();
        }
        if (StringUtils.isNotBlank(sord)) {
            if ("0".equals(sord)) {
                // 月度
                if (StringUtils.isBlank(month)) {
                    return getNullResult();
                }
            } else if ("1".equals(sord)) {
                // 季度
                if (StringUtils.isBlank(quarter)) {
                    return getNullResult();
                }
            }
        } else {
            return getNullResult();
        }
        try {
            Integer userIdInt = DataType.getAsInt(userId);
            List<BStsVO> obj=service.queryStsRs(userIdInt, year, type, sord, month, quarter);
           List<BStsVO> objTrans=  Lists.transform(obj, new Function<BStsVO, BStsVO>() {

                @Override
                public BStsVO apply(BStsVO input) {
                    try {
                        BStsVO copy = (BStsVO) BeanUtils.cloneBean(input);
                        String role = copy.getUser().getRole();
                        String roleName = "未知";
                        if ("1".equals(role)) {
                            roleName = "OM";
                        } else if ("2".equals(role)) {
                            roleName = "OC";
                        } else if ("3".equals(role)) {
                            roleName = "OM+";
                        }

                        copy.getUser().setRole(roleName);
                        return copy;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            result.setObj(objTrans);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ", e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
        return result;
    }

    /**
     * 
     * @Title 		   	函数名称：	stsStore
     * @Description   	功能描述：	
     * @param 		   	参          数：	
     * @return          返  回   值：	Json  
     * @throws
     */
    @RequestMapping(value = "/securi_stsStore")
    @ResponseBody
    public Json stsStore(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        // 年度
        String year = o.getString("year");
        // 季度(第一季度;第二季度;第三季度;第四季度)
        String quarter = o.getString("quarter");
        // 登录用户ID
        String userId = o.getString("userId");

        if (StringUtils.isBlank(year) || StringUtils.isBlank(quarter) || StringUtils.isBlank(userId)) {
            return getNullResult();
        }
        try {
            Integer userIdInt = DataType.getAsInt(userId);
            List<StsResultVO> re = new ArrayList<StsResultVO>();
            StsResultVO resultVO = new StsResultVO();
            resultVO.setMonth(quarter);
            resultVO.setResult(service.queryStsStore(userIdInt, year, quarter, ""));
            re.add(resultVO);
            List<BWorkType> workTypeList = workService.findWorkWithOutNeededAndQuarter(DataType.getAsString(userId));
            List<Object[]> list=service.queryStsStoreObj( workTypeList,userIdInt,  year,  quarter,  "1");
            if ("第一季度".equals(quarter)) {
                resultVO = new StsResultVO();
                resultVO.setMonth("1");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"1"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("2");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"2"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("3");
                resultVO.setResult(getListVO( list,workTypeList, userIdInt,  year,  quarter,"3"));
                re.add(resultVO);
            } else if ("第二季度".equals(quarter)) {
                resultVO = new StsResultVO();
                resultVO.setMonth("4");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"4"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("5");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"5"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("6");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"6"));
                re.add(resultVO);
            } else if ("第三季度".equals(quarter)) {
                resultVO = new StsResultVO();
                resultVO.setMonth("7");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"7"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("8");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"8"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("9");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"9"));
                re.add(resultVO);
            } else if ("第四季度".equals(quarter)) {
                resultVO = new StsResultVO();
                resultVO.setMonth("10");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"10"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("11");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"11"));
                re.add(resultVO);
                resultVO = new StsResultVO();
                resultVO.setMonth("12");
                resultVO.setResult(getListVO(list, workTypeList, userIdInt,  year,  quarter,"12"));
                re.add(resultVO);
            }
            result.setObj(re);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ", e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
        return result;
    }
public List<StsStoreVO> getListVO(List<Object[]> list,List<BWorkType> workTypeList,Integer userIdInt, String year, String quarter, String month){
    List<StsStoreVO> monthList= service.doformatToStsStoreVO(list, workTypeList, userIdInt, year, quarter, month);
    return service.getListWithAllStores(monthList,workTypeList,month,userIdInt);
}
    public Json getNullResult() {
        Json result = new Json();
        result.setCode(Code.NULL_CODE);
        result.setMsg(VERIFY_NULL);
        result.setSuccess(false);
        result.setObj(null);
        return result;
    }
}
