package com.wss.module.wan.main.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultStringCallBack;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：Presenter
 * Created by 吴天强 on 2018/10/18.
 */

public class WanMainPresenter extends BasePresenter<WanMainModule, IWanMainView> {


    public void getArticleList(int page) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getArticleList(getContext(), page, new OnResultStringCallBack() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {
                    if (code == 0) {
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
    protected WanMainModule createModule() {
        return new WanMainModule();
    }
}
