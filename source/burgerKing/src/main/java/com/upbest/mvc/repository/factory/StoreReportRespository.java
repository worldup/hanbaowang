package com.upbest.mvc.repository.factory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BShopReport;

public interface StoreReportRespository extends PagingAndSortingRepository<BShopReport, Serializable> {
    
   /* Page<BShopUser> findByShopnameLike(String shopName, Pageable pageable);
    
    BShopReport findByReport(String name);*/
	
	@Query("select r from BShopReport r where r.mon >= ?1 and r.mon < ?2 and r.shopId = ?3")
	List<BShopReport> findReportInfo(Date startTime,Date endTime,int shopId);
	
	@Query("select r from BShopReport r where r.mon >= ?1 and r.shopId = ?2")
	List<BShopReport> findReportInfoGt(Date startTime,int shopId);
	
	@Query("select r from BShopReport r where r.mon < ?1 and r.shopId = ?2")
	List<BShopReport> findReportInfoLt(Date endTime,int shopId);
	
	@Query("select r from BShopReport r where r.shopId = ?1")
	List<BShopReport> findReportInfo(int shopId);
}
