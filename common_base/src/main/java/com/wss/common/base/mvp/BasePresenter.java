package com.wss.common.base.mvp;

import android.content.Context;

/**
 * Describe：Presenter基类
 * Created by 吴天强 on 2018/10/17.
 */

public abstract class BasePresenter<M extends IBaseModule, V extends IBaseView> {

    private V view;
    private M module;


    /**
     * 绑定View
     */
    public void attachView(V view) {
        this.view = view;
        if (this.module == null) {
            this.module = createModule();
        }
    }

    /**
     * 解绑View
     */
    public void detachView() {
        this.view = null;
        this.module = null;
    }

    /**
     * 是否与View建立连接
     */
    protected boolean isViewAttached() {
        return view != null;
    }

    protected V getView() {
        return view;
    }

    protected M getModule() {
        return module;
    }

    protected Context getContext() {
        return getView().getContext();
    }

    /**
     * 通过该方法创建Module
     */
    protected abstract M createModule();
}
