package com.wss.module.wan.ui.series.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.series.mvp.contract.SeriesArticleContract;
import com.wss.module.wan.ui.series.mvp.model.SeriesArticleModel;

import java.util.List;

/**
 * Describe：体系文章Presenter
 * Created by 吴天强 on 2018/11/15.
 */
public class SeriesArticlePresenter extends BasePresenter<SeriesArticleModel, SeriesArticleContract.View>
        implements SeriesArticleContract.Presenter {


    @Override
    public void getArticle() {
        showLoading();
        getModel().getArticle(getView().getPage(), getView().getSeriesId()).subscribe(
                string -> {
                    dismissLoading();
                    List<Article> list = JsonUtils.getList(JsonUtils.getString(string, "datas"), Article.class);
                    if (ValidUtils.isValid(list)) {
                        getView().refreshArticleList(list);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> getView().onError("", t.getMessage()));
    }


    @Override
    protected SeriesArticleModel createModule() {
        return new SeriesArticleModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        getArticle();
    }
}
