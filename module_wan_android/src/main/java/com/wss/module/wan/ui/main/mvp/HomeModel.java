package com.wss.module.wan.ui.main.mvp;

import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.ui.main.mvp.contract.HomeContract;

/**
 * Describe：首页Module
 * Created by 吴天强 on 2018/10/17.
 */

public class HomeModel implements HomeContract.Model {
    @Override
    public void getBanner(OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(Api.GET_BANNER_LIST, callback);
    }

    @Override
    public void getArticleList(int page, OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.GET_ARTICLE_LIST, page), callback);
    }

    @Override
    public void getWXNumber(OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(Api.WX_NUMBER, callback);
    }

}
