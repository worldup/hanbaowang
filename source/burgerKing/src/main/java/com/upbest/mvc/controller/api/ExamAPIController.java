package com.upbest.mvc.controller.api;

import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.constant.Constant.OperatorResultMsg;
import com.upbest.mvc.entity.BActionPlan;
import com.upbest.mvc.entity.BProblemAnalysis;
import com.upbest.mvc.entity.BTestHeadingRela;
import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.entity.BTestPaperDetail;
import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.service.IStoreService;
import com.upbest.mvc.service.ITestPaperDetailService;
import com.upbest.mvc.service.ITestPaperService;
import com.upbest.mvc.statistic.ExamStatistic;
import com.upbest.mvc.thread.SendEmailThread;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.ExamInfoVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/exam")
public class ExamAPIController {

    public static final String SUCCESS = "操作成功";
    public static final String ERROR = "操作失败";
    private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);

    public static final String VERIFY_NULL = "相关参数为空";
    public static final String PARAM_ILLEGAL = "参数非法";

    @Autowired
    private IExamService service;

    @Autowired
    private ExamStatistic stsService;

    @Autowired
    private ITestPaperDetailService testPaperDetailService;
    
    @Autowired
    private ITestPaperService paperService;
    
    @Autowired
    private IStoreService shopService;

    @RequestMapping(value = "/securi_examtype")
    @ResponseBody
    public Json findExamType() {
        Json result = new Json();

        List<SelectionVO> list;
        try {
            list = service.queryExamType();
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setObj(list);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }

        return result;
    }
    
    @RequestMapping(value = "/securi_getLatestExams")
    @ResponseBody
    public Json getLatestExams(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) j.getObj();
        try {
            String storeId=o.getString("storeId");
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setObj(service.queryExamType(storeId));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }

        return result;
    }
    
    @RequestMapping(value = "/securi_examtype_filter")
    @ResponseBody
    public Json findFilterExamType() {
        Json result = new Json();

        List<SelectionVO> list;
        try {
            list = service.queryFilterExamType();
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setObj(list);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }

        return result;
    }

    /**
     * @throws JSONException 
     * 
     * @Title           函数名称：   getExamList
     * @Description     功能描述：   查询问卷列表
     * @param           参          数：   
     * @return          返  回   值：   Json  
     * @throws
     */
    @RequestMapping(value = "/securi_getExamList")
    @ResponseBody
    public Json getExamList(HttpServletRequest req) throws JSONException {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) j.getObj();
        String page = o.getString("page");
        String pageSize = o.getString("pageSize");
        String userId = o.getString("userId");
        if (StringUtils.isBlank(page) || StringUtils.isBlank(pageSize)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setObj(null);
            result.setSuccess(false);
            return result;
        }
        List<ExamInfoVO> list;
        Integer pageInt = null;
        Integer pageSizeInt = null;
        Integer userIdInt = null;
        try {
            pageInt = DataType.getAsInt(page);
            pageSizeInt = DataType.getAsInt(pageSize);
            userIdInt = DataType.getAsInt(userId);
        } catch (Exception e) {
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
        try {
            Pageable pageable = new PageRequest(pageInt - 1, pageSizeInt);
            list = service.findExamInfo(pageable, userIdInt).getRows();
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setObj(list);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @throws JSONException 
     * 
     * @Title           函数名称：   securi_findExamInfo
     * @Description     功能描述：   查询考卷详情
     * @param           参          数：   
     * @return          返  回   值：   Json  
     * @throws
     */
    @RequestMapping(value = "/securi_findExamInfo")
    @ResponseBody
    public Json securi_findExamInfo(HttpServletRequest req) throws JSONException {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) j.getObj();
        String examId = o.getString("examId");
        if (StringUtils.isBlank(examId)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setObj(null);
            result.setSuccess(false);
            return result;
        }
        Integer examIdInt = null;
        try {
            examIdInt = DataType.getAsInt(examId);
        } catch (Exception e) {
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
        try {
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setObj(service.findExamDetailInfo(examIdInt));
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 查询问卷测试详情
     * @param req
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/securi_findTestPaperDetail")
    @ResponseBody
    public Json securi_findTestPaperDetail(HttpServletRequest req) throws Exception {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) j.getObj();
        Integer testPaperId = o.getInteger("testPaperId");
        if (testPaperId == null) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setObj(null);
            result.setSuccess(false);
            return result;
        }
        try {
            result.setObj(service.findAfterTestExamDetaiInfo(testPaperId));
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     *查询门店上一次问卷成绩
     * @param req
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/securi_findLastExamScore")
    @ResponseBody
    public Json securi_findLastExamScore(HttpServletRequest req) throws Exception {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) j.getObj();
        Integer shopId = o.getInteger("shopId");
        Integer examType = o.getInteger("examType");
        if (shopId == null || examType == null) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setObj(null);
            result.setSuccess(false);
            return result;
        }
        try {
            result.setObj(service.findLastExamScore(shopId, examType));
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     *查询门店重复失分项
     * @param req
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/securi_findRepeatLostScoreItem")
    @ResponseBody
    public Json securi_findRepeatLostScoreItem(HttpServletRequest req) throws Exception {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) j.getObj();
        Integer shopId = o.getInteger("shopId");
        if (shopId == null) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setObj(null);
            result.setSuccess(false);
            return result;
        }
        try {
            result.setObj(service.findRepeatLostScoreItem(shopId));
            result.setCode(Code.SUCCESS_CODE);
            result.setMsg("获取成功");
            result.setSuccess(true);
        } catch (Exception e) {
           e.printStackTrace();
            result.setCode(Code.ILLEGAL_CODE);
            result.setMsg("获取失败");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 
     * @Title           函数名称：   saveExamResult
     * @Description     功能描述：   保存测评问卷结果
     * @param           参          数：   
     * @return          返  回   值：   Json  
     * @throws
     */
    @RequestMapping(value = "/securi_saveExamResult", method = RequestMethod.POST)
    @ResponseBody
    public Json saveExamResult(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJsonWithOutEncode(req);
        com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) j.getObj();
        try {
            String userId = o.getString("userId");
            String examId = o.getString("examId");
            String storeId = o.getString("storeId");
            // 问卷台头
            String examHeading = (String) o.getString("examHeading");
            // 问题列表
            String qst = (String) o.getString("questionList");
            // 问题分析
            String problemAnalysis = (String) o.getString("problemAnalysisList");
            // 行动计划
            String actionPlan = (String) o.getString("actionPlanList");
            // 必填参数校验
            List<Map<String, Object>> questionList = (List<Map<String, Object>>) getlistForJson(qst);
            // 问题分析
            List<Map<String, Object>> problemAnalysisList = (List<Map<String, Object>>) getlistForJson(problemAnalysis);
            // 行动计划
            List<Map<String, Object>> actionPlanList = (List<Map<String, Object>>) getlistForJson(actionPlan);
            // 问卷台头
            List<Map<String, Object>> examHeadingList = (List<Map<String, Object>>) getlistForJson(examHeading);
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(examId) || StringUtils.isBlank(storeId) || CollectionUtils.isEmpty(questionList)) {
                result.setCode(Code.NULL_CODE);
                result.setMsg(VERIFY_NULL);
                result.setObj(null);
                result.setSuccess(false);
                return result;
            }
            // 问卷台头信息 
            List<BTestHeadingRela> exHeadingList = getExHeadingList(examHeadingList);
            // 问题分析
            List<BProblemAnalysis> proAnaList = getProAnaList(problemAnalysisList);
            // 行动计划
            List<BActionPlan> actionPlList = getActionPlanList(actionPlanList);
            BTestPaper testPaper = new BTestPaper();
            testPaper.setEid(DataType.getAsInt(o.getString("examId")));
            testPaper.setStoreid(DataType.getAsInt(o.getString("storeId")));
            // 已完成
            testPaper.setTstate(1);
            testPaper.setTtotal(DataType.getAsInt(o.getString("total")));
            testPaper.setUserid(DataType.getAsInt(o.getString("userId")));
            testPaper.setTbegin(new Date(DataType.getAsLong(o.getString("beginTime"))));
            testPaper.setTend(new Date(DataType.getAsLong(o.getString("endTime"))));
            testPaper.setLevel(DataType.getAsInt(o.getString("level")));
            
            List<BTestPaperDetail> list = new ArrayList<BTestPaperDetail>();
            for (Map<String, Object> map : questionList) {
                BTestPaperDetail testPaperDetail = new BTestPaperDetail();
                // 图片需处理
                testPaperDetail.setQevidence(DataType.getAsString(map.get("qEvidence")));
                // 问题ID
                testPaperDetail.setQid(DataType.getAsInt(map.get("questionId")));
                List<BTestHeadingRela> tstHeading = getTestHeadingByQId(map.get("attr"), DataType.getAsInt(map.get("questionId")));
                // 答案
                testPaperDetail.setTanswer(DataType.getAsString(map.get("tAnswer")));
                // 得分
                testPaperDetail.setTvalue(DataType.getAsInt(map.get("tValue")));
                testPaperDetail.setTstHeadingRela(tstHeading);
                list.add(testPaperDetail);
            }
            testPaperDetailService.saveTestPaperDetail(list, testPaper, exHeadingList, proAnaList, actionPlList);
            String emails = getShopEmail(testPaper.getId());
            new SendEmailThread(service, "13636462617@163.com", testPaper.getId()).start();
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(OperatorResultMsg.SUCCESS);
            result.setObj(stsService.statistic(DataType.getAsInt(userId), DataType.getAsInt(examId)));
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
        return result;
    }

    private String getShopEmail(Integer testPaperId) {
    	BTestPaper testPaper = paperService.queryTestPaper(testPaperId);
    	Integer shopId = testPaper.getStoreid();
    	if(shopId == null){
    		return "";
    	}
    	
    	BShopInfoVO vo = shopService.findById(shopId);
		return vo.getEmail();
	}

	/**
     * 
     * @Title           函数名称：   getTestHeadingByQId
     * @Description     功能描述：   获取题目各个属性的值
     * @param           参          数：   
     * @return          返  回   值：   List<BTestHeadingRela>  
     * @throws
     */
    public List<BTestHeadingRela> getTestHeadingByQId(Object obj, Integer qId) {
        List<BTestHeadingRela> result = new ArrayList<BTestHeadingRela>();
        JSONArray jsonArray = (JSONArray) obj;
        List<Map<String, Object>> list = (List<Map<String, Object>>) getlistForJson(jsonArray.toString());
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> map : list) {
                BTestHeadingRela tstHeading = new BTestHeadingRela();
                tstHeading.setqId(qId);
                tstHeading.sethId(DataType.getAsInt(map.get("hId")));
                tstHeading.setValue(DataType.getAsString(map.get("value")));
                result.add(tstHeading);
            }
        }
        return result;
    }

    /**
     * 
     * @Title           函数名称：   getExHeadingList
     * @Description     功能描述：   获取问卷台头
     * @param           参          数：   
     * @return          返  回   值：   List<BTestHeadingRela>  
     * @throws
     */
    public List<BTestHeadingRela> getExHeadingList(List<Map<String, Object>> list) {
        List<BTestHeadingRela> result = new ArrayList<BTestHeadingRela>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> map : list) {
                BTestHeadingRela tstHeading = new BTestHeadingRela();
                tstHeading.sethId(DataType.getAsInt(map.get("hId")));
                tstHeading.setValue(DataType.getAsString(map.get("value")));
                result.add(tstHeading);
            }
        }
        return result;
    }

    /**
     * 
     * @Title           函数名称：   getProAnaList
     * @Description     功能描述：   获取问题分析
     * @param           参          数：   
     * @return          返  回   值：   List<BProblemAnalysis>  
     * @throws
     */
    public List<BProblemAnalysis> getProAnaList(List<Map<String, Object>> list) {
        List<BProblemAnalysis> result = new ArrayList<BProblemAnalysis>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> map : list) {
                BProblemAnalysis proAnaly = new BProblemAnalysis();
                // 解决方案
                proAnaly.setResolve(DataType.getAsString(map.get("resolve")));
                // 根源
                proAnaly.setResource(DataType.getAsString(map.get("resource")));
                // 失分项编号
                proAnaly.setScoreNum(DataType.getAsString(map.get("scoreNum")));
                result.add(proAnaly);
            }
        }
        return result;
    }

    /**
     * 
     * @Title           函数名称：   getActionPlanList
     * @Description     功能描述：   保存行动计划
     * @param           参          数：   
     * @return          返  回   值：   List<BActionPlan>  
     * @throws
     */
    public List<BActionPlan> getActionPlanList(List<Map<String, Object>> list) {
        List<BActionPlan> result = new ArrayList<BActionPlan>();
        try {
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> map : list) {
                    BActionPlan actPlan = new BActionPlan();
                    // 开始时间
                    actPlan.setBeginTime(new Date(DataType.getAsLong(map.get("beginTime"))));
                    // 预期结束时间
                    actPlan.setExpEndTime(new Date(DataType.getAsLong(map.get("expEndTime"))));
                    // 预期结果
                    actPlan.setExpResult(DataType.getAsString(map.get("expResult")));
                    // 实际结束时间
                    actPlan.setRealEndTime(new Date(DataType.getAsLong(map.get("realEndTime"))));
                    // 行动步骤
                    actPlan.setTack(DataType.getAsString(map.get("tack")));
                    // 负责人
                    actPlan.setUserName(DataType.getAsString(map.get("userName")));
                    result.add(actPlan);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return result;
    }

    /** 
     * Json 转成 List<Map<>> 
     * @param jsonStr 
     * @return 
     */
    public static List<Map<String, Object>> getlistForJson(String jsonStr) {
        List<Map<String, Object>> list = null;
        if (null != jsonStr) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                JSONObject jsonObj;
                list = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObj = (JSONObject) jsonArray.get(i);
                    list.add(getMapForJson(jsonObj.toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return list;
    }

    /** 
     * Json 转成 Map<> 
     * @param jsonStr 
     * @return 
     */
    public static Map<String, Object> getMapForJson(String jsonStr) {
        JSONObject jsonObject;
        if (null != jsonStr) {
            try {
                jsonObject = new JSONObject(jsonStr);
                Iterator<String> keyIter = jsonObject.keys();
                String key;
                Object value;
                Map<String, Object> valueMap = new HashMap<String, Object>();
                while (keyIter.hasNext()) {
                    key = keyIter.next();
                    value = jsonObject.get(key);
                    valueMap.put(key, value);
                }
                return valueMap;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return null;
    }

    /**
     * 上传问卷图片接口
     * @param req
     * @return
     */
    @RequestMapping(value = "/securi_HeadImg")
    @ResponseBody
    public Json updateHead(@RequestParam("headFile") MultipartFile file, HttpServletRequest req) {
        Json result = new Json();

        String headPath = ConfigUtil.get("questionPicPath");
        String fileName = "questionImage" + new Date().getTime() + getSuffic(file.getOriginalFilename());
        String filePath = headPath + fileName;
        String rootPath = req.getSession().getServletContext().getRealPath("/");

        try {
        	File picSaveDir = new File(headPath);
            if (!picSaveDir.exists())
                picSaveDir.mkdirs();
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File( filePath));
            URL url=new URL(req.getRequestURL().toString());
            String urlPath="";
                urlPath= "/upload/question_image/"+fileName;
            System.out.println(urlPath);
            result.setObj(urlPath);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());

            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg(ERROR);
        }

        return result;
    }

    /**
     * 获得文件后缀名
     * @param filename
     * @return
     */
    private String getSuffic(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

}
