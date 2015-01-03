package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BUploadInfo;
import com.upbest.mvc.repository.factory.UploadInfoRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IUploadInfoService;

@Service
public class UploadInfoServiceImpl implements IUploadInfoService {
    @Autowired
    private CommonDaoCustom<Object[]> common;
	@Autowired
	private UploadInfoRespository dao;

	@Override
	public void saveFile(String fileName,String savePath){
		BUploadInfo uploadInfo = new BUploadInfo();
		uploadInfo.setCreateTime(new Date());
		uploadInfo.setOriginalName(fileName);
		uploadInfo.setPath(savePath);
		
		dao.save(uploadInfo);
	}
	
	

	@Override
	public List<BUploadInfo> findAllUploadInfo() {
		return dao.findAll();
	}
	
	
	 @Override
	    public Page<Object[]> findImgeList(String reName, Pageable pageable) {
	        StringBuffer sql = new StringBuffer();
	        List<Object> params = new ArrayList<Object>();
	        sql.append("  SELECT s.id,                       ");
	        sql.append("  s.original_name,                       ");
	        sql.append(" s.path,                       ");
	        sql.append("  s.create_time                       ");
	        sql.append("  from bk_upload_info s                   ");
	        sql.append("   where 1=1                       ");
	        if (StringUtils.isNotBlank(reName)) {
	            sql.append(" and s.original_name like ?");
	            params.add("%" + reName + "%");
	        }
	        return common.queryBySql(sql.toString(), params, pageable);
	    }



    @Override
    public void deleteById(Integer id) {
        BUploadInfo entity=dao.findOne(id);
        dao.delete(entity);
    }

}
