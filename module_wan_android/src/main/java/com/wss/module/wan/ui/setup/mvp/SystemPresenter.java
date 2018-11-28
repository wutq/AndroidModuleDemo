package com.wss.module.wan.ui.setup.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.ui.setup.mvp.contract.SystemContract;

import java.util.List;

/**
 * Describe：体系Presenter
 * Created by 吴天强 on 2018/11/21.
 */

public class SystemPresenter extends BasePresenter<SystemContract.Model, SystemContract.View>
        implements SystemContract.Presenter {

    @Override
    public void getSystem() {
        getView().showLoading();
        getModule().getSystem(new OnResultCallBack<List<Classification>>() {
            @Override
            public void onSuccess(boolean success, int code, String msg, Object tag, List<Classification> response) {
                if (code == 0) {
                    if (response == null || response.size() < 1) {
                        getView().onEmpty(tag);
                    } else {
                        getView().systemList(response);
                    }
                } else {
                    getView().onError(tag, msg);
                }
            }

            @Override
            public void onFailure(Object tag, Exception e) {
                getView().onError(tag, Constants.ERROR_MESSAGE);
            }

            @Override
            public void onCompleted() {
                getView().dismissLoading();
            }
        });
    }

    @Override
    protected SystemContract.Model createModule() {
        return new SystemModel();
    }

    @Override
    public void start() {
        getSystem();
    }
}
