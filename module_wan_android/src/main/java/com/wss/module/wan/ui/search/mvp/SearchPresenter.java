package com.wss.module.wan.ui.search.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.search.mvp.contract.SearchContract;

import java.util.List;

/**
 * Describe：搜索Presenter
 * Created by 吴天强 on 2018/11/15.
 */

public class SearchPresenter extends BasePresenter<SearchContract.Module, SearchContract.View> implements SearchContract.Presenter {


    public void search() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().searchData(getView().getPage(), getView().getWord(), new OnResultCallBack<String>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {
                    if (code == Constants.SUCCESS_CODE) {
                        List<Article> articleList = JSON.parseArray(JSON.parseObject(response).getString("datas"), Article.class);
                        if (articleList == null || articleList.size() < 1) {
                            getView().onEmpty(tag);
                        } else {
                            getView().searchData(articleList);
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
    protected SearchModule createModule() {
        return new SearchModule();
    }

    @Override
    public void start() {

    }
}
