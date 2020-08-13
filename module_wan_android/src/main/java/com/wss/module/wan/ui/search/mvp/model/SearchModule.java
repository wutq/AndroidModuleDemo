package com.wss.module.wan.ui.search.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.ui.search.mvp.contract.SearchContract;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：搜索Module
 * Created by 吴天强 on 2018/11/15.
 */
public class SearchModule extends BaseModel implements SearchContract.Module {

    public SearchModule(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }


    @Override
    public Observable<String> searchData(int page, String word) {
        return NetworkManage.createPostForm().request(getLifecycleOwner(), String.format(Api.SEARCH_LIST, page, word));
    }
}
