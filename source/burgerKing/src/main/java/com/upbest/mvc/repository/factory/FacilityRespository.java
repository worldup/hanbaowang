package com.upbest.mvc.repository.factory;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BFacility;

public interface FacilityRespository extends PagingAndSortingRepository<BFacility, Integer> {
	public BFacility findByDeviceidAndUserid(String devId,int userid);
	public BFacility findByDeviceid(String devId);
}
