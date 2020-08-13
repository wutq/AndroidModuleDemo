package com.wss.module.wan.ui.wxnumber.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXNumberContract;
import com.wss.module.wan.ui.wxnumber.mvp.model.WXNumberModel;

/**
 * Describe：公众号Presenter
 * Created by 吴天强 on 2018/11/15.
 */
public class WXNumberPresenter extends BasePresenter<WXNumberModel, WXNumberContract.View> implements WXNumberContract.Presenter {


    @Override
    protected WXNumberModel createModule() {
        return new WXNumberModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        showLoading();
        getModel().getWxNumber().subscribe(
                numbers -> {
                    dismissLoading();
                    if (ValidUtils.isValid(numbers)) {
                        getView().refreshWxNumber(numbers);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> {
                    getView().onError("", t.getMessage());
                });
    }
}
