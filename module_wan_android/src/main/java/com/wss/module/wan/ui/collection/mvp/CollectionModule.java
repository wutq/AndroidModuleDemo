package com.wss.module.wan.ui.collection.mvp;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.module.wan.ui.collection.mvp.contract.CollectionContract;

/**
 * Describe：收藏Module
 * Created by 吴天强 on 2018/11/16.
 */

public class CollectionModule implements CollectionContract.Module {


    @Override
    public void getCollectionList(int page, ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.COLLECTION_LIST, page), callback);
    }
}
