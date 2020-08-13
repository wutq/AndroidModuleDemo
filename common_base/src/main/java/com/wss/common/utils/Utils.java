package com.wss.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.widget.TextView;

import com.wss.common.base.BaseApplication;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;

/**
 * Describe：工具类
 * Created by 吴天强 on 2018/10/15.
 */
public class Utils {

    /**
     * 读取 asset下的文件
     *
     * @param context  context
     * @param fileName fileName
     * @return String
     */
    @NonNull
    public static String getAssetFileData(@NotNull Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 验证邮箱
     *
     * @param email email字符串
     * @return 如果是邮件地址 返回 true
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断是否为纯数字
     *
     * @param str str
     * @return boolean
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断是否为电话，只校验位数
     *
     * @param str str
     * @return boolean
     */
    public static boolean isPhone(String str) {
        return ValidUtils.isValid(str) && str.length() == 11;
    }

    /**
     * 获取APP包名
     *
     * @return String
     */
    public static String getPackageName() {
        return BaseApplication.i().getPackageName();
    }

    /**
     * 获取APP 版本名称
     *
     * @return String
     */
    public static String getVersionName() {
        PackageManager packageManager = BaseApplication.i().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取APP 版本号
     *
     * @return Integer
     */
    public static Integer getVersionCode() {
        PackageManager packageManager = BaseApplication.i().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置TextView的背景色
     *
     * @param textView textView
     * @param color    颜色
     */
    public static void setTextViewDrawable(@NotNull TextView textView, int color) {
        setTextViewDrawable(textView, color, 4);
    }

    /**
     * 设置TextView的背景色
     *
     * @param textView textView
     * @param color    颜色
     * @param radius   圆角角度
     */
    public static void setTextViewDrawable(@NotNull TextView textView, int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(color);
        textView.setBackground(drawable);
    }

    /**
     * 跳转浏览器
     */
    public static void toSystemBrowser(@NonNull Context context, String url) {
        //应用内下载失败 跳转浏览器下载
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri contentUrl = Uri.parse(url);
        intent.setData(contentUrl);
        context.startActivity(intent);
    }




    /**
     * 电话号码中间替换成*
     *
     * @param phone 原电话
     * @return 替换后的电话
     */
    @Contract("_ -> param1")
    public static String replacePhone(String phone) {
        if (!isPhone(phone)) {
            return phone;
        }
        String replace = phone.substring(3, 7);
        return phone.replace(replace, "****");
    }

    /**
     * 把等于null的字符串转成“”
     *
     * @param text 原字符串
     * @return 转换后字符串
     */
    public static String checkText(String text) {
        if (!ValidUtils.isValid(text)) {
            return "";
        }
        return text;
    }
}
