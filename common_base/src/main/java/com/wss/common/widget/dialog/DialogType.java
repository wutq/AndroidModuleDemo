package com.wss.common.widget.dialog;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Describe：对话框类型
 * Created by 吴天强 on 2018/10/26.
 */
public interface DialogType {

    @IntDef({DEFAULT, NO_TITLE, INPUT, BOTTOM_IN, REPLACE_ALL, REPLACE_CONTENT, REPLACE_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    /**
     * 常规
     */
    int DEFAULT = 0;
    /**
     * 没有Title
     */
    int NO_TITLE = 1;
    /**
     * 带输入框
     */
    int INPUT = 2;
    /**
     * 底部进来
     */
    int BOTTOM_IN = 3;
    /**
     * 替换全部
     */
    int REPLACE_ALL = 4;
    /**
     * 替换content
     */
    int REPLACE_CONTENT = 5;
    /**
     * 替换底部弹出
     */
    int REPLACE_BOTTOM = 6;
}
