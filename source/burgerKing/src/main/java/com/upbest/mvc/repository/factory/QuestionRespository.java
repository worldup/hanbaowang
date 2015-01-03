package com.upbest.mvc.repository.factory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BQuestion;

public interface QuestionRespository extends JpaRepository<BQuestion, Integer> {

}
