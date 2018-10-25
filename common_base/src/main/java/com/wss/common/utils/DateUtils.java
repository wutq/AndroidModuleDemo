package com.wss.common.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Describe：日期帮助类
 * Created by 吴天强 on 2018/10/15.
 */

public class DateUtils {

    /**
     * 获取系统的时间
     *
     * @return String
     */
    public static String getCurrentTimeStr() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat fort = new SimpleDateFormat("HH:mm:ss");
        fort.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return fort.format(getCurrentTimeStamp());
    }

    /**
     * 获取当前时间戳
     *
     * @return long
     */
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

}
