package com.wss.common.widget.dialog;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Describe：对话框类型
 * Created by 吴天强 on 2018/10/26.
 */

public interface DialogType {

    @IntDef({DEFAULT, INPUT, COUNT, NO_TITLE, BOTTOM_IN})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    int DEFAULT = 0;
    int INPUT = 1;
    int COUNT = 2;
    int NO_TITLE = 3;
    int BOTTOM_IN = 4;
}
