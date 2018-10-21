package com.wss.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.widget.dialog.LoadingDialog;

/**
 * Describe：所有需要Mvp开发的Fragment的基类
 * Created by 吴天强 on 2018/10/17.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IBaseView {
    protected Context mContext;
    protected P presenter;
    private LoadingDialog loadingDialog;


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        loadingDialog = new LoadingDialog(mContext);

        if (presenter == null) {
            presenter = createPresenter();
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


    //***************************************IBaseView方法实现*************************************
    @Override
    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showEmpty(Object tag) {

    }

    @Override
    public void showError(Object tag, String error) {

    }
    //***************************************IBaseView方法实现*************************************

    protected abstract P createPresenter();
}
