package com.upbest.mvc.service;

import java.util.Map;

import com.upbest.mvc.entity.Buser;

public interface PushMessageServiceI {

	String push(String id, String ids, Buser buser) throws Exception;
    
    /**
     * 
     * @param id
     * @param ids
     * @param buser
     * @param pushTitle 	推送标题
     * @param pushContent   推送内容
     * @throws Exception
     */
	String push(String id, String ids, Buser buser,String pushTitle,String pushContent,Map<String, String> extra) throws Exception;
}
