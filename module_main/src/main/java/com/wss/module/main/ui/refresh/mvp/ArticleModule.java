package com.wss.module.main.ui.refresh.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.IBaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：文章Module
 * Created by 吴天强 on 2018/10/23.
 */

public class ArticleModule implements IBaseModule {


    void getArticleList(Context context, int page, ResponseCallback callback) {
        HttpUtils.create(context)
                .getRequest(String.format(Api.GET_ARTICLE_LIST, page), callback);
    }
}
