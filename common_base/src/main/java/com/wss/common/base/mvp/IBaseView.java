package com.wss.common.base.mvp;

import android.content.Context;

/**
 * Describe：所有View基类
 * Created by 吴天强 on 2018/10/17.
 */

public interface IBaseView {

    void showLoading();

    void dismissLoading();

    void showEmpty(Object tag);

    void showError(Object tag, String error);

    Context getContext();
}
