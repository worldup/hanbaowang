package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BExHeading;

public interface ExamHeadingRespository extends JpaRepository<BExHeading, Integer> {
	List<BExHeading> findByType(int type);
	BExHeading findByHValue(String value);
}
