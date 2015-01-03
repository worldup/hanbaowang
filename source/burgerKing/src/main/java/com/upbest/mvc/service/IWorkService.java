package com.upbest.mvc.service;

import java.util.List;

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
}
