package com.wss.common.utils.toast;

import android.widget.Toast;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/ToastUtils
 * time   : 2019/05/19
 * desc   : Toast 处理策略
 */
public interface IToastStrategy {

    /**
     * 短吐司显示的时长
     */
    int SHORT_DURATION_TIMEOUT = 1500;
    /**
     * 长吐司显示的时长
     */
    int LONG_DURATION_TIMEOUT = 3500;

    /**
     * 绑定 Toast 对象
     *
     * @param toast Toast
     */
    void bind(Toast toast);

    /**
     * 显示 Toast
     *
     * @param text 文本
     */
    void show(CharSequence text);

    /**
     * 取消 Toast
     */
    void cancel();
}