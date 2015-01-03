package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BBeaconShop;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.repository.factory.BeaconShopRespository;
import com.upbest.mvc.service.BeaconShopService;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.utils.DataType;

@Service
public class BeaconShopServiceImpl implements BeaconShopService {
    
    @Inject
    BeaconShopRespository beaconShopRespository;
    
    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Override
    public void saveBeaconShop(BBeaconShop beaconShop) {
        beaconShopRespository.save(beaconShop);
    }

    @Override
    public List<Object[]> findByBeaconid(Integer beaconId) {
        String sql = "select b.id, b.beacon_id, b.shop_id from bk_beacon_shop b where beacon_id = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(beaconId);
        return common.queryBySql(sql, params);
    }

    @Override
    public void deleteById(Integer id) {
        beaconShopRespository.delete(id);
    }

    @Override
    public void saveBeaconShop(String ids, String shopId) {
        if(StringUtils.isNotBlank(ids)){
            String[] idsArray = ids.split(",");
            for(int i=0;i<idsArray.length;i++){
                BBeaconShop shopUser=new BBeaconShop();
                shopUser.setBeaconId(DataType.getAsInt(idsArray[i]));
                shopUser.setShopId(DataType.getAsInt(shopId));
                beaconShopRespository.save(shopUser);
            }
        }
    }

}
