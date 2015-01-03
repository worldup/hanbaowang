package com.upbest.mvc.repository.factory;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upbest.mvc.entity.NoticeReadInfo;

public interface NoticeReadRespository extends JpaRepository<NoticeReadInfo, Serializable> {

}
