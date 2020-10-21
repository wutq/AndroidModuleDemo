package com.wss.module.user.ui.main.mvp;

import android.os.Handler;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.ToastUtils;
import com.wss.module.user.ui.main.mvp.contract.UserContract;
import com.wss.module.user.ui.main.mvp.model.UserModel;

/**
 * Describe：我的Presenter
 * Created by 吴天强 on 2018/11/21.
 */

public class  UserPresenter extends BasePresenter<UserModel, UserContract.View>
        implements UserContract.Presenter {

    @Override
    public void checkUpdate() {
        showLoading();
        new Handler().postDelayed(() -> {
            dismissLoading();
            ToastUtils.show("已是最新版本~");
        }, 1200);

    }

    @Override
    protected UserModel createModule() {
        return new UserModel(getLifecycleOwner());
    }

    @Override
    public void start() {

    }
}
