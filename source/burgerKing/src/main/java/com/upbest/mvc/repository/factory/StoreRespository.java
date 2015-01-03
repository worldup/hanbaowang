package com.upbest.mvc.repository.factory;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.Buser;

public interface StoreRespository extends PagingAndSortingRepository<BShopInfo, Serializable> {
    
    Page<BShopInfo> findByShopnameLike(String shopName, Pageable pageable);
    
    BShopInfo findByShopname(String name);
    
    BShopInfo findByShopnum(String shopnum);
}
