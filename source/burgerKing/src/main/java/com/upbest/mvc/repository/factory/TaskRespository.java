package com.upbest.mvc.repository.factory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upbest.mvc.entity.BWorkInfo;

public interface TaskRespository extends JpaRepository<BWorkInfo, Serializable> {
    
//    Page<BWorkInfo> findByNameLike(String taskName, Pageable pageable);
    
//    BWorkInfo findByName(String name);
	@Query("select w from BWorkInfo w where w.starttime > ?1 and w.starttime < ?2")
	List<BWorkInfo> findStartTimeBetween(Date startTime,Date endTime);
	
	@Query("select w from BWorkInfo w where DATEDIFF(dd, w.starttime, GETDATE()) = 0 and (w.state='0' or w.state is null) ")
	   List<BWorkInfo> queryUnDoneTaskCount();
}
