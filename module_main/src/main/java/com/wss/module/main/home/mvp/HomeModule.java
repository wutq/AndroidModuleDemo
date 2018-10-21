package com.wss.module.main.home.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.IBaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：首页Module
 * Created by 吴天强 on 2018/10/17.
 */

public class HomeModule implements IBaseModule {

    void getBanner(Context context, ResponseCallback callback) {
        HttpUtils.create(context)
                .getRequest(Api.GET_BANNER_LIST, callback);
    }

}
