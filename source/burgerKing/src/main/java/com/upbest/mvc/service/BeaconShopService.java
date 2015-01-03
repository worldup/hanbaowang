package com.upbest.mvc.service;

import java.util.List;

import com.upbest.mvc.entity.BBeaconShop;

public interface BeaconShopService {

    public void saveBeaconShop(BBeaconShop beaconShop);
    
    public List<Object[]> findByBeaconid(Integer beaconId);
    
    public void deleteById(Integer id);
    
    void saveBeaconShop(String ids,String shopId);
}
