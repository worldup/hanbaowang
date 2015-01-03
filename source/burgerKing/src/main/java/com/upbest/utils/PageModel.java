package com.upbest.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @ClassName   类  名   称：	PageModel.java
 * @Description 功能描述：	
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月16日上午1:22:57
 */
public class PageModel<T> implements Serializable {

    private static final long serialVersionUID = -6021076153656809655L;

    private List<T> rows = new ArrayList<T>(); // 存放的数据，支持泛型
    private int total; // 总页数
    private int page; // 页码
    private int records; // 总条数
    private int pageSize;
    private int pageIndex; // 用于Hibernate分页查询，当前页起始index

    public PageModel() {
        super();
    }

    public PageModel(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageModel(PageModel<?> pageEntity) {
        this.page = pageEntity.getPage();
        this.records = pageEntity.getRecords();
        this.total = pageEntity.getTotal();
        this.pageIndex = pageEntity.getPageIndex();
        this.pageSize = pageEntity.getPageSize();
    }

    public int getFirstResult() {
        if (page > 1) {
            pageIndex = (page - 1) * pageSize;
        }
        return pageIndex;
    }

    public int getMaxResults() {
        return getPageSize();
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int totalCount) {
        this.records = totalCount;
        this.total = (totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<T> getRows() {
        return rows;
    }

    public Object[] getRowsArray() {
        return rows.toArray();
    }

    public void setRows(List<T> data) {
        this.rows = data;
    }

    @SuppressWarnings("unchecked")
    public void setRows(Object[] data) {
        this.rows = (List<T>) Arrays.asList(data);
    }

    public void add(T vo) {
        rows.add(vo);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int totalCount) {
        this.total = totalCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
