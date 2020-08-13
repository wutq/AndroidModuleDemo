package com.wss.common.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.widget.dialog.LoadingDialog;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：所有需要Mvp开发的Activity的基类
 * Created by 吴天强 on 2018/10/15.
 */
@SuppressWarnings("unchecked")
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {

    private P presenter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(context);
        loadingDialog.setCancelable(loadingCancelable());
        //创建present
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    /**
     * 返回Presenter
     *
     * @return P
     */
    protected P getPresenter() {
        return presenter;
    }


    //***************************************IBaseView方法实现 start*************************************

    @Override
    public void showLoading() {
        showLoading("");
    }

    /**
     * 显示加载框
     *
     * @param msg 加载框文字
     */
    public void showLoading(String msg) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            if (!TextUtils.isEmpty(msg)) {
                loadingDialog.setTitleText(msg);
            }
            loadingDialog.show();
        }
        hideEmptyView();
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 加载框是否可以取消
     *
     * @return boolean
     */
    protected boolean loadingCancelable() {
        return true;
    }


    @Override
    public void onEmpty(Object tag) {
        dismissLoading();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        dismissLoading();
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    public Context getContext() {
        return context;
    }
    //***************************************IBaseView方法实现 end*************************************

    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    protected abstract P createPresenter();
}
