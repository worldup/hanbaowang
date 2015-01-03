package com.upbest.mvc.repository.factory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.upbest.mvc.entity.BExamPaperDetail;

public interface ExamnationPaperDetailRespository extends JpaRepository<BExamPaperDetail, Integer>{
	@Modifying
	@Transactional
	@Query("delete from BExamPaperDetail r where r.eid = ?1")
	void deleteByEid(Integer eid);

}
