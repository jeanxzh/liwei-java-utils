package com.zhz.project.common.util.zhzutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author LiWei <a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version 创建时间：2009-12-10 下午09:40:47
 * 
 */
public class ZhzDateUtil {
    private static Logger           logger   = Logger.getLogger(ZhzDateUtil.class);

    private static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

    static public int disDate(java.util.Date d1, java.util.Date d2) {
        long MILLI = 24 * 60 * 60 * 1000;
        long time1 = d1.getTime();
        long time2 = d2.getTime();
        long dis = time1 - time2;
        return (dis % MILLI == 0) ? (int) (dis / MILLI) : (int) (dis / MILLI) + 1;
    }

    static public String Date2YYYYMMDDString(Date date) {
        if (null != date) {
            return YYYYMMDD.format(date);
        }
        return null;

    }

    public static Date getYYYYMDDDate(String dateStr) {
        Date date = null;
        if (ZhzStringUtil.isNotNull(dateStr)) {
            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
            try {
                date = df.parse(ZhzStringUtil.ISOtoUTF8AfterTrimIfNotNull(dateStr));
            } catch (ParseException e) {
                logger.error("发布时间转换时发生异常", e);
            }
        }
        return date;
    }

}
