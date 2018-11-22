package com.wss.module.wan.ui.collection.mvp;

import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.ui.collection.mvp.contract.CollectionContract;

/**
 * Describe：收藏Module
 * Created by 吴天强 on 2018/11/16.
 */

public class CollectionModel implements CollectionContract.Model {


    @Override
    public void getCollectionList(int page, OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.COLLECTION_LIST, page), callback);
    }
}
