package com.upbest.mvc.service;


import java.io.File;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BFacility;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.vo.BFacilityVO;
import com.upbest.utils.PageModel;

public interface IFacilityService {
	/**
	 * 
	 * @param facility
	 */
	public void saveFacility(BFacility facility);
	
	public void deleteFacility(int facilityId);
	
	public void deleteFacilities(List<Integer> facilityIds);
	
	public List<BFacility> queryFacility();
	
	public BFacility queryFacility(Integer facilityId);
	
	public PageModel<BFacilityVO> queryFacility(String facilityName,Pageable pageable);
	
	public void updateFacility(BFacility facility);
	
	public BFacility queryByDevIdAndUser(String devid,Buser user);
	
	public void addFacilityFromExcel(File file) throws Exception;

	public void addFacilityFromExcel(Resource byteArrayResource) throws Exception;
	
	public void addFacilituToExcel() throws Exception;
}
