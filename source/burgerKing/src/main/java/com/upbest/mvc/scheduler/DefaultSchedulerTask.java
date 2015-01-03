package com.upbest.mvc.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.repository.factory.MessageRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.utils.DataType;

@Component
public class DefaultSchedulerTask implements SchedulerTask {
	private static final Logger logger = LoggerFactory.getLogger(DefaultSchedulerTask.class);
	
	@Autowired
	protected MessageRespository messageRespository;
	@Autowired
	protected WorkRespository workRespository;
	@Autowired
	protected PushMessageServiceI pushMessageService;
	@Autowired
	protected CommonDaoCustom<Object[]> commonDao;
	
	@Override
	public void scheduler() throws Exception {
		List<TaskInfo> taskInfo = queryNotCreateTaskInfos();
		
		if(!CollectionUtils.isEmpty(taskInfo)){
			pushMessage(taskInfo);
		}
		
	}

	/**
	 * 对用户进行消息推送
	 * @param users
	 * @param tipMessage
	 * @throws Exception 
	 */
	private void pushMessage(List<TaskInfo> taskInfo) throws Exception {
		if(!CollectionUtils.isEmpty(taskInfo)){
			List<BMessage> messages = new ArrayList<BMessage>();
			for (TaskInfo tinfo : taskInfo) {
				BMessage message = new BMessage();
				message.setMessageType("3");
				message.setPushTitle("任务通知");
				message.setPushContent(tinfo.getContent());
				message.setCreateTime(new Date());
				message.setIsRead("0");
				message.setReceiverId(tinfo.getUserId());
				
				messages.add(message);
			}
			messageRespository.save(messages);
			
			for (BMessage bMessage : messages) {
				 /**
	             * 额外数据
	             */
	            Map<String, Object> extra = new HashMap<String, Object>();
	        	extra.put("messageId", bMessage.getId());
				pushMessageService.push(bMessage.getId() + "", bMessage.getReceiverId() + "", null
						,bMessage.getPushTitle(),bMessage.getPushContent(),extra);
			}
			
        } 
	}

	/**
	 * 查询还没有创建(周，月，季度)类型的任务
	 * @return
	 */
	protected  List<TaskInfo> queryNotCreateTaskInfos(){
		List<TaskInfo> result = new ArrayList<TaskInfo>();
		
		//查询用户下(周/月份/季度)已经创建的任务
		Map<Integer, List<TaskInfo>> userTaskMap = queryUserCreatedTasks();
		
		//查询所有(周/月份/季度)的任务类型
		List<BWorkType> workTypeList = queryWorkTypes();
		
		result.addAll(queryNotCreateTaskInfosWithNotRelatedShop(userTaskMap,workTypeList));
		result.addAll(queryNotCreateTaskInfosWithRelatedShop(userTaskMap,workTypeList));
		
        return result;
	}
	
	private List<BWorkType> queryWorkTypes() {
		
		return workRespository.findByFrequency(getWorkType());
	}

	private Map<Integer, List<TaskInfo>> queryUserCreatedTasks() {
		Map<Integer, List<TaskInfo>> userTaskMap = new HashMap<Integer, List<TaskInfo>>();
		
		List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
		sql.append("	select u.id,t.work_type_id,t.store_id from  	")
			.append("	(select * from bk_user u where u.role = '2') u ")
			.append("	left join ")
			.append("	(select t.work_type_id,t.store_id,t.user_id		")
			.append("		from bk_work_info t where 1=1 		");
		if(getStartTime() != null){
        	sql.append("	and DATEDIFF(day,?,t.start_time) >= 0  ");
        	params.add(getStartTime());
        }
        if(getEndTime() != null){
        	sql.append("	and DATEDIFF(day,?,t.start_time) < 0  ");
        	params.add(getEndTime());
        }
        sql.append("	)t	");
	    sql.append("		on u.id = t.user_id	");
			
		
		List<Object[]> list = commonDao.queryBySql(sql.toString(),params);
		if(!CollectionUtils.isEmpty(list)){
			for (Object[] objAry : list) {
				int userId = DataType.getAsInt(objAry[0]);
				List<TaskInfo> tasks = userTaskMap.get(userId);
				if(null == tasks){
					tasks = new ArrayList<TaskInfo>();
					userTaskMap.put(userId, tasks);
				}
				TaskInfo taskInfo = new TaskInfo();
				taskInfo.setUserId(userId);
				taskInfo.setTaskType(DataType.getAsInt(objAry[1]));
				taskInfo.setShopId(DataType.getAsInt(objAry[2]));
				
				tasks.add(taskInfo);
			}
		}
		
		return userTaskMap;
	}

