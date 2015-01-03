package com.upbest.mvc.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BProblemAnalysis;
import com.upbest.mvc.repository.factory.BProblemAnalysisRespository;
import com.upbest.mvc.service.IBProblemAnalysisService;

@Service
public class BProblemAnalysisServiceImpl implements IBProblemAnalysisService {

    @Inject
    private BProblemAnalysisRespository bProblemAnalysisRespository;

    @Override
    public BProblemAnalysis saveOrUpdate(BProblemAnalysis entity) {
        return bProblemAnalysisRespository.save(entity);
    }

    @Override
    public List<BProblemAnalysis> queryByTId(Integer tId) {
        return bProblemAnalysisRespository.findByTId(tId);
    }

    
   
}
