package com.upbest.mvc.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BUpdate;

public interface IUpdateService {
    
    Page<Object[]> findUpdateList(Pageable requestPage, String versionNum);
    
    BUpdate findById(Integer id);
    
    void saveAPkInfo(BUpdate update);
    
    void delAPKInfoById(Integer id);
    
    BUpdate findLatest();
    
//    void addUpdateToAPK(Integer[] updateId, OutputStream out) throws Exception;
}
