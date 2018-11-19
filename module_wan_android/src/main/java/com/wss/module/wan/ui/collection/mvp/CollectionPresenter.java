package com.wss.module.wan.ui.collection.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultStringCallBack;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：收藏Presenter
 * Created by 吴天强 on 2018/11/16.
 */

public class CollectionPresenter extends BasePresenter<CollectionModule, ICollectionView> {


    public void getCollectionList() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getCollectionList(getContext(), getView().getPage(), new OnResultStringCallBack() {
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
    protected CollectionModule createModule() {
        return new CollectionModule();
    }
}
