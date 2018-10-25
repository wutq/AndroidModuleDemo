package com.wss.common.widget.pulltorefresh;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 刷新状态
 */
class State {


    @IntDef({REFRESH, LOAD_MORE})
    @Retention(RetentionPolicy.SOURCE)
    @interface REFRESH_STATE {
    }

    static final int REFRESH = 10;
    static final int LOAD_MORE = 11;
}