	private List<TaskInfo> queryNotCreateTaskInfosWithRelatedShop(
			Map<Integer, List<TaskInfo>> userTaskMap,
			List<BWorkType> workTypeList) {
		List<TaskInfo> result = new ArrayList<TaskInfo>();
		//查询用户下的门店信息
		Map<Integer, Map<Integer,BShopInfo>> userShopMap = queryUserShopMap();
		Map<Integer, Map<Integer, List<TaskInfo>>> userShopTaskMap = merger(userShopMap,userTaskMap);
		List<BWorkType> filterWorkType = filterWorkType(workTypeList,true);
		for (BWorkType bWorkType : filterWorkType) {
			for (java.util.Map.Entry<Integer, Map<Integer, List<TaskInfo>>> entry : userShopTaskMap.entrySet()) {
				Map<Integer, List<TaskInfo>> shopTaskMap = entry.getValue();
				Integer userId = entry.getKey();
				
				for (java.util.Map.Entry<Integer, List<TaskInfo>> shopTask : shopTaskMap.entrySet()) {
					List<TaskInfo> tasks = shopTask.getValue();
					
					//如果OC下没有创建任何任务,则历遍所有的门店
					if(CollectionUtils.isEmpty(tasks)){
						Map<Integer, BShopInfo> shopMap = userShopMap.get(userId);
						if(!CollectionUtils.isEmpty(shopMap)){
							for (Entry<Integer, BShopInfo> shopEntry : shopMap.entrySet()) {
								TaskInfo taskInfo1 = new TaskInfo();
								taskInfo1.setUserId(userId);
								taskInfo1.setShopRelated(true);
								taskInfo1.setShopName(shopEntry.getValue().getShopname());
								taskInfo1.setTaskTypeName(bWorkType.getTypename());	
								taskInfo1.setFrequence(bWorkType.getFrequency());	
								
								result.add(taskInfo1);
							}
						}
						
						continue;
					}
					
					boolean find = false;
					for (TaskInfo tinfo : tasks) {
						if(tinfo.getTaskType() == bWorkType.getId()){
							find = true;
							continue;
						}
					}
					if(!find){
						TaskInfo taskInfo1 = new TaskInfo();
						taskInfo1.setUserId(entry.getKey());
						taskInfo1.setShopRelated(true);
						BShopInfo shopInfo = userShopMap.get(entry.getKey()).get(shopTask.getKey());
						taskInfo1.setShopName(shopInfo == null ? null : shopInfo.getShopname());
						taskInfo1.setTaskTypeName(bWorkType.getTypename());	
						taskInfo1.setFrequence(bWorkType.getFrequency());	
						
						result.add(taskInfo1);
					}
				}
			}
		}
		return result;
	}

	/**
	 *  合并成用户门店
	 * @param userShopMap
	 * @param userTaskMap
	 * @return
	 */
	private Map<Integer, Map<Integer, List<TaskInfo>>> merger(
			Map<Integer, Map<Integer, BShopInfo>> userShopMap,
			Map<Integer, List<TaskInfo>> userTaskMap) {
		Map<Integer, Map<Integer, List<TaskInfo>>> result = new HashMap<Integer, Map<Integer,List<TaskInfo>>>();
		
		if(!CollectionUtils.isEmpty(userShopMap)){
			Map<Integer, Map<Integer, List<TaskInfo>>> map = buildUserShopTaskMap(userTaskMap);
			for (java.util.Map.Entry<Integer, Map<Integer, BShopInfo>> entry : userShopMap.entrySet()) {
				int userId = entry.getKey();
				Map<Integer, List<TaskInfo>> shopTaskMap = result.get(userId);
				if(shopTaskMap == null){
					shopTaskMap = new HashMap<Integer, List<TaskInfo>>();
					result.put(userId, shopTaskMap);
				}
				Map<Integer, List<TaskInfo>> shopTasks = map.get(userId);
				if(!CollectionUtils.isEmpty(shopTasks)){
					int shopId = entry.getKey();
					List<TaskInfo> tasks = shopTasks.get(shopId);
					shopTaskMap.put(shopId, tasks == null ? new ArrayList<TaskInfo>() : tasks);
				}
			}
		}
		return result;
	}

