package com.wss.module.user.ui.account.mvp.model;


import com.wss.common.base.mvp.BaseModel;
import com.wss.common.bean.User;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.common.net.request.RequestParam;
import com.wss.module.user.ui.account.mvp.contract.LoginContract;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：登录Module
 * Created by 吴天强 on 2018/11/13.
 */
public class LoginModel extends BaseModel implements LoginContract.Model {

    public LoginModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<User> login(String userName, String password) {
        RequestParam param = new RequestParam();
        param.addParameter("username", userName);
        param.addParameter("password", password);
        return NetworkManage.createPostForm().request(getLifecycleOwner(), Api.LOGIN, param, User.class);
    }
}
