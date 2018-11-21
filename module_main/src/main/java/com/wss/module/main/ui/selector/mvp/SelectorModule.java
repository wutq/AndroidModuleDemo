package com.wss.module.main.ui.selector.mvp;

import android.content.Context;
import android.text.TextUtils;

import com.tamic.novate.Throwable;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.common.utils.Utils;
import com.wss.module.main.ui.selector.mvp.contract.SelectContract;

/**
 * Describe：获取数据
 * Created by 吴天强 on 2018/10/24.
 */

public class SelectorModule implements SelectContract.Module {


    @Override
    public void getAddress(Context mContext, OnResultCallBack<String> callBack) {
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
