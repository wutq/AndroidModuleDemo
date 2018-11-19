package com.wss.module.wan.ui.setup.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultListCallBack;
import com.wss.common.net.callback.OnResultStringCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class SetupPresenter extends BasePresenter<SetupModule, ISetupView> {


    public void getSetup() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getTree(getContext(), new OnResultListCallBack<List<Classification>>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, List<Classification> response) {
                    if (code == 0) {
                        if (response == null || response.size() < 1) {
                            getView().onEmpty(tag);
                        } else {
                            getView().setupList(response);
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
    }

    public void getArticle() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getArticle(getContext(), getView().getPage(), getView().getSetupId(), new OnResultStringCallBack() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {
                    if (code == Constants.SUCCESS_CODE) {
                        List<Article> articleList = JSON.parseArray(JSON.parseObject(response).getString("datas"), Article.class);
                        if (articleList == null || articleList.size() < 1) {
                            getView().onEmpty(tag);
                        } else {
                            getView().articleList(articleList);
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
    }


    @Override
    protected SetupModule createModule() {
        return new SetupModule();
    }
}
