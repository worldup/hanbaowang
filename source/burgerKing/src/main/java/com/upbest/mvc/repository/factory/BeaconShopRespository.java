package com.upbest.mvc.repository.factory;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BBeaconShop;

public interface BeaconShopRespository extends PagingAndSortingRepository<BBeaconShop, Integer> {
    BBeaconShop findByBeaconId(Integer beanconId);
}
