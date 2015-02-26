package com.upbest.mvc.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.vo.BMessageVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.Constant;
import com.upbest.utils.DataType;

/**
 * 
 * @ClassName   类  名   称：	MessageAPIController.java
 * @Description 功能描述：	消息公告功能
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月20日下午3:20:20
 */
@Controller
@RequestMapping("/api/message")
public class MessageAPIController {
	
	@Autowired
	private IMessageService service;
	
	public static final String VERIFY_SUCCESS = "OK";
	public static final String VERIFY_NULL= "相关参数为空";
	public static final String PARAM_ILLEGAL= "参数非法";
	
	private static final Logger logger = LoggerFactory.getLogger(MessageAPIController.class);
	
	/**
	 * 
	 * @Title 		   	函数名称：	list
	 * @Description   	功能描述：   查看消息列表
	 * @param 		   	参          数：	
	 * @return          返  回   值：	Json  
	 * @throws
	 */
	@RequestMapping(value="/securi_list")
    @ResponseBody
    public Json list(HttpServletRequest req){
        Json result = new Json();
         Json j = Constant.convertJson(req);
         JSONObject o = (JSONObject) j.getObj();
         //消息类型，不传默认查全部消息类型,1:公告         2:紧急         3:任务 4:异常消息
         String messageType=o.getString("messageType");
         String page=o.getString("page");
         String pageSize=o.getString("pageSize");
         String userId=o.getString("userId");
         Integer receiveId=o.getInteger("receiveId");
         if(StringUtils.isBlank(pageSize)||StringUtils.isBlank(page)||StringUtils.isBlank(userId)){
             result.setCode(Code.NULL_CODE);
             result.setMsg(VERIFY_NULL);
             result.setSuccess(false);
             result.setObj(null); 
             return result;
         }
         try {
             Integer pageInt=DataType.getAsInt(page);
             Integer pageSizeInt=DataType.getAsInt(pageSize);
             Integer buserId=DataType.getAsInt(userId);
             
             List<Order> orders = new ArrayList<Order>();
             orders.add(new Order(Direction.ASC, "m.is_read"));
             orders.add(new Order(Direction.DESC, "m.create_time"));
             PageRequest requestPage = new PageRequest(pageInt != null ?pageInt- 1 : 0, pageSizeInt, new Sort(orders));
             result.setObj(getMessageInfo(service.findMessageListAPI(messageType,buserId,receiveId, requestPage).getContent()));
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ",e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
         return result;
    }
    private List<BMessageVO> getMessageInfo(List<Object[]> list){
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
	
	@RequestMapping(value="/securi_getMessageCount")
    @ResponseBody
    public Json getMessageCount(HttpServletRequest req){
        Json result = new Json();
         Json j = Constant.convertJson(req);
         JSONObject o = (JSONObject) j.getObj();
         String userId=o.getString("userId");
         if(StringUtils.isBlank(userId)){
             result.setCode(Code.NULL_CODE);
             result.setMsg(VERIFY_NULL);
             result.setSuccess(false);
             result.setObj(null); 
             return result;
         }
         try {
             Integer buserId=DataType.getAsInt(userId);
             result.setObj(service.getCountByUserId(buserId));
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ",e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
         return result;
    }
	
	@RequestMapping(value="/securi_getMessage")
    @ResponseBody
    public Json getMessage(HttpServletRequest req){
        Json result = new Json();
         Json j = Constant.convertJson(req);
         JSONObject o = (JSONObject) j.getObj();
         String id=o.getString("id");
         String userId=o.getString("userId");
         if(StringUtils.isBlank(id) || StringUtils.isBlank(userId)){
             result.setCode(Code.NULL_CODE);
             result.setMsg(VERIFY_NULL);
             result.setSuccess(false);
             result.setObj(null); 
             return result;
         }
         try {
             Integer bid=DataType.getAsInt(id);
             Integer uid=DataType.getAsInt(userId);
             
             BMessage b=service.findById(bid); 
             b.setIsRead("1");
             Object resultObj = b;
             
             if("1".equals(b.getMessageType())){
            	 //公告消息
            	 service.updateNoticeMessageStatu(uid,bid);
             }else{
            	 //其他类型的消息
            	 resultObj = service.saveBMessage(b);
             }
             result.setObj(resultObj);
             
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ",e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
         return result;
    }
	
	
	@RequestMapping(value="/securi_getMessageById")
    @ResponseBody
    public Json getMessageById(HttpServletRequest req){
        Json result = new Json();
         Json j = Constant.convertJson(req);
         JSONObject o = (JSONObject) j.getObj();
         Integer msgId = o.getInteger("messageId");
         if(msgId == null){
             result.setCode(Code.NULL_CODE);
             result.setMsg(VERIFY_NULL);
             result.setSuccess(false);
             result.setObj(null); 
             return result;
         }
         try {
             
             BMessage b=service.findById(msgId); 
             result.setObj(b);
             
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ",e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
         return result;
    }
	
	@RequestMapping(value="/securi_addMessage")
    @ResponseBody
    public Json addMessage(HttpServletRequest req){
        Json result = new Json();
         Json j = Constant.convertJson(req);
         JSONObject o = (JSONObject) j.getObj();
         //当前发送Id
         String userId=o.getString("userId");
        //获取接受者id
        String receiveIds=o.getString("receiveId");
         //消息标题
         String pushTitle=o.getString("pushTitle");
         //消息内容
         String pushContent=o.getString("pushContent");
         //消息类型
         String messageType=o.getString("messageType");
         if(StringUtils.isBlank(userId)||StringUtils.isBlank(pushTitle)
                 ||StringUtils.isBlank(pushContent) ||StringUtils.isBlank(messageType)){
             result.setCode(Code.NULL_CODE);
             result.setMsg(VERIFY_NULL);
             result.setSuccess(false);
             result.setObj(null); 
             return result;
         }
         try {
             String[] recevieIdArr = StringUtils.splitByWholeSeparator(receiveIds, ";");
             List<BMessage> listResult=new ArrayList();
             for (String recvId : recevieIdArr) {
                 Integer buserId = DataType.getAsInt(userId);
                 BMessage bMessage = new BMessage();
                 bMessage.setCreateTime(new Date());
                 bMessage.setIsRead("0");
                 bMessage.setPushTitle(pushTitle);
                 bMessage.setPushContent(pushContent);
                 bMessage.setMessageType(messageType);
                 bMessage.setUserId(buserId);
                 bMessage.setSenderId(buserId);
                 bMessage.setReceiverId(Integer.valueOf(recvId));
                 bMessage.setState("1");
                 bMessage.setPushTime(new Date());
                 service.saveBMessage(bMessage);
                 listResult.add(bMessage);
             }


             result.setObj(listResult);
             result.setCode(Code.SUCCESS_CODE);
             result.setSuccess(true);
             result.setMsg(VERIFY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("参数非法 ",e.getMessage());
            result.setCode(Code.TYPE_CONVERT_ERROR_CODE);
            result.setSuccess(false);
            result.setMsg(PARAM_ILLEGAL);
        }
         return result;
    }
	
	
}
