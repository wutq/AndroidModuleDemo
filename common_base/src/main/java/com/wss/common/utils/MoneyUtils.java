package com.wss.common.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Describe：金钱工具类
 * Created by 吴天强 on 2017/11/24.
 */

public class MoneyUtils {
    /**
     * 计算类
     */
    public static class Algorithm {

        /**
         * 金钱加法
         *
         * @param v1
         * @param v2
         * @return
         */
        public static String add(String v1, String v2) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2).toString();
        }

        /**
         * 金钱减法
         *
         * @param v1
         * @param v2
         * @return
         */
        public static String subtract(String v1, String v2) {
            if (TextUtils.isEmpty(v1)) {
                v1 = "0";
            }
            if (TextUtils.isEmpty(v2)) {
                v2 = "0";
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2).toString();
        }


        /**
         * 金钱乘法
         *
         * @param v1
         * @param v2
         * @return
         */
        public static String multiply(String v1, String v2) {
            return multiply(v1, v2, 0);
        }

        /**
         * 金钱乘法
         *
         * @param v1    乘数
         * @param v2    被乘数
         * @param scale 小数点保留位数
         * @return
         */
        public static String multiply(String v1, String v2, int scale) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            BigDecimal result = b1.multiply(b2);
            result = result.setScale(scale, BigDecimal.ROUND_HALF_UP);
            return result.toString();
        }

        /**
         * 金钱除法
         *
         * @param v1
         * @param v2
         * @return
         */
        public static String divide(String v1, String v2) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, BigDecimal.ROUND_HALF_UP).toString();
        }
    }

    /**
     * 将金钱转成显示的格式¥xx,xxx,xxx.xx如此格式
     *
     * @param textPrice 单位为分的金额
     * @return String
     */
    public static String formatPrice(String textPrice) {
        return formatPrice(Double.valueOf(textPrice) / 100);
    }

    /**
     * 将金钱转成显示的格式¥xx,xxx,xxx.xx如此格式
     *
     * @param price 单位为元的金额
     * @return String
     */
    public static String formatPrice(double price) {
        if (price > 0 && price < 1) {
            String temp = String.valueOf(price);
            if (temp.contains(".") && temp.substring(temp.indexOf(".") + 1, temp.length()).length() <= 1) {
                return String.format("%s%s", price, 0);
            }
            return String.format("%s", price);
        }
        if (price <= 0) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("#,###.00");
        format.setRoundingMode(RoundingMode.FLOOR);
        return String.format("%s", format.format(price));
    }

}
