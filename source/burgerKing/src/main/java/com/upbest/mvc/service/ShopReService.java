package com.upbest.mvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ShopReService {
    Page<Object[]> findReList(String reName, Integer shopId, Pageable requestPage);
    
    void deleteById(Integer id);

}
