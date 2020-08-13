package com.wss.common.view.browser.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.view.browser.mvp.contract.BrowserContract;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：浏览器Model
 * Created by 吴天强 on 2020/5/18.
 */
public class BrowserModel extends BaseModel implements BrowserContract.Presenter {

    public BrowserModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
}
