package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.upbest.mvc.entity.BExHeadingRela;

public interface ExHeadingRelaRespository extends JpaRepository<BExHeadingRela, Integer> {
	List<BExHeadingRela> findByModuleId(Integer moduleId);
	
	@Modifying
	@Transactional
	@Query("delete from BExHeadingRela r where r.moduleId = ?1")
	void deleteByModuleId(Integer moduleId);
	
	@Modifying
	@Transactional
	@Query("delete from BExHeadingRela r where r.eId = ?1")
	void deleteByEid(Integer examId);
	
	List<BExHeadingRela> findByEId(Integer eId);
}
