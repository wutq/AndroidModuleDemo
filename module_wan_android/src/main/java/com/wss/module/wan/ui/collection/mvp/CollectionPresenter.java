package com.wss.module.wan.ui.collection.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.collection.mvp.contract.CollectionContract;
import com.wss.module.wan.ui.collection.mvp.model.CollectionModel;

import java.util.List;

/**
 * Describe：收藏Presenter
 * Created by 吴天强 on 2018/11/16.
 */
public class CollectionPresenter extends BasePresenter<CollectionModel, CollectionContract.View>
        implements CollectionContract.Presenter {

    @Override
    public void getCollectionList() {
        showLoading();
        getModel().getCollectionList(getView().getPage()).subscribe(
                string -> {
                    dismissLoading();
                    List<Article> list = JsonUtils.getList(JsonUtils.getString(string, "datas"), Article.class);
                    if (ValidUtils.isValid(list)) {
                        getView().refreshCollectionList(list);
                    } else {
                        getView().onEmpty("");
                    }
                },
                t -> getView().onError("", t.getMessage())
        );
    }


    @Override
    protected CollectionModel createModule() {
        return new CollectionModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        getCollectionList();
    }
}
