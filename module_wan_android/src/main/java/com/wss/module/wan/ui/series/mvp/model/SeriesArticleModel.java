package com.wss.module.wan.ui.series.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.ui.series.mvp.contract.SeriesArticleContract;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：系统下文章Module
 * Created by 吴天强 on 2018/11/21.
 */
public class SeriesArticleModel extends BaseModel implements SeriesArticleContract.Model {

    public SeriesArticleModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<String> getArticle(int page, String id) {
        return NetworkManage.createGet().request(getLifecycleOwner(), String.format(Api.TREE_ARTICLE, page, id));
    }
}
