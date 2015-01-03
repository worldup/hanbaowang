package com.upbest.mvc.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BWorkInfo;
import com.upbest.mvc.repository.factory.MessageRespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.TaskRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.service.PushMessageServiceI;
import com.upbest.utils.DataType;

/**
 * 
 * @ClassName   类  名   称：	CheckTodayTask.java
 * @Description 功能描述：	，会对当天已经添加在日历中的，还没有提交完成的任务，做任务提醒
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年11月10日下午7:36:29
 */
@Component
public class CheckTodayTask implements SchedulerTask {

    @Autowired
    private TaskRespository taskRespository;

    @Autowired
    private WorkRespository workRespository;

    @Autowired
    private MessageRespository messageRespository;

    @Autowired
    protected PushMessageServiceI pushMessageService;

    @Autowired
    protected StoreRespository storeRes;
    
    public CheckTodayTask(){
    }

    @Override
    public void scheduler() throws Exception {
        List<BWorkInfo> list = taskRespository.queryUnDoneTaskCount();
        if (list.size() > 0) {
            pushMessage(list);
        }
    }

    private void pushMessage(List<BWorkInfo> works) throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        if (CollectionUtils.isEmpty(works)) {
            return;
        }
        List<BMessage> messages = new ArrayList<BMessage>();
        for (BWorkInfo taskInfo : works) {
            // 日期时间，门店，任务名称 ，任务描述
            if (StringUtils.isBlank(map.get(taskInfo.getExecuteid()))) {
                map.put(taskInfo.getExecuteid(), "");
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(taskInfo.getStarttime());
            // 日期时间
            map.put(taskInfo.getExecuteid(), map.get(taskInfo.getExecuteid()) + cal.get(Calendar.YEAR) + "年" + DataType.getAsInt(cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH)
                    + "日，");
            // 门店
            BShopInfo entity = null;
            if (taskInfo.getStoreid().intValue() != 0) {
                entity = storeRes.findOne(taskInfo.getStoreid().intValue());
            }
            if (entity != null) {
                map.put(taskInfo.getExecuteid(), map.get(taskInfo.getExecuteid()) + entity.getShopname() + "，");
            }
            // 任务名称
            map.put(taskInfo.getExecuteid(), map.get(taskInfo.getExecuteid()) + workRespository.findOne(taskInfo.getWorktypeid()).getTypename() + "，");
            // 任务描述
            map.put(taskInfo.getExecuteid(), map.get(taskInfo.getExecuteid()) + taskInfo.getContent() + "\n");
        }
        if (!CollectionUtils.isEmpty(map)) {
            for (Entry<Integer, String> entry : map.entrySet()) {
                if (StringUtils.isNotBlank(entry.getValue())) {
                    BMessage message = new BMessage();
                    message.setMessageType("3");
                    message.setPushTitle(getTitleTip() + "：\n" + entry.getValue());
                    message.setPushContent(getTitleTip() + "：\n" + entry.getValue());
                    message.setCreateTime(new Date());
                    message.setIsRead("0");
                    message.setPushTime(new Date());
                    message.setReceiverId(entry.getKey());
                    
                    messages.add(message);
                    
                   /* if(message.getReceiverId() == 786 || message.getReceiverId() == 789){
                    	messages.add(message);
                    }*/
                }
            }
        }
        messageRespository.save(messages);
        String pushTitle = "任务通知";
        String pushContent = getCurrentDateStr() + ",任务提醒";
        for (BMessage bMessage : messages) {
        	Map<String, Object> extra = new HashMap<String, Object>();
        	extra.put("messageId", bMessage.getId());
            pushMessageService.push(bMessage.getId() + "", bMessage.getReceiverId() + "", null,pushTitle,pushContent,extra);
            break;
        }

    }

	protected String getTitleTip() {
		return "今天要做的任务";
	}

	private String getCurrentDateStr() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
	}
}
