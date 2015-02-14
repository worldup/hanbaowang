package com.upbest.mvc.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BshopStatisticVO;
import com.upbest.mvc.vo.TaskDetailVO;

public interface IStoreService {

    BShopInfoVO findByAreaId(Integer id);
    
    BShopInfoVO findById(Integer id);

    Page<Object[]> findShopList(String shopName, Integer userId,String userRole,String regional, Pageable requestPage);
    Page<Object[]> findShopList(String shopName, Integer userId,String realName, Pageable requestPage);

    List<BShopInfoVO> getShopInfoVOList(String shopName, Integer userId, Pageable requestPage);
    List<BShopInfoVO> getShopInfoVOList(Integer userId,String shopName);

    BShopInfo saveBShop(BShopInfo entity);

    void deleteById(Integer id);

    List<String> getUserIds(String shopId);

    void deleteUserId(Integer id);

    public void addShopFromExcel(File file) throws Exception;

    public void addShopFromExcel(Resource byteArrayResource) throws Exception;
    
    List<BShopInfo> findAllShop();

    void updateLngLat(Integer id, String longitude, String latitude);

	Object queryReportInfo(Integer shopid, Date startTime, Date endTime);

	/**
	 * 
	 * @param shopid
	 * @param examTypeId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Object queryUserTestInfo(Integer shopid, Integer examTypeId,
			Date startTime, Date endTime);
	
	/**
	 * 查询用户下的门店
	 * @param userId
	 * @return
	 */
	List<BShopInfo> queryShopInfoByUserId(Integer userId);

	List<BshopStatisticVO> queryStatisticInfo(Integer userId);
	
	List<TaskDetailVO> queryTaskDetails(String userId,String workTypeId,String year,String sord,String month,String quarter);
	
	void updateStoreInfo();

	BShopInfo queryEntityById(Integer id);
	
	BShopInfo findByShopNum(String shopNum);
    //地图小黑框
    Map<String ,Object > findStoreMapBaseInfo(String shopId);
    //构造门店下拉列表
    List<Map<String,Object>> listShop4Combobox();
}
