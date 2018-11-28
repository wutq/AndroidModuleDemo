package com.wss.module.user.ui.account.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.User;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.user.ui.account.mvp.contract.RegisterContract;

/**
 * Describe：注册Presenter
 * Created by 吴天强 on 2018/11/13.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View>
        implements RegisterContract.Presenter {

    @Override
    public void register() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().register(getView().getUserInfo(), new OnResultCallBack<User>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, User response) {

                }

                @Override
                public void onFailure(Object tag, Exception e) {

                }

                @Override
                public void onCompleted() {

                }
            });
        }
    }

    @Override
    protected RegisterModel createModule() {
        return new RegisterModel();
    }

    @Override
    public void start() {

    }
}
