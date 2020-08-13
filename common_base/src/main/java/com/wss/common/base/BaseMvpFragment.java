package com.wss.common.base;


import android.os.Bundle;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.widget.dialog.LoadingDialog;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：所有需要Mvp开发的Fragment的基类
 * Created by 吴天强 on 2018/10/17.
 */
@SuppressWarnings("unchecked")
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IBaseView {

    private P presenter;
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    /**
     * 返回presenter
     *
     * @return P
     */
    protected P getPresenter() {
        return presenter;
    }

    //***************************************IBaseView方法实现 start *************************************


    @Override
    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
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

    //***************************************IBaseView方法实现 end*************************************

    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    protected abstract P createPresenter();
}
