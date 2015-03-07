package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.controller.PushMessageController;
import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.entity.NoticeReadInfo;
import com.upbest.mvc.repository.factory.MessageRespository;
import com.upbest.mvc.repository.factory.NoticeReadRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.service.IStoreUserService;
import com.upbest.utils.DataType;

@Service
public class MessageServiceImpl implements IMessageService{
    
    private static Logger logger = Logger.getLogger(PushMessageController.class);

    @Inject
    MessageRespository messageRespository;
    
    @Autowired
    private CommonDaoCustom<Object[]> common;
    
    @Inject
    protected UserRespository buserRepository;
    
    @Inject
    protected NoticeReadRespository noticeReadRespository;
    
    @Autowired
    private IStoreUserService storeUserService;
    
    @Autowired
    private IBuserService userService;
    
    @Override
    public Page<Object[]> findMessageList(String name,String type,Integer userId, Pageable requestPage) {

//        Buser buser = buserRepository.findByNameAndIsdel(name, "1");
        StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT m.id,                       ");
        sql.append("         m.push_title,       ");
        sql.append("         m.push_content,       ");
        sql.append("         m.state,               ");
        sql.append("         m.create_time,             ");
        sql.append("         m.push_time,                ");
        sql.append("         m.is_read,           ");
        sql.append("         t1.real_name as creater,                 ");
        sql.append("         t2.real_name as sender,                 ");
        sql.append("         t3.real_name as receiver,                ");
        sql.append("         m.message_type                 ");
        sql.append("    FROM bk_message m              ");
        sql.append("    left join bk_user t1              ");
        sql.append("    on t1.id = m.user_id              ");
        sql.append("    left join bk_user t2              ");
        sql.append("    on t2.id = m.sender_id              ");
        sql.append("    left join bk_user t3              ");
        sql.append("    on t3.id = m.receiver_id              ");
//        sql.append("   where 1 = 1  and m.user_id = ?         ");
        sql.append("   where 1 = 1          ");
     
//        params.add(buser.getId());
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and m.push_title like ? ");
            params.add("%" + name + "%");
        }
        if(StringUtils.isNotBlank(type)){
            sql.append(" and m.message_type=? ");
            params.add(type);
        }
        if(userId!=0){
            Buser buser=buserRepository.findOne(userId);
            if(!"0".equals(buser.getRole())){
                sql.append(" and (m.user_id = ? or m.receiver_id = ? )");
                params.add(userId);
                params.add(userId);
            }
        }
        sql.append(" order by m.create_time desc ");
        return common.queryBySql(sql.toString(), params,requestPage);
    }
    public Page<Object[]> getInstanceMessages(Integer userId,Integer receiveId, String messageType, Pageable requestPage){

        StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT m.id,                       ");
        sql.append("         m.push_title,       ");
        sql.append("         m.push_content,       ");
        sql.append("         m.state,               ");
        sql.append("         m.create_time,             ");
        sql.append("         m.push_time,                ");
        sql.append("         m.is_read,           ");
        sql.append("         t1.real_name as creater,                 ");
        sql.append("         t2.real_name as sender,                 ");
        sql.append("         t3.real_name as receiver,                ");
        sql.append("         m.message_type                 ");
        sql.append("    FROM bk_message m              ");
        sql.append("    left join bk_user t1              ");
        sql.append("    on t1.id = m.user_id              ");
        sql.append("    left join bk_user t2              ");
        sql.append("    on t2.id = m.sender_id              ");
        sql.append("    left join bk_user t3              ");
        sql.append("    on t3.id = m.receiver_id              ");
        sql.append("   where 1 = 1          ");
        if(userId!=null&&-1!=userId){
            sql.append(" and  m.sender_id = ? ");
            params.add(userId);
        }
        if(receiveId!=null&&-1!=receiveId){
            sql.append(" and  m.receiver_id = ? ");
            params.add(receiveId);
        }
            sql.append(" and m.message_type=? ");
            params.add(messageType);
        return common.queryBySql(sql.toString(), params, requestPage);
    }
    public Page<Object[]> getAllMessages(Integer userId, String messageType, Pageable requestPage){
        Buser user=buserRepository.findOne(userId);
        StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT m.id,                       ");
        sql.append("         m.push_title,       ");
        sql.append("         m.push_content,       ");
        sql.append("         m.state,               ");
        sql.append("         m.create_time,             ");
        sql.append("         m.push_time,                ");
        sql.append("         m.is_read,           ");
        sql.append("         t1.real_name as creater,                 ");
        sql.append("         t2.real_name as sender,                 ");
        sql.append("         t3.real_name as receiver,                ");
        sql.append("         m.message_type                 ");
        sql.append("    FROM bk_message m              ");
        sql.append("    left join bk_user t1              ");
        sql.append("    on t1.id = m.user_id              ");
        sql.append("    left join bk_user t2              ");
        sql.append("    on t2.id = m.sender_id              ");
        sql.append("    left join bk_user t3              ");
        sql.append("    on t3.id = m.receiver_id              ");
        sql.append("   where 1 = 1          ");
        if(0!=userId){
            sql.append(" and  m.receiver_id = ? ");
            params.add(userId);
        }
        if(null!=user){
            if(user.getRole().equals("2")){
                //OC
                sql.append(" and m.message_type!='4' ");
            }
            /*List<Buser> users = userService.getSuperiories(userId);
            StringBuilder createUserIds = new StringBuilder();
            createUserIds.append(userId);
            if(!CollectionUtils.isEmpty(users)){
            	for (Buser buser : users) {
            		createUserIds.append(",").append(buser.getId());
    			}
            }
            sql.append("	and m.user_id in ("+createUserIds.toString()+") ");*/
        }
        if(StringUtils.isBlank(messageType)||"".equals(messageType)){
            sql.append("  union   ");
            //公告
            sql.append(getNoticeSql(userId,params));
        }
        else{
            sql.append(" and m.message_type=? ");
            params.add(messageType);
        }
        return common.queryBySql(sql.toString(), params, requestPage);
    }
    
    public Page<Object[]> getNoticeMessage(Integer userId, Pageable requestPage){
        StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append(getNoticeSql(userId,params));
        return common.queryBySql(sql.toString(), params, requestPage);
    }
    
    public String getNoticeSql(Integer userId, List<Object> params){
        StringBuffer sql=new StringBuffer();
        sql.append("  SELECT * from     (                  ");
        
        sql.append("  SELECT m.id,                       ");
        sql.append("         m.push_title,       ");
        sql.append("         m.push_content,       ");
        sql.append("         m.state,               ");
        sql.append("         m.create_time,             ");
        sql.append("         m.push_time,                ");
        sql.append("         is_read = 0 is_read,           ");
        sql.append("         t1.real_name as creater,                 ");
        sql.append("         t2.real_name as sender,                 ");
        sql.append("         t3.real_name as receiver,                ");
        sql.append("         m.message_type                 ");
        sql.append("    FROM bk_message m              ");
        sql.append("    left join bk_user t1              ");
        sql.append("    on t1.id = m.user_id              ");
        sql.append("    left join bk_user t2              ");
        sql.append("    on t2.id = m.sender_id              ");
        sql.append("    left join bk_user t3              ");
        sql.append("    on t3.id = m.receiver_id              ");
        sql.append("   where 1 = 1          ");
        sql.append(" and m.message_type=? ");
        
        sql.append("  and m.id not in (			");
        sql.append("	select r.message_id from  bk_notice_read r where r.user_id = ?");
        sql.append("	)	");
        
        params.add(1);
        params.add(userId);
        
        sql.append("	union	");
        
        
        sql.append("  SELECT m.id,                       ");
        sql.append("         m.push_title,       ");
        sql.append("         m.push_content,       ");
        sql.append("         m.state,               ");
        sql.append("         m.create_time,             ");
        sql.append("         m.push_time,                ");
        sql.append("         is_read = 1 is_read,           ");
        sql.append("         t1.real_name as creater,                 ");
        sql.append("         t2.real_name as sender,                 ");
        sql.append("         t3.real_name as receiver,                ");
        sql.append("         m.message_type                 ");
        sql.append("    FROM bk_message m              ");
        sql.append("    left join bk_user t1              ");
        sql.append("    on t1.id = m.user_id              ");
        sql.append("    left join bk_user t2              ");
        sql.append("    on t2.id = m.sender_id              ");
        sql.append("    left join bk_user t3              ");
        sql.append("    on t3.id = m.receiver_id              ");
        sql.append("   where 1 = 1          ");
        sql.append(" and m.message_type=? ");
        
        sql.append("  and m.id in (			");
        sql.append("	select r.message_id from  bk_notice_read r where r.user_id = ?");
        sql.append("	)	");
        
        sql.append("  )  m           ");
        
        params.add(1);
        params.add(userId);
        
       /* List<Buser> users = userService.getSuperiories(userId);
        StringBuilder createUserIds = new StringBuilder();
        createUserIds.append(userId);
        if(!CollectionUtils.isEmpty(users)){
        	for (Buser buser : users) {
        		createUserIds.append(",").append(buser.getId());
			}
        }
        sql.append("	and m.user_id in ("+createUserIds.toString()+") ");*/
        return sql.toString();
    }
    
    @Override
    public Page<Object[]> findMessageListAPI(String type,Integer userId, Integer receiveId,Pageable requestPage) {
        Page<Object[]> page;
        if(StringUtils.isBlank(type)){
            //全部消息
            page = getAllMessages(userId,requestPage);
        }else{
            if("1".equals(type)){
                page = getNoticeMessage(userId,requestPage);
            }else if("6".equals(type)){
                page=getInstanceMessages(userId,receiveId,type,requestPage);
            }
            else{
                //其他消息类型
                page = getAllMessages(userId,type,requestPage);
            }
        }
        return page;
    }

    private Page<Object[]> getAllMessages(Integer userId, Pageable requestPage) {
    	Buser user=buserRepository.findOne(userId);
        StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT *  from (                     ");
        sql.append("  SELECT m.id,                       ");
        sql.append("         m.push_title,       ");
        sql.append("         m.push_content,       ");
        sql.append("         m.state,               ");
        sql.append("         m.create_time,             ");
        sql.append("         m.push_time,                ");
        sql.append("         m.is_read,           ");
        sql.append("         t1.real_name as creater,                 ");
        sql.append("         t2.real_name as sender,                 ");
        sql.append("         t3.real_name as receiver,                ");
        sql.append("         m.message_type                 ");
        sql.append("    FROM bk_message m              ");
        sql.append("    left join bk_user t1              ");
        sql.append("    on t1.id = m.user_id              ");
        sql.append("    left join bk_user t2              ");
        sql.append("    on t2.id = m.sender_id              ");
        sql.append("    left join bk_user t3              ");
        sql.append("    on t3.id = m.receiver_id              ");
        sql.append("   where 1 = 1          ");
        if(0!=userId){
            sql.append(" and  m.receiver_id = ? ");
            params.add(userId);
        }
        if(null!=user){
            if(user.getRole().equals("2")){
                //OC
                sql.append(" and m.message_type!='4' ");
            }
            
            /*List<Buser> users = userService.getSuperiories(userId);
            StringBuilder createUserIds = new StringBuilder();
            createUserIds.append(userId);
            if(!CollectionUtils.isEmpty(users)){
            	for (Buser buser : users) {
            		createUserIds.append(",").append(buser.getId());
    			}
            }
            sql.append("	and m.user_id in ("+createUserIds.toString()+") ");*/
        }
        sql.append("  union   ");
        //公告
        sql.append(getNoticeSql(userId,params));
        sql.append("  ) m	");
        return common.queryBySql(sql.toString(), params, requestPage);
	}
	@Override
    public List<Object[]> findMessageById(String id) {
//        return messageRespository.findOne(id);
//        Buser buser = buserRepository.findByNameAndIsdel(name, "1");
        StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT m.id,                       ");
        sql.append("         m.push_title,       ");
        sql.append("         m.push_content,       ");
        sql.append("         m.state,               ");
        sql.append("         m.create_time,             ");
        sql.append("         m.push_time,                ");
        sql.append("         m.is_read,           ");
        sql.append("         t1.real_name as creater,                 ");
        sql.append("         t2.real_name as sender,                 ");
        sql.append("         t3.real_name as receiver,                 ");
        sql.append("         m.message_type                 ");
        sql.append("    FROM bk_message m              ");
        sql.append("    left join bk_user t1              ");
        sql.append("    on t1.id = m.user_id              ");
        sql.append("    left join bk_user t2              ");
        sql.append("    on t2.id = m.sender_id              ");
        sql.append("    left join bk_user t3              ");
        sql.append("    on t3.id = m.receiver_id              ");
        sql.append("   where 1 = 1          ");
        if(StringUtils.isNotBlank(id)){
            sql.append(" and m.id = ?");
            params.add(id);
        }
        return common.queryBySql(sql.toString(), params);
    }

    @Override
    public BMessage saveBMessage(BMessage message) {
        return    messageRespository.save(message);
    }

    @Override
    public void deleteById(Integer id) {
        BMessage message = messageRespository.findOne(id);
        messageRespository.delete(message);
    }

    @Override
    public BMessage findById(Integer id) {
        return messageRespository.findOne(id);
    }

    @Override
    public List<Buser> editeMessageById(List<String> nameList) {
        List<Buser> list = new ArrayList<Buser>();
        Buser buser = null;
        for(int i=0; i<nameList.size(); i++){
            buser = buserRepository.findByRealnameAndIsdel(nameList.get(i), "1");
            list.add(buser);
        }
        return list;
    }

    @Override
    public List<String> getUserIds(String MessageId) {
        StringBuffer sql = new StringBuffer();
        List<String> params = new ArrayList<String>();
        sql.append("  select ur.id, ur.real_name          ");
        sql.append("    from bk_shop_user su              ");
        sql.append("    join bk_user ur                   ");
        sql.append("      on ur.id = su.user_id           ");
        sql.append("   where 1 = 1                        ");
        if (StringUtils.isNotBlank(MessageId)) {
            sql.append("     and su.message_id = ?             ");
            params.add(MessageId);
        }
        String ids = "";
        String userNames = "";
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        List<String> result = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] obj : list) {
                ids += DataType.getAsString(obj[0]) + ",";
                userNames += DataType.getAsString(obj[1]) + ",";
            }
        }
        if (StringUtils.isNotBlank(ids)) {
            ids = ids.substring(0, ids.length() - 1);
        }
        if (StringUtils.isNotBlank(userNames)) {
            userNames = userNames.substring(0, userNames.length() - 1);
        }

        result.add(ids);
        result.add(userNames);
        return result;
    }


    /*@Override
    public void push(String id, String ids, BMessage message, Buser buser) throws Exception {
        //根据职工号进行推送
        if(StringUtils.isNotBlank(ids)){
            String[] idsArray = ids.split(",");
            String[] empArray = new String[idsArray.length];
//            String[] empArray = {"xubin"};
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
                String emps = "";
                Boolean str = false;
                for(int i=0; i < empArray.length; i++){
    //              JPushClient jPushClient = new JPushClient(secret[i], keys[i]);  
                    JPushClient jPushClient = new JPushClient(MasterSecret, appKey);  
                  if(jPushClient != null){
           
                      MessageResult result = jPushClient.sendNotificationWithTag(1, empArray[i], message.getPushTitle(), message.getPushContent());
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
                          }
                      }else{
                          logger.error("无法获取数据，检查网络是否超时");
                          throw new Exception("无法获取数据，检查网络是否超时");
                      }  
                  } 
                }
                if(str){
                    deleteById(Integer.parseInt(id));
                }
                if(StringUtils.isNotEmpty(emps)){
                    throw new Exception(emps);
                }
            }
        }
    }
    
    //推送消息后修改消息状态
    public void changeMessageState(String id, String receiverId, BMessage message, Buser buser){
        message.setState("1");
        message.setPushTime(new Date());//推送时间修改
        message.setSenderId(buser.getId());//发送人修改
        saveBMessage(message);
        storeUserService.saveBMessageUser(receiverId, id);
    }*/
    
