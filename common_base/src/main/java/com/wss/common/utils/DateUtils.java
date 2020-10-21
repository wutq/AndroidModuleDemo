package com.wss.common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Describe：日期帮助类
 * Created by 吴天强 on 2018/10/15.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    public static final String DATE_FORMAT = "yyyyMMddHH";
    public static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";
    public static final String DATE_FORMAT_LINE = "yyyy-MM-dd";
    public static final String DATE_FORMAT_TIME = "HH:mm:ss";
    public static final String DATE_FORMAT_DEFAULT = DATE_FORMAT_SLASH + " " + DATE_FORMAT_TIME;
    private static final int SECOND = 60;
    private static final int DAY = 60;
    private static final int MOUTH = 60;


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
        return getFormatDate(getCurrentTimeStamp(), DATE_FORMAT_LINE);
    }

    /**
     * 获取格式化的当前系统时间
     *
     * @return String
     */
    public static String getCurrentDateStr(String pattern) {
        return getFormatDate(getCurrentTimeStamp(), pattern);
    }

    /**
     * 获取格式化时间
     *
     * @param date    date
     * @param pattern 格式化格式（默认yyyy-MM-dd HH:mm:ss）
     */
    @NotNull
    public static String getFormatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (!ValidUtils.isValid(pattern)) {
            pattern = DATE_FORMAT_DEFAULT;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
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
            pattern = DATE_FORMAT_DEFAULT;
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
    public static String getFormatDate2(String stringDate, String pattern) {
        if (TextUtils.isEmpty(stringDate)) {
            return "";
        }
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);
            String sourcePattern = String.format("%s %s", DATE_FORMAT_LINE, DATE_FORMAT_TIME);
            return sdf1.format(new SimpleDateFormat(sourcePattern).parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringDate;
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
        switch (stringDate.length()) {
            case 10:
                parentPattern = "yyyy-MM-dd";
                break;
            case 16:
                parentPattern = "yyyy-MM-dd HH:mm";
                break;
            case 19:
                parentPattern = "yyyy-MM-dd HH:mm:ss";
                break;
            default:
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
     * 日期转日历
     */
    @NotNull
    public static Calendar getFormatDate(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_LINE);
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(stringDate);
            if (date != null) {
                calendar.setTime(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
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
            if (date != null) {
                timeStamp = date.getTime();
            }
        } catch (ParseException e) {
            return timeStamp;
        }
        return timeStamp;
    }

    /**
     * 时间转换
     *
     * @param date 转换的时间戳
     */
    public static String dateTransformation(long date) {
        long difference = (getCurrentTimeStamp() / 1000) - (date / 1000);
        if (difference > 0) {
            if (difference < SECOND * SECOND) {
                long temp = difference / 60;
                if (temp < 1) {
                    return "1分钟前";
                }
                return difference / 60 + "分钟前";
            } else if (difference < DAY * SECOND * SECOND) {

                return (int) (difference / 60 / 60) + "小时前";
            } else if (difference < MOUTH * DAY * SECOND * SECOND) {

                return (int) (difference / 60 / 60 / 24) + "日前";
            } else {
                return getFormatDate(date, DATE_FORMAT_SLASH);
            }
        } else {
            return getFormatDate(date, DATE_FORMAT_SLASH);
        }
    }

    /**
     * 格式化时间
     *
     * @param dateStr 5月21日 17：00
     * @return String
     */
    @Nullable
    public static String getMessageDate(long dateStr) {
        try {
            Date date = new Date();
            date.setTime(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return String.format("%s月%s日%s:%s", (calendar.get(Calendar.MONTH) + 1),
                    calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据月份返回中文
     *
     * @param month 月份
     * @return 中文
     */
    @NotNull
    @Contract(pure = true)
    public static String getDateFormatMonth(int month) {
        switch (month) {
            case 1:
                return "一月";
            case 2:
                return "二月";
            case 3:
                return "三月";
            case 4:
                return "四月";
            case 5:
                return "五月";
            case 6:
                return "六月";
            case 7:
                return "七月";
            case 8:
                return "八月";
            case 9:
                return "九月";
            case 10:
                return "十月";
            case 11:
                return "十一月";
            case 12:
                return "十二月";
            default:
                return "";
        }
    }

    /**
     * 计算两个时间的时间差
     *
     * @param time 时间
     * @return 差值
     */
    public static long getTimeDifference(long time) {
        long difference = System.currentTimeMillis() - time;
        Log.e("时间差：", difference + "|" + difference / 1000);
        return difference / 1000;
    }

    /**
     * 获取当前时间往前往后n的日期
     * eg: date ==null 默认是系统当前时间  否则以date为时间起点
     * n > 0 往后
     * n = 0 当前时间
     * n < 0 往前
     *
     * @param date
     * @param n
     * @return
     * @author zhangheng5@lenovo.com
     */
    @NotNull
    public static List<String> getBeforeDate(Date date, int n) {
        List<String> list = new ArrayList<>();
        Calendar calender = Calendar.getInstance();
        Date today = new Date();
        if (null != date) {
            today = date;
        }
        calender.setTime(today);

        if (n > 0) {
            for (int i = 0; i <= n; i++) {
                calender.add(Calendar.DATE, i);
                list.add(getFormatDate(calender.getTime(), DATE_FORMAT_LINE));
                calender.setTime(today);
            }
        } else if (n < 0) {
            for (int i = n; i <= 0; i++) {
                calender.add(Calendar.DATE, i);
                list.add(getFormatDate(calender.getTime(), DATE_FORMAT_LINE));
                calender.setTime(today);
            }
        } else {
            calender.add(Calendar.DATE, 0);
            list.add(getFormatDate(calender.getTime(), DATE_FORMAT_LINE));
            calender.setTime(today);
        }

        return list;
    }

    /**
     * 根据时间戳判断是否是明天
     *
     * @param time 秒级 时间戳
     * @return b
     */
    public static boolean isTomorrow(long time) {
        Calendar curDate = Calendar.getInstance();
        Calendar tomorrowCalendar = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        long timeInMillis = tomorrowCalendar.getTimeInMillis();
        return time >= tomorrowCalendar.getTimeInMillis() / 1000;
    }

    /**
     * 格式化时间
     *
     * @param dateStr 时间字符串
     * @return boolean
     */
    public static boolean isToday(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH) + 1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        return year1 == year2 && month1 == month2 && day1 == day2;
    }

    /**
     * 判断某一个时间是不是下午
     *
     * @param dateStr 时间字符串
     * @return boolean
     */
    public static boolean isDatePm(String dateStr) {
        int a = Integer.parseInt(getFormatDate2(dateStr, "HH"));
        return a >= 12 && a < 24;
    }


    /**
     * 获取afterMinute钟以前或者以后的时间
     *
     * @param afterMinute 之前传负数 之后传正数
     * @return yyyy-MM-dd HH:mm:ss;
     */
    public static String getAfterMinuteTime(int afterMinute) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(getCurrentTimeStamp() + 60 * 1000 * afterMinute);
        return sd.format(date);
    }


}
