package com.wss.module.wan.ui.setup.mvp;

import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.ui.setup.mvp.contract.SystemArticleContract;

/**
 * Describe：系统下文章Module
 * Created by 吴天强 on 2018/11/21.
 */

public class SystemArticleModel implements SystemArticleContract.Model {


    @Override
    public void getArticle(int page, String id, OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.TREE_ARTICLE, page, id), callback);
    }
}
