package com.wss.common.utils;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

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

    /**
     * 测量 View
     *
     * @param measureSpec
     * @param defaultSize View 的默认大小
     * @return
     */
    public static int measure(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }
    /**
     * 测量文字高度
     *
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
    }
}
