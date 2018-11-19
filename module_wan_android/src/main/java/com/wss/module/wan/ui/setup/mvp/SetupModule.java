package com.wss.module.wan.ui.setup.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：Module
 * Created by 吴天强 on 2018/11/15.
 */

public class SetupModule extends BaseModule {

    void getTree(Context context, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(Api.TREE, callback);
    }

    void getArticle(Context context, int page, String id, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(String.format(Api.TREE_ARTICLE, page, id), callback);
    }
}
