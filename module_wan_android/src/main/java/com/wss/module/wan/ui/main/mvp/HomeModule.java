package com.wss.module.wan.ui.main.mvp;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.module.wan.ui.main.mvp.contract.HomeContract;

/**
 * Describe：首页Module
 * Created by 吴天强 on 2018/10/17.
 */

public class HomeModule implements HomeContract.Module {
    @Override
    public void getBanner(ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(Api.GET_BANNER_LIST, callback);
    }

    @Override
    public void getArticleList(int page, ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.GET_ARTICLE_LIST, page), callback);
    }

    @Override
    public void getWXNumber(ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(Api.WX_NUMBER, callback);
    }

}
