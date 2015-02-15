package com.upbest.mvc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.StoreUserRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.service.IStoreUserService;
import com.upbest.utils.DataType;

@Service
public class StoreUserServiceImpl implements IStoreUserService {

    @Inject
    protected StoreUserRespository storeUrRepository;

    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Autowired
    private IMessageService messageService;

    @Override
    public void saveBShopUser(String ids,String shopId) {
        if(StringUtils.isNotBlank(ids)){
            String[] idsArray = ids.split(",");
            for(int i=0;i<idsArray.length;i++){
                BShopUser shopUser=new BShopUser();
                shopUser.setShopId(DataType.getAsInt(shopId));
                shopUser.setUserId(DataType.getAsInt(idsArray[i]));
                storeUrRepository.save(shopUser);
            }
        }
    }

    @Override
    public void saveBMessageUser(String receiverId, String messageId,Buser buser) {
        if(StringUtils.isNotBlank(receiverId)){
            BMessage message = messageService.findById(Integer.parseInt(messageId));
            message.setId(null);
            message.setReceiverId(DataType.getAsInt(receiverId));
            message.setState("1");
            message.setPushTime(new Date());//推送时间修改
            message.setSenderId(buser == null ? null : buser.getId());//发送人修改
            
            messageService.saveBMessage(message);
        }
    }


}
