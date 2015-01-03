package com.upbest.mvc.repository.factory;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BExaminationPaper;

public interface ExamnationPaperRespository extends JpaRepository<BExaminationPaper, Integer>{
	List<BExaminationPaper> findByEtypeIn(Collection<Integer> types);
	
	List<BExaminationPaper> findByEtype(Integer examType);
}
