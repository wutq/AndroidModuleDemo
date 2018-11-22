package com.wss.module.user.ui.account.mvp;

import com.wss.common.bean.User;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.RequestParam;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.user.ui.account.mvp.contract.RegisterContract;

/**
 * Describe：注册Module
 * Created by 吴天强 on 2018/11/13.
 */

public class RegisterModel implements RegisterContract.Model {


    @Override
    public void register(User user, OnResultCallBack callback) {
        if (user == null)
            callback.onError("",null);
        RequestParam param = new RequestParam();
        param.addParameter("username", user.getUsername());
        param.addParameter("password", user.getPassword());
        param.addParameter("repassword", user.getPassword());
        HttpUtils.getInstance()
                .postRequest(Api.REGISTER, param, callback);
    }
}
