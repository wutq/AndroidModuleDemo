package com.wss.module.wan.ui.collection.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.collection.mvp.contract.CollectionContract;

import java.util.List;

/**
 * Describe：收藏Presenter
 * Created by 吴天强 on 2018/11/16.
 */

public class CollectionPresenter extends BasePresenter<CollectionContract.Model, CollectionContract.View>
        implements CollectionContract.Presenter {


    @Override
    public void getCollectionList() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getCollectionList(getView().getPage(), new OnResultCallBack<String>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {
                    if (code == Constants.SUCCESS_CODE) {
                        List<Article> articleList = JSON.parseArray(JSON.parseObject(response).getString("datas"), Article.class);
                        if (articleList == null || articleList.size() < 1) {
                            getView().onEmpty(tag);
                        } else {
                            getView().collectionList(articleList);
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
    protected CollectionModel createModule() {
        return new CollectionModel();
    }

    @Override
    public void start() {
        getCollectionList();
    }

}
