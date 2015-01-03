package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BExaminationAssign;

public interface ExaminationAssignRespository extends JpaRepository<BExaminationAssign, Integer> {
    BExaminationAssign findByUseridAndEid(Integer userId, Integer eId);
    
    List<BExaminationAssign> findByEid(Integer eId);
}
