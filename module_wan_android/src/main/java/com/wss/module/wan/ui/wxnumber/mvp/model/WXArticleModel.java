package com.wss.module.wan.ui.wxnumber.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXArticleContract;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：微信文章Model
 * Created by 吴天强 on 2020/8/13.
 */
public class WXArticleModel extends BaseModel implements WXArticleContract.Model {

    public WXArticleModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<String> getWxArticle(int wxId, int page) {
        return NetworkManage.createGet().request(getLifecycleOwner(), String.format(Api.WX_ARTICLE_LIST, wxId, page));
    }
}
