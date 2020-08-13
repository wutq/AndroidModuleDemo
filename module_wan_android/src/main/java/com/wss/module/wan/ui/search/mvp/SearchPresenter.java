package com.wss.module.wan.ui.search.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.search.mvp.contract.SearchContract;
import com.wss.module.wan.ui.search.mvp.model.SearchModule;

import java.util.List;

/**
 * Describe：搜索Presenter
 * Created by 吴天强 on 2018/11/15.
 */

public class SearchPresenter extends BasePresenter<SearchModule, SearchContract.View> implements SearchContract.Presenter {

    @Override
    public void search() {
        getView().showLoading();
        getModel().searchData(getView().getPage(), getView().getWord()).subscribe(
                string -> {
                    dismissLoading();
                    List<Article> datas = JsonUtils.getList(JsonUtils.getString(string, "datas"), Article.class);
                    if (ValidUtils.isValid(datas)) {
                        getView().refreshSearchData(datas);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> getView().onError("", t.getMessage()));
    }


    @Override
    protected SearchModule createModule() {
        return new SearchModule(getLifecycleOwner());
    }

    @Override
    public void start() {

    }
}
