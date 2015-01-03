package com.upbest.mvc.repository.factory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BUpdate;

public interface UpdateRespository extends PagingAndSortingRepository<BUpdate, Integer>{
    
//    @Query("select * from Bk_update u where 1=1 order by u.verion_num desc, u.update_time desc ")
//    BUpdate findByOrderByVersionNumDescUpdateTimeDesc();
}
