package com.upbest.mvc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.upbest.mvc.entity.BFacility;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.FacilityRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.utils.DataType;

@Component
public class FacilityHandler implements Handler {
	
	@Autowired
	private FacilityRespository respository;
	
	@Autowired
	private UserRespository userRespository;

	@Override
	public boolean handerRow(String[] rows) throws Exception {
		if(rows != null){
			BFacility facility = buildFacility(rows);
			respository.save(facility);
		}
		return true;
	}

	private BFacility buildFacility(String[] rows) {
		String deviceId = DataType.getAsString(rows[1]);
		BFacility facility = getFacility(deviceId);
		
		facility.setDeviceversion(DataType.getAsString(rows[0]));
		facility.setDeviceid(deviceId);
		facility.setDevicebrand(DataType.getAsString(rows[2]));
		facility.setDevicemodel(DataType.getAsString(rows[3]));
		facility.setDeviceos(DataType.getAsString(rows[4]));
		facility.setDeviceosversion(DataType.getAsInt(rows[5]));
		facility.setDeviceresolution(DataType.getAsString(rows[6]));
		
		String username = rows[7];
		if(StringUtils.hasText(username)){
			Buser user = null;
			try {
				user = userRespository.findByName(username);
				if(user != null){
					facility.setUserid(user.getId());
				}
			} catch (Exception e) {
			}
			
		}
		return facility;
	}

	private BFacility getFacility(String deviceId) {
		BFacility facility = respository.findByDeviceid(deviceId);
		facility = facility == null ? new BFacility() : facility;
		
		return facility;
	}

}
