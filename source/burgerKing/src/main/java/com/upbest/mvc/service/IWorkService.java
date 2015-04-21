package com.upbest.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BWorkType;

public interface IWorkService {

    BWorkType findById(Integer id);

    void saveBWorkType(BWorkType entity);

    void deleteById(Integer id);

    Page<Object[]> findWorkList(String typeName, Pageable requestPage);

    List<BWorkType> findWork(String userId);
    
    List<BWorkType> findWorkWithOutNeeded(String userId);
    
    List<BWorkType> findWorkWithOutNeededAndQuarter(String userId);
    
    int getMaxSortNum();
    
    void saveBWorkType(List<BWorkType> list);
    String  getWorkPlan4Excel(String omUserId,String ocUserId,String month);
    List<Map<String,Object>> getAllWorkPlanByUserId(String userId,String month);
    void sendWorkPlanMailByUserId(String userId,String month);
    void sendWorkPlanMailByUserIdExt(String userId,String month,String emails);
}