/**
 * 
 * @Title 		   	函数名称：	getMessageCountByType
 * @Description   	功能描述：	根据消息类型查询消息数
 * @param 		   	参          数：	
 * @return          返  回   值：	int  
 * @throws
 */
    public int getMessageCountByTypeAndUsrId(String type,Integer userId){
        StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append(" select count(1) from bk_message t where 1=1 and t.is_read='0'");
        if(StringUtils.isNotEmpty(type)){
            if(!"1".equals(type)){
                sql.append("  and t.message_type = ?  ");
                params.add(type);
                if(userId != null){
                    sql.append("  and t.receiver_id=? ");
                    params.add(userId);
                }
            }else{
                sql.append("  and t.message_type = ?  ");
                sql.append("  and t.id not in (			");
                sql.append("	select r.message_id from  bk_notice_read r where r.user_id = ?");
                sql.append("	)	");
                params.add(type);
                params.add(userId);
            }
        }
        List<Object[]> list=common.queryBySql(sql.toString(), params);
        return DataType.getAsInt(DataType.getAsString(list.get(0)));
    }

    @Override
    public Map<String,Integer> getCountByUserId(Integer userId) {
        Map<String,Integer> result=new HashMap<String,Integer>();
        int gg = getMessageCountByTypeAndUsrId("1", userId);
        result.put("notice", gg);
        int jj = getMessageCountByTypeAndUsrId("2", userId);
        result.put("urgency", jj);
        int rw = getMessageCountByTypeAndUsrId("3", userId);
        result.put("task", rw);
        int yc = getMessageCountByTypeAndUsrId("4", userId);
        result.put("exception", yc);
        int um = getMessageCountByTypeAndUsrId("6", userId);
        result.put("userMessage",um);
        Buser buser =buserRepository.findOne(userId);
        if(buser!=null){
            if(buser.getRole().equals("2")){
                //OC
                result.put("total", jj + gg + rw);
            }else {
                result.put("total", jj + gg + rw + yc);
            }
        }
        return result;
    }
    
    @Override
    public void updateNoticeMessageStatu(Integer uid, Integer bid) {
    	NoticeReadInfo info = new NoticeReadInfo();
    	info.setMessageId(bid);
    	info.setUserId(uid);
    	
    	noticeReadRespository.save(info);
    }

}
