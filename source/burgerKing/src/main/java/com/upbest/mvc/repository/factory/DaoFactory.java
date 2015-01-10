package com.upbest.mvc.repository.factory;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Title. 用于处理个�?化分页查询操作；<br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2012-7-31 下午1:54:56
 * <p>
 * Company: 北京宽连十方数字�?��有限公司
 * <p>
 * Author: Walter.Luo
 * <p>
 * Version: 1.0
 * <p>
 * 
 * @param <T>
 */
@Component
public class DaoFactory<T> {
    @Value("${database}")
    private String database;
    private static final String HQL_COUNT = "select count(*) ";

    @PersistenceContext
    private EntityManager em;

    /** hsql */
    protected String hsql;

    /** 存储hsql中的待定参数 */
    private List<Object> parameters;

    /**
     * 获取统计查询
     * 
     * @return
     */
    private int getCountQuery() {
        int result = 0;
        Query query = null;
        boolean complexQry = false;
        if (StringUtils.containsIgnoreCase((StringUtils.replaceEach(hsql, new String[] { " ", "\r\n" }, new String[] { "", "" })), "groupby")) {
            complexQry = true;
        }
        query = em.createQuery(HQL_COUNT + StringUtils.substring(hsql, StringUtils.indexOfIgnoreCase(hsql, "from", 0)));
        if (parameters != null) {
            Assert.notNull(parameters);
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }
        }
        if (complexQry) {
            result = query.getResultList().size();
        } else {
            result = Integer.parseInt(String.valueOf(query.getSingleResult()));
        }

