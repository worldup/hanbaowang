package com.upbest.mvc.service;


import java.util.Date;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BShopStatistic;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.vo.ShopAreaVO;
import com.upbest.mvc.vo.ShopStatisticVO;

public interface IShopStatisticService {	
	
	 Page<Object[]> findShopStatistic(Buser buser, String sales,String mon,Pageable pageable);
	
	public Object addShopStatisticFromExcel(Resource byteArrayResource) throws Exception;
	
	/**
	 * 根据门店号进行查询
	 * @param shopId
	 * @return
	 */
	public BShopStatistic queryShopStatistic(String shopId);

	/**
	 * 
	 * @param examType 问卷类型
	 * @param sortField 
	 * @param endTime 
	 * @param startTime 
	 * @param fields 
	 * @param sortInfo 排序信息 ["sales,desc","tc,asc"]
	 * @return
	 */
//	public List<BShopStatistic> queryShopStatistic(String[] examType, String[] sortInfo);

	/**
	 * 
	 * @param userId 对应的角色为OM
	 * @param examType
	 * @param shopArea
	 * @param startTime
	 * @param endTime
	 * @param sortField
	 * @return
	 */
	public List<ShopStatisticVO> queryShopStatistic(Integer userId,Integer[] examType, ShopAreaVO shopArea, Date startTime, Date endTime, String sortField);
	
	public  List<ShopStatisticVO> queryShopStatistic(Integer shopId,String year);
	
}
