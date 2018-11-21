package com.wss.module.user.ui.account.mvp;

import android.text.TextUtils;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.User;
import com.wss.common.net.callback.OnResultObjectCallBack;
import com.wss.module.user.ui.account.mvp.contract.RegisterContract;

/**
 * Describe：注册Presenter
 * Created by 吴天强 on 2018/11/13.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.Module, RegisterContract.View>
        implements RegisterContract.Presenter {


    public void register() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().register(getView().getUserInfo(), new OnResultObjectCallBack<User>() {
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
    protected RegisterModule createModule() {
        return new RegisterModule();
    }

    @Override
    public void start() {

    }
}
