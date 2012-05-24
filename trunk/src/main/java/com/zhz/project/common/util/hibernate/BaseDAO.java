package com.zhz.project.common.util.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version BaseDAO.java, v 0.1 May 24, 2012 3:32:52 PM
 */
@Repository
public class BaseDAO extends HibernateDaoSupport {
    private String              error        = "";
    public static String        WHERE        = " where 1=1 ";
    public static String        WHERE_DELETE = " and ifDelete =0 ";
    HibernateTransactionManager transactionManager;

    private static Logger       logger       = Logger.getLogger(BaseDAO.class);

    @SuppressWarnings("unchecked")
    public List list(String table) {
        return this.getHibernateTemplate().find("from " + table);
    }

    /**
     * 获取某次查询集合的数量
     * @param FromQuery
     * @return
     */
    @SuppressWarnings("unchecked")
    public int getNumOnQuery(String FromQuery) {
        String querySentence = "SELECT count(*) " + FromQuery;
        List list = this.getHibernateTemplate().find(querySentence);
        if (list != null && list.size() > 0) {
            Long countLong = (Long) list.get(0);
            int count = countLong.intValue();
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 执行一般查询 Sql语句
     */
    @SuppressWarnings("unchecked")
    public List select(String sqlStr) {
        List list = null;
        try {
            list = this.getHibernateTemplate().find(sqlStr);
            return list;
        } catch (Exception e) {
            logger.error("查询语句错误:" + sqlStr, e);
            return null;
        }
    }

    /**
     * 保存数据
     */
    public boolean save(Object obj) {
        boolean re = true;
        try {
            this.getHibernateTemplate().save(obj);
            return re;
        } catch (Exception e) {
            logger.error("保存数据错误.类名称为:" + obj.getClass().getName(), e);
            return false;
        }
    }

    /**
     * 保存数据
     */
    public boolean update(Object obj) {
        boolean re = true;
        try {
            this.getHibernateTemplate().update(obj);
            return re;

        } catch (Exception e) {
            logger.error("更新数据错误.类名称为:" + obj.getClass().getName(), e);
            return false;
        }
    }

    /**
     * 查找数据
     * 
     * @param obj
     */
    @SuppressWarnings("unchecked")
    public List find(String table, String keyName, String keyField) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyField + "'");
            return re;
        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table, e);
            return null;
        }
    }

    /**
     * 查找数据 添加 order by 和 limit条件
     * 
     * @param obj
     */
    @SuppressWarnings("unchecked")
    public List findbyorderlimit(String table, String keyName, String keyField, String orderlimit) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyField + "'" + orderlimit);
            return re;
        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table, e);
            return null;
        }
    }

    /**
     * 用于分页
     * @param table
     * @param keyName
     * @param keyValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public List findOne(String table, String keyName, String keyValue) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyValue + "'");
            return re;

        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Object findOneObject(String table, String keyName, String keyValue) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyValue + "'");

            if (re != null && re.size() > 0) {
                return re.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table, e);
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    public List findAllByPage(final String table, final String whereStatement, final String order,
                              final int pageNow, final int pageSize) {

        try {
            List list = (List) this.getHibernateTemplate().execute(new HibernateCallback() {
                int size = pageNow * pageSize - pageSize;

                public Object doInHibernate(Session session) throws HibernateException,
                                                            SQLException {
                    Query q = session.createQuery("from " + table + " " + whereStatement + " "
                                                  + order);
                    q.setFirstResult(size);
                    q.setMaxResults(pageSize);
                    List list = q.list();
                    return list;
                }
            });
            return list;
        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table, e);
            return null;
        }

    }

    /**
     * 删除数据
     */
    public boolean delete(Object obj) {
        boolean re = true;
        Session s = null;
        try {
            s = this.getSession();
            s.delete(obj);
            s.flush();
        } catch (Exception e) {
            logger.error("删除新数据错误.类名称为:" + obj.getClass().getName(), e);
            re = false;
        } finally {
            if (s.isOpen())
                s.close();
        }
        return re;
    }

    public boolean delete(String table, String keyName, Object keyField) {
        boolean re = true;
        Session s = null;
        String sql = "delete from " + table + " where " + keyName + "='" + keyField + "'";

        s = this.getSession();
        s.createQuery(sql).executeUpdate();

        return re;
    }

    /**
     * 执行SQL语句，插入或者删除、修改
     * 
     * @param strSql
     * @return
     */
    public boolean execute(String strSql) {
        boolean re = true;
        Session s = this.getSession();
        org.hibernate.Transaction tx = s.beginTransaction();
        try {
            s.createQuery(strSql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            logger.error("执行语句失败.表名:" + strSql, e);
            re = false;
            tx.rollback();
        } finally {
            if (s.isOpen())
                s.close();
        }
        return re;
    }

    /**
     * 执行SQL语句，插入或者删除、修改
     * 
     * @param strSql
     * @return
     */
    public boolean executeInNewTransaction(String strSql) {
        boolean re = true;
        Session s = this.getSession();
        org.hibernate.Transaction tx = s.beginTransaction();
        try {
            s.createQuery(strSql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            logger.error("执行语句失败.表名:" + strSql, e);
            re = false;
            tx.rollback();
        } finally {
            if (s.isOpen())
                s.close();
        }
        return re;
    }

    public HibernateTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(HibernateTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
