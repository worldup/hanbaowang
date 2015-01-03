package com.upbest.mvc.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BShopReport;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.repository.factory.StoreReportRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IStoreReportService;
import com.upbest.mvc.service.IStoreUserService;
import com.upbest.utils.DataType;

@Service
public class StoreReportServiceImpl implements IStoreReportService {

    @Inject
    protected StoreReportRespository storeShopRepository;

    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Override
    public void saveBShop(BShopReport entity) {
        storeShopRepository.save(entity);
    }

    @Override
    public void saveBShop(List<BShopReport> list) {
        storeShopRepository.save(list);

    }

  
}
