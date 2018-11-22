package com.wss.module.wan.ui.search.mvp;

import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.ui.search.mvp.contract.SearchContract;

/**
 * Describe：搜索Module
 * Created by 吴天强 on 2018/11/15.
 */

public class SearchModule implements SearchContract.Module {

    @Override
    public void searchData(int page, String word, OnResultCallBack callback) {
        HttpUtils.getInstance()
                .postRequest(String.format(Api.SEARCH_LIST, page, word), callback);
    }
}
