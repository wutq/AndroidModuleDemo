package com.wss.common.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import com.wss.common.base.BaseApplication;

/**
 * description: 拨打电话工具类
 *
 * @author 杨伟-tony
 * create by 2020/5/22 11:26
 */
public class PhoneUtils {
    /**
     * 拨打电话
     * ps：调起拨号盘
     *
     * @param phoneNum 电话号码
     */
    public static void callPhoneWaitFor(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.i().startActivity(intent);
        
    }

    /**
     * 拨打电话
     * 吊起拨号盘 直接拨打电话
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public static void callPhoneDirect(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.i().startActivity(intent);
    }

}
