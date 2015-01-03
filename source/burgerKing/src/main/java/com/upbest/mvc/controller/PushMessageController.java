package com.upbest.mvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.service.IStoreUserService;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.pageModel.Json;
import com.upbest.utils.DataType;


@Controller
@RequestMapping("/push")
public class PushMessageController {
    private static final Logger logger = LoggerFactory.getLogger(PushMessageController.class);

    @Autowired
    private IMessageService messageService;
    
    @Autowired
    private IStoreUserService storeUserService;
    
    @Autowired
    private PushMessageServiceI pushMessageServiceI;
    
    @ResponseBody
    @RequestMapping("/message")
    public Json pushMessage(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "ids", required = false) String ids, Model model, HttpSession session,
            HttpServletResponse response) {
        BMessage message = messageService.findById(DataType.getAsInt(id));
        Buser buser = (Buser)session.getAttribute("buser");
        Json json = new Json();
        json.setSuccess(false);
        List<Map<Object, Object>> listMap = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        try {
            String title = message.getPushTitle();
            String content = message.getPushContent();
            String sender = buser.getRealname();
            String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            map.put("title", title);
            map.put("content", content);
            map.put("sender", sender);
            map.put("time", time);
            if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
                if(StringUtils.isEmpty(sender)){
                    logger.error("获取消息失败！");
                    json.setMsg("获取消息失败！");
                    return json;
                }
            }
            
            /**
             * 额外数据
             */
            Map<String, Object> extra = new HashMap<String, Object>();
        	extra.put("messageId", message.getId());
            String emps = pushMessageServiceI.push(id, ids, buser,message.getPushTitle(),message.getPushContent(),extra);
            if(StringUtils.isEmpty(emps)){
            	map.put("emp", "");
            	listMap.add(map);
            	json.setObj(listMap);
            	json.setMsg("消息推送成功！");
            	json.setSuccess(true);
            }else{
            	logger.error("消息推送失败！");
                map.put("emp", emps);
                listMap.add(map);
                json.setObj(listMap);
                json.setMsg("消息推送失败！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
        	logger.error("消息推送失败！");
        	json.setMsg("消息推送失败！");
            json.setSuccess(false);
        }
        //状态修改
        /*message.setState("1");
        message.setPushTime(new Date());//推送时间修改
        message.setSenderId(buser.getId());//发送人修改
        messageService.saveBMessage(message);
        storeUserService.saveBMessageUser(ids, DataType.getAsString(id));*/
        
        if(StringUtils.isEmpty(json.getMsg()) || !json.getMsg().contains("失败")){
            json.setSuccess(true);
        }
        
        return json;
    }
    
    
}
