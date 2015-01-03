package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upbest.mvc.entity.BExBaseInfo;

public interface ExamBaseinfoRespository extends JpaRepository<BExBaseInfo, Integer> {
	@Query("select e from BExBaseInfo e where e.bpid is null")
	List<BExBaseInfo> findTopExam();
}
