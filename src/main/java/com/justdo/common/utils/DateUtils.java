package com.justdo.common.utils;


import com.justdo.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期工具类
 *
 * @author magican
 *
 */
public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

//    public static String format(Date date) {
//
//        return format(date, DATE_PATTERN);
//    }

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATE_TIME_PATTERN);
        }
    };

    public static Date parse(String dateStr) throws ParseException {
        return threadLocal.get().parse(dateStr);
    }

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);

        }
        return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * <p>
     * Des: 只包含年月日的时间格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * <p>
     * Des: 包含年月日带时、分、秒的时间格式，时间为24小时制的
     */
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * <p>
     * Des: 包含年月日带时、分的时间格式，时间为24小时制的
     */
    public static final String DEFAULT_TIME_NO_SCE_FORMAT = "yyyy-MM-dd HH:mm";
    /**
     * <p>
     * Des: 只包含时、分、秒的时间格式，时间为24小时制的
     */
    public static final String TIME_NO_YMD_FORMAT = "HH:mm:ss";

    /**
     *
     * <p>
     * Description 将传入的日期加指定的天数
     * </p>
     * <p>
     * Company sihe
     * </p>
     * <p>
     * Copyright Copyright(c)2007
     * </p>
     *
     * @author lijiaji
     * @create time: 2007-3-2 下午04:04:15
     * @version 1.0
     * @param date 被加的时间对象
     * @param size 增加的天数
     * @modified records:
     */
    public static Date addDay(Date date, int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, size);
        return calendar.getTime();
    }

    public static Date addYear(Date date, int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, size);
        return calendar.getTime();
    }

    /**
     * 比较两日期,排除null
     * @param d1
     * @param d2
     * @return
     */
    public static boolean equals(Date d1,Date d2){
        if(d1 == null && d2 == null) return true;
        if(d1 == null) return false;
        return d1.equals(d2);
    }
    /**
     * <p>
     * Description 将传入的字符串格式的日期增加指定的天，返回减后的字符串格式的日期 字符串日期格式必需为:yyyy-MM-dd
     * </p>
     * <p>
     * Company sihe
     * </p>
     * <p>
     * Copyright Copyright(c)2007
     * </p>
     *
     * @author lijiaji
     * @create time: 2007-3-5 上午09:34:03
     * @version 1.0
     *
     * @modified records:
     */
    public static String addDay(String date, int size) {
        Date d = convertStrToDate(date, DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_YEAR, size);
        d = calendar.getTime();
        return formatToDate(d);
    }

    /**
     * <p>
     * Description 将传入的字符串格式的日期增加指定的天，返回减后的字符串格式的日期 字符串日期格式为用户指定
     * </p>
     * <p>
     * Company sihe
     * </p>
     * <p>
     * Copyright Copyright(c)2007
     * </p>
     *
     * @author lijiaji
     * @create time: 2007-3-5 上午09:43:00
     * @version 1.0
     *
     * @modified records:
     */
    public static String addDay(String date, int size, String pattern) {
        Date d = convertStrToDate(date, pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_YEAR, size);
        d = calendar.getTime();
        return formatDate(d, pattern);
    }

    /**
     *
     * <p>
     * Description 将传入的日期加指定的月数
     * </p>
     * <p>
     * Company sihe
     * </p>
     * <p>
     * Copyright Copyright(c)2007
     * </p>
     *
     * @author lijiaji
     * @create time: 2007-3-2 下午04:04:15
     * @version 1.0
     * @param date 被加的时间对象
     * @param size 加上的月数
     * @modified records:
     */
    public static Date addMonth(Date date, int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, size);
        return calendar.getTime();
    }

    /**
     *
     * <p>
     * Description 将传入的字符串格式的日期增加指定的月数
     * </p>
     * <p>
     * Company sihe
     * </p>
     * <p>
     * Copyright Copyright(c)2007
     * </p>
     *
     * @author lijiaji
     * @create time: 2007-3-5 上午09:08:57
     * @version 1.0
     *
     * @modified records:
     */
    public static String addMonth(String date, int size) {
        Date d = convertStrToDate(date, DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, size);
        d = calendar.getTime();
        return formatToDate(d);
    }

    /**
     * <p>
     * Description 将传入的字符串格式的日期增加指定的月数，返回减后的字符串格式的日期 字符串日期格式为自己定义
     * </p>
     * <p>
     * Company sihe
     * </p>
     * <p>
     * Copyright Copyright(c)2007
     * </p>
     *
     * @author lijiaji
     * @create time: 2007-3-5 上午09:24:40
     * @version 1.0
     *
     * @modified records:
     */
    public static String addMonth(String date, int size, String pattern) {
        Date d = convertStrToDate(date, pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, size);
        d = calendar.getTime();
        return formatDate(d, pattern);
    }

    /** 计算2个日期间的天数 */
    public static int betweenDay(Calendar d1, Calendar d2) {
        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(6) - d1.get(6);
        int y2 = d2.get(1);
        if (d1.get(1) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(6);
                d1.add(1, 1);
            } while (d1.get(1) != y2);
        }
        return days;
    }

    /** 计算2个日期间的天数 */
    public static int betweenDay(Date date1, Date date2) {
        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        d1.setTime(date1);
        d2.setTime(date2);
        return betweenDay(d1, d2);
    }

    /** 计算2个日期间的天数 */
    public static int betweenDay(String time1, String time2) {
        int day = 0;
        Date date1 = convertStrToDate(time1, DEFAULT_DATE_FORMAT);
        Date date2 = convertStrToDate(time2, DEFAULT_DATE_FORMAT);
        long timediff = date1.getTime() - date2.getTime();
        day = Integer.valueOf(String.valueOf(timediff / 3600000 / 24));
        return day;
    }
    /** 计算2个日期间的月差 */
    public static int betweenMonths(String time1, String time2) {
        return betweenMonths(convertStrToDate(time1, DEFAULT_TIME_NO_SCE_FORMAT),convertStrToDate(time2, DEFAULT_TIME_NO_SCE_FORMAT));
    }
    /** 计算2个日期间的yue差 */
    public static int betweenMonths(Date time1, Date time2) {
        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        d1.setTime(time1);
        d2.setTime(time2);
        int month1 = d1.get(Calendar.MONTH), year1 = d1.get(Calendar.YEAR),month2 = d2.get(Calendar.MONTH),year2 = d2.get(Calendar.YEAR);
        if(year1 == year2){
            return month1 - month2;
        }else{
            return (year1 - year2) * 12 + (month1 - month2);
        }
    }
    /** 计算2个日期间的小时差 */
    public static int betweenHours(String time1, String time2) {
        int hours = 0;
        Date date1 = convertStrToDate(time1, DEFAULT_TIME_NO_SCE_FORMAT);
        Date date2 = convertStrToDate(time2, DEFAULT_TIME_NO_SCE_FORMAT);
        long timediff = date1.getTime() - date2.getTime();
        hours = Integer.valueOf(String.valueOf(timediff / 3600000));
        return hours;
    }
    /** 计算2个日期间的小时差 */
    public static int betweenHours(Date time1, Date time2) {
        int hours = 0;
        long timediff = time1.getTime() - time2.getTime();
        hours = Integer.valueOf(String.valueOf(timediff / 3600000));
        return hours;
    }
    /** 计算2个日期间的分钟差 */
    public static int betweenMinutes(String time1, String time2) {
        int minutes = 0;
        Date date1 = convertStrToDate(time1, DEFAULT_TIME_NO_SCE_FORMAT);
        Date date2 = convertStrToDate(time2, DEFAULT_TIME_NO_SCE_FORMAT);
        long timediff = date1.getTime() - date2.getTime();
        minutes = Integer.valueOf(String.valueOf(timediff / 60000));
        return minutes;
    }
    /** 计算2个日期间的分钟差 */
    public static int betweenMinutes(Date time1, Date time2) {
        int minutes = 0;
        long timediff = time1.getTime() - time2.getTime();
        minutes = Integer.valueOf(String.valueOf(timediff / 60000));
        return minutes;
    }
    /**
     * 两个日期之间的年数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int betweenYear(String time1, String time2) {
        int year1 = Integer.parseInt(time1.split("-")[0]);
        int year2 = Integer.parseInt(time2.split("-")[0]);
        return year1 - year2 > 0 ? year1 - year2 : year2 - year1;
    }

    /** 日期转日历* */
    public static Calendar convertDateToCal(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /** 日期转换字符(动态格式转换) */
    public static String convertDateToStr(Date date, String type) {
        if (type == null) {
            return formatDefault(date);
        }
        SimpleDateFormat dateformat = new SimpleDateFormat(type);
        return dateformat.format(date);
    }

    /** 字符转换日历(动态格式转换) */
    public static Calendar convertStrToCal(String date_str, String type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(convertStrToDate(date_str, type));
        return cal;
    }

    /**
     *
     * Description 将指定格式的字符串转化为日期类型
     * @create time: 2007-3-2 下午03:07:49
     * @version 1.0
     *
     * @modified records:
     */
    public static Date convertStrToDate(String date, String pattern) {
        if (pattern == null) {
            return formatDefault(date);
        }
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        try {
            return formater.parse(date);
        } catch (ParseException e) {
            throw new BaseException(e, "时间格式不合法:[date:" + date + ",pattern:" + pattern + "]");
        }
    }

    /**
     *
     * <p>
     * Description 将date换化为用户指定的格式
     * @param pattern 日期格式
     * @param date 日期
     * @create time: 2007-3-2 下午02:47:42
     * @version 1.0
     *
     * @modified records:
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        return formater.format(date);
    }
    /**
     * String 格式的日期到 String 格式的日期
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(String date, String pattern) {
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        return formater.format(convertStrToDate(date, pattern));
    }

    /**
     *
     * Description 将date换化为yyyy-MM-dd格式的字符串
     * @create time: 2007-3-2 下午02:39:30
     * @version 1.0
     *
     * @modified records:
     */
    public static String formatToDate(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return formater.format(date);
    }

    /**
     *
     * @author 占翔
     * @description 将格式为yyyy/MM/dd格式的字符串转换为yyyy-MM- dd
     * @param str
     * @return
     * @modified
     */
    public static String replaceSlashByHyphen(String str) {
        String[] split_str = str.split("/");
        String year = split_str[0];
        String month = Integer.valueOf(split_str[1]) < 10 && !split_str[1].contains("0") ? "0" + split_str[1]
                : split_str[1];
        String days = Integer.valueOf(split_str[2]) < 10 && !split_str[2].contains("0") ? "0" + split_str[2]
                : split_str[2];
        return year + "-" + month + "-" + days;
    }

    /**
     *
     * Description 将date换化为HH:mm:ss格式的字符串
     * @create time: 2007-3-2 下午02:46:39
     * @version 1.0
     *
     * @modified records:
     */
    public static String formatToTime(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(TIME_NO_YMD_FORMAT);
        return formater.format(date);
    }

    /**
     *
     * @author 占翔
     * @description 将date换化为yyyy-MM-dd HH:mm
     * @param date
     * @return
     * @modified
     */
    public static String formatToTimeNosce(String date) {
        Date thedate = DateUtils.convertStrToDate(date, DEFAULT_DATE_FORMAT);
        SimpleDateFormat formater = new SimpleDateFormat(DEFAULT_TIME_NO_SCE_FORMAT);
        return formater.format(thedate);
    }

    /**
     *
     * Description 将date换化为yyyy-MM-dd HH:mm:ss格式的字符串
     * @create time: 2007-3-2 下午02:46:35
     * @version 1.0
     *
     * @modified records:
     */
    public static String formatToTimestamp(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat formater = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        return formater.format(date);
    }

    /**
     *
     * Description 将传入的日期减一天
     * @create time: 2007-3-2 下午04:36:40
     * @version 1.0
     * @param date 被减的时间对象
     * @modified records:
     */
    public static Date forwardDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     *
     * 获得当前年月日，加0
     *
     * @return
     */
    public static String getCurrDateWithZero() {
        String month = getCurrMonth() + "";
        String day = getDay() + "";
        if (getCurrMonth() < 10) {
            month = "0" + getCurrMonth();
        }
        if (getDay() < 10) {
            day = "0" + getDay();
        }
        return getCurrYear() + "-" + month + "-" + day;

    }

    /** 获得当前日期 */
    public static Date getCurrDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     *
     * @description 不加零
     * @return
     * @modified
     */
    public static String getCurrDateNoZero() {
        String month = getCurrMonth() + "";
        String day = getDay() + "";
        return getCurrYear() + "-" + month + "-" + day;
    }

    /**
     * 根据传入的日期取得当前的年
     *
     * @author 覃忠君
     * @param date
     * @return int
     */
    @SuppressWarnings("static-access")
    public static int getCurrentYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(cal.YEAR);
    }

    /** 获得当前月 */
    public static int getCurrMonth() {
        Calendar cal = Calendar.getInstance();
        return (cal.get(Calendar.MONTH) + 1);
    }

	/*
	 * ###############################返回给定日期####### #######################
	 */
    /** 获得本月的第一天的日期 */
    public static Date getCurrMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        String s = (getYear(cal)) + "-" + (getMonth(cal)) + "-01";
        return convertStrToDate(s, DEFAULT_DATE_FORMAT);
    }

    /** 获得本月的最后一天的日期 */
    public static Date getCurrMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        String s = (getYear(cal)) + "-" + (getMonth(cal)) + "-" + getDays(cal);
        return convertStrToDate(s, DEFAULT_DATE_FORMAT);
    }

    /** 获得当前年 */
    public static int getCurrYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    // 将startDate和endDate范围内的日期加入到List（List中包括startDate和endDate）中，return
    // List<Date>
