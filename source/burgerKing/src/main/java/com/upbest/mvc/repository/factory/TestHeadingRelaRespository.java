package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BTestHeadingRela;

public interface TestHeadingRelaRespository extends JpaRepository<BTestHeadingRela, Integer> {
	List<BTestHeadingRela> findByTid(int testPaperId);
	
}
