package com.wss.module.wan.main.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.IBaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：Module
 * Created by 吴天强 on 2018/10/18.
 */

public class WanMainModule implements IBaseModule {

    void getArticleList(Context context, int page, ResponseCallback callback) {
        HttpUtils.create(context)
                .getRequest(String.format(Api.GET_ARTICLE_LIST, page), callback);
    }

}
