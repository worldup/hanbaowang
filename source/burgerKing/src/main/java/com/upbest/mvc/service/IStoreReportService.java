package com.upbest.mvc.service;

import java.util.List;

import com.upbest.mvc.entity.BShopReport;


public interface IStoreReportService {

    void saveBShop(BShopReport entity);
    
    void saveBShop(List<BShopReport> list);
}
