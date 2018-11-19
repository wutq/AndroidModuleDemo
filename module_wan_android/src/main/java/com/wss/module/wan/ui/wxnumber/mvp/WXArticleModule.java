package com.wss.module.wan.ui.wxnumber.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class WXArticleModule extends BaseModule {

    void getWXArticle(Context context, int id, int page, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(String.format(Api.WX_ARTICLE_LIST, id, page), callback);
    }
}
