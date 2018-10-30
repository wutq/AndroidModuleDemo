package com.wss.common.widget.pulltorefresh;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 加载状态
 */
interface ViewStatus {

    @IntDef({CONTENT_STATUS, LOADING_STATUS, EMPTY_STATUS, ERROR_STATUS})
    @Retention(RetentionPolicy.SOURCE)
    @interface VIEW_STATUS {

    }

    int CONTENT_STATUS = 0;
    int LOADING_STATUS = 1;
    int EMPTY_STATUS = 2;
    int ERROR_STATUS = 3;

}
