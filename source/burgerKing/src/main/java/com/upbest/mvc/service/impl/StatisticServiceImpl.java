package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.constant.Constant.MessageType;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IStatisticService;
import com.upbest.mvc.vo.MessageCountInfoVO;
import com.upbest.mvc.vo.UserStatisticVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Service
public class StatisticServiceImpl implements IStatisticService {
	
	@Autowired
	private IBuserService service;
	
	@Autowired
    private CommonDaoCustom<Object[]> common;
	
	@Override
	public PageModel<UserStatisticVO> queryStatisticResult(Pageable pageable) {
		PageModel<UserStatisticVO> result = new PageModel<UserStatisticVO>();
		
		Page<Buser> page = service.findUsers(pageable);
		if(!page.hasContent()){
			return result;
		}
		
        result.setPage(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setRecords((int)page.getTotalElements());
        
        List<Buser> users = page.getContent();
	
		Map<Integer, Integer> userShopMap = queryShopNum(users);
		Map<Integer, Integer> userTaskMap = queryTaskNum(users);
		Map<Integer, MessageCountInfoVO> userMessageMap = queryMessageNum(users);
		
		Map<Integer, UserStatisticVO> statisitcMap = buildUserStatisticMap(users);
		merge(statisitcMap,userShopMap,userTaskMap,userMessageMap);
		
		result.setRows(new ArrayList<UserStatisticVO>(statisitcMap.values()));
		return result;
	}
	
	private void merge(Map<Integer, UserStatisticVO> statisitcMap,
			Map<Integer, Integer> userShopMap,
			Map<Integer, Integer> userTaskMap,
			Map<Integer, MessageCountInfoVO> userMessageMap) {
		if(!CollectionUtils.isEmpty(statisitcMap)){
			for (Entry<Integer, UserStatisticVO> entry : statisitcMap.entrySet()) {
				int userId = entry.getKey();
				UserStatisticVO vo = entry.getValue();
				
				vo.setShopNum(getNumber(userShopMap.get(userId)));
				vo.setTaskNum(getNumber(userTaskMap.get(userId)));
				vo.setMessageCountInfo(userMessageMap.get(userId));
			}
		}
		
	}
	
	private int getNumber(Integer integer){
		return integer == null ? 0 : integer;
	}

	private Map<Integer, UserStatisticVO> buildUserStatisticMap(
			List<Buser> users) {
		Map<Integer, UserStatisticVO> map = new HashMap<Integer, UserStatisticVO>();
		if(!CollectionUtils.isEmpty(users)){
			for (Buser buser : users) {
				UserStatisticVO vo = new UserStatisticVO();
				vo.setUserId(buser.getId());
				vo.setUserName(buser.getName());
				
				map.put(vo.getUserId(), vo);
			}
		}
		return map;
	}

	/**
     * 查询用户下的门店数
     * @param users
     * @return
     * select t.user_id userId,coutn(*) num from bk_shop_user t where t.user_id in (73,76) group by t.user_id;
     */
    private  Map<Integer, Integer> queryShopNum(List<Buser> users){
    	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    	
    	StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT t.user_id,                       ");
        sql.append("         count(*)          				   ");
        sql.append("   FROM BK_SHOP_USER t          		  ");
        sql.append("   where t.user_id in (                 ");
        int size = users.size();
        for(int i = 0;i < size;i++){
        	sql.append(users.get(i).getId());
        	if(i < size - 1){
        		sql.append(",");
        	}
        }
        sql.append("			)							");
        sql.append("	group by t.user_id");
        
        List<Object[]> page = common.queryBySql(sql.toString(), params);
        
        for (Object[] objAry : page) {
			map.put(DataType.getAsInt(objAry[0]), DataType.getAsInt(objAry[1]));
		}
    	return map;
    }
    /**
     * 查询用户下的任务数
     * @param users
     * @return
     */
    private Map<Integer, Integer> queryTaskNum(List<Buser> users){
    	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    	
    	StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT t.user_id,                       ");
        sql.append("         count(*)          				   ");
        sql.append("   FROM BK_WORK_INFO t          		  ");
        sql.append("   where t.user_id in (                 ");
        int size = users.size();
        for(int i = 0;i < size;i++){
        	sql.append(users.get(i).getId());
        	if(i < size - 1){
        		sql.append(",");
        	}
        }
        sql.append("			)							");
        sql.append("	group by t.user_id						");
        
        List<Object[]> page = common.queryBySql(sql.toString(), params);
        
        for (Object[] objAry : page) {
			map.put(DataType.getAsInt(objAry[0]), DataType.getAsInt(objAry[1]));
		}
    	return map;
    }
    
    /**
     * 查询用户下的消息数
     * @param users
     * @return
     */
    private Map<Integer, MessageCountInfoVO> queryMessageNum(List<Buser> users){
    	StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT t.user_id,                       ");
        sql.append("  		 t.message_type,                       ");
        sql.append("         t.receiver_id,         				   ");
        sql.append("         t.sender_id        				   ");
        sql.append("   FROM BK_MESSAGE t          		  ");
        sql.append("   where t.user_id in (                 ");
        int size = users.size();
        for(int i = 0;i < size;i++){
        	sql.append(users.get(i).getId());
        	if(i < size - 1){
        		sql.append(",");
        	}
        }
        sql.append("			)							");

        /*StringBuilder sb = new StringBuilder();
        for (Buser user : users) {
			sb.append(user.getId()).append(",");
		}
        params.add(sb.substring(0, sb.length() - 1));*/
        
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        
    	return buildMessageCoutnInfoMap(list);
    }

	private Map<Integer, MessageCountInfoVO> buildMessageCoutnInfoMap(
			List<Object[]> list) {
		Map<Integer, MessageCountInfoVO> map = new HashMap<Integer, MessageCountInfoVO>();
		for (Object[] objAry : list) {
			int userId = DataType.getAsInt(objAry[0]);
			MessageCountInfoVO vo = map.get(userId);
			if(vo == null){
				vo = new MessageCountInfoVO();
				map.put(userId, vo);
			}
			
			Integer receiverId = objAry[2] == null ? null : DataType.getAsInt(objAry[2]);
			Integer sendId = objAry[3] == null ? null : DataType.getAsInt(objAry[3]);
			int messageType = DataType.getAsInt(objAry[1]);
			
			boolean hasReceiverId = receiverId != null,
					hasSendId = sendId != null;
			
			if(hasReceiverId){
				vo.setTotalReciveMsgNum(vo.getTotalReciveMsgNum() + 1);
			}
			if(hasSendId){
				vo.setTotalSendMsgNum(vo.getTotalSendMsgNum() + 1);
			}
			
			if(hasReceiverId || hasSendId){
				int reciveNum = 0,
						sendNum = 0;
				switch (MessageType.getMessageType(messageType)) {
					case Emergency:
							reciveNum = vo.getEmergencyMsgReciveNum();
							sendNum = vo.getEmergencyMsgSendNum();
							vo.setEmergencyMsgReciveNum(hasReceiverId ? reciveNum + 1 : reciveNum);
							vo.setEmergencyMsgSendNum(hasSendId ? sendNum + 1 : sendNum);
							break;
						case Exception:
							reciveNum = vo.getExceptionMsgReciveNum();
							sendNum = vo.getExceptionMsgSendNum();
							vo.setExceptionMsgReciveNum(hasReceiverId ? reciveNum + 1 : reciveNum);
							vo.setExceptionMsgSendNum(hasSendId ? sendNum + 1 : sendNum);
							break;
						case Notice:
							reciveNum = vo.getNoticeMsgReciveNum();
							sendNum = vo.getNoticeMsgSendNum();
							vo.setNoticeMsgReciveNum(hasReceiverId ? reciveNum + 1 : reciveNum);
							vo.setNoticeMsgSendNum(hasSendId ? sendNum + 1 : sendNum);
							break;
						case Task:
							reciveNum = vo.getTaskMsgReciveNum();
							sendNum = vo.getTaskMsgSendNum();
							vo.setTaskMsgReciveNum(hasReceiverId ? reciveNum + 1 : reciveNum);
							vo.setTaskMsgSendNum(hasSendId ? sendNum + 1 : sendNum);
							break;
						default:
							break;
				}
				
			}
			
		}
		return map;
	}
}
