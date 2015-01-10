package com.upbest.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.upbest.mvc.repository.factory.DaoFactory;
import com.upbest.mvc.service.CommonDaoCustom;

@Repository
public class CommonDaoCustomImpl<T> extends DaoFactory implements CommonDaoCustom {

	/**
	 * 根据sql语句查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Object[]> queryBySql(String sql, List params) {

		return (List<Object[]>) queryObjectsListBySql(sql, params);
	}

	/**
	 * 根据hsql查询博客分页数据
	 * 
	 * @param hsql
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<T> getListByHsql(String hsql, List params, Pageable pageable) {

		return hqlQueryForPage(hsql, params, pageable);
	}

	public void upadteBySql(String sql, List params) {
		// TODO Auto-generated method stub
		updateBySql(sql, params);
	}

	public Page queryBySql(String sql, List params, Pageable pageable) {
		// TODO Auto-generated method stub
		return queryObjectsPageBySql(sql, params, pageable);
	}

	@Override
	public String singlQueryBySql(String sql, List params) {
		// TODO Auto-generated method stub
		return getSingleQuery(sql, params);
	}
}
