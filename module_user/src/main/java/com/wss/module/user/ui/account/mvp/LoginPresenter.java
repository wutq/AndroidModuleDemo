package com.wss.module.user.ui.account.mvp;

import android.app.Activity;

import com.wss.common.base.BaseApplication;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.Event;
import com.wss.common.constants.EventAction;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.module.user.ui.account.mvp.contract.LoginContract;
import com.wss.module.user.ui.account.mvp.model.LoginModel;

/**
 * Describe：登录Presenter
 * Created by 吴天强 on 2018/11/13.
 */

public class LoginPresenter extends BasePresenter<LoginModel, LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String userName, String password) {
        showLoading();
        getModel().login(userName, password).subscribe(
                user -> {
                    dismissLoading();
                    BaseApplication.i().setUser(user);
                    EventBusUtils.sendEvent(new Event(EventAction.EVENT_LOGIN_SUCCESS));
                    ((Activity) getContext()).finish();
                }, t -> {
                    dismissLoading();
                    new AppDialog.Builder(getContext())
                            .setContent(ValidUtils.isValid(t.getMessage()) ? t.getMessage() : "登录失败")
                            .setSingleButton("确定")
                            .create()
                            .show();

                });
    }


    @Override
    protected LoginModel createModule() {
        return new LoginModel(getLifecycleOwner());
    }

    @Override
    public void start() {
    }
}
