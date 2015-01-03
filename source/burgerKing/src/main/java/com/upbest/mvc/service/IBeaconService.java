package com.upbest.mvc.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BBeacon;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BeaconVO;
import com.upbest.mvc.vo.TreeVO;



public interface IBeaconService {
    public void addBeaconFromExcel(Resource byteArrayResource) throws Exception;
    
    Page<Object[]> queryBeacon(String beaconName, Pageable pageable);

    List<Object[]> queryBeaconById(Integer id);
    
    //
    List<Object[]> queryBeaconByUuid(String uuid);
    
    public void saveBeacon(BBeacon beacon);
    
    public void deleteBeacon(Integer beaconId);
    
    public BBeacon findByUuid(String uuid);
    
    List<TreeVO> getUrVOList();
    
    public BShopInfo findStoreByUuid(String uuid);
    
    
}
