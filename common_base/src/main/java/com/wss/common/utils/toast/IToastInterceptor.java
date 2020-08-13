package com.wss.common.utils.toast;

import android.widget.Toast;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/ToastUtils
 * time   : 2019/05/19
 * desc   : Toast 拦截器接口
 */
public interface IToastInterceptor {

    /**
     * 根据显示的文本决定是否拦截该 Toast
     *
     * @param toast Toast
     * @param text  显示的Toast内容
     * @return boolean
     */
    boolean intercept(Toast toast, CharSequence text);
}