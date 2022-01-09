package com.zlgg.study.common.elasticsearch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 23:03
 * Description:
 */
public class DateUtil {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";

    private static Calendar calendar;
    private static Date date;

    public static synchronized String getDateStr(String dateFormat, int offset) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        if (offset != 0) {
            date = new Date(date.getTime() + offset);
        }
        return sdf.format(date);
    }

    public static String getDateStr(String dateFormat) {

        return getDateStr(dateFormat, 0);
    }

    public static String getDateStr() {
        return getDateStr(DATE_TIME);
    }

    public static synchronized Date getDate(String dateFormat, int offset) {
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        if (offset != 0) {
            date = new Date(date.getTime() + offset);
        }
        return date;
    }

    public static Date getDate(String dateFormat) {
        return getDate(dateFormat, 0);
    }

    public static Date getDate() {
        return getDate(DATE_TIME, 0);
    }
}
