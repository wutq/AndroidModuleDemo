package com.wss.module.user.ui.account.mvp;

import com.tamic.novate.Throwable;
import com.wss.common.bean.User;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.RequestParam;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.user.ui.account.mvp.contract.LoginContract;

/**
 * Describe：登录Module
 * Created by 吴天强 on 2018/11/13.
 */

public class LoginModel implements LoginContract.Model {

    @Override
    public void login(User user, OnResultCallBack callback) {
        if (user == null) {
            callback.onError("", (Throwable) new Exception("用户信息为空"));
        }
        RequestParam param = new RequestParam();
        param.addParameter("username", user.getUsername());
        param.addParameter("password", user.getPassword());
        HttpUtils.getInstance()
                .postRequest(Api.LOGIN, param, callback);
    }
}
