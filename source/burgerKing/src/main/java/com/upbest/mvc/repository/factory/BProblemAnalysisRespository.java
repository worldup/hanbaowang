package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BProblemAnalysis;

public interface BProblemAnalysisRespository extends JpaRepository<BProblemAnalysis, Integer> {
    
    List<BProblemAnalysis> findByTId(Integer tId);
    
}
