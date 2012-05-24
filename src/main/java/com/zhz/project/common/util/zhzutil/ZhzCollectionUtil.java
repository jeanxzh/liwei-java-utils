package com.zhz.project.common.util.zhzutil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version ZhzCollectionUtil.java, v 0.1 May 24, 2012 3:28:47 PM
 */
public class ZhzCollectionUtil {
    /**
     * 将list数据集转换为二维数组
     * 
     * @param list
     * @return 数组
     */
    public static String[][] listArrayToArray(List list) {
        if (list == null || list.size() == 0)
            return null;
        int row = list.size();

        int col = ((Object[]) list.get(0)).length;
        String array[][] = new String[row][col];
        int i = 0;
        for (Iterator it = list.iterator(); it.hasNext();) {
            Object[] ob = (Object[]) it.next();
            for (int j = 0; j < col; j++) {
                array[i][j] = ob[j] == null ? "" : (ob[j] + "").trim();
            }
            i++;
        }
        return array;

    }

    static public String[][] listMapToArray(List list) {
        if (list == null || list.size() == 0)
            return null;

        int row = list.size();
        int col = ((Map) list.get(0)).size();
        String[][] result = new String[row][col];

        for (int i = 0; i < row; i++) {
            Map map = (Map) list.get(i);
            Set keys = map.entrySet();
            int j = 0;
            for (Iterator it = keys.iterator(); it.hasNext();) {
                java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                String key = (String) entry.getKey();
                result[i][j] = map.get(key) == null ? "" : (map.get(key) + "").trim();
                ;
                j++;
            }
        }

        return result;
    }

    /**
     * 将list数据集转换为二维数组
     * 
     * @param list
     * @return 数组
     */
    public static String[][] listToArray(List list) {
        if (list == null || list.size() == 0)
            return null;
        int row = list.size();

        int col = ((Object[]) list.get(0)).length;
        String array[][] = new String[row][col];
        int i = 0;
        for (Iterator it = list.iterator(); it.hasNext();) {
            Object[] ob = (Object[]) it.next();
            for (int j = 0; j < col; j++) {
                array[i][j] = ob[j] == null ? "" : (ob[j] + "").trim();
            }
            i++;
        }
        return array;

    }

    /**
     * 把对象的属性映射成Map类型
     * 
     * @param obj
     * @return
     */
    public static Map describe(Object obj) {
        Map re = null;
        try {
            // 将VO中与PO中属性名称相同的值,复制到PO
            re = BeanUtils.describe(obj);
        } catch (Exception e) {
            System.out.println("对象映射成Map出错:" + obj.getClass().getName());
        }
        return re;
    }

    public static String[][] mapListToArray(List list) {
        if (list == null || list.size() == 0)
            return null;

        int row = list.size();
        int col = ((Map) list.get(0)).size();
        String[][] result = new String[row][col];

        for (int i = 0; i < row; i++) {
            Map map = (Map) list.get(i);
            Set keys = map.entrySet();
            int j = 0;
            for (Iterator it = keys.iterator(); it.hasNext();) {
                java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                String key = (String) entry.getKey();
                result[i][j] = map.get(key) == null ? "" : (map.get(key) + "").trim();
                ;
                j++;
            }
        }

        return result;
    }
}
