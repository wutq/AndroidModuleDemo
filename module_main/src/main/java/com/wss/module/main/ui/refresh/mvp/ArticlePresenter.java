package com.wss.module.main.ui.refresh.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constant;
import com.wss.common.net.callback.OnResultStringCallBack;
import com.wss.module.main.bean.Article;

import java.util.List;

/**
 * Describe：文章Article
 * Created by 吴天强 on 2018/10/23.
 */

public class ArticlePresenter extends BasePresenter<ArticleModule, IArticleView> {


    public void getArticleList() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getArticleList(getContext(), getView().getPage(), new OnResultStringCallBack() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String
                        response) {
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
                    getView().onError(tag, Constant.ERROR_MESSAGE);
                }

                @Override
                public void onCompleted() {
                    getView().dismissLoading();
                }
            });
        }
    }


    @Override
    protected ArticleModule createModule() {
        return new ArticleModule();
    }
}
