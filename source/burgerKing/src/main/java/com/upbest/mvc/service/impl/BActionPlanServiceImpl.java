package com.upbest.mvc.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BActionPlan;
import com.upbest.mvc.repository.factory.BActionPlanRespository;
import com.upbest.mvc.service.IBActionPlanService;

@Service
public class BActionPlanServiceImpl implements IBActionPlanService {

    @Inject
    private BActionPlanRespository bActionPlanRespository;

    @Override
    public BActionPlan saveOrUpdate(BActionPlan entity) {
        return bActionPlanRespository.save(entity);
    }

    @Override
    public List<BActionPlan> queryByTId(Integer tId) {
        return bActionPlanRespository.findByTId(tId);
    }

}
