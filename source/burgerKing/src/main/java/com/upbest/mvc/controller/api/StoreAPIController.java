package com.upbest.mvc.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.upbest.mvc.vo.ShopRankVO;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IShopStatisticService;
import com.upbest.mvc.service.IStoreService;
import com.upbest.mvc.vo.ShopAreaVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;

/**
 * 
 * @ClassName   类  名   称：	StoreController.java
 * @Description 功能描述：	门店相关接口
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月20日下午2:41:47
 */
@Controller
@RequestMapping("/api/store")
public class StoreAPIController {

    @Autowired
    private IStoreService service;

    @Autowired
    private IShopStatisticService shopStatisticService;
    @Autowired
    private IStoreService storeService;
    public static final String VERIFY_SUCCESS = "OK";
    public static final String VERIFY_NULL = "相关参数为空";
    public static final String PARAM_ILLEGAL = "参数非法";
    public static final String OPER_ERROR = "操作失败";

    private static final Logger logger = LoggerFactory.getLogger(StoreAPIController.class);

    /**
     * 
     * @Title 		   	函数名称：	get
     * @Description   	功能描述：	查看门店详情
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
        String id = o.getString("id");
        if (StringUtils.isBlank(id)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        result.setObj(service.findByAreaId(DataType.getAsInt(id)));
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
    /**
     * 
     * @Title 		   	函数名称：	getStsForReport
     * @Description   	功能描述：	门店报表接口
     * @param 		   	参          数：	
     * @return          返  回   值：	Json  
     * @throws
     */
    @RequestMapping(value = "/securi_getStsForReport")
    @ResponseBody
    public Json getStsForReport(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        //门店编号
        String shopId = o.getString("shopId");
        //年份
        String year=o.getString("year");
        if (StringUtils.isBlank(shopId)||StringUtils.isBlank(year)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        result.setObj(shopStatisticService.queryShopStatistic(DataType.getAsInt(shopId), year));
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }


    /**
     * 
     * @Title 		   	函数名称：	list
     * @Description   	功能描述：   查看门店列表
     * @param 		   	参          数：	
     * @return          返  回   值：	Json  
     * @throws
     */
    @RequestMapping(value = "/securi_list")
    @ResponseBody
    public Json list(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        // 门店名称
        String shopName = o.getString("shopName");
        String page = o.getString("page");
        String pageSize = o.getString("pageSize");
        String userId = o.getString("userId");
        if (StringUtils.isBlank(userId)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        try {
            Integer buserId = DataType.getAsInt(userId);
            if(StringUtils.isBlank(page)){
                result.setObj(service.getShopInfoVOList(buserId, shopName));
            }else if(StringUtils.isNotBlank(pageSize)){
                Integer pageInt = DataType.getAsInt(page);
                Integer pageSizeInt = DataType.getAsInt(pageSize);
                PageRequest requestPage = new PageRequest(pageInt != null ? pageInt - 1 : 0, pageSizeInt, new Sort(Direction.DESC, new String[] { "t.id" }));
                result.setObj(service.getShopInfoVOList(shopName, buserId, requestPage));
            }
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
     * 某个门店的详细统计信息
     * @return
     */
    @RequestMapping(value = "/securi_shopStatistic_detail")
    @ResponseBody
    public Json shopStatisticDetail(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        // 门店名称
        String shopId = o.getString("shopId");
        if (StringUtils.isEmpty(shopId)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }

        try {
            result.setObj(shopStatisticService.queryShopStatistic(shopId));
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
        return result;
    }

    /**
     * 某个门店的详细统计信息
     * @return
     */
    @RequestMapping(value = "/securi_shopStatistic_list")
    @ResponseBody
    public Json listShopStatistic(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();

        // 要统计的问卷类型
        String[] examType = null;
        String[] sortInfo = null;
        if (o != null) {
            // 获取考卷的类型
            if (o.get("examType") != null) {
                JSONArray jsonArray = ((JSONArray) o.get("examType"));
                examType = jsonArray.toArray(new String[jsonArray.size()]);
            }
            if (o.get("sortInfo") != null) {
                JSONArray jsonArray = ((JSONArray) o.get("sortInfo"));
                sortInfo = jsonArray.toArray(new String[jsonArray.size()]);
            }
        }
        try {
            // result.setObj(shopStatisticService.queryShopStatistic(examType,sortInfo));
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
        return result;
    }

    /**
     * 某个门店的详细统计信息
     * @return
     */
    @RequestMapping(value = "/securi_shopStatistic")
    @ResponseBody
    public Json shopStatistic(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();

        // 要统计的问卷类型
        String[] showField = null;
        String[] fields = null;
        Integer[] examType = null;
        ShopAreaVO shopArea = null;
        String sortField = "";
        if (o != null) {
            // 获取考卷的类型
            if (StringUtils.isNotEmpty(o.getString("examType"))) {
            	String examTypeStr = o.getString("examType");
            	if(!StringUtils.isEmpty(examTypeStr)){
            		String[] strAry = examTypeStr.split(",");
            		examType = new Integer[strAry.length];
            		for(int i = 0;i < strAry.length;i++){
            			examType[i] = Integer.parseInt(strAry[i]);
            		}
            	}
            }
            o.getString("filterField");
            shopArea = o.getObject("shopArea", ShopAreaVO.class);
            sortField = o.getString("sortField");
        }
        int year = o.getInteger("year");
        int month = o.getInteger("month");
        Date startTime = DataType.getAsDate(getDateStr(year,month), "yyyyMM");
        Date endTime = new DateTime(startTime).plusMonths(1).toDate();
        Integer userId = o.getInteger("userId");
        try {
            result.setObj(shopStatisticService.queryShopStatistic(userId,examType, shopArea,startTime,endTime,sortField));
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
        return result;
    }

    private String getDateStr(int year, int month) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(year);
    	sb.append(month < 10 ? ("0" + month) : month);
		return sb.toString();
	}
	private String[] pickFields(String[] showField) {
    	List<String> fields = new ArrayList<String>();
    	for (String str : showField) {
    		try {
				Integer.valueOf(str);
			} catch (Exception e) {
				fields.add(str);
			}
		}
    	
		return fields.toArray(new String[fields.size()]);
	}
	private Integer[] pickExamType(String[] showField) {
		List<Integer> fields = new ArrayList<Integer>();
    	for (String str : showField) {
    		try {
    			fields.add(Integer.valueOf(str));
			} catch (Exception e) {
			}
		}
    	
		return fields.toArray(new Integer[fields.size()]);
	}
	@RequestMapping(value = "/securi_listAll")
    @ResponseBody
    public Json listAll(HttpServletRequest req) {
        Json result = new Json();
        try {
            result.setObj(service.getShopInfoVOList(null,""));
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("操作失败", e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg(OPER_ERROR);
        }
        return result;
    }
	
	
	/**
     * 更新经维度
     * @return
     */
    @RequestMapping(value="/securi_updateLngLat")
    @ResponseBody
    public Json updateLngLat(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        Integer shopid=o.getInteger("shopid");
        String longitude=o.getString("longitude");
        String latitude=o.getString("latitude");
         try {
             service.updateLngLat(shopid,longitude,latitude);
             
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
         return result;
    }
    
    /**
     * 查询报表信息
     * @param req
     * @return
     */
    @RequestMapping(value="/securi_queryReportInfo")
    @ResponseBody
    public Json queryReportInfo(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        Integer shopid = o.getInteger("shopid");
        Date startTime = parseLong(o.getLong("startTime"));
        Date endTime = parseLong(o.getLong("endTime"));
         try {
             result.setObj(service.queryReportInfo(shopid,startTime,endTime));
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
         return result;
    }
    
    /**
     * 查询门店某份问卷类型的用户答题情况
     * @param req
     * @return
     */
    @RequestMapping(value="/securi_queryStoreTestInfo")
    @ResponseBody
    public Json queryStoreTestInfo(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        Integer shopid = o.getInteger("shopid");
        Integer examTypeId = o.getInteger("examTypeId");
        Date startTime = parseLong(o.getLong("startTime"));
        Date endTime = parseLong(o.getLong("endTime"));
         try {
             result.setObj(service.queryUserTestInfo(shopid,examTypeId,startTime,endTime));
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
         return result;
    }
    
    /**
     * 查询用户下的门店的tc,sales信息
     * @param req
     * @return
     */
    @RequestMapping(value="/securi_queryStatisticInfo")
    @ResponseBody
    public Json queryStatisticInfo(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        Integer userId = o.getInteger("userId");
         try {
             result.setObj(service.queryStatisticInfo(userId));
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
         return result;
    }
    
    
    @RequestMapping(value="/securi_getTaskDetail")
    @ResponseBody
    public Json getTaskDetail(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String userId = o.getString("userId");
        String workTypeId = o.getString("workTypeId");
        // 年度
        String year = o.getString("year");
        // 0:月度 1:季度(排序方式)
        String sord = o.getString("sord");
        // 月度
        String month = o.getString("month");
        // 季度(第一季度;第二季度;第三季度;第四季度)
        String quarter = o.getString("quarter");
        
        if (StringUtils.isBlank(year) || StringUtils.isBlank(userId)) {
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
             result.setObj(service.queryTaskDetails(userId, workTypeId,year,sord,month,quarter));
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg("获取失败");
        }
         return result;
    }
    public Json getNullResult() {
        Json result = new Json();
        result.setCode(Code.NULL_CODE);
        result.setMsg(VERIFY_NULL);
        result.setSuccess(false);
        result.setObj(null);
        return result;
    }
    private Date parseLong(Long lng){
    	return lng == null ? null : new Date(lng);
    }

    //添加小黑框
    public Map<String ,Object > getStoreMapBaseInfo(String shopId){
      return    service.findStoreMapBaseInfo(shopId);
    }
    @RequestMapping(value="/securi_getShopBaseInfo")
    @ResponseBody
    public Json getShopBaseInfo(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String shopId = o.getString("shopId");
        Map<String ,Object > map= getStoreMapBaseInfo(shopId);
        result.setObj(map);
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
    //添加门店排行
    @RequestMapping(value="/securi_getShopRank")
    public Json shopRank(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);

        JSONObject o = (JSONObject) j.getObj();
        Integer userId=o.getInteger("i_userid");
        String province=o.getString("in_province");
        String region=o.getString("in_regional");
        String fields=o.getString("i_fields");
        String orderFiled=o.getString("i_orderby");
        String month=o.getString("i_yearmonth");
        List<ShopRankVO> list= storeService.getShopRank(userId, province, region, fields, orderFiled, month);
        result.setObj(list);
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg(VERIFY_SUCCESS);
        return result;
    }
}
