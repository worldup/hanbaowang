package com.upbest.mvc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.upbest.mvc.controller.PushMessageController;
import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.service.IStoreUserService;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.DataType;

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
    		String pushContent,Map<String, Object> extra) throws Exception {
    	 String emps = "";
    	 
    	BMessage message = messageService.findById(DataType.getAsInt(id));
        //根据职工号进行推送
          if(StringUtils.isNotBlank(ids)){
              String[] idsArray = ids.split(",");
              String[] empArray = new String[idsArray.length];
//              String[] empArray = {"xubin"};
              for(int i=0; i<idsArray.length; i++){
                  empArray[i] = buserRepository.findOne(Integer.parseInt(idsArray[i])).getEmp();
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
            	  extra = extra == null ? new HashMap<String, Object>() : extra;
            	  
                  Boolean str = false;
                  for(int i=0; i < empArray.length; i++){
      //              JPushClient jPushClient = new JPushClient(secret[i], keys[i]);  
                      JPushClient jPushClient = new JPushClient(MasterSecret, appKey);  
                    if(jPushClient != null){
             
                        MessageResult result = jPushClient.sendNotificationWithTag(message.getId(), empArray[i], pushTitle, pushContent,0,extra);
                        logger.debug(Thread.currentThread().getName()+"++++endtime:"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        if (null != result){
                            if (result.getErrcode() == ErrorCodeEnum.NOERROR.value()){
                                logger.debug("职工号：" + empArray[i] + "，发送成功， sendNo=" + result.getSendno());
                                changeMessageState(id, idsArray[i], message, buser);
                                str = true;
                            }else{
                                logger.error("发送失败， 错误代码=" + result.getErrcode() + ", 错误消息=" + result.getErrmsg());
                                logger.error("职工号:" + empArray[i] + "，发送失败");
                                emps = emps + empArray[i] + " ";
                                
//                                throw new Exception("职工号:" + empArray[i] + "，发送失败");
                            }
                        }else{
                            logger.error("无法获取数据，检查网络是否超时");
                            throw new Exception("无法获取数据，检查网络是否超时");
                        }  
                    } 
                  }
                  /*if(str){
                      messageService.deleteById(Integer.parseInt(id));
                  }
*/              }
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
