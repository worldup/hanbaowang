package com.upbest.mvc.repository.factory;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BWorkType;

    
public interface WorkRespository extends PagingAndSortingRepository<BWorkType, Serializable> {
   Page<BWorkType> findByTypenameLike(String typeName, Pageable pageable);
    
   BWorkType findByTypename(String name);
   
   List<BWorkType> findByFrequency(int frequency);
   
  // @Query("select r from BWorkType r where r.typename!='其它' order by r.sortNum ")
   List<BWorkType> findByTypenameNotOrderBySortNumAsc(String typeName);
   
   @Query("select r from BWorkType r where r.frequency!='4' ")
   List<BWorkType> findWorkTypeWithOutNeeded();
   
   @Query("select r from BWorkType r where r.frequency!='4' and r.frequency!='3' ")
   List<BWorkType> findWorkTypeWithOutNeededAndQuarter();
   
   @Query("select max(t.sortNum) from BWorkType t")
   int getMaxSortNum();
}
