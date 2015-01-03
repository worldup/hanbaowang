package com.upbest.mvc.handler;

import org.springframework.batch.item.excel.RowMapper;

public interface RowMapperHandler<T> {
	public boolean handler(T entity);
	
	public RowMapper<T> getRowMapper();
}
