package com.wss.module.wan.ui.search.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class SearchModule extends BaseModule {

    void searchData(Context context, int page, String word, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .postRequest(String.format(Api.SEARCH_LIST, page, word), callback);
    }
}
