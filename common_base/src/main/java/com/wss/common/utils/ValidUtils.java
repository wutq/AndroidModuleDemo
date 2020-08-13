package com.wss.common.utils;

import android.text.TextUtils;

import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Map;

/**
 * 对象空判断验证类
 * Created by 吴天强 on 2017/12/3.
 */
public class ValidUtils {
    @Contract(value = "null -> false", pure = true)
    public static boolean isValid(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    @Contract("null, _ -> false")
    public static boolean isValid(Collection collection, int position) {
        return collection != null && !collection.isEmpty() && collection.size() > position;
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isValid(Map map) {
        return map != null && !map.isEmpty();
    }

    @Contract("null -> false")
    public static boolean isValid(String str) {
        return !TextUtils.isEmpty(str);
    }

    @Contract(value = "null -> false; !null -> true", pure = true)
    public static boolean isValid(Object obj) {
        return obj != null;
    }
}