//    public static List<Date> getDateListBetweenDates(Date startDate, Date endDate) {
//        List<Date> list = new ArrayList<Date>();
//        Calendar startTime = Calendar.getInstance();
//        startTime.clear();
//        startTime.setTime(startDate);
//        int startYear = startTime.get(1);
//        int startMonth = startTime.get(2);
//        int startDay = startTime.get(5);
//
//        Calendar endTime = Calendar.getInstance();
//        endTime.clear();
//        endTime.setTime(endDate);
//        int endYear = endTime.get(1);
//        int endMonth = endTime.get(2);
//        int endDay = endTime.get(5);
//
//        for (int x = startYear; x <= endYear; ++x) {
//            boolean isLeapYear = (x % 4 == 0) && (((x % 100 != 0) || (x % 400 == 0)));
//
//            int max = 0;
//            if (startMonth == 1) {
//                if (isLeapYear)
//                    max = 29;
//
//                if (!(isLeapYear))
//                    max = 28;
//            }
//
//            if ((startMonth == 3) || (startMonth == 5) || (startMonth == 8) || (startMonth == 10))
//                max = 30;
//
//            if ((startMonth == 0) || (startMonth == 2) || (startMonth == 4) || (startMonth == 6) || (startMonth == 7)
//                    || (startMonth == 9) || (startMonth == 11)) {
//                max = 31;
//            }
//
//            int y = 0;
//
//            if (x == startYear)
//                y = startMonth;
//
//            for (; y < 12; ++y) {
//                max = 0;
//                if (y == 1) {
//                    if (isLeapYear)
//                        max = 29;
//
//                    if (!(isLeapYear))
//                        max = 28;
//                }
//
//                if ((y == 3) || (y == 5) || (y == 8) || (y == 10))
//                    max = 30;
//
//                if ((y == 0) || (y == 2) || (y == 4) || (y == 6) || (y == 7) || (y == 9) || (y == 11))
//                    max = 31;
//
//                int ty = y + 1;
//
//                int z = 1;
//
//                if ((x == startYear) && (y == startMonth))
//                    z = startDay;
//
//                for (; z <= max; ++z) {
//                    String str_temp = x + "-" + ty + "-" + z;
//                    SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
//                    Date date_temp = null;
//                    try {
//                        date_temp = format.parse(str_temp);
//                    } catch (ParseException e) {
//                        throw new BaseException(e, "时间格式不合法");
//                    }
//                    list.add(date_temp);
//                    if ((x == endYear) && (y == endMonth) && (z == endDay))
//                        break;
//
//                }
//
//                if ((x == endYear) && (y == endMonth))
//                    break;
//            }
//
//        }
//
//        return list;
//    }

    /** 获得当前天 */
    public static int getDay() {
        Calendar cal = Calendar.getInstance();
        return (cal.get(Calendar.DAY_OF_MONTH));
    }

    /** 获得给定日期的当天 */
    public static int getDay(Calendar cal) {
        return (cal.get(Calendar.DAY_OF_MONTH));
    }

    /** 获得给定日期的当天 */
    public static int getDay(Date date) {
        return (convertDateToCal(date).get(Calendar.DAY_OF_MONTH));
    }

    /** 获得给定日期的当天 */
    public static int getDay(String date_str, String type) {
        return (convertStrToCal(date_str, type).get(Calendar.DAY_OF_MONTH));
    }

    /** 获得给定日期当月的天数 */
    public static int getDays(Calendar cal) {
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /** 获得给定日期当月的天数 */
    public static int getDays(Date date) {
        return (convertDateToCal(date).getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    // 获取date_str中的月的天数。
    public static int getDays(String date_str) {
        Calendar c = convertStrToCal(date_str, DEFAULT_DATE_FORMAT);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /** 获得给定日期当月的天数 */
    public static int getDays(String date_str, String type) {
        return (convertStrToCal(date_str, type).getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     *
     * @author 占翔
     * @description 给定时间月最后1天
     * @param time
     * @return
     * @modified
     */
    public static String getEndForMonth(String time) {
        Date thetime = convertStrToDate(time, DEFAULT_DATE_FORMAT);
        int year = getYear(thetime);
        String month = getMonth(thetime) > 9 ? String.valueOf(getMonth(thetime)) : "0"
                + String.valueOf(getMonth(thetime));
        int days = getDays(thetime);
        return year + "-" + month + "-" + days;
    }

    /**
     *
     * @author 占翔
     * @description 获得给定时间月第一天
     * @param time
     * @return
     * @modified
     */
    public static String getFirstforMonth(String time) {
        Date thetime = convertStrToDate(time, DEFAULT_DATE_FORMAT);
        int year = getYear(thetime);
        int month = getMonth(thetime);
        String tmpmonth = String.valueOf(month);
        if (month < 10)
            tmpmonth = "0" + tmpmonth;
        return year + "-" + tmpmonth + "-" + "01";
    }

    /** 获得给定日历的月 */
    public static int getMonth(Calendar cal) {
        return (cal.get(Calendar.MONTH) + 1);
    }

    /** 获得给定日期的月 */
    public static int getMonth(Date date) {
        return (convertDateToCal(date).get(Calendar.MONTH) + 1);
    }

    /** 获得给定日期字符串对应的月 */
    public static int getMonth(String date_str, String type) {
        return (convertStrToCal(date_str, type).get(Calendar.MONTH) + 1);
    }

    // 获取date中月的的英文单词简写比如2013-08-01结果为：Aug
    public static String getMonth_en(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.US);
        return sdf.format(date);
    }

	/*
	 * ###############################字符格式和日期格式的转换#
	 * #############################
	 */

    // 获取时间格式字符串的中月的的英文单词简写比如2013-08-01结果为：Aug
    public static String getMonth_en(String datestr) {
        Date date = convertStrToDate(datestr, "yyyy-MM");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.US);
        return sdf.format(date);
    }

    /**
     *
     * @author 占翔
     * @description 根据当月最后1天 取该周星期六
     * @param time
     * @return
     * @modified
     */
    public static String getSaturday(String time) {
        String week = DateUtils.getWeek(DateUtils.convertStrToDate(time, DEFAULT_DATE_FORMAT));
        String saturday = "";
        if (week.equals("星期六")) {
            saturday = DateUtils.formatDate(DateUtils.convertStrToDate(time, DEFAULT_DATE_FORMAT), DEFAULT_DATE_FORMAT);
            ;
        }
        if (week.equals("星期日")) {
            saturday = DateUtils.addDay(time, 6);
        }
        if (week.equals("星期一")) {
            saturday = DateUtils.addDay(time, 5);
        }
        if (week.equals("星期二")) {
            saturday = DateUtils.addDay(time, 4);
        }
        if (week.equals("星期三")) {
            saturday = DateUtils.addDay(time, 3);
        }
        if (week.equals("星期四")) {
            saturday = DateUtils.addDay(time, 2);
        }
        if (week.equals("星期五")) {
            saturday = DateUtils.addDay(time, 1);
        }
        return saturday;
    }

    // 将startDate和endDate范围内的日期加入到List（List中包括startDate和endDate）中，格式为yyyy-MM-dd,return
    // List<String>
    public static List<String> getStringListBetweenDates(Date startDate, Date endDate) {
        List<String> list = new ArrayList<String>();
        Calendar startTime = Calendar.getInstance();
        startTime.clear();
        startTime.setTime(startDate);
        int startYear = startTime.get(1);
        int startMonth = startTime.get(2);
        int startDay = startTime.get(5);

        Calendar endTime = Calendar.getInstance();
        endTime.clear();
        endTime.setTime(endDate);
        int endYear = endTime.get(1);
        int endMonth = endTime.get(2);
        int endDay = endTime.get(5);

        for (int x = startYear; x <= endYear; ++x) {
            boolean isLeapYear = (x % 4 == 0) && (((x % 100 != 0) || (x % 400 == 0)));

            int max = 0;
            if (startMonth == 1) {
                if (isLeapYear)
                    max = 29;

                if (!(isLeapYear))
                    max = 28;
            }

            if ((startMonth == 3) || (startMonth == 5) || (startMonth == 8) || (startMonth == 10))
                max = 30;

            if ((startMonth == 0) || (startMonth == 2) || (startMonth == 4) || (startMonth == 6) || (startMonth == 7)
                    || (startMonth == 9) || (startMonth == 11)) {
                max = 31;
            }

            int y = 0;

            if (x == startYear)
                y = startMonth;

            for (; y < 12; ++y) {
                max = 0;
                if (y == 1) {
                    if (isLeapYear)
                        max = 29;

                    if (!(isLeapYear))
                        max = 28;
                }

                if ((y == 3) || (y == 5) || (y == 8) || (y == 10))
                    max = 30;

                if ((y == 0) || (y == 2) || (y == 4) || (y == 6) || (y == 7) || (y == 9) || (y == 11))
                    max = 31;

                int ty = y + 1;

                int z = 1;

                if ((x == startYear) && (y == startMonth))
                    z = startDay;

                for (; z <= max; ++z) {
                    list.add(x + "-" + ty + "-" + z);
                    if ((x == endYear) && (y == endMonth) && (z == endDay))
                        break;

                }

                if ((x == endYear) && (y == endMonth))
                    break;
            }

        }

        return list;
    }

    /**
     *
     * @author 占翔
     * @description 根据当月第一天取该周的星期天的日期
     * @param time
     * @return
     * @modified
     */

    public static String getSunday(String time) {
        String week = DateUtils.getWeek(DateUtils.convertStrToDate(time, DEFAULT_DATE_FORMAT)); // 获得该天所在星期数

        String sunday = "";
        if (week.equals("星期日")) {
            sunday = DateUtils.formatDate(DateUtils.convertStrToDate(time, DEFAULT_DATE_FORMAT), DEFAULT_DATE_FORMAT); // 把日期转换成yyyy-mm-dd
            // 方便数据库查询
        }
        if (week.equals("星期一")) {
            sunday = DateUtils.reduceDay(time, 1);
        }
        if (week.equals("星期二")) {
            sunday = DateUtils.reduceDay(time, 2);
        }
        if (week.equals("星期三")) {
            sunday = DateUtils.reduceDay(time, 3);
        }
        if (week.equals("星期四")) {
            sunday = DateUtils.reduceDay(time, 4);
        }
        if (week.equals("星期五")) {
            sunday = DateUtils.reduceDay(time, 5);
        }
        if (week.equals("星期六")) {
            sunday = DateUtils.reduceDay(time, 6);
        }
        return sunday;
    }

    /**
     * 获取给定日期的开始结束日期。
     *
     * @return 返回季度的开始结束日期字符串，如：2013-7-1|2013-9-30
     */
    public static String getThisSeasonTime(Date dateinput) {
        int month = getMonth(dateinput);
        int[][] array = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if ((month >= 1) && (month <= 3))
            season = 1;

        if ((month >= 4) && (month <= 6))
            season = 2;

        if ((month >= 7) && (month <= 9))
            season = 3;

        if ((month >= 10) && (month <= 12))
            season = 4;

        int start_month = array[(season - 1)][0];
        int end_month = array[(season - 1)][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;
        int end_days = getDays(years_value + "-" + end_month + "-" + "01");
        String seasonDate = years_value + "-" + start_month + "-" + start_days + "|" + years_value + "-" + end_month
                + "-" + end_days;
        return seasonDate;
    }

    // 获取给定日期的中文星期几（返回中文）
    public static String getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("E").format(c.getTime());
    }


    /**
     * 获得给定日期的是星期几（返回中文）
     *
     * @author 朱健
     * @description
     * @param date
     * @return
     * @modified
     */
    public static String getWeek(String date) {
        Calendar calendar = Calendar.getInstance();
        Date d = convertStrToDate(date, DEFAULT_DATE_FORMAT);
        calendar.setTime(d);
        return new SimpleDateFormat("E").format(calendar.getTime());
    }

    // 取得给定日期的英文简写
    public static String getWeek_en(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("E", Locale.US).format(c.getTime());
    }

    // 取得给定日期的英文简写
    public static String getWeek_en(String datestr) {
        Date date = convertStrToDate(datestr, DEFAULT_DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("E", Locale.US).format(c.getTime());
    }

	/*
	 * ###############################日期字符格式验证##### #########################
	 */

    /**
     * 返回一年中的第几周数 格式为：yyyy-周数 2009-01
     *
     * @author 朱健
     * @description
     * @param date
     * @return
     * @modified
     */
    public static String getWeekInYear(String date) {
        Calendar calendar = Calendar.getInstance();
        String[] tmp = date.split("-");
        int year = Integer.parseInt(tmp[0]);
        int day = Integer.parseInt(tmp[2]);
        if ("12".equals(tmp[1]) && day > 23) {
            calendar.set(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]) - 1, Integer.parseInt(tmp[2]));
            int weektmp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (weektmp == 6)
                weektmp = 0;// 定义星期六为第一天
            else
                weektmp++;
            calendar.add(Calendar.DATE, (7 - weektmp));

            String[] tmp2 = DateUtils.formatDate(calendar.getTime(), DEFAULT_DATE_FORMAT).split("-");
            year = Integer.parseInt(tmp2[0]);

        }
        calendar.set(year, 0, 1); // 月是实际月份减1
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week != 6) {
            calendar.add(Calendar.DATE, -(week + 1));
        }

        Date startdate = calendar.getTime();
        calendar.set(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]) - 1, Integer.parseInt(tmp[2]));
        Date enddate = calendar.getTime();

        long l = enddate.getTime() - startdate.getTime();
        long value = (l / (1000 * 24 * 60 * 60) / 7) + 1;
        String rtvalue = year + "-";
        if (value < 10) {
            rtvalue = rtvalue + "0" + value;
        } else {
            rtvalue += value;
        }
        return rtvalue;
    }

    /** 获得给定日历的年 */
    public static int getYear(Calendar cal) {
        return cal.get(Calendar.YEAR);
    }

    /** 获得给定日期的年 */
    public static int getYear(Date date) {
        return convertDateToCal(date).get(Calendar.YEAR);
    }

    /** 获得给定日期字符串对应的年 */
    public static int getYear(String date_str, String type) {
        return (convertStrToCal(date_str, type).get(Calendar.YEAR));
    }

    /**
     *
     * @author 占翔
     * @description 判断日期格式是否正确 把月份 天数也考虑进去了
     * @param date
     * @return
     * @modified
     */
    public static boolean isDate(String date) {
        boolean boo = true;
        if (isDate(date, DEFAULT_DATE_FORMAT)) {
            String[] split_date = date.split("\\-");
            try {
                int year = Integer.valueOf(split_date[0]);
                int month = Integer.valueOf(split_date[1]);
                int day = Integer.valueOf(split_date[2]);
                if (year < 0) {
                    boo = false;
                }

                if (month > 12 || month < 0) {
                    boo = false;
                }
                if (day < 0) {
                    boo = false;
                }

                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    if (day > 31) {
                        boo = false;
                    }
                }
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    if (day > 30) {
                        boo = false;
                    }
                }
                if (month == 2) {
                    java.util.GregorianCalendar c = new java.util.GregorianCalendar();
                    if (c.isLeapYear(year)) // 判断是不是闰年
                    {
                        if (day > 29) {
                            return false;
                        }
                    } else {
                        if (day > 28) {
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                boo = false;
            }
        } else {
            boo = false;
        }
        return boo;
    }

    /**
     * 判断日期格式是否正确
     * */
    public static boolean isDate(String date_str, String type) {
        SimpleDateFormat dateformat = new SimpleDateFormat(type);
        try {
            dateformat.parse(date_str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * Description 将传入的日期加一天
     * @create time: 2007-3-2 下午03:38:49
     * @version 1.0
     * @param date 被加的时间对象
     * @modified records:
     */
    public static Date nextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     *
     * @author 朱健
     * @description 获取下一个月
     * @param month
     * @return
     * @modified
     */
    public static String nextMonth(String month) {
        Date d = DateUtils.convertStrToDate(month, "yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, 1);
        d = calendar.getTime();
        return DateUtils.formatDate(d, "yyyy-MM");
    }

    /**
     * 获取后一周
     *
     * @author 朱健
     * @description 获取后一周 周数格式(2009-01)表示2009年第一周
     * @param date
     * @return
     * @modified
     */
    public static String nextWeek(String date) {
        String[] tmp = date.split("-");
        int year = Integer.parseInt(tmp[0]);
        int week = Integer.parseInt(tmp[1]);
        week++;
        if (week > 52) {
            year++;
            week = 1;
        }
        String weekStr = String.valueOf(week);
        if (week < 10)
            weekStr = "0" + weekStr;
        return String.valueOf(year) + "-" + weekStr;

    }

    /**
     *
     * @author 朱健
     * @description 获取上一个月
     * @param month
     * @return
     * @modified
     */
    public static String prevMonth(String month) {
        Date d = DateUtils.convertStrToDate(month, "yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -1);
        d = calendar.getTime();
        return DateUtils.formatDate(d, "yyyy-MM");
    }

    /**
     * 获取前一周
     *
     * @author 朱健
     * @description 获取前一周 周数格式(2009-01)表示2009年第一周
     * @param date
     * @return
     * @modified
     */
    public static String PrevWeek(String date) {
        String[] tmp = date.split("-");
        int year = Integer.parseInt(tmp[0]);
        int week = Integer.parseInt(tmp[1]);
        week--;
        if (week <= 0) {
            year--;
            week = 52;
        }
        String weekStr = String.valueOf(week);
        if (week < 10)
            weekStr = "0" + weekStr;
        return String.valueOf(year) + "-" + weekStr;

    }

    /**
     *
     * Description 将传入的日期加指定的天数
     * @create time: 2007-3-2 下午04:33:58
     * @version 1.0
     * @param date 被减的时间对象
     * @param size 减去的天数
     * @modified records:
     */
    public static Date reduceDay(Date date, int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -size);
        return calendar.getTime();
    }

    /**
     *
     * Description 将传入的字符串格式的日期减去指定的天，返回减后的字符串格式的日期 字符串日期格式必需为:yyyy-MM-dd
     * @create time: 2007-3-2 下午04:36:40
     * @version 1.0
     * @param date 被减的时间对象
     * @modified records:
     */
    public static String reduceDay(String date, int size) {
        Date d = convertStrToDate(date, DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_YEAR, -size);
        return formatToDate(calendar.getTime());
    }

    /**
     *
     * Description 将传入的字符串格式的日期减一天，返回减后的字符串格式的日期 字符串日期格式为自己定义
     * @create time: 2007-3-2 下午05:13:14
     * @version 1.0
     *
     * @modified records:
     */
    public static String reduceDay(String date, int size, String pattern) {
        Date d = convertStrToDate(date, pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_YEAR, -size);
        return formatDate(calendar.getTime(), pattern);
    }

    /**
     *
     * Description 将传入的日期减去指定的月数
     * @create time: 2007-3-2 下午04:39:56
     * @version 1.0
     * @param date 被减的时间对象
     * @param size 减去的月数
     * @modified records:
     */
    public static Date reduceMonth(Date date, int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -size);
        return calendar.getTime();
    }

    /**
     *
     * Description 将传入的字符串日期格式减去指定的月数
     * @create time: 2007-3-2 下午04:39:56
     * @version 1.0
     * @param date 被减的时间对象
     * @param size 减去的月数
     * @modified records:
     */
    public static String reduceMonth(String date, int size) {
        Date d = convertStrToDate(date, DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -size);
        d = calendar.getTime();
        return formatToDate(d);
    }

    /**
     *
     * Description 将传入的字符串格式的日期减去指定的月数，返回减后的字符串格式的日期 字符串日期格式为自己定义
     * @create time: 2007-3-5 上午09:18:01
     * @version 1.0
     *
     * @modified records:
     */
    public static String reduceMonth(String date, int size, String pattern) {
        Date d = convertStrToDate(date, pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -size);
        d = calendar.getTime();
        return formatDate(d, pattern);
    }

    /**
     *
     * @author 占翔
     * @description 转成yyyy-MM-dd 类型 加0
     * @param time
     * @return
     * @modified
     */
    public static String setDate(String time) {
        String aa[] = time.split("-");
        int year = Integer.valueOf(aa[0]);
        int month = Integer.valueOf(aa[1]);
        int day = Integer.valueOf(aa[2]);
        String mm = "";
        String dd = "";
        if (month < 10) {
            mm = "0" + month;
        } else {
            mm = String.valueOf(month);
        }
        if (day < 10) {
            dd = "0" + day;
        } else {
            dd = String.valueOf(day);
        }
        time = year + "-" + mm + "-" + dd;
        return time;
    }

    /**
     * 将时间按照默认格式转换为时间类型<br>
     * 默认匹配格式顺序<br>
     * 1.yyyy-MM-dd HH:mm:ss
     * 2.yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static Date formatDefault(String dateStr){
        try {
            return convertStrToDate(dateStr, DateUtils.DEFAULT_TIME_FORMAT);
        } catch (Exception e) {
            return convertStrToDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT);
        }
    }

    /**
     * 将时间按照默认格式转换为字符串<br>
     * 默认匹配格式顺序<br>
     * 1.yyyy-MM-dd HH:mm:ss
     * 2.yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDefault(Date date){
        try {
            return convertDateToStr(date, DateUtils.DEFAULT_TIME_FORMAT);
        } catch (Exception e) {
            return convertDateToStr(date, DateUtils.DEFAULT_DATE_FORMAT);
        }
    }

    /**
     * 获取当前时间的字符串
     * @return
     */
    public static String getTimeNow(){
        return convertDateToStr(new Date(), DateUtils.DEFAULT_TIME_FORMAT);
    }
    /**
     * 获取当前时间的字符串
     * @return
     */
    public static String formatTimeNow(String format){
        return convertDateToStr(new Date(), format);
    }
}
