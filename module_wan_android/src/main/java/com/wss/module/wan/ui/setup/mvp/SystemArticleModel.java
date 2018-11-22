package com.wss.module.wan.ui.setup.mvp;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.module.wan.ui.setup.mvp.contract.SystemArticleContract;

/**
 * Describe：系统下文章Module
 * Created by 吴天强 on 2018/11/21.
 */

public class SystemArticleModel implements SystemArticleContract.Model {


    @Override
    public void getArticle(int page, String id, ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.TREE_ARTICLE, page, id), callback);
    }
}
