package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.entity.BExBaseInfo;
import com.upbest.mvc.repository.factory.BArearespository;
import com.upbest.mvc.repository.factory.BExBaseInfoRespository;
import com.upbest.mvc.repository.factory.QuestionRespository;
import com.upbest.mvc.service.IBExBaseInfoService;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.service.IQuestionManageService;
import com.upbest.mvc.service.ITestPaperDetailService;
import com.upbest.mvc.service.ITestPaperService;
import com.upbest.mvc.vo.ExamInfoVO;
import com.upbest.mvc.vo.ExamSheetVO;
import com.upbest.mvc.vo.LoseDetailVO;
import com.upbest.mvc.vo.QuestionVO;
import com.upbest.mvc.vo.TestPaperDetailVO;
import com.upbest.mvc.vo.TestPaperVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.ComparatorExamSts;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;
import com.upbest.utils.RoleFactory;

@Controller
@RequestMapping("/examManager")
public class ExamnationManagerController {
    
    @Inject
    private IBExBaseInfoService baseService;
    @Inject
    private IExamService service;
	
	@Inject
	private ITestPaperDetailService testPaperDetailService;
	
	@Inject
	private IBuserService userService;

    @Inject
    private IQuestionManageService qmService;

    @Inject
    private ITestPaperService testPaperService;
    
    @Inject
    private BExBaseInfoRespository baseInfoRes;
    
    @Inject
    private QuestionRespository quesRes;
    
    @Inject
    private BArearespository areaRes;
	
	/**
	 * 
	 * @param examId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/testPaperDetail")
	public String showTestPaperDetail(@RequestParam("examId")int examId,@RequestParam("userId")int userId,Model model) throws Exception {
		List<TestPaperDetailVO> list = testPaperDetailService.queryTestPaperDetails(userId, examId);
		model.addAttribute("testDetail", buildMap(list));
		model.addAttribute("userName", userService.findById(userId) == null ? "" : userService.findById(userId).getName());
		
		return "examinationManager/showTestPaperDetail";
	}
	
	private Map<String, List<TestPaperDetailVO>> buildMap(List<TestPaperDetailVO> list) {
		Map<String, List<TestPaperDetailVO>> map = new HashMap<String, List<TestPaperDetailVO>>();
		if(!CollectionUtils.isEmpty(list)){
			for (TestPaperDetailVO testPaperDetailVO : list) {
				String questionType = testPaperDetailVO.getQuestionType();
				if(!StringUtils.hasText(questionType)){
					continue;
				}
				List<TestPaperDetailVO> temp = map.get(questionType);
				if(temp == null){
					temp = new ArrayList<TestPaperDetailVO>();
					map.put(questionType, temp);
				}
				temp.add(testPaperDetailVO);
			}
		}
		return map;
	}

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("current", "examManager");
        return "examinationManager/examinationManager";
    }
    
    /**
     * 
     * @Title 		   	函数名称：	index
     * @Description   	功能描述：	转到失分率统计页面
     * @param 		   	参          数：	
     * @return          返  回   值：	String  
     * @throws
     */
    @RequestMapping("/loseSts/index")
    public String loseStsIndex(Model model) {
        model.addAttribute("current", "losePerSts");
        return "loseSts/loseStsManager";
    }
    @RequestMapping("/loseSts/showDetail")
    public String showDetail(@RequestParam("id") int id,Model model) {
        model.addAttribute("question", quesRes.findOne(id));
        return "loseSts/showDetail";
    }

    @RequestMapping("/addExamIndex")
    public String addExamIndex(Model model) throws Exception {
        model.addAttribute("current", "addExamination");
        model.addAttribute("questionType", toJson(qmService.queryQuestionType()));
        model.addAttribute("heads", service.findHeadInfo(1));
        return "examinationManager/addExamination";
    }

