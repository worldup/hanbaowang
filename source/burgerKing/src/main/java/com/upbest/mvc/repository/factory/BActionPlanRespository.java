package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BActionPlan;

public interface BActionPlanRespository extends JpaRepository<BActionPlan, Integer> {
    
    List<BActionPlan> findByTId(Integer tId);
    
}
