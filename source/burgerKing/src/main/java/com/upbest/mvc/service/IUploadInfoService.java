package com.upbest.mvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BUploadInfo;

public interface IUploadInfoService {
	/**
	 * 保存文件信息
	 * @param file
	 */
	public void saveFile(String fileName,String savePath);
	
	
	
	List<BUploadInfo> findAllUploadInfo();
	
	 public Page<Object[]> findImgeList(String reName, Pageable pageable);
	 
	 public void deleteById(Integer id);
}
