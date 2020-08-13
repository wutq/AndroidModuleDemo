package com.wss.common.manage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wss.common.base.R;
import com.wss.common.base.bean.BaseBean;
import com.wss.common.bean.Template;
import com.wss.common.constants.Constants;
import com.wss.common.constants.Dic;
import com.wss.common.utils.NetworkUtil;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.Utils;
import com.wss.common.view.browser.BrowserActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

/**
 * Describe：Activity跳转
 * Created by 吴天强 on 2018/10/22.
 */
public class ActivityToActivity {


    ////////////////////////////////////////普通Activity跳转////////////////////////////////////////

    /**
     * 普通Activity之间跳转
     *
     * @param activity activity
     * @param clazz    目标activity
     */
    public static void toActivity(Context activity, Class<? extends Activity> clazz) {
        toActivityForResult(activity, clazz, null, 0);
    }

    /**
     * 普通Activity之间跳转
     *
     * @param activity activity
     * @param clazz    目标activity
     * @param params   携带参数
     */
    public static void toActivity(Context activity, Class<? extends Activity> clazz, Map<String, ?> params) {
        toActivityForResult(activity, clazz, params, 0);
    }

    /**
     * 普通Activity之间跳转
     *
     * @param activity    activity
     * @param clazz       目标activity
     * @param requestCode 请求码  需大于0
     */
    public static void toActivityForResult(Context activity, Class<? extends Activity> clazz, int requestCode) {
        toActivityForResult(activity, clazz, null, requestCode);
    }

    /**
     * 普通Activity之间跳转
     * ps：兼容从Fragment中使用ActivityForResult。传统方式Fragment中的onActivityResult中无法收到回调
     *
     * @param fragment    当前fragment
     * @param activity    activity
     * @param clazz       目标activity
     * @param requestCode 请求码  需大于0
     */
    public static void toActivityForResult(Fragment fragment, Context activity, Class<? extends Activity> clazz, int requestCode) {
        toActivityForResult(fragment, activity, clazz, null, requestCode);
    }

    /**
     * 普通Activity之间跳转
     *
     * @param activity    activity
     * @param clazz       目标activity
     * @param params      参数
     * @param requestCode 请求码  需大于0
     */
    public static void toActivityForResult(Context activity, Class<? extends Activity> clazz, Map<String, ?> params, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, clazz);
        assembleParams(intent, params);
        if (requestCode > 0) {
            ((Activity) activity).startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }
    }

    /**
     * 在Fragment中调用startActivityForResult
     *
     * @param fragment    跳转所在的Fragment
     * @param activity    activity
     * @param clazz       目标activity
     * @param params      参数
     * @param requestCode 请求码  需大于0
     */
    public static void toActivityForResult(@NotNull Fragment fragment, Context activity, Class<? extends Activity> clazz, Map<String, ?> params, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, clazz);
        assembleParams(intent, params);
        fragment.startActivityForResult(intent, requestCode);
    }

    private static void assembleParams(Intent intent, Map<String, ?> params) {
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
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
                } else if (value instanceof Bundle) {
                    intent.putExtra(key, (Bundle) value);
                } else if (value instanceof BaseBean) {
                    intent.putExtra(key, (BaseBean) value);
                } else if (value instanceof ArrayList) {
                    intent.putExtra(key, (ArrayList) value);
                } else if (value instanceof HashMap) {
                    intent.putExtra(key, (HashMap) value);
                }
            }
        }
    }
    //////////////////////////////////////WebView跳转//////////////////////////////////////

    /**
     * 跳转WebView url 不可为空
     *
     * @param context context
     * @param url     链接
     * @param title   标题
     */
    public static void toWebView(Context context, String url, String title) {
        if (!NetworkUtil.isLink(url)) {
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put(Dic.URL, url);
        param.put(Dic.TITLE_TEXT, title);
        toActivity(context, BrowserActivity.class, param);
    }

    /**
     * 跳转WebView url 不可为空
     *
     * @param context 链接
     * @param url     标题
     */
    public static void toWebView(Context context, String url) {
        toWebView(context, url, "");
    }

    //////////////////////////////////////ARouter跳转//////////////////////////////////////

    /**
     * ARouter跳转Activity
     *
     * @param activity Activity
     * @param url      目标Activity Url
     */
    public static void toActivity(Activity activity, String url) {
        toActivityForResult(activity, url, null, 0);
    }

    /**
     * ARouter跳转Activity
     *
     * @param activity Activity
     * @param url      目标Activity Url
     */
    public static void toActivity(Activity activity, String url, Map<String, ?> params) {
        toActivityForResult(activity, url, params, 0);
    }

    /**
     * ARouter跳转Activity
     *
     * @param activity    Activity
     * @param url         目标Activity Url
     * @param requestCode 请求码  需大于0
     */
    public static void toActivityForResult(Activity activity, String url, int requestCode) {
        toActivityForResult(activity, url, null, requestCode);
    }

    /**
     * ARouter跳转Activity
     *
     * @param activity    Activity
     * @param url         目标Activity Url
     * @param params      参数
     * @param requestCode 请求码  需大于0
     */
    public static void toActivityForResult(Activity activity, String url, Map<String, ?> params, int requestCode) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Postcard postcard = ARouter.getInstance()
                .build(url)
                .withOptionsCompat(ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.anim_right_in, R.anim.anim_right_out));
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = params.get(key);
                if (value instanceof String) {
                    postcard.withString(key, (String) value);
                } else if (value instanceof Boolean) {
                    postcard.withBoolean(key, (boolean) value);
                } else if (value instanceof Integer) {
                    postcard.withInt(key, (int) value);
                } else if (value instanceof Float) {
                    postcard.withFloat(key, (float) value);
                } else if (value instanceof Double) {
                    postcard.withDouble(key, (double) value);
                } else if (value instanceof Long) {
                    postcard.withLong(key, (long) value);
                } else if (value instanceof Short) {
                    postcard.withShort(key, (short) value);
                } else if (value instanceof Bundle) {
                    postcard.withBundle(key, (Bundle) value);
                } else if (value instanceof BaseBean) {
                    postcard.withSerializable(key, (BaseBean) value);
                } else if (value instanceof ArrayList) {
                    postcard.withSerializable(key, (ArrayList) value);
                } else if (value instanceof HashMap) {
                    postcard.withSerializable(key, (HashMap) value);
                }
            }
        }
        if (requestCode > 0) {
            LogisticsCenter.completion(postcard);
            activity.startActivityForResult(new Intent(activity, postcard.getDestination()), requestCode);
        } else {
            postcard.navigation();
        }
    }


    /**
     * 通过模板跳转Activity
     *
     * @param activity activity
     * @param template 模板信息
     */
    @SuppressWarnings("unchecked")
    public static void toActivity(Activity activity, Template template) {
        switch (template.getType()) {
            case Constants.TemplateType.ACTIVITY:
                //跳转Activity
                toActivity(activity, template.getClazz(), template.getParams());
                break;
            case Constants.TemplateType.AROUTER:
                //跳转Arouter
                toActivity(activity, template.getUrl(), template.getParams());
                break;
            case Constants.TemplateType.WEB_VIEW:
                //跳转WebView
                toWebView(activity, template.getUrl());
                break;
            case Constants.TemplateType.SYS_BROWSER:
                //跳转手机浏览器
                Utils.toSystemBrowser(activity, template.getUrl());
                break;
            default:

        }
    }

    /**
     * 跳转登录页
     *
     * @param context ctx
     */
    public static void toLoginActivity(Context context) {
        ToastUtils.show("跳转登录页");
    }
}