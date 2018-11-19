package com.wss.module.wan.ui.wxnumber.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultStringCallBack;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class WXArticlePresenter extends BasePresenter<WXArticleModule, IWXArticleView> {


    public void getList() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getWXArticle(getContext(), getView().getWXNumberId(), getView().getPage(), new OnResultStringCallBack() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String
                        response) {
                    if (code == Constants.SUCCESS_CODE) {
                        final List<Article> articleList = JSON.parseArray(JSON.parseObject(response).getString("datas"), Article.class);
                        if (articleList == null || articleList.size() < 1) {
                            getView().onEmpty(tag);
                        } else {
                            getView().wxArticleList(articleList);
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
    protected WXArticleModule createModule() {
        return new WXArticleModule();
    }
}
