package com.wss.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wss.common.base.bean.BaseBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Describe：Activity跳转
 * Created by 吴天强 on 2018/10/22.
 */

public class ActivityToActivity {


    public static void toActivity(Context activity, Class<? extends Activity> clazz) {
        toActivity(activity, clazz, null);
    }

    public static void toActivity(Context activity, Class<? extends Activity> clazz,
                                  Map<String, Object> params) {
        Intent intent = new Intent(activity, clazz);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = params.get(key);
                if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                } else if (value instanceof Boolean) {
                    intent.putExtra(key, (boolean) value);
                } else if (value instanceof Integer) {
                    intent.putExtra(key, (int) value);
                } else if (value instanceof Float) {
                    intent.putExtra(key, (float) value);
                } else if (value instanceof Double) {
                    intent.putExtra(key, (double) value);
                } else if (value instanceof Long) {
                    intent.putExtra(key, (long) value);
                } else if (value instanceof Short) {
                    intent.putExtra(key, (short) value);
                } else if (value instanceof BaseBean) {
                    intent.putExtra(key, (BaseBean) value);
                } else if (value instanceof ArrayList) {
                    intent.putExtra(key, (ArrayList) value);
                } else if (value instanceof HashMap) {
                    intent.putExtra(key, (HashMap) value);
                }
            }
        }
        activity.startActivity(intent);
    }


}