	private Map<Integer, Map<Integer, List<TaskInfo>>> buildUserShopTaskMap(
			Map<Integer, List<TaskInfo>> userTaskMap) {

		Map<Integer, Map<Integer, List<TaskInfo>>> map = new HashMap<Integer, Map<Integer,List<TaskInfo>>>();
		if(!CollectionUtils.isEmpty(userTaskMap)){
			for (java.util.Map.Entry<Integer, List<TaskInfo>> entry : userTaskMap.entrySet()) {
				int userId = entry.getKey();
				Map<Integer, List<TaskInfo>> shopTaks1 = map.get(userId);
				if(shopTaks1 == null){
					shopTaks1 = new HashMap<Integer, List<TaskInfo>>();
					map.put(userId, shopTaks1);
				}
				
				List<TaskInfo> tasks = entry.getValue();
				if(!CollectionUtils.isEmpty(tasks)){
					for (TaskInfo taskInfo : tasks) {
						Integer shopId = taskInfo.getShopId();
						if(shopId != null){
							List<TaskInfo> tasks1 =shopTaks1.get(shopId);
							if(tasks1 == null){
								tasks1 = new ArrayList<TaskInfo>();
								shopTaks1.put(shopId, tasks1);
							}
							tasks1.add(taskInfo);
							
						}
					}
				}
			}
		}
		
		return map;
	}

	private Map<Integer, Map<Integer, BShopInfo>> queryUserShopMap() {
		Map<Integer, Map<Integer, BShopInfo>> userShopMap = new HashMap<Integer, Map<Integer,BShopInfo>>();
		
		List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        
       /* sql.append("	select u.id,s.id as shopId,s.shop_name from ");
        sql.append("	(select u.*		"
        		+ "				from bk_user u where u.id=392) u	")
			.append("	left join bk_shop_user su  on u.id = su.user_id		")
			.append("	left join bk_shop_info s on su.shop_id = s.id	");*/
        
        sql.append("	select DISTINCT u.id,s.id as shop_id,s.shop_name	")
        	.append("	from bk_shop_user su	")
			.append("	left join bk_user u on su.user_id = u.id		")
			.append("	left join bk_shop_info s on su.shop_id = s.id	")
			.append("	where u.id is not null and s.id is not null");
		
        List<Object[]> list = commonDao.queryBySql(sql.toString(),params);
        if(!CollectionUtils.isEmpty(list)){
        	for (Object[] objAry : list) {
				int userId = DataType.getAsInt(objAry[0]);
				int shopId = DataType.getAsInt(objAry[1]);
				String shopName = DataType.getAsString(objAry[2]);
				
				Map<Integer, BShopInfo> shopMap = userShopMap.get(userId);
				if(shopMap == null){
					shopMap = new HashMap<Integer, BShopInfo>();
					userShopMap.put(userId, shopMap);
				}
				
				BShopInfo shopEntity = new BShopInfo();
				shopEntity.setId(shopId);
				shopEntity.setShopname(shopName);
				shopMap.put(shopId, shopEntity);
			}
        }
		return userShopMap;
	}

	/**
	 * 查询没有创建的任务(此任务未与门店关联)
	 * @param userTaskMap
	 * @param workTypeList
	 * @return
	 */
	private List<TaskInfo> queryNotCreateTaskInfosWithNotRelatedShop(
			Map<Integer, List<TaskInfo>> userTaskMap,
			List<BWorkType> workTypeList) {
		List<TaskInfo> result = new ArrayList<TaskInfo>();
		List<BWorkType> filterWorkType = filterWorkType(workTypeList,false);
		for (java.util.Map.Entry<Integer, List<TaskInfo>> taskInfo : userTaskMap.entrySet()) {
			for (BWorkType bWorkType : filterWorkType) {
				List<TaskInfo> taskInfos = taskInfo.getValue();
				boolean find = false;
				for (TaskInfo tinfo : taskInfos) {
					if(tinfo.getTaskType() == bWorkType.getId()){
						find = true;
						continue;
					}
				}
				if(!find){
					TaskInfo taskInfo1 = new TaskInfo();
					taskInfo1.setUserId(taskInfo.getKey());
					taskInfo1.setShopRelated(false);
					taskInfo1.setTaskTypeName(bWorkType.getTypename());	
					taskInfo1.setFrequence(bWorkType.getFrequency());
					result.add(taskInfo1);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param workTypeList
	 * @return
	 */
	private List<BWorkType> filterWorkType(
			List<BWorkType> workTypeList,boolean related) {
		List<BWorkType> list = new ArrayList<BWorkType>();
		if(!CollectionUtils.isEmpty(workTypeList)){
			int relatedVal = related ? 0 : 1;
			for (BWorkType bWorkType : workTypeList) {
				if(bWorkType.getIsToStore()== relatedVal){
					list.add(bWorkType);
				}
			}
		}
		return list;
	}

	/**
	 * 子类该重写
	 * @return
	 */
	protected int getWorkType(){
		return 0;
	}
	/**
	 * 子类该重写
	 * @return
	 */
	protected Date getStartTime(){
		throw new NullPointerException("调度起始时间不能为空");
	}
	/**
	 * 子类该重写
	 * @return
	 */
	protected Date getEndTime(){
		throw new NullPointerException("调度结束时间不能为空");
	}
	
	
}
