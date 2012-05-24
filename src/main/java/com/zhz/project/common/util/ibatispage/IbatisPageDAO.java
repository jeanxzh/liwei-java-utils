package com.zhz.project.common.util.ibatispage;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version IbatisPageDAO.java, v 0.1 May 24, 2012 3:32:39 PM
 */
//@Repository
public class IbatisPageDAO extends SqlMapClientDaoSupport {

    /**
     * 获取指定条数的记录
     */
    public List getSpeList(String sql, Map sqlMap) {

        List list = null;
        try {
            list = getSqlMapClientTemplate().queryForList(sql, sqlMap);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取总记录数
     */
    public int getTotalCount(String sql, Map totalCountSqlMap) {
        return ((Integer) getSqlMapClientTemplate().queryForObject(sql, totalCountSqlMap))
            .intValue();
    }
}