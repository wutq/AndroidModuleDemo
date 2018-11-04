package com.wss.common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.wss.common.constants.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Describe：日期帮助类
 * Created by 吴天强 on 2018/10/15.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    /**
     * 获取当前时间戳
     *
     * @return long
     */
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取格式化的当前系统时间
     *
     * @return String
     */
    public static String getCurrentDateStr() {
        return getFormatDate(getCurrentTimeStamp(), Constants.DATE_FORMAT_LINE);
    }


    /**
     * 获取格式化时间
     *
     * @param timeStamp 时间戳
     * @param pattern   格式化格式（默认yyyy-MM-dd HH:mm:ss）
     */
    public static String getFormatDate(long timeStamp, String pattern) {
        String time;
        if (Long.toString(Math.abs(timeStamp)).length() < 11) {
            timeStamp *= 1000;
        }
        if (TextUtils.isEmpty(pattern)) {
            pattern = Constants.DATE_FORMAT_DEFAULT;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getDefault());
        time = format.format(timeStamp);
        return time;
    }

    /**
     * 格式化返回日期时间
     *
     * @param stringDate stringDate
     * @param pattern    pattern
     * @return String
     */
    public static String getFormatDate(String stringDate, String pattern) {
        if (TextUtils.isEmpty(stringDate)) {
            return "";
        }
        String parentPattern;
        if (stringDate.length() == 16) {
            parentPattern = "yyyy-MM-dd HH:mm";
        } else if (stringDate.length() == 19) {
            parentPattern = "yyyy-MM-dd HH:mm:ss";
        } else {
            return stringDate;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);
        try {
            return sdf1.format(new SimpleDateFormat(parentPattern).parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringDate;
    }

    /**
     * 获取系统的时间
     *
     * @return String
     */
    public static String getCurrentTimeStr() {
        SimpleDateFormat fort = new SimpleDateFormat("HH:mm:ss");
        fort.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return fort.format(getCurrentTimeStamp());
    }

    /**
     * 获取当前时间的下一天/ 前一天时间
     *
     * @param time time
     * @param day  正数为以后  负数为以前
     * @return
     */
    public static long getNextDayTimeStamp(long time, int day) {
        long timeStamp = 0;
        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            timeStamp = getStringToTimeStamp(sdf.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            return timeStamp;
        }
        return timeStamp;
    }

    /**
     * 根据字符串时间获取时间戳
     *
     * @param stringDate stringDate
     * @return long
     */
    public static long getStringToTimeStamp(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date;
        long timeStamp = 0;
        if (TextUtils.isEmpty(stringDate)) {
            return timeStamp;
        }
        try {
            date = simpleDateFormat.parse(stringDate);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            return timeStamp;
        }
        return timeStamp;
    }

    /**
     * 根据当前时间获取问候语
     *
     * @return 问候语
     */
    public static String getTimeTransformation() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨";
        } else if (hour >= 5 && hour < 7) {
            return "清晨";
        } else if (hour >= 7 && hour < 9) {
            return "早上";
        } else if (hour >= 9 && hour < 12) {
            return "上午";
        } else if (hour >= 12 && hour < 14) {
            return "中午";
        } else if (hour >= 14 && hour < 17) {
            return "下午";
        } else if (hour >= 17 && hour < 19) {
            return "傍晚";
        } else if (hour >= 19 && hour < 21) {
            return "晚上";
        } else if (hour >= 21 && hour < 24) {
            return "深夜";
        }
        return "";
    }

    /**
     * 时间转换
     *
     * @param date 转换的时间戳
     */
    public static String dateTransformation(long date) {
        long difference = (getCurrentTimeStamp() / 1000) - (date / 1000);
        if (difference > 0) {
            if (difference < 60 * 60) {
                return difference / 60 + "分钟前";
            } else if (difference < 24 * 60 * 60) {

                return (int) (difference / 60 / 60) + "小时前";
            } else if (difference < 30 * 24 * 60 * 60) {

                return (int) (difference / 60 / 60 / 24) + "日前";
            } else {
                return getFormatDate(date, Constants.DATE_FORMAT_SLASH);
            }
        } else {
            return getFormatDate(date, Constants.DATE_FORMAT_SLASH);
        }
    }
}
