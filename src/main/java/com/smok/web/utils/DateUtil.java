package com.smok.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by smok on 2016/1/5.
 */
public class DateUtil {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";


    /**
     * 格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化时间 返回格式：yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return format(date, DATE_FORMAT);
    }

    /**
     * 格式化时间 返回格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, DATETIME_FORMAT);
    }

    /**
     * 解析时间字符串
     *
     * @param dateTime
     * @param pattern
     * @return
     * @throws Exception
     */
    public static Date parse(String dateTime, String pattern) throws Exception {
        return new SimpleDateFormat(pattern).parse(dateTime);
    }

    /**
     * 解析时间字符串 格式为yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseDate(String date) throws Exception {
        return parse(date, DATE_FORMAT);
    }

    /**
     * 解析时间字符串 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     * @throws Exception
     */
    public static Date parseDateTime(String dateTime) throws Exception {
        return parse(dateTime, DATETIME_FORMAT);
    }

    public static String getTodayDate() {
        return formatDate(new Date());
    }

    public static String getYesterdayDate() {
        return formatDate(getYestoday());
    }

    public static String getTomorrowDate() {
        return formatDate(getTomorrow());
    }

    public static String getCurrentDateTime() {
        return formatDateTime(new Date());
    }

    public static Date getYestoday() {
        return getDate(Calendar.getInstance().getTime(), -1);
    }

    public static Date getTomorrow() {
        return getDate(Calendar.getInstance().getTime(), 1);
    }

    public static Date getDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 对比date2比date1多多少天,0-同一天,负数-早n天,正数-晚n天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int diffDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 365 + cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
    }

}
