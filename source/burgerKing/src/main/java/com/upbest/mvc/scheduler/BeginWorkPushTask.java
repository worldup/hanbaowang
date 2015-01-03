package com.upbest.mvc.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.BWorkInfo;
import com.upbest.mvc.repository.factory.MessageRespository;
import com.upbest.mvc.repository.factory.TaskRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.service.PushMessageServiceI;

/**
 * 检查任务是否已经开始，如果没有开始则进行消息推送
 * @author QunZheng
 *
 */
@Component
public class BeginWorkPushTask implements SchedulerTask {
	
	@Autowired
	private TaskRespository taskRespository;
	
	@Autowired
	private WorkRespository workRespository;
	
	@Autowired
	private MessageRespository messageRespository;
	
	@Autowired
	protected PushMessageServiceI pushMessageService;
	
	@Override
	public void scheduler() throws Exception {
		List<BWorkInfo> works = queryWillBeginWork();
		pushMessage(works);
		
	}

	private List<BWorkInfo> queryWillBeginWork() {
		DateTime dateTime = new DateTime();
		DateTime curBeginTime = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0);
		DateTime startTime = curBeginTime.plusDays(1);
		DateTime endTime = startTime.plusDays(1);
		
		return taskRespository.findStartTimeBetween(startTime.toDate(), endTime.toDate());
	}
	
	private void pushMessage(List<BWorkInfo> works) throws Exception {
		if(CollectionUtils.isEmpty(works)){
			return;
		}
		List<BMessage> messages = new ArrayList<BMessage>();
		for (BWorkInfo taskInfo : works) {
			BMessage message = new BMessage();
			message.setMessageType("3");
			message.setPushTitle(workRespository.findOne(taskInfo.getWorktypeid()).getTypename());
			message.setPushContent(taskInfo.getContent());
			message.setCreateTime(new Date());
//        message.setState("1");
			message.setIsRead("0");
			message.setUserId(taskInfo.getUserid());
			message.setPushTime(new Date());
			message.setSenderId(taskInfo.getUserid());
			message.setReceiverId(taskInfo.getExecuteid());
			message.setTaskId(taskInfo.getId());
		}
		
		messageRespository.save(messages);
		
		for (BMessage bMessage : messages) {
			/**
             * 额外数据
             */
            Map<String, Object> extra = new HashMap<String, Object>();
        	extra.put("messageId", bMessage.getId());
			pushMessageService.push(bMessage.getId() + "", bMessage.getReceiverId() + "", null,
							bMessage.getPushTitle(),bMessage.getPushContent(),extra);
		}
		
		
	}
}
