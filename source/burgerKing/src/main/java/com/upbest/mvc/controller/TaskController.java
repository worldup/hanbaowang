package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.BWorkInfo;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.service.ITaskService;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.TaskVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

/**
 * 
 * @ClassName   类  名   称：	TaskController.java
 * @Description 功能描述：	任务管理
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月17日下午3:51:38
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    ITaskService taskService;
    
    @Autowired
    IMessageService messageService;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("current","task");
        return "/task/taskList";
    }

    @ResponseBody
    @RequestMapping("/detail")
    public void detail(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        BWorkInfo shop = taskService.findById(Integer.parseInt(id));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(shop, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    
    @ResponseBody
    @RequestMapping("/del")
    public void del(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        taskService.deleteById(Integer.parseInt(id));
        outPrint("0", response);
    }

    @ResponseBody
    @RequestMapping("/list")
    public void list(@RequestParam(value = "taskName", required = false) String name,
            @RequestParam(value = "userName", required = false) String uName,
            @RequestParam(value = "sDate", required = false) String sDate,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sidx", required = false) String sidx,
            @RequestParam(value = "sord", required = false) String sord,
            Model model, HttpSession session, HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        if("starttime".equals(sidx)){
            sidx="t.start_time";
        }
        Order or=null;
        List<Order> orders=new ArrayList<Order>();
        orders.add(new Order(Direction.ASC,"typ.sort_num"));
        if(StringUtils.isNotBlank(sord)&&StringUtils.isNotBlank(sidx)){
            if(sord.equalsIgnoreCase("desc")){
                 or=new Order(Direction.DESC,sidx);
            }else{
                 or=new Order(Direction.ASC,sidx);
            }
            orders.add(or);
        }
        Sort so=new Sort(orders);
        Buser user = (Buser)session.getAttribute("buser");
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize, so);
        Page<Object[]> task = taskService.findWorkList(user, name, uName, sDate, requestPage);
        PageModel result = new PageModel();
        result.setPage(page);
        result.setRows(getTaskInfo(task.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(task.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    /**
     * 
     * @Title 		   	函数名称：	loadStore
     * @Description   	功能描述：	加载门店相关信息
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @ResponseBody
    @RequestMapping("/loadStore")
    public void loadStore(@RequestParam(value = "id", required = false) Integer id,HttpServletResponse response) {
        List<SelectionVO> storeList=taskService.getStoreList(id);
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(storeList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    /**
     * 
     * @Title 		   	函数名称：	loadTask
     * @Description   	功能描述：	加载任务类型相关信息
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @ResponseBody
    @RequestMapping("/loadTask")
    public void loadTask(HttpServletResponse response) {
        List<SelectionVO> storeList=taskService.getTaskList();
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(storeList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    
    /**
     * 
     * @Title 		   	函数名称：	loadUser
     * @Description   	功能描述：	加载执行人相关信息
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws                  add by xubin
     */
    @ResponseBody
    @RequestMapping("/loadUser")
    public void loadUser(HttpServletResponse response, HttpSession session) {
        Buser buser=(Buser)session.getAttribute("buser");
        List<SelectionVO> userList = taskService.getUserList(buser);
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(userList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    private List<TaskVO> getTaskInfo(List<Object[]> list) {
        List<TaskVO> result = new ArrayList<TaskVO>();
        if (!CollectionUtils.isEmpty(list)) {
            TaskVO entity = null;
            for (Object[] obj : list) {
                entity = new TaskVO();
                entity.setId(DataType.getAsString(obj[0]));
                entity.setContent(DataType.getAsString(obj[1]));
                entity.setFinishpretime(DataType.getAsDate(obj[2]));
                entity.setFinishrealtime(DataType.getAsDate(obj[3]));
//                entity.setName(DataType.getAsString(obj[4]));
                entity.setPreresult(DataType.getAsString(obj[4]));
                entity.setRealresult(DataType.getAsString(obj[5]));
                entity.setStarttime(DataType.getAsDate(obj[6]));
                entity.setState(DataType.getAsString(obj[7]));
                entity.setUserName(DataType.getAsString(obj[8]));
                entity.setExecuteName(DataType.getAsString(obj[9]));
                entity.setWorkTypeName(DataType.getAsString(obj[10]));
                entity.setStoreName(DataType.getAsString(obj[11]));
                entity.setIsSelfCreate(DataType.getAsString(obj[12]));
                entity.setQuarter(DataType.getAsString(obj[13]));
                result.add(entity);
            }
        }
        return result;
    }

    @RequestMapping(value = "/create")
    public String createForm(String id, Model model) {
        if(StringUtils.isNotBlank(id)){
            BWorkInfo task = taskService.findById(Integer.parseInt(id));
            model.addAttribute("task",task);
        }
        return "/task/addTask";
    }
    @RequestMapping(value = "get")
    public String get(String id, Model model, HttpSession session) {
       /* BWorkInfo task = taskService.findById(Integer.parseInt(id));*/
        List<TaskVO> list=getTaskInfo(taskService.findWorkInfoList(id));
        model.addAttribute("task",list.get(0));
        return "/task/getTask";
    }
    @ResponseBody
    @RequestMapping("/add")
    public void list(@RequestParam(value = "jsons", required = false) String vo,
            Model model, HttpSession session, HttpServletResponse response) {
        BWorkInfo workInfo = new BWorkInfo();
        JSONObject jso = JSONObject.parseObject(vo);
        workInfo = (BWorkInfo) JSONObject.toJavaObject(jso,
                BWorkInfo.class);   
        Buser buser=(Buser)session.getAttribute("buser");
        workInfo.setState("0");
        workInfo.setUserid(buser.getId());
        taskService.saveBWorkInfo(workInfo);
        List<TaskVO> list = getTaskInfo(taskService.findWorkInfoList(workInfo.getId().toString()));
        //将任务信息创建到消息表中
        BMessage message = new BMessage();
        message.setMessageType("3");
        message.setPushTitle(list.get(0).getWorkTypeName());
        message.setPushContent(workInfo.getContent());
        message.setCreateTime(new Date());
//        message.setState("1");
        message.setIsRead("0");
        message.setUserId(buser.getId());
        message.setPushTime(new Date());
        message.setSenderId(buser.getId());
        message.setReceiverId(workInfo.getExecuteid());
        message.setTaskId(workInfo.getId());
        messageService.saveBMessage(message);
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
}
