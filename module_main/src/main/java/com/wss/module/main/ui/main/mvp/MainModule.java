package com.wss.module.main.ui.main.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.NetConfig;
import com.wss.common.net.RequestParam;

/**
 * Describe：首页Module
 * Created by 吴天强 on 2018/11/20.
 */

public class MainModule extends BaseModule {

    void checkUpdate(Context context, RequestParam param, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .setBaseUrl(NetConfig.Url.MY_SERVICE_URL)
                .getRequest(Api.CHECK_UPDATE, param, callback);
    }
}
