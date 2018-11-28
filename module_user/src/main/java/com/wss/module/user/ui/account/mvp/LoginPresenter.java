package com.wss.module.user.ui.account.mvp;

import android.text.TextUtils;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.User;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.user.ui.account.mvp.contract.LoginContract;

/**
 * Describe：登录Presenter
 * Created by 吴天强 on 2018/11/13.
 */

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View>
        implements LoginContract.Presenter {


    @Override
    public void login() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().login(getView().getUserInfo(), new OnResultCallBack<User>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, User response) {
                    if (code == 0 && response != null && !TextUtils.isEmpty(String.valueOf(response.getId()))) {
                        getView().loginSuccess(response);
                    } else {
                        getView().onError(tag, msg);
                    }
                }

                @Override
                public void onFailure(Object tag, Exception e) {
                    getView().onError(tag, msg);
                }

                @Override
                public void onCompleted() {
                    getView().dismissLoading();
                }
            });
        }
    }


    @Override
    protected LoginModel createModule() {
        return new LoginModel();
    }

    @Override
    public void start() {

    }
}
