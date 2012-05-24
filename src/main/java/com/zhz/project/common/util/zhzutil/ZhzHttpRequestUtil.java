package com.zhz.project.common.util.zhzutil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.zhz.project.common.util.alibaba.StringUtil;
import com.zhz.project.common.util.hibernate.BaseDAO;

/**
 * 页面处理的工具类
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version ZhzHttpRequestUtil.java, v 0.1 May 24, 2012 3:29:11 PM
 */
public class ZhzHttpRequestUtil {
    /**
     * 从HttpServletRequest提取参数，将参数从ISO转为UTF-8，然后trim
     * <br/>
     * 这个功能也可以通过Filter实现的，但是简单起见，就这样处理了
     * 
     * @param request
     * @param name
     * @return
     */
    public static String getParameters(HttpServletRequest request, String name) {
        return ZhzStringUtil.ISOtoUTF8AfterTrimIfNotNull(request.getParameter(name));
    }

    /**
     *对选择的记录进行删除，
     *
     * @param request
     * @param baseDao
     * @param table
     * @param key
     * @return  是否删除成功
     */
    public static boolean delete(HttpServletRequest request, BaseDAO baseDao, final String table,
                                 final String key) {
        String value = ZhzHttpRequestUtil.getParameters(request, key);
        boolean delResult = false;
        if (ZhzStringUtil.isNotNull(value)) {
            delResult = baseDao.execute("update " + table + "  set ifDelete =1 where " + key + "="
                                        + value);
            if (delResult) {
                request.setAttribute("del", "del");
                request.setAttribute("delResult", "delResult");
            }
        }
        return delResult;
    }

    /**
     * 具有搜索功能和翻页功能的查询
     *
     * @param request
     * @param baseDao
     * @param table
     * @param key
     * @param entryTatal
     * @param whereStatement
     * @param pageSize
     * @param LIST
     * @return  查询的结果集
     */
    @SuppressWarnings("unchecked")
    public static List getList(HttpServletRequest request, BaseDAO baseDao, final String table,
                               final String key, int entryTatal, String whereStatement,
                               int pageSize, String LIST) {

        /////////////////////////////////////////
        //构造搜索框进行查询

        //获取搜索框的类型
        String keywordType = ZhzHttpRequestUtil.getParameters(request, "keywordType");
        //获取搜索框的值
        String keyword = ZhzHttpRequestUtil.getParameters(request, "keyword");

        //根据搜索框的值进行查询
        if (StringUtil.isNotBlank(keyword)) {
            entryTatal = baseDao.getNumOnQuery(" FROM " + table + " where ifDelete =0 and  "
                                               + keywordType + " like '%" + keyword + "%'");
            whereStatement = "where  ifDelete =0 and " + keywordType + " like '%" + keyword + "%'";
        }

        if (StringUtil.isBlank(whereStatement)) {
            entryTatal = baseDao.getNumOnQuery(" FROM " + table + " where ifDelete =0");
            whereStatement = "where ifDelete =0";
        }

        /////////////////////////////////////////
        //获取页值
        int pageNow = 0;
        String pageNowString = ZhzHttpRequestUtil.getParameters(request, "pageNow");

        if (ZhzStringUtil.isNotNull(pageNowString)) {
            pageNow = Integer.parseInt(pageNowString.trim());
        } else {
            pageNow = 1;
        }
        if (pageNow <= 1)
            pageNow = 1;

        int pageTatal = 0;
        if (entryTatal / pageSize == 0)
            pageTatal = entryTatal / pageSize + 1;
        else
            pageTatal = entryTatal / pageSize;
        if (pageNow > pageTatal) {
            pageNow = pageTatal;
        }

        /////////////////////////////////////////
        //根据当前页等条件查询
        List list = baseDao.findAllByPage(table, whereStatement, "order by " + key + " DESC",
            pageNow, pageSize);

        //查询的结果集
        request.setAttribute(LIST, list);

        request.setAttribute("pageNow", pageNow);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("pageTatal", pageTatal);
        request.setAttribute("entryTatal", entryTatal);

        request.setAttribute("keyword", ZhzHttpRequestUtil.getParameters(request, "keyword"));
        request.setAttribute("keywordType", ZhzHttpRequestUtil
            .getParameters(request, "keywordType"));

        return list;
    }

    /**
     * 获取记录
     *
     * @param request
     * @param baseDao
     * @param TABLE
     * @param KEY
     * @param name
     * @return  查询的对象
     */
    public static Object getRecord(HttpServletRequest request, BaseDAO baseDao, String TABLE,
                                   String KEY, String name) {
        String value = ZhzHttpRequestUtil.getParameters(request, KEY);

        Object object = null;
        if (ZhzStringUtil.isNotNull(value)) {
            object = baseDao.findOneObject(TABLE, KEY, value);
            request.setAttribute(name, object);
        }
        return object;
    }

    /**
     * 将HashMap写进Request里面
     * 
     * @param map
     *            HashMap
     * @param fileName
     *            文件名
     */
    @SuppressWarnings("unchecked")
    public static void writeMapToRequest(HttpServletRequest request, Map map) {
        Set keys = map.entrySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            request.setAttribute((String) (entry.getKey()), entry.getValue());
        }
    }

    /**
     * 将HashMap写进ModelMap里面
     * 
     * @param map
     *            HashMap
     * @param fileName
     *            文件名
     */
    @SuppressWarnings("unchecked")
    public static void writeMapToModelMap(ModelMap model, Map map) {
        Set keys = map.entrySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            model.put((String) (entry.getKey()), entry.getValue());
        }
    }
}
