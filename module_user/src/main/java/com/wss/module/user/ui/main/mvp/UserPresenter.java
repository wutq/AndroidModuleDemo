package com.wss.module.user.ui.main.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.AppInfo;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.common.utils.ToastUtils;
import com.wss.module.user.ui.main.mvp.contract.UserContract;

/**
 * Describe：我的Presenter
 * Created by 吴天强 on 2018/11/21.
 */

public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View>
        implements UserContract.Presenter {

    @Override
    public void checkUpdate() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().checkUpdate(new OnResultCallBack<AppInfo>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, AppInfo response) {
                    if (code == 1000 || response != null) {
                        //需要更新
                        getView().needUpdate(response);
                    } else {
                        getView().isLastVersion();
                    }
                }

                @Override
                public void onFailure(Object tag, Exception e) {
                    ToastUtils.showToast(getContext(), Constants.ERROR_MESSAGE);
                }

                @Override
                public void onCompleted() {
                    getView().dismissLoading();
                }
            });
        }
    }

    @Override
    protected UserContract.Model createModule() {
        return new UserModel();
    }

    @Override
    public void start() {

    }
}
