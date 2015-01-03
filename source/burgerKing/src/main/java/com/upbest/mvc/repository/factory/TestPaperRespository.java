package com.upbest.mvc.repository.factory;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upbest.mvc.entity.BTestPaper;

public interface TestPaperRespository extends JpaRepository<BTestPaper, Integer> {
	List<BTestPaper> findByUseridAndEid(Integer userid,Integer eid);
	
	@Query("select r from BTestPaper r where r.tend is not null and r.tend > ?1 and r.tend < ?2 and r.storeid=?3 and r.eid in (?4) order by r.tend desc")
	List<BTestPaper> findTestInfo(Date startTime,Date endTime,int shopId,List<Integer> examIds);
	
	@Query("select r from BTestPaper r where r.tend is not null and r.tend > ?1 and r.storeid=?2 and r.eid in (?3)  order by r.tend desc")
	List<BTestPaper> findTestInfoGt(Date startTime,int shopId,List<Integer> examIds);
	
	@Query("select r from BTestPaper r where r.tend is not null and r.tend < ?1 and r.storeid=?2 and r.eid in (?3)  order by r.tend desc")
	List<BTestPaper> findTestInfoLt(Date endTime,int shopId,List<Integer> examIds);
	
	@Query("select r from BTestPaper r where r.tend is not null and r.storeid=?1 and r.eid in (?2)  order by r.tend desc")
	List<BTestPaper> findTestInfo(int shopId,List<Integer> examIds);
	
	List<BTestPaper> findByStoreid(int shopId,Pageable pageable);
	
	List<BTestPaper> findByStoreidAndEidIn(int shopId,List<Integer> examIds,Pageable pageable);
	
	@Query("select AVG	(tp.ttotal) from BTestPaper tp where tp.eid in ?1 and tp.storeid = ?2 and tp.tend = ?3 group by tp.tend")
	List<Double> findAvgScore(List<Integer> examIds,Integer shopId,Date date);

	Page<BTestPaper> findByEidInAndStoreid(List<Integer> examIds,
			Integer shopId, Pageable pageable);
	
	
}
