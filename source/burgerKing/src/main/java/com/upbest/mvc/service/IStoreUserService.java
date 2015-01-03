package com.upbest.mvc.service;

import com.upbest.mvc.entity.Buser;


public interface IStoreUserService {

    void saveBShopUser(String ids,String shopId);

    void saveBMessageUser(String ids,String messageId, Buser buser);
}
