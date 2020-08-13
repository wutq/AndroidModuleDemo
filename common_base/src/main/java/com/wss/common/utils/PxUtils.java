package com.wss.common.utils;

import android.content.Context;

import com.wss.common.base.BaseApplication;

import androidx.annotation.NonNull;

/**
 * Describe：尺寸工具类
 * Created by 吴天强 on 2017/9/19.
 */
public class PxUtils {
    /**
     * 得到设备屏幕的宽度
     *
     * @param context ctx
     * @return int
     */
    public static int getScreenWidth(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     *
     * @param context ctx
     * @return int
     */
    public static int getScreenHeight(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     *
     * @param context ctx
     * @return float
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 把密度转换为像素
     *
     * @param dpValue dp值
     * @return int
     */
    public static int dp2px(float dpValue) {
        float scale = getScreenDensity(BaseApplication.i());
        return (int) (dpValue * scale + 0.5);
    }

    /**
     * 将像素转换成dp
     *
     * @param pxValue px值
     * @return int
     */
    public static int px2dp(float pxValue) {
        float scale = getScreenDensity(BaseApplication.i());
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue sp值
     * @return int
     */
    public static int sp2px(float spValue) {
        float fontScale = getScreenDensity(BaseApplication.i());
        return (int) (spValue * fontScale + 0.5f);
    }
}
