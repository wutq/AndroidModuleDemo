package com.wss.module.user.ui.account.mvp;

import android.text.TextUtils;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.User;
import com.wss.common.net.RequestParam;
import com.wss.common.net.callback.OnResultObjectCallBack;

/**
 * Describe：登录Presenter
 * Created by 吴天强 on 2018/11/13.
 */

public class UserPresenter extends BasePresenter<UserModule, IUserView> {


    public void login() {
        if (isViewAttached()) {
            getView().showLoading();
            RequestParam param = new RequestParam();
            param.addParameter("username", getView().getName());
            param.addParameter("password", getView().getPassword());
            getModule().login(getView().getContext(), param, new OnResultObjectCallBack<User>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, User response) {
                    if (code == 0 && response != null &&
                            !TextUtils.isEmpty(String.valueOf(response.getId()))) {
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

    public void register() {
        if (isViewAttached()) {
            getView().showLoading();
            RequestParam param = new RequestParam();
            param.addParameter("username", getView().getName());
            param.addParameter("password", getView().getPassword());
            param.addParameter("repassword", getView().getPassword());
            getModule().register(getView().getContext(), param, new OnResultObjectCallBack<User>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, User response) {
                    if (code == 0 && response != null &&
                            !TextUtils.isEmpty(String.valueOf(response.getId()))) {
                        getView().registerSuccess(response);
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
    protected UserModule createModule() {
        return new UserModule();
    }
}
