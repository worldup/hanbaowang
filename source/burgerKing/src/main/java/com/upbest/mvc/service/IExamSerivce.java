package com.upbest.mvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BExamRefer;

public interface IExamSerivce {
    Page<Object[]> findExamList(String examName, Pageable pageable);

    List<BExamRefer> findById();
    BExamRefer findById(Integer id);
    void saveBExamRefer(BExamRefer entity);
    void deleteById(Integer id);
}
