package com.upbest.mvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BExBaseInfo;
import com.upbest.mvc.vo.BExBaseInfoVO;
import com.upbest.mvc.vo.TreeVO;

public interface IBExBaseInfoService {
    Page<Object[]> findBaseList(String baseName, Pageable requestPage);
    
    public List<TreeVO> getUrVOList();
    
    public List<TreeVO> getUrVOList(Integer id);
    
    public List<BExBaseInfoVO> getBUser();
    
    BExBaseInfo saveBase (BExBaseInfo entity);
    
    BExBaseInfoVO findById(Integer id);
    
    public List<String> getBaseId(String baseId);
    
    public BExBaseInfo saveBaseInfo(String ids,String bvalue,String bcomments,String brushElection,int isKey);
    
    public void deleteBaseInfo(Integer id );

}