package com.wss.module.user.ui.account.mvp;

import android.app.Activity;
import android.text.TextUtils;

import com.wss.common.base.BaseApplication;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.Event;
import com.wss.common.constants.EventAction;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.user.ui.account.mvp.contract.RegisterContract;
import com.wss.module.user.ui.account.mvp.model.RegisterModel;

/**
 * Describe：注册Presenter
 * Created by 吴天强 on 2018/11/13.
 */
public class RegisterPresenter extends BasePresenter<RegisterModel, RegisterContract.View> implements RegisterContract.Presenter {

    private static final int MIN_INPUT_LENGTH = 6;

    @Override
    public void register(String userName, String password, String passwordConfirm) {

        if (userName.length() < MIN_INPUT_LENGTH || password.length() < MIN_INPUT_LENGTH ||
                passwordConfirm.length() < MIN_INPUT_LENGTH) {
            ToastUtils.show("用户名或密码长度至少6位！");
            return;
        }
        if (!TextUtils.equals(password, passwordConfirm)) {
            ToastUtils.show("两次输入密码不一致！");
            return;
        }

        showLoading();
        getModel().register(userName, password).subscribe(
                user -> {
                    dismissLoading();
                    BaseApplication.i().setUser(user);
                    EventBusUtils.sendEvent(new Event(EventAction.EVENT_REGISTER_SUCCESS));
                    ((Activity) getContext()).finish();
                },
                t -> {
                    dismissLoading();
                    ToastUtils.show(ValidUtils.isValid(t.getMessage()) ? t.getMessage() : "注册失败");
                }
        );
    }

    @Override
    protected RegisterModel createModule() {
        return new RegisterModel(getLifecycleOwner());
    }

    @Override
    public void start() {

    }
}
