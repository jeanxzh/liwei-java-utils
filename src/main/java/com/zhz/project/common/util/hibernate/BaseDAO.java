package com.zhz.project.common.util.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDAO extends HibernateDaoSupport {
    private static Logger logger = Logger.getLogger(BaseDAO.class);

    public List list(String table) {
        return this.getHibernateTemplate().find("from " + table);
    }

    /*
     * 
     * 获取某次查询集合的数量
     */
    public int getNumOnQuery(String FromQuery) {
        String querySentence = "SELECT count(*) " + FromQuery;
        List list = this.getHibernateTemplate().find(querySentence);
        Long countLong = (Long) list.get(0);
        int count = countLong.intValue();
        return count;

    }

    /**
     * 执行一般查询 Sql语句
     */
    public List select(String sqlStr) {
        List list = null;
        try {
            list = this.getHibernateTemplate().find(sqlStr);
        } catch (Exception e) {
            logger.error("查询语句错误:\n" + sqlStr + "\n" + e.toString(), e);
            return null;
        }
        return list;

    }

    /**
     * 保存数据
     */
    public boolean save(Object obj) {
        boolean re = true;
        try {
            this.getHibernateTemplate().save(obj);
            re = true;
        } catch (Exception e) {
            logger.error("保存数据错误.类名称为:" + obj.getClass().getName() + "\n" + e.toString(), e);
            re = false;
        }
        return re;
    }

    /**
     * 保存数据
     */
    public boolean update(Object obj) {
        boolean re = true;
        try {
            this.getHibernateTemplate().update(obj);
        } catch (Exception e) {
            logger.error("更新数据错误.类名称为:" + obj.getClass().getName() + "\n" + e.toString(), e);
            re = false;
        }
        return re;
    }

    /**
     * 查找数据
     * 
     * @param obj
     */
    public List find(String table, String keyName, String keyField) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyField + "'");
        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table + "\n" + e.toString(), e);
            return re;
        }
        return re;
    }

    /**
     * 查找数据 添加 order by 和 limit条件
     * 
     * @param obj
     */
    // /
    public List findbyorderlimit(String table, String keyName, String keyField, String orderlimit) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyField + "'" + orderlimit);
        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table + "\n" + e.toString(), e);
            return re;
        }
        return re;
    }

    // 用于分页
    public List findOne(String table, String keyName, String keyValue) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyValue + "'");

        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table + "\n" + e.toString(), e);
            return re;
        }
        return re;
    }

    public Object findOneObject(String table, String keyName, String keyValue) {
        List re = null;
        try {
            re = this.getHibernateTemplate().find(
                " from " + table + " where " + keyName + "='" + keyValue + "'");

        } catch (Exception e) {
            logger.error("查找数据错误.类名称为:" + table + "\n" + e.toString(), e);
            return re;
        }
        re.get(0);
        return re.get(0);
    }

    public List findAllByPage(final String table, final String whereStatement, final String order,
                              final int pageNow, final int pageSize) {
        List list = (List) this.getHibernateTemplate().execute(new HibernateCallback() {
            int size = pageNow * pageSize - pageSize;

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q = session.createQuery("from " + table + " " + whereStatement + " " + order);
                q.setFirstResult(size);
                q.setMaxResults(pageSize);
                List list = q.list();
                return list;
            }
        });

        return list;

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
            logger.error("删除新数据错误.类名称为:" + obj.getClass().getName() + "\n" + e.toString(), e);
            re = false;
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
    public boolean execute(String strSql) {
        boolean re = true;
        Session s = this.getSession();
        org.hibernate.Transaction tx = s.beginTransaction();
        try {
            s.createQuery(strSql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            logger.error("执行语句失败.表名:" + "\n" + e.toString() + strSql, e);
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
    public boolean executeT(String strSql) {
        boolean re = true;
        Session s = this.getSession();

        s.createQuery(strSql).executeUpdate();

        return re;
    }

    /**
     * 删除数据
     */
    public boolean deleteT(Object obj) {
        boolean re = true;
        Session s = null;

        s = this.getSession();
        s.delete(obj);

        return re;
    }

    public boolean deleteT(String table, String keyName, Object keyField) {
        boolean re = true;
        Session s = null;
        String sql = "delete from " + table + " where " + keyName + "='" + keyField + "'";

        s = this.getSession();
        s.createQuery(sql).executeUpdate();

        return re;
    }

}
