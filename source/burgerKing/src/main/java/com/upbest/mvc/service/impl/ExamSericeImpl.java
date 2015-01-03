package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BExamRefer;
import com.upbest.mvc.repository.factory.ExamRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IExamSerivce;
@Service
public class ExamSericeImpl implements IExamSerivce {
    @Autowired
    private CommonDaoCustom<Object[]> common;
    @Autowired
    private ExamRespository examRespository;

    @Override
    public Page<Object[]> findExamList(String examName, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT s.id,                       ");
        sql.append("         s.name,       ");
        sql.append("         s.timer,       ");
        sql.append("         s.date,       ");
        sql.append("         s.num,       ");
        sql.append("         s.type       ");
        sql.append("    FROM bk_exam_refer s  ");
        sql.append("   where 1=1                       ");
        if (StringUtils.isNotBlank(examName)) {
            sql.append(" and s.name like ?");
            params.add("%" + examName + "%");
        }
        return common.queryBySql(sql.toString(), params, pageable);
    }

    @Override
    public List<BExamRefer> findById() {
        return (List<BExamRefer>) examRespository.findAll();
    }
    
    @Override
    public BExamRefer findById(Integer id) {
        return examRespository.findOne(id);
    }

    @Override
    public void saveBExamRefer(BExamRefer entity) {
        examRespository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        BExamRefer entity=examRespository.findOne(id);
        examRespository.delete(entity);
    }
}
