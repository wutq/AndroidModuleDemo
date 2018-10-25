package com.wss.module.main.ui.selector.mvp;

import android.content.Context;
import android.text.TextUtils;

import com.tamic.novate.Throwable;
import com.wss.common.base.mvp.IBaseModule;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.common.utils.Utils;

/**
 * Describe：获取数据
 * Created by 吴天强 on 2018/10/24.
 */

public class SelectorModule implements IBaseModule {

    /**
     * 获取省市区
     */
    void getProvince(Context mContext, OnResultCallBack<String> callBack) {
        if (callBack == null) {
            return;
        }
        String assetFileData = Utils.getAssetFileData(mContext, "province.json");
        if (!TextUtils.isEmpty(assetFileData)) {
            callBack.onSuccess(true, 0, "成功", "", assetFileData);
        } else {
            callBack.onFailure("", new Throwable(new java.lang.Throwable(), 1));
        }
        callBack.onCompleted();
    }
}
