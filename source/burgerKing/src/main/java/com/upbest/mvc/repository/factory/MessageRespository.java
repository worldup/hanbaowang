package com.upbest.mvc.repository.factory;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BMessage;

public interface MessageRespository extends PagingAndSortingRepository<BMessage, Serializable>{

    Page<BMessage> findByUserId(Integer id, Pageable pageable);
    
//    BMessage findById(String id);
}
