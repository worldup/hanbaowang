package com.upbest.mvc.repository.factory;

import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BMessage;

public interface PushMessageRepository extends PagingAndSortingRepository<BMessage, Serializable> {

    
}
