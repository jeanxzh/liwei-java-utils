/**
 * Zhz.net Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.zhz.project.common.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zhz.project.common.util.alibaba.StringUtil;

/**
 * 日期处理类
 *
 * @author liwei
 * @version $Id: DateUtils.java, v 0.1 Jan 7, 2011 3:11:51 AM liwei Exp $
 */
public class DateUtils {
    private static Logger      logger          = Logger.getLogger(DateUtils.class);

    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple          = "yyyy-MM-dd HH:mm:ss";

    /** 年月日 yyyy-MM-dd */
    public static final String dtSimple        = "yyyy-MM-dd";

    /** 年月日(中文) yyyy年MM月dd日 */
    public static final String dtSimpleChinese = "yyyy年MM月dd日";

    public static final String week            = "EEEE";

    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort         = "yyyyMMdd";

    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong          = "yyyyMMddHHmmss";

    /** 时分秒 HH:mm:ss */
    public static final String hmsFormat       = "HH:mm:ss";

    /** 年-月-日 小时:分钟 yyyy-MM-dd HH:mm */
    public static final String simpleFormat    = "yyyy-MM-dd HH:mm";

    // 年月日时分秒毫秒(无下划线)
    public static final String dtLongMill      = "yyyyMMddHHmmssS";

