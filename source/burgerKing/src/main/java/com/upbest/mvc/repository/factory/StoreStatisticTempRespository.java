package com.upbest.mvc.repository.factory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BShopStatisticTemp;

public interface StoreStatisticTempRespository extends
		JpaRepository<BShopStatisticTemp, Integer> {
}
