package com.wss.module.wan.ui.collection.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：收藏Module
 * Created by 吴天强 on 2018/11/16.
 */

public class CollectionModule extends BaseModule {

    void getCollectionList(Context context, int page, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(String.format(Api.COLLECTION_LIST, page), callback);
    }

}