    /**
     * yyyy-MM-dd
     * 
     * @param date
     * 
     * @return
     */
    public static final String dtSimpleFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimple).format(date);
    }

    /**
     * yyyyMMddHHmmssS
     * 
     * @param date
     * 
     * @return
     */
    public static final String dtLongMillFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtLongMill).format(date);
    }

    /**
     * yyyy-mm-dd 日期格式转换为日期
     * 
     * @param strDate
     * 
     * @return
     */
    public static final Date strToDtSimpleFormat(String strDate) {
        if (strDate == null) {
            return null;
        }

        try {
            return getFormat(dtSimple).parse(strDate);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * yyyy-MM-dd HH:mm 日期格式转换为日期
     * 
     * @param strDate
     * 
     * @return
     */
    public static final Date strToSimpleFormat(String strDate) {
        if (strDate == null) {
            return null;
        }

        try {
            return getFormat(simpleFormat).parse(strDate);

        } catch (Exception e) {
        }

        return null;
    }

    /**
     * yyyy-MM-dd HH:mm 或者yyyy-MM-dd  转换为日期格式
     * @param strDate
     * @return
     */
    public static final Date strToDate(String strDate) {
        if (strToSimpleFormat(strDate) != null) {
            return strToSimpleFormat(strDate);
        } else {
            return strToDtSimpleFormat(strDate);
        }

    }

    /**
     * yyyy-MM-dd 日期字符转换为时间
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Date string2Date(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }

        return getFormat(dtSimple).parse(stringDate);
    }

    /**
     * 返回日期时间（Add by Sunzy）
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Date string2DateTime(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }

        return getFormat(simple).parse(stringDate);
    }

    /**
     * 返回日期时间（Add by Sunzy）
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Date string2DateTimeByAutoZero(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        if (stringDate.length() == 11)
            stringDate = stringDate + "00:00:00";
        else if (stringDate.length() == 13)
            stringDate = stringDate + ":00:00";
        else if (stringDate.length() == 16)
            stringDate = stringDate + ":00";
        else if (stringDate.length() == 10)
            stringDate = stringDate + " 00:00:00";

        return getFormat(simple).parse(stringDate);
    }

    /**
     * yyyy-MM-dd 日期字符转换为长整形
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Long string2DateLong(String stringDate) throws ParseException {
        Date d = string2Date(stringDate);

        if (d == null) {
            return null;
        }

        return new Long(d.getTime());
    }

    /**
     * 获取当前日期的日期差 now= 2005-07-19 diff = 1 -> 2005-07-20 diff = -1 -> 2005-07-18
     * 
     * @param diff
     * 
     * @return
     */
    public static final String getDiffDate(int diff) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        return dtSimpleFormat(c.getTime());
    }

    public static final String getDiffDate(String srcDate, String format, int diff) {
        DateFormat f = new SimpleDateFormat(format);

        try {
            Date source = f.parse(srcDate);
            Calendar c = Calendar.getInstance();

            c.setTime(source);
            c.add(Calendar.DATE, diff);
            return f.format(c.getTime());
        } catch (Exception e) {
            return srcDate;
        }
    }

    /**
     * 把日期类型的日期换成数字类型 YYYYMMDD类型
     * 
     * @param date
     * 
     * @return
     */
    public static final Long dateToNumber(Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();

        c.setTime(date);

        String month;
        String day;

        if ((c.get(Calendar.MONTH) + 1) >= 10) {
            month = "" + (c.get(Calendar.MONTH) + 1);
        } else {
            month = "0" + (c.get(Calendar.MONTH) + 1);
        }

        if (c.get(Calendar.DATE) >= 10) {
            day = "" + c.get(Calendar.DATE);
        } else {
            day = "0" + c.get(Calendar.DATE);
        }

        String number = c.get(Calendar.YEAR) + "" + month + day;

        return new Long(number);
    }

    /**
     * 获取每月的某天到月末的区间
     * 
     * @param date
     * 
     * @return
     */
    public static Map getLastWeek(String StringDate, int interval) throws ParseException {
        Map lastWeek = new HashMap();
        Date tempDate = DateUtils.shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);

        int dayOfMonth = cad.getActualMaximum(Calendar.DAY_OF_MONTH);

        cad.add(Calendar.DATE, (dayOfMonth - 1));
        lastWeek.put("endDate", DateUtils.shortDate(cad.getTime()));
        cad.add(Calendar.DATE, interval);
        lastWeek.put("startDate", DateUtils.shortDate(cad.getTime()));

        return lastWeek;
    }

    /**
     * 获取下月
     * 
     * @param date
     * 
     * @return
     */
    public static String getNextMon(String StringDate) throws ParseException {
        Date tempDate = DateUtils.shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.MONTH, 1);
        return DateUtils.shortDate(cad.getTime());
    }

    /**
     * add by daizhixia 20050808 获取下一天
     * 
     * @param StringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static String getNextDay(String StringDate) throws ParseException {
        Date tempDate = DateUtils.string2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.DATE, 1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * add by chencg 获取下一天 返回 dtSimple 格式字符
     * 
     * @param date
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static String getNextDay(Date date) throws ParseException {
        Calendar cad = Calendar.getInstance();
        cad.setTime(date);
        cad.add(Calendar.DATE, 1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * add by shengyong 20050808 获取前一天
     * 
     * @param StringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static String getBeforeDay(String StringDate) throws ParseException {
        Date tempDate = DateUtils.string2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.DATE, -1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * add by shengyong 获取前一天 返回 dtSimple 格式字符
     * 
     * @param date
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static String getBeforeDay(Date date) throws ParseException {
        Calendar cad = Calendar.getInstance();
        cad.setTime(date);
        cad.add(Calendar.DATE, -1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * add by chencg 获取下一天 返回 dtshort 格式字符
     * 
     * @param StringDate
     *            "20061106"
     * 
     * @return String "2006-11-07"
     * 
     * @throws ParseException
     */
    public static Date getNextDayDtShort(String StringDate) throws ParseException {
        Date tempDate = DateUtils.shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.DATE, 1);
        return cad.getTime();
    }

    /**
     * 校验start与end相差的天数，是否满足end-start lessEqual than days
     * 
     * @param start
     * @param end
     * @param days
     * 
     * @return
     */
    public static boolean checkDays(Date start, Date end, int days) {
        int g = countDays(start, end);

        return g <= days;
    }

    /**
     * alahan add 20050825 获取传入时间相差的日期
     * 
     * @param dt
     *            传入日期，可以为空
     * @param diff
     *            需要获取相隔diff天的日期 如果为正则取以后的日期，否则时间往前推
     * 
     * @return
     */
    public static String getDiffStringDate(Date dt, int diff) {
        Calendar ca = Calendar.getInstance();

        if (dt == null) {
            ca.setTime(new Date());
        } else {
            ca.setTime(dt);
        }

        ca.add(Calendar.DATE, diff);
        return dtSimpleFormat(ca.getTime());
    }

    /**
     * 返回日期时间（Add by Gonglei）
     * 
     * @param stringDate
     *            (yyyyMMdd)
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final String StringToStringDate(String stringDate) {
        if (stringDate == null) {
            return null;
        }

        if (stringDate.length() != 8) {
            return null;
        }

        return stringDate.substring(0, 4) + stringDate.substring(4, 6) + stringDate.substring(6, 8);
    }

    /**
     * 将字符串按format格式转换为date类型
     * 
     * @param str
     * @param format
     * 
     * @return
     */
    public static Date string2Date(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获得日期是周几
     * @author xiang.zhaox
     * @param date
     * @return dayOfWeek
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getFormat(dtLongMill).format(new Date()));
        }

    }

    /**
     * 将8位日期转换为10位日期（Add by Alcor）
     * 
     * @param stringDate yyyymmdd
     * @return yyyy-mm-dd
     * @throws ParseException
     */
    public static final String shortString2SimpleString(String shortString) {
        if (shortString == null) {
            return null;
        }
        try {
            return getFormat(dtSimple).format(shortstring2Date(shortString));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取格式
     *
     * @param format
     * @return
     */
    public static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static final String simpleFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(simple).format(date);
    }

    /**
     * yyyyMM-dd
     *
     * @param date
     * @return
     */
    public static final String dtShortSimpleFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtShort).format(date);
    }

    /**
     * @param strDate   yyyyMMdd
     * @return    yyyy-MM-dd
     */
    public static final String dtFromShortToSimpleStr(String strDate) {
        if (null != strDate) {
            Date date;
            try {
                date = shortstring2Date(strDate);
            } catch (ParseException e) {
                date = null;
            }
            if (null != date) {
                return dtSimpleFormat(date);
            }
        }
        return "";
    }

    /**
     * 获取输入日期的相差日期
     *
     * @param dt
     * @param idiff
     *
     * @return
     */
    public static final String getDiffDate(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();

        c.setTime(dt);
        c.add(Calendar.DATE, idiff);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * 获取输入日期的相差日期,转换为日，分秒
     * @param dt
     * @param idiff
     * @return
     */
    public static final String getDiffDateMin(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();

        c.setTime(dt);
        c.add(Calendar.DATE, idiff);
        return simpleFormat(c.getTime());
    }

    /**
     * 获取输入日期的相差日期
     *
     * @param dt
     * @param idiff
     *
     * @return
     */
    public static final String getDiffDateDtShort(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();

        c.setTime(dt);
        c.add(Calendar.DATE, idiff);
        return dtShortSimpleFormat(c.getTime());
    }

    /**
     * 获取输入日期月份的相差日期
     *
     * @param dt
     * @param idiff
     * @return
     */
    public static final String getDiffMon(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, idiff);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * yyyy年MM月dd日
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleChineseFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimpleChinese).format(date);
    }

    /**
     * yyyy-MM-dd到 yyyy年MM月dd日 转换
     *
     * @param date
     *
     * @return
     */
    public static final String dtSimpleChineseFormatStr(String date) throws ParseException {
        if (date == null) {
            return "";
        }

        return getFormat(dtSimpleChinese).format(string2Date(date));
    }

    /**
     * 返回日期时间（Add by wangjl）(时分秒：23:59:59)
     *
     * @param stringDate String 型
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date string2DateTimeBy23(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        if (stringDate.length() == 11)
            stringDate = stringDate + "23:59:59";
        else if (stringDate.length() == 13)
            stringDate = stringDate + ":59:59";
        else if (stringDate.length() == 16)
            stringDate = stringDate + ":59";
        else if (stringDate.length() == 10)
            stringDate = stringDate + " 23:59:59";

        return getFormat(simple).parse(stringDate);
    }

    /**
     * 计算日期差值
     *
     * @param String
     * @param String
     * @return int（天数）
     */
    public static final int calculateDecreaseDate(String beforDate, String afterDate)
                                                                                     throws ParseException {
        Date date1 = getFormat(dtSimple).parse(beforDate);
        Date date2 = getFormat(dtSimple).parse(afterDate);
        long decrease = getDateBetween(date1, date2) / 1000 / 3600 / 24;
        int dateDiff = (int) decrease;
        return dateDiff;
    }

    /**
     * 计算时间差
     *
     * @param dBefor
     *            首日
     * @param dAfter
     *            尾日
     * @return 时间差(毫秒)
     */
    public static final long getDateBetween(Date dBefor, Date dAfter) {
        long lBefor = 0;
        long lAfter = 0;
        long lRtn = 0;

        /** 取得距离 1970年1月1日 00:00:00 GMT 的毫秒数 */
        lBefor = dBefor.getTime();
        lAfter = dAfter.getTime();

        lRtn = lAfter - lBefor;

        return lRtn;
    }

    /**
     * 当前时间与参数时间差
     *
     * @param dBefor
     *            首日
     * @return 时间差(分)
     */
    public static final int getDateBetweenNow(Date dateBefore) {
        if (dateBefore == null) {
            return 0;
        }
        return (int) (getDateBetween(dateBefore, new Date()) / 1000 / 60);
    }

    /**
     * 返回日期时间（Add by Gonglei）
     *
     * @param stringDate
     *            (yyyyMMdd)
     *
     * @return
     *
     * @throws ParseException
     */
    public static final Date shortstring2Date(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }

        return getFormat(dtShort).parse(stringDate);
    }

    /**
     * 返回短日期格式（yyyyMMdd格式）
     *
     * @param stringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static final String shortDate(Date Date) {
        if (Date == null) {
            return null;
        }

        return getFormat(dtShort).format(Date);
    }

    /**
     * 返回长日期格式（yyyyMMddHHmmss格式）
     *
     * @param stringDate
     *
     * @return
     *
     * @throws ParseException
     */
    public static final String longDate(Date Date) {
        if (Date == null) {
            return null;
        }

        return getFormat(dtLong).format(Date);
    }

    /**
     * 日期转换为字符串 HH:mm:ss
     *
     * @param date
     *
     * @return
     */
    public static final String hmsFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(hmsFormat).format(date);
    }

    /**
     * 时间转换字符串 2005-06-30 15:50
     *
     * @param date
     *
     * @return
     */
    public static final String simpleDate(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(simpleFormat).format(date);
    }

    /**
     * 字符串  2005-06-30 15:50 转换成时间
     * @param date String
     * @return
     * @throws ParseException
     */
    public static final Date simpleFormatDate(String dateString) throws ParseException {
        if (dateString == null) {
            return null;
        }
        return getFormat(simpleFormat).parse(dateString);
    }

    public static final Date getDiffDateTime(int diff) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        return c.getTime();
    }

    /**
     * 获取当前日期的日期时间差
     *
     * @param diff
     * @param hours
     *
     * @return
     */
    public static final String getDiffDateTime(int diff, int hours) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        c.add(Calendar.HOUR, hours);
        return dtSimpleFormat(c.getTime());
    }

    /**
     * 计算当前时间几小时之后的时间
     *
     * @param date
     * @param hours
     *
     * @return
     */
    public static Date addHours(Date date, long hours) {
        return addMinutes(date, hours * 60);
    }

    /**
     * 计算当前时间几分钟之后的时间
     *
     * @param date
     * @param minutes
     *
     * @return
     */
    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, minutes * 60);
    }

    /**
     * @param date1
     * @param secs
     *
     * @return
     */

    public static Date addSeconds(Date date1, long secs) {
        return new Date(date1.getTime() + (secs * 1000));
    }

    public static String getWebNextDayString() {
        Calendar cad = Calendar.getInstance();
        cad.setTime(new Date());
        cad.add(Calendar.DATE, 1);
        return DateUtils.dtSimpleFormat(cad.getTime());
    }

    /**
     * add by chencg 获取下一天 返回 dtshort 格式字符
     *
     * @param StringDate
     *            "20061106"
     *
     * @return String "20061107"
     *
     * @throws ParseException
     */
    public static String getNextDayDtShortToShort(String StringDate) throws ParseException {
        Date tempDate = DateUtils.shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();

        cad.setTime(tempDate);
        cad.add(Calendar.DATE, 1);
        return DateUtils.dtShortSimpleFormat(cad.getTime());
    }

    /**
     * add by daizhixia 20050808 取得相差的天数
     *
     * @param startDate 格式为 2008-08-01
     * @param endDate 格式为 2008-08-01
     *
     * @return
     */
    public static long countDays(String startDate, String endDate) {
        Date tempDate1 = null;
        Date tempDate2 = null;
        long days = 0;

        try {
            tempDate1 = DateUtils.string2Date(startDate);

            tempDate2 = DateUtils.string2Date(endDate);
            days = (tempDate2.getTime() - tempDate1.getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    /**
     * 返回日期相差天数，向下取整数
     *
     * @param dateStart
     *            一般前者小于后者dateEnd
     * @param dateEnd
     *
     * @return
     */
    public static int countDays(Date dateStart, Date dateEnd) {
        if ((dateStart == null) || (dateEnd == null)) {
            return -1;
        }

        return (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static Date now() {
        return new Date();
    }

    /**
     * 校验输入的时间格式是否合法，但不需要校验时间一定要是8位的
     *
     * @param statTime
     *
     * @return alahan add 20050901
     */
    public static boolean checkTime(String statTime) {
        if (statTime.length() > 8) {
            return false;
        }

        String[] timeArray = statTime.split(":");

        if (timeArray.length != 3) {
            return false;
        }

        for (int i = 0; i < timeArray.length; i++) {
            String tmpStr = timeArray[i];

            try {
                Integer tmpInt = new Integer(tmpStr);

                if (i == 0) {
                    if ((tmpInt.intValue() > 23) || (tmpInt.intValue() < 0)) {
                        return false;
                    } else {
                        continue;
                    }
                }

                if ((tmpInt.intValue() > 59) || (tmpInt.intValue() < 0)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    /**
     * 返回日期时间（Add by Gonglei）
     *
     * @param stringDate
     *            (yyyyMMdd)
     *
     * @return
     *
     * @throws ParseException
     */
    public static final String stringToStringDate(String stringDate) {

        if (StringUtil.isEmpty(stringDate)) {
            return null;
        }

        if (stringDate.length() != 8) {
            return null;
        }

        return stringDate.substring(0, 4) + stringDate.substring(4, 6) + stringDate.substring(6, 8);
    }

    /**
     * 加减天数
     *
     * @param date
     * @return Date
     * @author shencb 2006-12 add
     */
    public static final Date increaseDate(Date aDate, int days) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(aDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 是否闰年
     *
     * @param year
     * @return
     */
    public static final boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

    }

    /**
     * 判断是否是默认工作日，一般默认工作日是星期一都星期五， 所以，这个函数本质是判断是否是星期一到星期五
     *
     * @param date
     * @return
     */
    public static final boolean isDefaultWorkingDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return !(week == 7 || week == 1);
    }

    /**
     * 获取星期名，如“星期一”、“星期二”
     * @param date
     * @return
     */
    public static final String getWeekDay(Date date) {
        return getFormat(week).format(date);
    }

    public static Date parseDateNoTime(String sDate, String format) throws ParseException {
        if (StringUtil.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        }

        DateFormat dateFormat = new SimpleDateFormat(format);

        if ((sDate == null) || (sDate.length() < format.length())) {
            throw new ParseException("length too little", 0);
        }

        return dateFormat.parse(sDate);
    }

    /**
     * 获取当前时间的字符串格式，以半个小时为单位<br>
     * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:00
     * 当前时间2007-02-02 22:33 则返回 2007-02-02 22:30
     * @return
     */
    public static final String getNowDateForPageSelectAhead() {

        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) < 30) {
            cal.set(Calendar.MINUTE, 0);
        } else {
            cal.set(Calendar.MINUTE, 30);
        }
        return simpleDate(cal.getTime());
    }

    /**
     * 获取当前时间的字符串格式，以半个小时为单位<br>
     * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:30
     * 当前时间2007-02-02 22:33 则返回 2007-02-02 23:00
     * @return
     */
    public static final String getNowDateForPageSelectBehind() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) < 30) {
            cal.set(Calendar.MINUTE, 30);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
            cal.set(Calendar.MINUTE, 0);
        }
        return simpleDate(cal.getTime());
    }

    /**
     * 把日期2007/06/14转换为20070614
     * @method formatDateString
     * @param date
     * @return
     */
    public static String formatDateString(String date) {
        String result = "";
        if (StringUtil.isBlank(date)) {
            return "";
        }
        if (date.length() == 10) {
            result = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
        }
        return result;
    }

    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(simple);
        return getDateString(date, dateFormat);
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }

        return dateFormat.format(date);
    }

    public static final String shortStringToString(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        return shortDate(strToDtSimpleFormat(stringDate));
    }

    /**
     * 获得毫秒级的时间戳
     * @return
     */
    public static final String getTimeWithSSS() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return sdFormat.format(date);
    }

    /**
     * 判断日期合法性<pre>
     * 1.日期格式为YYYYMMDD
     * 2.日期存在
     * 
     * 例如：
     * 2010a024      ---->     false 
     * 20100631      ---->     false
     * 20101313      ---->     false
     * 20100809      ---->     true
     * </pre>
     * @param dateStr
     * @return
     */
    public static boolean checkDateValid(String dateStr) {

        Date tmpDate = null;
        try {
            tmpDate = DateUtils.shortstring2Date(dateStr);
        } catch (ParseException e) {
            return false;
        }

        if (tmpDate != null) {
            if (StringUtil.equals(DateUtils.shortDate(tmpDate), dateStr)) {
                return true;
            }
        }
        return false;
    }

    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
    }

}
