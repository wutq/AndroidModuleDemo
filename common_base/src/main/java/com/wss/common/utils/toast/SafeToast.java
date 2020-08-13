package com.wss.common.utils.toast;

import android.app.Application;
import android.os.Handler;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/ToastUtils
 * time   : 2018/12/06
 * desc   : Toast 显示安全处理
 */
public final class SafeToast extends BaseToast {

    public SafeToast(Application application) {
        super(application);

        // 反射 Toast 中的字段
        try {

            // 获取 mTN 字段对象
            Field mTnField = Toast.class.getDeclaredField("mTN");
            mTnField.setAccessible(true);
            Object mTn = mTnField.get(this);

            // 获取 mTN 中的 mHandler 字段对象
            Field mHandlerField = mTnField.getType().getDeclaredField("mHandler");
            mHandlerField.setAccessible(true);
            Handler mHandler = (Handler) mHandlerField.get(mTn);

            // 偷梁换柱
            mHandlerField.set(mTn, new SafeHandler(mHandler));

        } catch (IllegalAccessException | NoSuchFieldException ignored) {
        }
    }
}