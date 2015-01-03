package com.upbest.mvc.handler;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.upbest.mvc.entity.BBeacon;
import com.upbest.mvc.entity.BBeaconShop;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.repository.factory.BeaconRespository;
import com.upbest.mvc.repository.factory.BeaconShopRespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.utils.DataType;

@Component
public class BeaconHandler implements Handler{

    @Autowired
    private BeaconRespository respository;
    
    @Autowired
    private StoreRespository storeRespository;
    
    @Autowired
    private BeaconShopRespository bStoreRespository;

    @Override
    public boolean handerRow(String[] rows) throws Exception {
        if(rows != null){
            BBeacon beacon = buildBeacon(rows);
            respository.save(beacon);
        }
        return true;
    }
        
    private BBeacon buildBeacon(String[] rows) {
        String uuid = DataType.getAsString(rows[0]);
        BBeacon beacon = getBeacon(uuid);
        beacon.setUuid(uuid);
        beacon.setMajorId(DataType.getAsString(rows[1]));
        beacon.setMinorId(DataType.getAsString(rows[2]));
        beacon.setName(DataType.getAsString(rows[3]));
        beacon.setMac(DataType.getAsString(rows[4]));
        beacon.setMeasurrdPower(DataType.getAsString(rows[5]));
        beacon.setSignalStrength(DataType.getAsString(rows[6]));
        beacon.setRemainingPower(DataType.getAsString(rows[7]));
        beacon.setCompany(DataType.getAsString(rows[8]));
        beacon.setModel(DataType.getAsString(rows[9]));
        beacon.setMold(DataType.getAsString(rows[10]));
        beacon.setDistance(DataType.getAsString(rows[11]));
        beacon.setRemarks(DataType.getAsString(rows[12]));
        Date date=new Date();
        beacon.setLastDate(date);
        beacon= respository.save(beacon);
        String shopname = rows[13];
        if(StringUtils.hasText(shopname)){
            BShopInfo shopInfo = null;
            try {
                shopInfo = storeRespository.findByShopname(shopname);
                if(shopInfo != null){
                   BBeaconShop beaconShop= bStoreRespository.findByBeaconId(beacon.getId());
                   if(beaconShop!=null)
                   {
                       beaconShop.setShopId(shopInfo.getId());
                   }
                   else
                   {
                       beaconShop=new BBeaconShop();
                       beaconShop.setShopId(shopInfo.getId());
                       beaconShop.setBeaconId(beacon.getId());
                   }
                    
                    bStoreRespository.save(beaconShop);
                }
            } catch (Exception e) {
            }
            
        }
        return beacon;
    }
    
    private BBeacon getBeacon(String uuid) {
        BBeacon beacon = respository.findByUuid(uuid);
        if(beacon == null){
            beacon = new BBeacon();
        }
        return beacon;
    }
}