        return result;
    }

    /**
     * 获取统计查询
     * 
     * @return
     */
    protected int getCountQueryBySql() {
        int result = 0;
        Query query = null;
        if (hsql.indexOf("order by") > -1&&"SQL_SERVER".equalsIgnoreCase(database)) {
            // 加上top 100 percent，否则不能进行时间排序
            if (hsql.indexOf("distinct") > -1) {
                hsql = hsql.replaceAll("select distinct", "select distinct top 100 percent ").replaceAll("SELECT distinct", "select distinct top 100 percent ");
            } else {
                hsql = hsql.replaceAll("select", "select top 100 percent ").replaceAll("SELECT", "select top 100 percent ");
            }
        }
        query = em.createNativeQuery("select count(*) from ( " + hsql + " ) as aa");
        if (parameters != null) {
            Assert.notNull(parameters);
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }
        }
        result = Integer.valueOf(ObjectUtils.toString(query.getSingleResult()));

        return result;
    }

    /**
     * 获取 parameters
     * 
     * @return parameters
     */
    public List<Object> getParameters() {
        return parameters;
    }

    public int getUnauditedQueryCount(String entity) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("select count(*) from ");
        sb.append(entity);
        sb.append(" en where en.auditStatus = 0");
        if (entity.equals("Blog") || entity.equals("Question")) {
            sb.append(" and en.isDel != 1");
        } else {
            sb.append(" and en.isDelete != 1");
        }
        Query query = em.createQuery(sb.toString());
        String value = String.valueOf(query.getSingleResult());
        int result = Integer.parseInt(value);
        return result;
    }

    public String getSingleQuery(String sql, List<Object> parameters) {

        Query query = em.createNativeQuery(sql);

        Assert.notNull(parameters);
        for (int i = 0; i < parameters.size(); i++) {
            parameters.set(i, parameters.get(i));
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }
        }

        return String.valueOf(query.getSingleResult());
    }

    /**
     * hsql 动�?分页
     * 
     * @param hsql
     *            个�?华HSQL
     * @param parameters
     *            参数
     * @param pageable
     *            分页详情
     * @return 返回分页信息
     */
    public Page<T> hqlQueryForPage(String hsql, List<Object> parameters, Pageable pageable) {
        setHsql(hsql);
        setParameters(parameters);
        Query query = em.createQuery(hsql);
        Assert.notNull(parameters);
        for (int i = 0; i < parameters.size(); i++) {
            parameters.set(i, parameters.get(i));
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }

        }

        return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, pageable);
    }

    /**
     * 使用本地SQl查询对象
     * 
     * @param sql
     *            本地SQL语句
     * @param parameters
     *            设置参数
     * @param cla
     *            查询化类�?
     * @return
     */
    protected Object queryBySql(String sql, List<Object> parameters, Class cla) {
        Query query = em.createNativeQuery(sql, cla);
        Assert.notNull(parameters);
        for (int i = 0; i < parameters.size(); i++) {
            parameters.set(i, parameters.get(i));
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }

        }
        return query.getSingleResult();
    }

    /**
     * 使用本地SQL查询对象
     * 
     * @param sql
     *            本地SQL语句
     * @param parameters
     *            参数
     * @param cla
     *            结果集反射依赖类
     * @return 返回集合形式的结果；例{obj,obj,obj}
     */
    protected List<?> queryObjectListBySql(String sql, List<Object> parameters, Class cla) {
        Query query = em.createNativeQuery(sql, cla);
        Assert.notNull(parameters);
        for (int i = 0; i < parameters.size(); i++) {
            parameters.set(i, parameters.get(i));
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }

        }
        return query.getResultList();
    }

    /**
     * 本地SQL查询
     * 
     * @param sql
     *            本地化查询SQL
     * @param parameters
     *            查询参数
     * @return 返回集合形式；数组元数为对象数组�?
     */
    protected List<?> queryObjectsListBySql(String sql, List<Object> parameters) {
        Query query = em.createNativeQuery(sql);
        Assert.notNull(parameters);
        for (int i = 0; i < parameters.size(); i++) {
            parameters.set(i, parameters.get(i));
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }
        }
        return query.getResultList();
    }

    /**
     * 本地SQL查询分页
     * 
     * @param sql
     *            本地化查询SQL
     * @param parameters
     *            查询参数
     * @return 返回集合形式；数组元数为对象数组�?
     */
    protected Page<T> queryObjectsPageBySql(String sql, List<Object> parameters, Pageable pageable) {
        Sort sort = pageable.getSort();
        if(null!=sort){
            Iterator<Order> orders = sort.iterator();
            if(sql.indexOf("order by")!=-1){
                sql = sql.substring(0, sql.indexOf("order by"));
            }
                if (orders.hasNext()) {
                    sql += " order by ";
                }
                while (orders.hasNext()) {
                    Order or = orders.next();
                    String sord = or.getDirection().name();
                    String sidx = or.getProperty();
                    sql += sidx + " " + sord + ",";
                }
                if (sql.endsWith(",")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
            
        }
       
        setHsql(sql);
        setParameters(parameters);
        Query query = em.createNativeQuery(sql);
        Assert.notNull(parameters);
        for (int i = 0; i < parameters.size(); i++) {
            parameters.set(i, parameters.get(i));
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }
        }
        return pageable == null ? new PageImpl<T>(query.getResultList()) : readPageBySql(query, pageable);
    }

    /**
     * 设置分页查询
     * 
     * @param query
     *            查询
     * @param pageable
     *            分页信息
     * @return
     */
    private Page<T> readPage(Query query, Pageable pageable) {

        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        int total = getCountQuery();

        return new PageImpl<T>(query.getResultList(), pageable, total);
    }

    /**
     * 设置分页查询
     * 
     * @param query
     *            查询
     * @param pageable
     *            分页信息
     * @return
     */
    private Page<T> readPageBySql(Query query, Pageable pageable) {

        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        int total = getCountQueryBySql();

        return new PageImpl<T>(query.getResultList(), pageable, total);
    }

    /**
     * 设置 hsql
     * 
     * @param hsql
     *            hsql
     */
    public void setHsql(String hsql) {
        this.hsql = hsql;
    }

    /**
     * 设置 parameters
     * 
     * @param parameters
     *            parameters
     */
    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * 执行更新语句
     * 
     * @param sql
     * @param parameters
     */
    public void updateBySql(String sql, List<Object> parameters) {

        Query query = em.createNativeQuery(sql);
        for (int i = 0; i < parameters.size(); i++) {
            parameters.set(i, parameters.get(i));
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }
        }
        query.executeUpdate();
    }

}
