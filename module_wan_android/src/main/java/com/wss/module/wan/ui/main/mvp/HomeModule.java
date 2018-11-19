package com.wss.module.wan.ui.main.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：首页Module
 * Created by 吴天强 on 2018/10/17.
 */

public class HomeModule extends BaseModule {

    void getBanner(Context context, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(Api.GET_BANNER_LIST, callback);
    }

    void getArticleList(Context context, int page, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(String.format(Api.GET_ARTICLE_LIST, page), callback);
    }


    void getWXNumber(Context context, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(Api.WX_NUMBER, callback);
    }

}
