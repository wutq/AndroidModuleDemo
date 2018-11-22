package com.wss.module.main.ui.refresh.mvp;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.ui.refresh.mvp.contract.RefreshContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：Presenter
 * Created by 吴天强 on 2018/10/23.
 */

public class StringPresenter extends BasePresenter<RefreshContract.Model, RefreshContract.View>
        implements RefreshContract.Presenter {


    @Override
    public void getStringList() {
        if (isViewAttached()) {
            getView().showLoading();
            new Thread(new Runnable() {
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        final List<String> result = new ArrayList<>();
                        //第一次加载
                        for (int i = 1; i < 11; i++) {
                            result.add(String.format("第%d页，第%d条测试数据", getView().getPage(), i));
                        }

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().stringList(result);
                                getView().dismissLoading();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }


    @Override
    protected RefreshContract.Model createModule() {
        return null;
    }

    @Override
    public void start() {

    }
}
