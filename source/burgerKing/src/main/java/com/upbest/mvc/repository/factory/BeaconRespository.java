package com.upbest.mvc.repository.factory;


import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BBeacon;


public interface BeaconRespository extends JpaRepository<BBeacon, Integer> {

    public BBeacon findByUuid(String uuid);
}
