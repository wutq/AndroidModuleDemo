package com.wss.module.wan.ui.wxnumber.mvp;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXNumberContract;

/**
 * Describe：公众号文章Module
 * Created by 吴天强 on 2018/11/15.
 */

public class WXArticleModule implements WXNumberContract.Module {


    @Override
    public void getWXArticle(int id, int page, ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.WX_ARTICLE_LIST, id, page), callback);
    }
}
