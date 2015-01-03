package com.upbest.mvc.repository.factory;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BShopStatistic;

public interface StoreStatisticRespository extends JpaRepository<BShopStatistic, Integer> {
	public BShopStatistic findByShopId(String shopid);
	public List<BShopStatistic> findByShopIdIn(Collection<String> shopId);
	List<BShopStatistic> findByShopIdIn(Collection<String> shopId,Sort sort);
	/**
	 * 根据门店号与月份进行查询
	 * @param shopid
	 * @param month
	 * @return
	 */
	public BShopStatistic findByShopIdAndMonth(String shopid,Date month);
}
