package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BExBaseInfo;

public interface BExBaseInfoRespository extends JpaRepository<BExBaseInfo, Integer> {
    
    List<BExBaseInfo> findByBvalueLike(String name);
    
    List<BExBaseInfo> findByBrushElection(int brushElection);
}
