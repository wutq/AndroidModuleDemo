package com.wss.module.wan.ui.wxnumber.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXNumberContract;

import java.util.List;

/**
 * Describe：公众号Presenter
 * Created by 吴天强 on 2018/11/15.
 */

public class WXArticlePresenter extends BasePresenter<WXNumberContract.Model, WXNumberContract.View>
        implements WXNumberContract.Presenter {


    @Override
    public void getWXArticle() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getWXArticle(getView().getWXNumberId(), getView().getPage(), new OnResultCallBack<String>() {
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
    protected WXArticleModel createModule() {
        return new WXArticleModel();
    }

    @Override
    public void start() {
        getWXArticle();
    }
}