    @RequestMapping("/addQuesPage")
    public String addQuesPage(@RequestParam("quesType") int quesType, Model model) throws Exception {
        model.addAttribute("quesType", quesType);
        return "examinationManager/addQuestion";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageModel<ExamInfoVO> queryExamInfo(@RequestParam("page") int page, @RequestParam("rows") int size, HttpServletResponse response) {
        Pageable pageable = new PageRequest(page - 1, size);
        return service.findExamInfo(pageable,0);
    }

    @RequestMapping(value = "/addExamInfo", method = RequestMethod.POST)
    @ResponseBody
    public void addExamInfo(@RequestParam("examInfo") String examInfo) {
        ObjectMapper mapper = new ObjectMapper();
        ExamInfoVO vo = null;
        try {
            vo = mapper.readValue(examInfo, ExamInfoVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.addExamInfo(vo);
    }

    @RequestMapping("/examPageDetail")
    public String showExamPageDetail(@RequestParam("examId") int examId, Model model) throws Exception {
        ExamInfoVO vo = service.findExamInfo(examId);
        buildQuestionMap(vo);

        model.addAttribute("examPageDetail", vo);
        return "examinationManager/showExamPageDetail";
    }

    @RequestMapping("/userAnswerInfo")
    public String userAnswerInfo(@RequestParam("examId") int examId, Model model) throws Exception {

        model.addAttribute("basicInfo", service.findExamBasicInfo(examId));
        model.addAttribute("examId", examId);
        return "examinationManager/userAnswerInfo";
    }
    
    @RequestMapping("/modify")
    public String modify(@RequestParam("examId") int examId, Model model) throws Exception {
    	model.addAttribute("current", "updateExamination");
        model.addAttribute("questionType", toJson(qmService.queryQuestionType()));
        model.addAttribute("heads", service.findHeadInfo(1));
        
        model.addAttribute("basicInfo", service.findExamBasicInfo(examId));
        model.addAttribute("examId", examId);
        model.addAttribute("questionInfo", toJson(filter(service.findQuestions(examId))));
        model.addAttribute("headValues", toJson(service.findHeadValues(examId)));
        return "examinationManager/updateExamination";
    }

    private List<QuestionVO> filter(List<QuestionVO> questions) {
    	List<QuestionVO> simple = new ArrayList<QuestionVO>();
    	if(!CollectionUtils.isEmpty(questions)){
    		for (QuestionVO questionVO : questions) {
    			QuestionVO vo = new QuestionVO();
    			vo.setId(questionVO.getId());
    			vo.setQtype(questionVO.getQtype());
    			vo.setQvalue(questionVO.getQvalue());
    			
    			simple.add(vo);
			}
    	}
		return simple;
	}

	@RequestMapping("/deleteExam")
    @ResponseBody
    public Json deleteExam(@RequestParam("examId") int examId)
	{
	    boolean hasAssgin=service.hasAssign(examId);
	    Json json=new Json();
	    if(hasAssgin)
	    {
	        json.setSuccess(false);
	        json.setMsg("次问卷已经下发，不能被删除");
	        return json;
	    }
	    try
	    {
	       service.deleteExamPaper(examId);
	       json.setSuccess(true);
	       json.setMsg("操作成功");
	    }catch(Exception e)
	    {
	        json.setSuccess(false);
	        json.setMsg("操作失败");
	    }
	    return  json;
	}
    
    
    
    @RequestMapping("/userAnswerInfoList")
    @ResponseBody
    public PageModel<TestPaperVO> userAnswerInfoList(@RequestParam("page") int page, @RequestParam("rows") int size, @RequestParam("examId") int examId
            ,@RequestParam(value = "shopName", required = false) String shopName) {

        Pageable pageable = new PageRequest(page - 1, size);
        return testPaperService.queryTestPaperInfos(examId, pageable,shopName);
    }

    private void buildQuestionMap(ExamInfoVO vo) {
        List<QuestionVO> list = vo.getQuestionList();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<QuestionVO>> map = new HashMap<String, List<QuestionVO>>();
            vo.setQuestionMap(map);
            for (QuestionVO questionVO : list) {
                String quesType = questionVO.getQuestionType();
                List<QuestionVO> quesList = map.get(quesType);
                if (quesList == null) {
                    quesList = new ArrayList<QuestionVO>();
                    map.put(quesType, quesList);
                }

                quesList.add(questionVO);
            }

        }
    }

    private String toJson(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    @ResponseBody
    @RequestMapping("/getUserTreeList")
    public void getUserTreeList(@RequestParam(value = "id", required = false) Integer id, HttpServletResponse response) {
        String jsonString = new String();
        List<TreeVO> list = baseService.getUrVOList(id);
        /*  StringBuilder sb = new StringBuilder();
          sb.append("[\n");
          if (!CollectionUtils.isEmpty(list)) {
              for (TreeVO tree : list) {
                  sb.append("{");
                  sb.append("\"id\":\"" + tree.getId() + "\"");
                  sb.append(",\"name\":\"" + tree.getName() + "\"");
                  sb.append(",\"pId\":\"" + tree.getPid() + "\",checked:false");
                  sb.append(",open:true");
                  sb.append("},\n");
              }
              StringBuffer sb2 = new StringBuffer();
              sb2.append(sb.toString().substring(0, sb.toString().length() - 2));
              sb2.append("\n]");
              jsonString = sb2.toString();
          }
          String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(jsonString, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
          outPrint(json, response);*/
        JSONArray pArray = new JSONArray();
        try {
            for (TreeVO tree : list) {
                JSONObject object = new JSONObject();
                object.put("id", tree.getId());
                object.put("pId", tree.getPid());
                object.put("name", tree.getName());
                object.put("isParent",tree.isParent());
                object.put("checked", false);
                object.put("open", true);
                pArray.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        outPrint(pArray.toString(), response);
    }

    private void outPrint(String str, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            writer.write(str);
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    @RequestMapping("/examport")
    public void examport(@RequestParam(value = "id", required = false) String id,HttpServletResponse response) throws Exception {
        String[] ary=id.split(",");
        Integer[] idAry = new Integer[ary.length];
        for (int i = 0;i < ary.length;i++) {
            idAry[i] = Integer.valueOf(ary[i]);
        }
        
       /* response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition",
                "attachment;filename=xx.xlsx");*/
//        response.addHeader("Content-Length", inputStream.available() + "");
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment; filename=xxx.xlsx");

        service.addEaxmToExcel(idAry, response.getOutputStream());
    }
    /**
     * 
     * @Title 		   	函数名称：	loseSts
     * @Description   	功能描述：	失分率统计列表
     * @param 		   	参          数：	
     * @return          返  回   值：	PageModel<ExamInfoVO>  
     * @throws
     */
    @RequestMapping("/loseSts/list")
    @ResponseBody
    public void loseStsList(@RequestParam(value = "examType", required = false) String examType,
            @RequestParam(value = "storeName", required = false) String storeName,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer pageSize,
            @RequestParam(value = "sidx", required = false) String sidx,
            @RequestParam(value = "sord", required = false) String sord,
            Model model, HttpSession session, HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        //排序 begin
      /*  if("losePercent".equals(sidx)){
            sidx="loseTotalScore";
        }*/
      /*  if(sord.equalsIgnoreCase("desc")){
             or=new Order(Direction.DESC,sidx);
        }else{        Order or=null;

             or=new Order(Direction.ASC,sidx);
        }
        Sort so=new Sort(or);*/
        //排序 end
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize);
        
        Page<Object[]> sts = service.queryLoseSts(examType,storeName,requestPage);
        PageModel result = new PageModel();
        result.setPage(page);
        result.setRows(doFormatToSheet(sts.getContent(),sord));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(sts.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    
    public List<ExamSheetVO> doFormatToSheet(List<Object[]> list,String sord){
        List<ExamSheetVO> result=new ArrayList<ExamSheetVO>();
        if(!CollectionUtils.isEmpty(list)){
            ExamSheetVO  vo=null;
            for(Object[] obj:list){
                vo=new ExamSheetVO();
                vo.setId(DataType.getAsString(obj[0]));
                vo.setEname(DataType.getAsString(obj[1]));
                vo.setNum(DataType.getAsString(obj[2]));
                vo.setDescription(DataType.getAsString(obj[3]));
                vo.setModel(DataType.getAsString(obj[4]));
                vo.setQtype(DataType.getAsString(obj[5]));
                vo.setEid(DataType.getAsString(obj[9]));
                String moduleId=DataType.getAsString(obj[10]);
              BExBaseInfo baseInfo= baseInfoRes.findOne(DataType.getAsInt(moduleId));
              if(null!=baseInfo){
                  if(baseInfo.getIsKey()==1){
                      //是关键项
                      //统计得到N次
                      String nCnt=service.queryKeyRela(moduleId,vo.getId(),"N");
                      //统计得到Y次
                      String yCnt=service.queryKeyRela(moduleId,vo.getId(),"Y");
                      //统计得总问卷数
                      String total=DataType.getAsInt(nCnt)+DataType.getAsInt(yCnt)+"";
                      //失分率
                      double losing=DataType.getAsDouble(DataType.getAsDouble(nCnt)/DataType.getAsDouble(total))*100;
                      vo.setLosingD(losing);
                      //失分
                      vo.setPoints(nCnt);
                      //总分
                      vo.setAggregate(total);
                      //总得分
                      vo.setScore(yCnt);
                      //失分率
                      vo.setLosing(String.format("%.2f", Double.valueOf(DataType.getAsString(losing)))+"%");
                      
                  }else{
                      //失分
                      vo.setPoints(DataType.getAsString(obj[6]));
                      //总分
                      vo.setAggregate(DataType.getAsString(obj[7]));
                      //总得分
                      vo.setScore(DataType.getAsString(obj[8]));
                      double losing=DataType.getAsDouble(DataType.getAsDouble(vo.getPoints())/DataType.getAsDouble(vo.getAggregate()))*100;
                      vo.setLosingD(losing);
                      //失分率
                      vo.setLosing(String.format("%.2f", Double.valueOf(DataType.getAsString(losing)))+"%");
                  }
                  
              }
              vo.setModuleId(DataType.getAsString(obj[10])); 
                result.add(vo);
            }
        }
        ComparatorExamSts comparator = new ComparatorExamSts(sord);
        Collections.sort(result, comparator);
        return result;
    }
    
    @RequestMapping("/loseSts/detail")
    @ResponseBody
    public void detail(@RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer pageSize,
            @RequestParam(value = "sidx", required = false) String sidx,
            @RequestParam(value = "sord", required = false) String sord,
            @RequestParam(value = "daqu", required = false) String daqu,
            @RequestParam(value = "sheng", required = false) String sheng,
            @RequestParam(value = "shi", required = false) String shi,
            @RequestParam(value = "quxian", required = false) String quxian,
            @RequestParam(value = "userIds", required = false) String userIds,
            @RequestParam(value = "storeName", required = false) String storeName,
            Model model, HttpSession session, HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        //排序 begin
        if("beginTime".equals(sidx)){
            sidx="tp.t_begin";
        }
        Order or=null;
        if(sord.equalsIgnoreCase("desc")){
             or=new Order(Direction.DESC,sidx);
        }else{
             or=new Order(Direction.ASC,sidx);
        }
        Sort so=new Sort(or);
        //排序 end
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize, so);
        
        Page<Object[]> sts = service.queryLoseStsDetail( id, daqu, sheng, shi, quxian,
                 userIds, storeName, requestPage);
        PageModel result = new PageModel();
        result.setPage(page);
        result.setRows(doFormatToLoseDetail(sts.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(sts.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    
    public List<LoseDetailVO> doFormatToLoseDetail(List<Object[]> list){
        List<LoseDetailVO> result=new ArrayList<LoseDetailVO>();
        if(!CollectionUtils.isEmpty(list)){
            LoseDetailVO  vo=null;
            for(Object[] obj:list){
                vo=new LoseDetailVO();
                //是否关键项
                String isKey=DataType.getAsString(obj[12]);
                vo.setDesc(service.queryDescByTid(DataType.getAsString(obj[0]),isKey));
                if((!StringUtils.isEmpty(vo.getDesc())&&"1".equals(isKey))||"0".equals(isKey)){
                    vo.setId(DataType.getAsString(obj[0]));
                    vo.setBeginTime(DataType.getAsDate(obj[1]));
                    vo.setRealName(DataType.getAsString(obj[2]));
                    vo.setEmp(DataType.getAsString(obj[3]));
                    vo.setRole(RoleFactory.getRoleName(DataType.getAsString(obj[4])));
                    vo.setValue(DataType.getAsString(obj[5]));
                    vo.setPic(DataType.getAsString(obj[6]));
                    vo.setShopName(DataType.getAsString(obj[7]));
                    vo.setShopNum(DataType.getAsString(obj[8]));
                    vo.setTestValue(DataType.getAsString(obj[9]));
                    vo.setQuesValue(DataType.getAsString(obj[10]));
                    result.add(vo);
                }
            }
        }
        return result;
    }
    /**
     * 
     * @Title 		   	函数名称：	getCityList
     * @Description   	功能描述：	获取大区等下拉列表
     * @param 		   	参          数：	
     * @return          返  回   值：	List<BArea>  
     * @throws
     */
    @ResponseBody
    @RequestMapping("/getCityList")
    public List<BArea> getCityList(@RequestParam(value = "pId", required = false) String pId, HttpServletResponse response) {
         List<BArea> areaList=new ArrayList<BArea>();
          if(StringUtils.isEmpty(pId)){
              //pId为空则查大区
              areaList=areaRes.findByAreaWithTopContext("%%");
          }else{
              areaList=areaRes.findByAreaWithContext(areaRes.findOne(DataType.getAsInt(pId)),"%%");
          }
          return areaList;
    }  
    
    /**
     * 
     * @Title 		   	函数名称：	examportLose
     * @Description   	功能描述：	导出失分率统计
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @RequestMapping("/examportLose")
    public void examportLose(@RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "examType", required = false) String examType,
            @RequestParam(value = "storeName", required = false) String storeName,
            HttpServletResponse response) throws Exception {
        response.setContentType("application/x-msdownload");
        
        response.setHeader("Content-disposition", "attachment; filename="+new String("失分率统计".getBytes("GBK"),"ISO8859-1")+".xlsx");
        service.addEaxmToExcelLose(id, response.getOutputStream());
    }
    
    /**
     * 
     * @Title 		   	函数名称：	examportLoseDetail
     * @Description   	功能描述：	失分项查看
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @RequestMapping("/examportLoseDetail")
    public void examportLoseDetail(@RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "ids", required = false) String ids,
            HttpServletResponse response) throws Exception {
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment; filename="+new String("失分项查看".getBytes("GBK"),"ISO8859-1")+".xlsx");
        service.addEaxmToExcelLoseDetail(id, ids, response.getOutputStream());
    }
    
    /**
     * 
     * @Title 		   	函数名称：	del
     * @Description   	功能描述：	删除测评结果
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    public void del(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        service.delExamResult(id);
        outPrint("0", response);
    }
}
