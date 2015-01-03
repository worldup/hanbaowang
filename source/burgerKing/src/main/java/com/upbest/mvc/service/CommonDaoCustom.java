package com.upbest.mvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonDaoCustom<T> {

	/**
	 * 根据sql语句查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Object[]> queryBySql(String sql, List params);

	/**
	 * 根据sql语句查询分页数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Page<T> queryBySql(String sql, List params, Pageable pageable);

	/**
	 * 根据hsql查询分页数据
	 * 
	 * @param hsql
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<T> getListByHsql(String hsql, List params, Pageable pageable);

	/**
	 * 根据sql执行更新操作
	 * 
	 * @param sql
	 * @param params
	 */
	public void upadteBySql(String sql, List params);

	/**
	 * 根据sql查询单个信息
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public String singlQueryBySql(String sql, List params);

}
