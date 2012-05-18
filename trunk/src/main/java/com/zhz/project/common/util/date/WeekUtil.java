/**
 * Zhznet.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.zhz.project.common.util.date;

/**
 *
 * @author Administrator
 * @version $Id: WeekUtil.java, v 0.1 May 18, 2012 9:09:43 AM Administrator Exp $
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 * @version $Id: WeekUtil.java, v 0.1 May 18, 2012 9:15:10 AM Administrator Exp $
 */
public class WeekUtil {
    private static Logger logger = Logger.getLogger(WeekUtil.class);

    /**
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<List<Date>> dateListByWeek(Date beginDate, Date endDate) {

        List<List<Date>> weekList = new ArrayList<List<Date>>();

        Calendar cal = Calendar.getInstance();

        cal.setTime(beginDate);

        int firstWeek = cal.get(Calendar.WEEK_OF_YEAR);

        cal.setTime(endDate);

        int lastWeek = cal.get(Calendar.WEEK_OF_YEAR);

        int weekCount = lastWeek - firstWeek;

        List<Date> dateList = null;

        if (weekCount == 0) {
            System.out.println("起止日期都在同一周内");
            dateList = calcDateList(beginDate, endDate);
            weekList.add(dateList);

        } else if (weekCount == 1) {
            System.out.println("横跨两周");
            weekList.add(dateToWeekend(beginDate));
            weekList.add(dateToWeekBegin(endDate));

        } else {
            System.out.println("横跨多周");

            weekList.add(dateToWeekend(beginDate));

            Date d1 = dateAtNextWeekBegin(beginDate);

            Date d2 = dateAtBeforeWeekEnd(endDate);

            long dateDiff = DateUtils.getDiffDays(d2, d1) + 2;
            long weekDiff = dateDiff / 7;
            cal.setTime(d1);
            for (int i = 0; i < weekDiff; i++) {
                cal.add(Calendar.DATE, 0);
                Date d3 = cal.getTime();
                cal.add(Calendar.DATE, 6);
                Date d4 = cal.getTime();
                weekList.add(calcDateList(d3, d4));

                cal.add(Calendar.DATE, 1);
                Date d5 = cal.getTime();
                cal.setTime(d5);
            }

            weekList.add(dateToWeekBegin(endDate));

        }

        return weekList;
    }

    /**
     * 测试通过
     * 返回当日到本周周末的日期列表，以星期天作为起始，包括当日
     * @param date
     * @return
     */
    public static List<Date> dateToWeekend(Date date) {
        Date dateAtWeekend = dateAtWeekend(date);
        List<Date> dateList = calcDateList(date, dateAtWeekend);
        return dateList;
    }

    /**
     * 测试通过
     * 返回当周的最后一天，以星期天作为起始，
     * @param date
     * @return
     */
    public static Date dateAtWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dateToWeekendDays = calcDaysLast(date);
        cal.add(Calendar.DATE, dateToWeekendDays);
        return cal.getTime();
    }

    /**
      * 测试通过
      * 返回本周开始到当日的日期列表，以星期天作为起始，包括当日
      * @param date
      * @return
      */
    public static List<Date> dateToWeekBegin(Date date) {
        Date dateAtWeekBegin = dateAtWeekBegin(date);
        List<Date> dateList = calcDateList(dateAtWeekBegin, date);
        return dateList;
    }

    /**
     * 测试通过
     * 返回当周的第一天，以星期天作为起始，
     * @param date
     * @return
     */
    public static Date dateAtWeekBegin(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dateToWeekBeginDays = calcDaysLast(date);
        cal.add(Calendar.DATE, dateToWeekBeginDays - 6);
        Date result = cal.getTime();
        return result;
    }

    /**
     * 测试通过
     * 返回beginDate和endDate之间的日期的列表，包括beginDate和endDate
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> calcDateList(Date beginDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(beginDate);

        long diff = DateUtils.getDiffDays(endDate, beginDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);

        for (int i = 0; i < diff; i++) {
            cal.add(Calendar.DATE, 1);
            Date date = cal.getTime();
            dateList.add(date);
        }
        return dateList;

    }

    /**
     * 计算该日离周末还有几天（不包括当日），以星期天作为起始，
     * 即星期天离周末还有6天，星期六离周末还有0天
     * @param date
     * @return
     */
    public static int calcDaysLast(Date date) {
        SimpleDateFormat weekformatter = new SimpleDateFormat("E");
        String whichday = weekformatter.format(date);

        if (whichday.equals(Constants.Monday)) {
            return 5;
        } else if (whichday.equals(Constants.Tuesday)) {
            return 4;
        } else if (whichday.equals(Constants.Wednesday)) {
            return 3;
        } else if (whichday.equals(Constants.Thursday)) {
            return 2;
        } else if (whichday.equals(Constants.Friday)) {
            return 1;
        } else if (whichday.equals(Constants.Saturday)) {
            return 0;
        } else
            return 6;
    }

    ////////////////////////////////////
    /**
     * 测试通过
     * 返回下周的第一天，以星期天作为起始，
     * @param date
     * @return
     */
    public static Date dateAtNextWeekBegin(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dateToWeekendDays = calcDaysLast(date);
        cal.add(Calendar.DATE, dateToWeekendDays + 1);
        return cal.getTime();
    }

    /**
     * 测试通过
     * 返回上周的最后一天，以星期天作为起始，
     * @param date
     * @return
     */
    public static Date dateAtBeforeWeekEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dateToWeekBeginDays = calcDaysLast(date);
        cal.add(Calendar.DATE, dateToWeekBeginDays - 7);
        return cal.getTime();
    }

    public class Constants {
        public static final String Saturday  = "星期一";
        public static final String Friday    = "星期二";
        public static final String Thursday  = "星期三";
        public static final String Wednesday = "星期四";
        public static final String Tuesday   = "星期五";
        public static final String Monday    = "星期六";
    }

}
