package com.upbest.mvc.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Splitter;
import com.upbest.mvc.vo.CommonWordsVO;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.constant.Constant.OperatorResultMsg;
import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.BWorkInfo;
import com.upbest.mvc.repository.factory.MessageRespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.ITaskService;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.mvc.vo.TaskCaVO;
import com.upbest.mvc.vo.TaskVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

/**
 * 任务接口
 * 
 * @author QunZheng
 *
 */
@Controller
@RequestMapping("/api/task")
public class TaskAPIController {
    private static final Logger logger = LoggerFactory.getLogger(TaskAPIController.class);

    @Autowired
    private ITaskService service;
    public static final String VERIFY_NULL = "相关参数为空";
    public static final String PARAM_ILLEGAL = "参数非法";
    
    @Autowired
    protected PushMessageServiceI pushMessageService;
    @Autowired
    private  ITaskService taskService;

    @Autowired
    protected IBuserService userService;
    
    @Autowired
    protected MessageRespository messageRespository;
    
    @Autowired
    protected WorkRespository wkRes;
    
    @Autowired
    protected StoreRespository storeRes;
    
    /**
     * 更新用户头像接口
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "/securi_list")
    @ResponseBody
    public Json verify(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String userId = o.getString("userId");
        String page = o.getString("page");
        String size = o.getString("size");
        // 哪一天、时间戳
        String date = o.getString("date");
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(page) || StringUtils.isBlank(size) || StringUtils.isBlank(date)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }

        try {
            Integer userIdInt = DataType.getAsInt(userId);
            Integer pageInt = DataType.getAsInt(page);
            Integer sizeInt = DataType.getAsInt(size);
            Long dateLong = DataType.getAsLong(date);
            PageModel<Object> queryResult = service.findWorkListByUserId(userIdInt, dateLong, new PageRequest(pageInt - 1, sizeInt));

            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setObj(queryResult.getRows());
            result.setMsg(OperatorResultMsg.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
        return result;
    }

    @RequestMapping(value = "/securi_caList")
    @ResponseBody
    public Json queryTaskList(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String userId = o.getString("userId");
        String month = o.getString("month");
        String year = o.getString("year");
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(month) || StringUtils.isBlank(year)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        try {
            Integer userIdInt = DataType.getAsInt(userId);
            List<TaskVO> queryResult = service.getTaskResult(userIdInt, month, year);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setObj(getTaskList(queryResult));
            result.setMsg(OperatorResultMsg.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
        return result;
    }

    public List<TaskCaVO> getTaskList(List taskList) {
        List<TaskCaVO> taskCaVO = new ArrayList<TaskCaVO>();
        List<TaskVO> taskVOList = (List<TaskVO>) taskList;
        Map<Long, List<TaskVO>> map = new HashMap<Long, List<TaskVO>>();
        if (!CollectionUtils.isEmpty(taskList)) {
            TaskCaVO caVO = null;
            for (TaskVO vo : taskVOList) {
                if (map.get(formatDate(vo.getStarttime()).getTime()) == null) {
                    caVO = new TaskCaVO();
                    map.put(formatDate(vo.getStarttime()).getTime(), getTaskVOList(taskVOList, formatDate(vo.getStarttime()).getTime()));
                    caVO.setDate(formatDate(vo.getStarttime()).getTime());
                    caVO.setList(getTaskVOList(taskVOList, formatDate(vo.getStarttime()).getTime()));
                    taskCaVO.add(caVO);
                }
            }
        }
        return taskCaVO;
    }

    public Date formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dd = format.format(date);
        Date ddd = null;
        try {
            ddd = format.parse(dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ddd;
    }

    public List<TaskVO> getTaskVOList(List<TaskVO> taskVOList, long time) {
        List<TaskVO> result = new ArrayList<TaskVO>();
        if (!CollectionUtils.isEmpty(taskVOList)) {
            int count = 0;
            for (TaskVO obj : taskVOList) {
                if (time == formatDate(obj.getStarttime()).getTime() && count < 3) {
                    result.add(obj);
                    count++;
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/securi_del")
    @ResponseBody
    public Json deleteTask(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String taskId = o.getString("taskId");
        if (StringUtils.isBlank(taskId)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }

        try {
            Integer taskIDInt = DataType.getAsInt(taskId);
            service.deleteById(taskIDInt);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(OperatorResultMsg.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }

        return result;
    }

    @RequestMapping(value = "/securi_add")
    @ResponseBody
    public Json addTask(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        // 默认不存在相同类型的任务
        boolean isExistSameWork = false;
        try {
            List<BWorkInfo> list = new ArrayList<BWorkInfo>();
            List<Object> taskList = (List<Object>) o.get("tasks");
            for (Object obj : taskList) {
                Map<String, String> map = (Map<String, String>) obj;
                list.addAll(splitBatchStoreTask(map));
            }
            list = service.saveTasks(list);
            if(isExistSameWork){
                result.setCode(Code.EXIST_SAME_TASK_CODE);
                result.setSuccess(false);
                result.setMsg(OperatorResultMsg.EXIST_SAME_TASK_MSG);
                result.setObj(list);
            }else{
                result.setCode(Code.SUCCESS_CODE);
                result.setSuccess(true);
                result.setMsg(OperatorResultMsg.SUCCESS);
                result.setObj(list);
            }
           
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg(OperatorResultMsg.ERROR);
        }
        return result;
    }
    private  List<BWorkInfo> splitBatchStoreTask(Map<String, String> map){
        List<BWorkInfo> bWorkInfoList=new ArrayList();
        String storeIds= map.get("storeId");
        String taskTypes= map.get("taskType");
        String descs= map.get("desc");
        String taskNames= map.get("taskName");
        String starTimes= map.get("starTime");
        String[] storeIdArr=StringUtils.splitPreserveAllTokens(storeIds, ";");
        String[] taskTypeArr=StringUtils.splitPreserveAllTokens(taskTypes, ";") ;
        String[] taskNameArr=StringUtils.splitPreserveAllTokens(taskNames, ";");
        String[] descArr=StringUtils.splitPreserveAllTokens(descs, ";");
        String[] starTimeArr=StringUtils.splitPreserveAllTokens(starTimes, ";");
        if(storeIdArr!=null){
            if(storeIdArr.length==1){
               for(int i=0;i<taskTypeArr.length;i++){
                   BWorkInfo taskInfo = new BWorkInfo();
                   if(ArrayUtils.isEmpty(descArr)){
                       taskInfo.setContent(DataType.getAsString(""));
                   }
                   else{
                       taskInfo.setContent(DataType.getAsString(descArr[i]));
                   }
                   taskInfo.setStarttime(new Date(DataType.getAsLong(starTimeArr[i])));
                   taskInfo.setWorktypeid(DataType.getAsInt(taskTypeArr[i]));
                   taskInfo.setWorktypename(DataType.getAsString(taskNameArr[i]));
                   taskInfo.setUserid(DataType.getAsInt(map.get("userId")));
                   if(StringUtils.isNotBlank(DataType.getAsString(map.get("storeId")))){
                       taskInfo.setStoreid(DataType.getAsInt(map.get("storeId")));
                   }
                   taskInfo.setQuarter(getQuarter(taskInfo.getStarttime()));
                   // 新增added by zhaojinhua
                   taskInfo.setExecuteid(DataType.getAsInt(map.get("executeId")));
                   taskInfo.setState(DataType.getAsString(map.get("state")));
                   // 0:自主 1:委派
                   taskInfo.setIsSelfCreate(DataType.getAsString(map.get("isSelfCreate")));
                   if(DataType.getAsString(map.get("isSelfCreate")).equals("1")){
                       //委派需立即推送一条任务消息
                       doPushTaskMessage(taskInfo);
                   }
                   // if (!service.isExistSameTask(taskInfo.getUserid(), taskInfo.getWorktypeid(), taskInfo.getStarttime())) {
                   // 不存在相同任务类型的任务
                   bWorkInfoList.add(taskInfo);
               }
            }
            else if(storeIdArr.length>1){
                for(int i=0;i<storeIdArr.length;i++){
                    BWorkInfo taskInfo = new BWorkInfo();
                    taskInfo.setContent(DataType.getAsString(descs));
                    int idx=starTimes.indexOf(";");
                    if(idx>-1){
                        starTimes=starTimes.substring(0,idx);
                    }
                    taskInfo.setStarttime(new Date(DataType.getAsLong(starTimes)));
                    taskInfo.setWorktypeid(DataType.getAsInt(taskTypes));
                    taskInfo.setWorktypename(DataType.getAsString(taskNames));
                    taskInfo.setUserid(DataType.getAsInt(map.get("userId")));
                    if(StringUtils.isNotBlank(DataType.getAsString(storeIdArr[i]))){
                        taskInfo.setStoreid(DataType.getAsInt(storeIdArr[i]));
                    }
                    taskInfo.setQuarter(getQuarter(taskInfo.getStarttime()));
                    // 新增added by zhaojinhua
                    taskInfo.setExecuteid(DataType.getAsInt(map.get("executeId")));
                    taskInfo.setState(DataType.getAsString(map.get("state")));
                    // 0:自主 1:委派
                    taskInfo.setIsSelfCreate(DataType.getAsString(map.get("isSelfCreate")));
                    if(DataType.getAsString(map.get("isSelfCreate")).equals("1")){
                        //委派需立即推送一条任务消息
                        doPushTaskMessage(taskInfo);
                    }
                    // if (!service.isExistSameTask(taskInfo.getUserid(), taskInfo.getWorktypeid(), taskInfo.getStarttime())) {
                    // 不存在相同任务类型的任务
                    bWorkInfoList.add(taskInfo);
                }
            }
        }
        return bWorkInfoList;

    }
    /**
     * 
     * @Title 		   	函数名称：	doPushTaskMessage
     * @Description   	功能描述：	推送任务消息
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    public void doPushTaskMessage(BWorkInfo taskInfo){
        BMessage message = new BMessage();
        message.setMessageType("3");
        message.setPushTitle(wkRes.findOne(taskInfo.getWorktypeid()).getTypename());
        Calendar cal=Calendar.getInstance();
        cal.setTime(taskInfo.getStarttime());
        String pushContent="";
        pushContent+=userService.findById(taskInfo.getUserid()).getRealname();
        pushContent+="刚刚给您安排了新任务：\n";
        pushContent+=cal.get(Calendar.YEAR)+"年"+DataType.getAsInt(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日\n";
        if(0!=taskInfo.getStoreid()){
            pushContent+=storeRes.findOne(taskInfo.getStoreid()).getShopname()+"\n";
        }
        pushContent+=wkRes.findOne(taskInfo.getWorktypeid()).getTypename();
        pushContent+=","+taskInfo.getContent();
        message.setPushContent(pushContent);
        message.setCreateTime(new Date());
        message.setIsRead("0");
        message.setReceiverId(taskInfo.getExecuteid());
        messageRespository.save(message);
        try {
        	
        	/**
             * 额外数据
             */
            Map<String, String> extra = new HashMap<String, String>();
        	extra.put("messageId",""+ message.getId());
            pushMessageService.push(message.getId() + "", message.getReceiverId() + "", null
            			,message.getPushTitle(),message.getPushContent(),extra);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
    }
    public String getQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month == 1 || month == 2 || month == 3) {
            return "第一季度 ";
        } else if (month == 4 || month == 5 || month == 6) {
            return "第二季度 ";
        } else if (month == 7 || month == 8 || month == 9) {
            return "第三季度 ";
        } else if (month == 10 || month == 11 || month == 12) {
            return "第四季度 ";
        }
        return "";
    }

    /*@RequestMapping(value = "/securi_updateStatus")
    @ResponseBody
    public Json updateStatus(HttpServletRequest req) {
    	Json result = new Json();
    	Json j = Constant.convertJson(req);
    	JSONObject o = (JSONObject) j.getObj();

    	int taskId = o.getInteger("taskId");
    	String status = o.getString("status");

    	try {
    		service.updateTaskStatus(taskId, status);

    		result.setCode(Code.SUCCESS_CODE);
    		result.setSuccess(true);
    		result.setMsg(OperatorResultMsg.SUCCESS);
    	} catch (Exception e) {
    		logger.error(e.getMessage());

    		result.setCode(Code.ILLEGAL_CODE);
    		result.setSuccess(false);
    		result.setMsg(OperatorResultMsg.ERROR);
    	}

    	return result;
    }*/

    @RequestMapping(value = "/securi_update")
    @ResponseBody
    public Json updateTask(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        // 默认不存在相同类型的任务
        boolean isExistSameWork = false;
        String taskId = o.getString("taskId");
        String userId = o.getString("userId");
        String taskType = o.getString("taskType");
        String storeId = o.getString("storeId");
        if (StringUtils.isBlank(taskId) || StringUtils.isBlank(userId) || StringUtils.isBlank(taskId) || StringUtils.isBlank(taskType) || StringUtils.isBlank(storeId)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        try {
            Integer taskIdInt = DataType.getAsInt(taskId);
            Integer userIdInt = DataType.getAsInt(userId);
            Integer taskTypeInt = DataType.getAsInt(taskType);
            Integer storeIdInt = DataType.getAsInt(storeId);

            BWorkInfo taskInfo = service.findById(taskIdInt);
            Integer typeId=taskInfo.getWorktypeid();
            taskInfo.setId(taskIdInt);
            taskInfo.setContent(o.getString("desc"));
            taskInfo.setStarttime(new Date(DataType.getAsLong(o.getString("startTime"))));
            taskInfo.setFinishrealtime(new Date(DataType.getAsLong(o.getString("finishTime"))));
            taskInfo.setWorktypeid(taskTypeInt);
            taskInfo.setUserid(userIdInt);
            taskInfo.setStoreid(storeIdInt);
            taskInfo.setState(o.getString("status"));
            
            if("1".equals(taskInfo.getState())&&"1".equals(taskInfo.getIsSelfCreate())){
                //任务完成提醒OM(委派的任务且是已完成的任务)
                BMessage message = new BMessage();
                message.setMessageType("3");
                message.setPushTitle(wkRes.findOne(taskInfo.getWorktypeid()).getTypename());
                Calendar cal=Calendar.getInstance();
                cal.setTime(taskInfo.getStarttime());
                String pushContent="";
                pushContent+=userService.findById(userIdInt).getRealname();
                pushContent+="完成您安排的任务：";
                pushContent+=cal.get(Calendar.YEAR)+"年"+DataType.getAsInt(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日"+"  ";
                if(0!=taskInfo.getStoreid()){
                    pushContent+=storeRes.findOne(taskInfo.getStoreid()).getShopname()+"  ";
                }
                pushContent+=wkRes.findOne(taskInfo.getWorktypeid()).getTypename();
                pushContent+=","+taskInfo.getContent();
                message.setPushContent(pushContent);
                message.setCreateTime(new Date());
                message.setIsRead("0");
                message.setPushTime(new Date());
                message.setReceiverId(userService.findById(userIdInt).getPid());
                messageRespository.save(message);
                try {
                    pushMessageService.push(message.getId() + "", message.getReceiverId() + "", null);
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                    e.printStackTrace();
                }
            }
            if( typeId!=taskTypeInt){
               // if (!service.isExistSameTask(taskInfo.getUserid(), taskInfo.getWorktypeid(), taskInfo.getStarttime())) {
                    // 不存在相同任务类型的任务
                    isExistSameWork=false;  
                    service.saveBWorkInfo(taskInfo);
               // } else {
                    // 存在相同任务类型的任务
                   // isExistSameWork = true;
               // }
            }else{
                isExistSameWork=false;
                service.saveBWorkInfo(taskInfo);
            }
            if(isExistSameWork){
                result.setCode(Code.EXIST_SAME_TASK_CODE);
                result.setSuccess(false);
                result.setMsg(OperatorResultMsg.EXIST_SAME_TASK_MSG);
            }else{
                result.setCode(Code.SUCCESS_CODE);
                result.setSuccess(true);
                result.setMsg(OperatorResultMsg.SUCCESS);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }

        return result;
    }

     @RequestMapping(value = "/securi_getUserList")
      @ResponseBody
      public Json getUserList(HttpServletRequest req) {
          Json result = new Json();
          Json j = Constant.convertJson(req);
          JSONObject o = (JSONObject) j.getObj();

          String userId = o.getString("userId");
          if (StringUtils.isBlank(userId)) {
              result.setCode(Code.NULL_CODE);
              result.setMsg(VERIFY_NULL);
              result.setSuccess(false);
              result.setObj(null);
              return result;
          }
          try {
              result.setCode(Code.SUCCESS_CODE);
              result.setSuccess(true);
              result.setMsg(OperatorResultMsg.SUCCESS);
              result.setObj(userService.getOCUserList(DataType.getAsInt(userId)));
          } catch (Exception e) {
              logger.error(e.getMessage());

              result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
              result.setSuccess(false);
              result.setMsg(PARAM_ILLEGAL);
          }

          return result;
      }
    @RequestMapping(value = "/securi_commonWordsList")
    @ResponseBody
    public Json commonWordsList(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        String taskType = o.getString("taskType");
        if (StringUtils.isBlank(taskType)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg(VERIFY_NULL);
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        try {
            List<CommonWordsVO> commonWordsVOs=taskService.getCommonWordsByTaskType(taskType);
            result.setObj(commonWordsVOs);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(OperatorResultMsg.SUCCESS);
         //   result.setObj(userService.getOCUserList(DataType.getAsInt(userId)));
        } catch (Exception e) {
            logger.error(e.getMessage());

            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }

        return result;
    }

    /*  private List<TaskVO> getTaskInfo(List<Object[]> list) {
          List<TaskVO> result = new ArrayList<TaskVO>();
          if (!CollectionUtils.isEmpty(list)) {
              TaskVO entity = null;
              for (Object[] obj : list) {
                  entity = new TaskVO();
                  entity.setId(DataType.getAsString(obj[0]));
                  entity.setContent(DataType.getAsString(obj[1]));
                  entity.setFinishpretimeLong(DataType.getAsLong(obj[2]));
                  entity.setFinishrealtimeLong(DataType.getAsLong(obj[3]));
                  	entity.setName(DataType.getAsString(obj[4]));
                  entity.setPreresult(DataType.getAsString(obj[4]));
                  entity.setRealresult(DataType.getAsString(obj[5]));
                  entity.setStarttimeLong(DataType.getAsLong(obj[6]));
                  entity.setState(DataType.getAsString(obj[7]));
                  entity.setUserName(DataType.getAsString(obj[8]));
                  entity.setExecuteName(DataType.getAsString(obj[9]));
                  entity.setWorkTypeName(DataType.getAsString(obj[10]));
                  entity.setStoreName(DataType.getAsString(obj[11]));
                  result.add(entity);
              }
          }
          return result;
      }*/
}
