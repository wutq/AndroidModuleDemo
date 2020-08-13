package com.wss.common.view.browser.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.view.browser.mvp.contract.BrowserContract;
import com.wss.common.view.browser.mvp.model.BrowserModel;

/**
 * Describe：浏览器控制器
 * Created by 吴天强 on 2020/5/18.
 */
public class BrowserPresenter extends BasePresenter<BrowserModel, BrowserContract.View>
        implements BrowserContract.Presenter {
    @Override
    protected BrowserModel createModule() {
        return new BrowserModel(getLifecycleOwner());
    }

    @Override
    public void start() {

    }
}
