package com.upbest.mvc.repository.factory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.BUploadInfo;

public interface UploadInfoRespository extends JpaRepository<BUploadInfo, Integer> {

}
