package com.upbest.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.Buser;

public interface IMessageService {
   Page<Object[]> getInstanceMessages(Integer userId,Integer receiveId, String messageType, Pageable requestPage);
    Page<Object[]> findMessageList(String name,String type,Integer userId, Pageable requestPage);
    
    List<Object[]> findMessageById(String id);
    
    List<Buser> editeMessageById(List<String> name);
    
    BMessage findById(Integer id);
    
    BMessage saveBMessage(BMessage message);
    
    void deleteById(Integer id);
    
    List<String> getUserIds(String messageId);

//    void push(String id, String ids, BMessage message, Buser buser) throws Exception;
    
    Map<String,Integer> getCountByUserId(Integer userId);
    
    Page<Object[]> findMessageListAPI(String type,Integer userId, Integer receiveId,Pageable requestPage);

	void updateNoticeMessageStatu(Integer uid, Integer bid);

}
