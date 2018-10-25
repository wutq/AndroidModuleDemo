package com.wss.common.widget.pulltorefresh;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 加载状态
 */
class ViewStatus {

    @IntDef({CONTENT_STATUS, LOADING_STATUS, EMPTY_STATUS, ERROR_STATUS})
    @Retention(RetentionPolicy.SOURCE)
    @interface VIEW_STATUS {

    }

    static final int CONTENT_STATUS = 0;
    static final int LOADING_STATUS = 1;
    static final int EMPTY_STATUS = 2;
    static final int ERROR_STATUS = 3;

}
