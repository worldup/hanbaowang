package com.upbest.mvc.service.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.upbest.mvc.controller.PushMessageController;
import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.service.IStoreUserService;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.DataType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PushMessageServiceImpl implements PushMessageServiceI {
    
    private static Logger logger = Logger.getLogger(PushMessageController.class);
    
    @Inject
    private UserRespository buserRepository;
    
    @Inject
    private IMessageService messageService;
    
    @Inject
    private IStoreUserService storeUserService;

    @Override
    public String push(String id, String ids, Buser buser) throws Exception {
        return push(ids, ids, buser, null, null, null);
    }
    
    @Override
    public String push(String id, String ids, Buser buser, String pushTitle,
    		String pushContent,Map<String, String> extra) throws Exception {
    	 String emps = "";
    	 
    	BMessage message = messageService.findById(DataType.getAsInt(id));
        //根据职工号进行推送
          if(StringUtils.isNotBlank(ids)){
              String[] idsArray = ids.split(",");
              String[] empArray = new String[idsArray.length];
//              String[] empArray = {"xubin"};
              for(int i=0; i<idsArray.length; i++){
                  empArray[i] = buserRepository.findOne(Integer.parseInt(idsArray[i])).getId()+"";
              }
              String appKey = ConfigUtil.get("Appkey");
              String MasterSecret = ConfigUtil.get("MasterSecret");
              if(appKey == null || MasterSecret == null){
                  throw new Exception("设备不存在或推送属性配置不正确");
              }
              else{
      //            String[] keys =jsonPush.getAppkey().split(",");   
      //            String[] secret =jsonPush.getMasterSecret().split(",");
            	  pushTitle = StringUtils.isEmpty(pushTitle) ?  message.getPushTitle() : pushTitle;
            	  pushContent = StringUtils.isEmpty(pushContent) ? message.getPushContent() : pushContent;
            	  extra = extra == null ? new HashMap<String, String>() : extra;
                  JPushClient jPushClient = new JPushClient(MasterSecret, appKey);
                  Boolean str = false;
                  for(int i=0; i < empArray.length; i++){
      //              JPushClient jPushClient = new JPushClient(secret[i], keys[i]);  
                    if(jPushClient != null){
                        PushPayload pushPayload=   PushPayload.newBuilder()
                                .setPlatform(Platform.android()).setAudience(Audience.alias(empArray[i])).setNotification(Notification.android(pushContent, pushTitle,extra))
                              //  .setAudience(Audience.tag(empArray[i])).setMessage(cn.jpush.api.push.model.Message.newBuilder().setMsgContent(pushContent).setTitle(pushTitle).addExtras(extra).build())
                                .build();

                        try {
                            PushResult result= jPushClient.sendPush(pushPayload);
                            logger.debug(Thread.currentThread().getName() + "++++endtime:" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            if (result.isResultOK()){
                                logger.debug("职工号：" + empArray[i] + "，发送成功， sendNo=" + result.sendno);
                                changeMessageState(id, idsArray[i], message, buser);
                                str = true;
                            }
                        } catch (APIConnectionException e) {
                            // Connection error, should retry later
                            logger.error("APIConnectionException，检查网络是否超时");
                            logger.error("职工号:" + empArray[i] + "，发送失败");
                        } catch (APIRequestException e) {
                            // Should review the error, and fix the request
                            logger.error("Should review the error, and fix the request", e);
                            logger.error("发送失败， 错误代码=" + e.getErrorCode() + ", 错误消息=" +e.getErrorMessage());
                            logger.error("职工号:" + empArray[i] + "，发送失败");
                            emps = emps + empArray[i] + " ";
                        }
                        }
                    } 
                  }

          }
          
          return emps;
          
    }
    
  //推送消息后修改消息状态
    public void changeMessageState(String id, String receiverId, BMessage message, Buser buser){
    	BMessage msg = messageService.findById(Integer.parseInt(id));
    	if(msg.getReceiverId() != null){
    		msg.setState("1");
    		msg.setPushTime(new Date());//推送时间修改
    	}else{
    		storeUserService.saveBMessageUser(receiverId, id, buser);
    	}
    }

}
