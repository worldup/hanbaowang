package com.upbest.mvc.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.StoreStatisticRespository;
import com.upbest.mvc.repository.factory.StoreTarRespository;
import com.upbest.mvc.repository.factory.StoreUserRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IStoreService;

/**
 * 每天凌晨更新一次门店基本信息表
 * @author Administrator
 *
 */
@Component
public class ImportShopInfo implements SchedulerTask {
	
    @Autowired
    private StoreRespository storeRespository;
    
    @Autowired 
    private StoreStatisticRespository storeStsRes;
    
    @Autowired 
    private StoreUserRespository suRes;
    
    @Autowired
    private CommonDaoCustom<Object[]> common;
    
    @Autowired
    private IStoreService service;
    
	@Override
	public void scheduler() throws Exception {
	 service.updateStoreInfo();
	}
}
