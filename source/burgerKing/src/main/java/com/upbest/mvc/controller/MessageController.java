package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.service.IStoreUserService;
import com.upbest.mvc.vo.BMessageVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping(value = "/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    IMessageService messageService;
    
    @Autowired
    IBuserService userService;
    
    @Autowired
    IStoreUserService storeUserService;
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("current", "message");
        return "/message/messageList";
    }
    
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void getMessageListInfo(HttpSession session, @RequestParam(value = "messageName", required = false) String name, HttpServletResponse response, @RequestParam(value = "rows", 
            required = false) Integer pageSize, 
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "sidx", required = false) String sidx,
            @RequestParam(value = "sord", required = false) String sord
            ){
        if (pageSize == null) {
            pageSize = 10;
        }
        if("createTime".equals(sidx)){
            sidx="m.create_time";
        }
        if("pushTime".equals(sidx)){
            sidx="m.push_time";
        }
        Order or=null;
        List<Order> orders=new ArrayList<Order>();
        if(StringUtils.isNotBlank(sord)&&StringUtils.isNotBlank(sidx)){
            if(sord.equalsIgnoreCase("desc")){
                 or=new Order(Direction.DESC,sidx);
            }else{
                 or=new Order(Direction.ASC,sidx);
            }
            orders.add(or);
        }
        Sort so=new Sort(orders);
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize, so);
        Buser user=(Buser)session.getAttribute("buser");
        Page<Object[]> messageList = messageService.findMessageList(name,null,user.getId(),requestPage);
        PageModel result=new PageModel();
        result.setPage(page);
        result.setRows(getShopInfo(messageList.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(messageList.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json,response);
    }
    
    @RequestMapping("/edite")
    public String edite(@RequestParam(value = "id", required = false) String id, Model model,HttpSession session,
            HttpServletResponse response, HttpServletRequest request) {
        List<Object[]> messageList = messageService.findMessageById(id);
        model.addAttribute("message", getShopInfo(messageList).get(0));
        return "/message/addMessage";
    }
    
    @RequestMapping(value = "/get")
    public String get(String id, Model model, HttpSession session) {
        List<Object[]> messageList = messageService.findMessageById(id);
        model.addAttribute("message", getShopInfo(messageList).get(0));
        return "/message/getMessage";
    }
    
    /*@ResponseBody//weiyong
    @RequestMapping(value = "/getCheck")
    public void getCheck(String id, Model model, HttpSession session, HttpServletResponse response) {
        List<Object[]> messageList = messageService.findMessageById(id);
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(messageList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
//        model.addAttribute("message", getShopInfo(messageList).get(0));
        outPrint(json,response);
    }*/

    @RequestMapping(value = "/push")
    public String push(String id, Model model) {
//        if(StringUtils.isNotBlank(id)){
//            BMessage message = messageService.findById(Integer.parseInt(id));
//            message.setState("1");
//            messageService.saveBMessage(message);
//            model.addAttribute("message",message);
//        }
        model.addAttribute("messageid", id);
        return "/message/pushMessage";
    }
    
    /*@ResponseBody
    @RequestMapping("/pushMessage")
    public void pushMessage(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "ids", required = false) String ids, Model model, HttpSession session,
            HttpServletResponse response) {
        BMessage message = messageService.findById(Integer.parseInt(id));
        message.setState("1");//状态修改
        message.setPushTime(new Date());//推送时间修改
        message.setSenderId(((Buser)session.getAttribute("buser")).getId());//发送人修改
        messageService.saveBMessage(message);
        storeUserService.saveBMessageUser(ids, DataType.getAsString(id));
    }*/
    
    @ResponseBody
    @RequestMapping("/getUserTreeList")
    public void getUserTreeList(@RequestParam(value = "role", required = false) String role, Model model, HttpSession session, HttpServletResponse response) {
        String jsonString = new String();
        List<TreeVO> list = userService.getUrVOListMessage(((Buser)session.getAttribute("buser")).getId());
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        String regEx = "[''”“\"\"]";
        Pattern pat = Pattern.compile(regEx);
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
        outPrint(json, response);
    }
    
    @ResponseBody
    @RequestMapping("/add")
    public void addMessage(@RequestParam(value = "jsons", required = false) String vo, HttpServletRequest req, HttpServletResponse response, HttpSession session){
        String jsonString = new String();
        BMessage message = new BMessage();
        BMessageVO messageVo = new BMessageVO();
        JSONObject jso = JSONObject.parseObject(vo);
        messageVo = (BMessageVO) JSONObject.toJavaObject(jso, BMessageVO.class);
        message.setId(messageVo.getId());
        message.setMessageType(messageVo.getMessageType());
        message.setPushTitle(messageVo.getPushTitle());
        message.setPushContent(messageVo.getPushContent());
        //新增重新修改后的消息，创建时间变为现在的时间
//        if(messageVo.getCreateTime() == null){
            message.setCreateTime(new Date());
//        }else{
//            message.setCreateTime(messageVo.getCreateTime());
//        }
        //修改后的或者新增的消息推送时间都为null
//        message.setPushTime(messageVo.getPushTime());
        //新增或者修改紧急状态消息时，推送状态和读取状态都为“0”,新增或者修改公告消息时，推送状态不显示为null
        if("2".equals(messageVo.getMessageType())){
            message.setState("0");
        }
//        else{
//            message.setState(messageVo.getState());
//        }
//        if("".equals(messageVo.getIsRead())){
            message.setIsRead("0");
//        }else{
//            message.setIsRead(messageVo.getIsRead());
//        }
        List<String> nameList = new ArrayList<String>();
        nameList.add(messageVo.getCreater());
        nameList.add(messageVo.getSender());
        nameList.add(messageVo.getReceiver());
        List<Buser> userList = messageService.editeMessageById(nameList);
        if(userList.get(0) != null){
            message.setUserId(userList.get(0).getId());
        }else{
            message.setUserId(((Buser)session.getAttribute("buser")).getId());
        }
        if(userList.get(1) != null){
            message.setSenderId(userList.get(1).getId());
        }
        if(userList.get(2) != null){
            message.setReceiverId(userList.get(2).getId());
        }
        messageService.saveBMessage(message);
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(jsonString, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    
    @ResponseBody
    @RequestMapping("/del")
    public void delMessage(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        messageService.deleteById(Integer.parseInt(id));
        outPrint("0", response);
    }
    
    @RequestMapping(value = "/create")
    public String createMessage(String id, Model model) {
        if(StringUtils.isNotBlank(id)){
            BMessage message = messageService.findById(Integer.parseInt(id));
            model.addAttribute("message",message);
        }
        return "/message/addMessage";
    }
    
    private List<BMessageVO> getShopInfo(List<Object[]> list){
        List<BMessageVO> result=new ArrayList<BMessageVO>();
        if(!CollectionUtils.isEmpty(list)){
            BMessageVO entity=null;
            for(Object[] obj:list){
                entity=new BMessageVO();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setPushTitle(DataType.getAsString(obj[1]));
                entity.setPushContent(DataType.getAsString(obj[2]));
                entity.setState(DataType.getAsString(obj[3]));
                entity.setCreateTime(DataType.getAsDate(obj[4]));
                entity.setPushTime(DataType.getAsDate(obj[5]));
                entity.setIsRead(DataType.getAsString(obj[6]));
                entity.setCreater(DataType.getAsString(obj[7]));
                entity.setSender(DataType.getAsString(obj[8]));
                entity.setReceiver(DataType.getAsString(obj[9]));
                entity.setMessageType(DataType.getAsString(obj[10]));
//                List<String> urList=messageService.getUserIds(DataType.getAsString(obj[0]));
//                if(!CollectionUtils.isEmpty(urList)){
//                    entity.setUserIds(urList.get(0));
//                    entity.setUserNames(urList.get(1));
//                }
                result.add(entity);
            }
        }
        return result;
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
