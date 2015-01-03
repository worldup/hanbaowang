package com.upbest.mvc.handler;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.BArearespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.StoreUserRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.utils.DataType;

@Component
public class ShopHandler implements Handler {

    @Autowired
    private UserRespository userRespository;
    
    @Autowired
    private StoreRespository storeRespositoy;
    
    @Autowired
    private StoreUserRespository storeUserRespositoy;
    
    @Autowired
    private BArearespository areaRespository;
    
    
    @Override
    public boolean handerRow(String[] rows) throws Exception {
        
        if(rows != null){
            String Shopnum=DataType.getAsString(rows[3]);
            BShopInfo shop=getUserByShopnum(Shopnum);
            
            BArea regional = getRegional(DataType.getAsString(rows[10]));
            BArea province = getProvince(regional,DataType.getAsString(rows[11]));
            BArea prefecture = getPrefecture(province,DataType.getAsString(rows[12]));
            BArea district = getDistrict(prefecture,DataType.getAsString(rows[14]));
            
            if(province == null && prefecture != null && prefecture.getLevel() == 1){
            	province = prefecture;
            }
            
            shop.setShopname(DataType.getAsString(rows[0]));
            shop.setShopphone(DataType.getAsString(rows[1]));
            shop.setShopaddress(DataType.getAsString(rows[2]));
            shop.setShopnum(Shopnum);
            shop.setShopsize(DataType.getAsString(rows[4]));
            shop.setShopopentime(DataType.getAsDate(rows[5]));
            shop.setShopbusinesstime(DataType.getAsString(rows[6]));
            if(shop.getShopseatnum()==null)
            {
                shop.setShopseatnum(0);
            }else
            {
                shop.setShopseatnum(DataType.getAsInt(rows[7]));
            }
            shop.setShopbusinessarea(DataType.getAsString(rows[8]));
            shop.setRegional(buildAreaInfo(regional));
            shop.setProvince(buildAreaInfo(province));
            shop.setPrefecture(buildAreaInfo(prefecture));
            shop.setCityGrade(DataType.getAsString(rows[13]));
            shop.setDistrict(buildAreaInfo(district));
            shop.setMold(DataType.getAsString(rows[15]));
            shop.setBusinessCircle(DataType.getAsString(rows[16]));
            shop.setCircleSize(DataType.getAsString(rows[17]));
            shop.setSubwayNumber(DataType.getAsString(rows[18]));
            shop.setAnExit(DataType.getAsString(rows[19]));
            shop.setMeters(DataType.getAsString(rows[20]));
            shop.setBusNumber(DataType.getAsString(rows[21]));
            shop.setCompetitor(DataType.getAsString(rows[22]));
            shop.setMainCompetitors(DataType.getAsString(rows[23]));
            shop.setRivalsName(DataType.getAsString(rows[24]));
            shop.setKfcNumber(DataType.getAsString(rows[25]));
            shop.setMnumber(DataType.getAsString(rows[26]));
            shop.setPizzaNumber(DataType.getAsString(rows[27]));
            shop.setStarbucksNumber(DataType.getAsString(rows[28]));
            shop.setUpdateTime(DataType.getAsDate(rows[29]));
            shop.setChineseName(DataType.getAsString(rows[30]));
            shop.setEnglishName(DataType.getAsString(rows[31]));
            shop.setChineseAddress(DataType.getAsString(rows[32]));
            shop.setEnglishAddress(DataType.getAsString(rows[33]));
            shop.setStraightJointJoin(DataType.getAsString(rows[34]));
           
            Date date = new Date();
            shop.setCreateTime(date);
            
            List<Buser> userList = userRespository.findByNameInAndIsdel(rows[9].split(","),"1");
            
            storeRespositoy.save(shop);
            if(userList != null)
            {
                for (Buser buser : userList) {
                    
                    BShopUser shopUser = new BShopUser();
                    shopUser.setUserId(buser.getId());
                    shopUser.setShopId(shop.getId());
                    storeUserRespositoy.save(shopUser);
                }
         }
            
    }
        return true;

 }
    private String buildAreaInfo(BArea regional) {
    	if(regional != null){
    		return regional.getId() + ":" + regional.getArea();
    	}
		return null;
	}
	private BArea getRegional(String regional) {
    	if(!StringUtils.hasText(regional)){
    		return null;
    	}
    	
    	List<BArea> areaList = areaRespository.findByAreaWithTopContext("%" + regional + "%");
		return CollectionUtils.isEmpty(areaList) ? null : areaList.get(0);
	}
	private BArea getProvince(BArea regional, String province) {
		if(!StringUtils.hasText(province)){
    		return null;
    	}
    	
		
		List<BArea> areaList = null;
		if(regional == null){
			areaList = areaRespository.findByAreaLike("%" + province + "%");
		}else{
			areaList = areaRespository.findByAreaWithContext(regional,"%" + province + "%");
		}
		return CollectionUtils.isEmpty(areaList) ? null : areaList.get(0);
	}
	
	private BArea getPrefecture(BArea province, String prefecture) {
		if(!StringUtils.hasText(prefecture)){
    		return null;
    	}
    	
		
		List<BArea> areaList = null;
		if(province == null){
			areaList = areaRespository.findByAreaLike("%" + prefecture + "%");
		}else{
			areaList = areaRespository.findByAreaWithContext(province,"%" + prefecture + "%");
		}
		return CollectionUtils.isEmpty(areaList) ? null : areaList.get(0);
	}
	
	private BArea getDistrict(BArea prefecture, String district) {
		if(!StringUtils.hasText(district)){
    		return null;
    	}
    	
		
		List<BArea> areaList = null;
		if(prefecture == null){
			areaList = areaRespository.findByAreaLike("%" + district + "%");
		}else{
			areaList = areaRespository.findByAreaWithContext(prefecture,"%" + district + "%");
		}
		return CollectionUtils.isEmpty(areaList) ? null : areaList.get(0);
	}
	private BShopInfo getUserByShopnum(String shopnum) {
        BShopInfo shop = storeRespositoy.findByShopnum(shopnum);
        if(shop == null){
            shop = new BShopInfo();
        }
        return shop;
    }
}
