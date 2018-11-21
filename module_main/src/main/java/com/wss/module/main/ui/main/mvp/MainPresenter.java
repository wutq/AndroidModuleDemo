package com.wss.module.main.ui.main.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.net.RequestParam;
import com.wss.common.net.callback.OnResultObjectCallBack;
import com.wss.common.utils.Utils;
import com.wss.module.main.bean.AppInfo;

/**
 * Describe：首页Presenter
 * Created by 吴天强 on 2018/11/20.
 */

public class MainPresenter extends BasePresenter<MainModule, IMainView> {

    public void checkUpdate() {
        if (isViewAttached()) {
            RequestParam param = new RequestParam();
            param.addParameter("versionCode", Utils.getVersionCode());
            getModule().checkUpdate(getContext(), param, new OnResultObjectCallBack<AppInfo>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, AppInfo response) {
                    if (code == 1000) {
                        //需要更新
                        getView().needUpdate(response);
                    }
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
    protected MainModule createModule() {
        return new MainModule();
    }
}
