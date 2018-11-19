package com.wss.module.user.ui.account.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.RequestParam;

/**
 * Describe：登录Module
 * Created by 吴天强 on 2018/11/13.
 */

public class UserModule extends BaseModule {

    void login(Context context, RequestParam param, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .postRequest(Api.LOGIN, param, callback);
    }

    void register(Context context, RequestParam param, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .postRequest(Api.REGISTER, param, callback);
    }
}
