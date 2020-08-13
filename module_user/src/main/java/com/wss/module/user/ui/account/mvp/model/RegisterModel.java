package com.wss.module.user.ui.account.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.bean.User;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.common.net.request.RequestParam;
import com.wss.module.user.ui.account.mvp.contract.RegisterContract;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：注册Module
 * Created by 吴天强 on 2018/11/13.
 */
public class RegisterModel extends BaseModel implements RegisterContract.Model {


    public RegisterModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
    @Override
    public Observable<User> register(String userName, String password) {
        RequestParam param = new RequestParam();
        param.addParameter("username", userName);
        param.addParameter("password", password);
        param.addParameter("repassword", password);
        return NetworkManage.createPostForm().request(getLifecycleOwner(), Api.REGISTER, param, User.class);
    }
}
