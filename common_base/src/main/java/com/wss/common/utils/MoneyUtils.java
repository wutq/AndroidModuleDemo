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
         * @param value1 参数1
         * @param value2 参数2
         * @return 结果
         */
        public static String add(Object value1, Object value2) {
            BigDecimal b1 = new BigDecimal(String.valueOf(value1));
            BigDecimal b2 = new BigDecimal(String.valueOf(value2));
            return b1.add(b2).toString();
        }

        /**
         * 金钱减法
         *
         * @param value1 参数1
         * @param value2 参数2
         * @return 结果
         */
        public static String subtract(String value1, String value2) {
            if (TextUtils.isEmpty(value1)) {
                value1 = "0";
            }
            if (TextUtils.isEmpty(value2)) {
                value2 = "0";
            }
            BigDecimal b1 = new BigDecimal(value1);
            BigDecimal b2 = new BigDecimal(value2);
            return b1.subtract(b2).toString();
        }


        /**
         * 金钱乘法
         *
         * @param value1 参数1
         * @param value2 参数2
         * @return 结果
         */
        public static String multiply(String value1, String value2) {
            return multiply(value1, value2, 0);
        }

        /**
         * 金钱乘法
         *
         * @param value1 乘数
         * @param value2 被乘数
         * @param scale  小数点保留位数
         * @return 结果
         */
        public static String multiply(String value1, String value2, int scale) {
            BigDecimal b1 = new BigDecimal(value1);
            BigDecimal b2 = new BigDecimal(value2);
            BigDecimal result = b1.multiply(b2);
            result = result.setScale(scale, BigDecimal.ROUND_HALF_UP);
            return result.toString();
        }

        /**
         * 金钱除法
         *
         * @param value1 参数1
         * @param value2 参数2
         * @return 结果
         */
        public static String divide(String value1, String value2) {
            BigDecimal b1 = new BigDecimal(value1);
            BigDecimal b2 = new BigDecimal(value2);
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
        return formatPrice(Double.parseDouble(textPrice) / 100);
    }

    /**
     * 将金钱转成显示的格式¥xx,xxx,xxx.xx如此格式
     *
     * @param price 单位为元的金额
     * @return String
     */
    public static String formatPrice(double price) {
        if (price <= 0) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("#,###.00");
        format.setRoundingMode(RoundingMode.FLOOR);
        return String.format("%s", format.format(price));
    }

    /**
     * 将金钱转成显示的格式¥xx.xx如此格式
     *
     * @param price 单位为元的金额
     * @return String
     */
    public static String formatPriceT(double price) {
        if (price <= 0) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("0.00");
        format.setRoundingMode(RoundingMode.FLOOR);
        return String.format("%s", format.format(price));
    }
    /**
     * 将金钱转成显示的格式¥xx.xx如此格式
     *
     * @param textPrice 单位为分的金额
     * @return String
     */
    public static String formatPriceT(String textPrice) {
        return formatPriceT(Double.parseDouble(textPrice) / 10000);
    }

}
