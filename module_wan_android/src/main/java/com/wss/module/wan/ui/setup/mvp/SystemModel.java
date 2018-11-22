package com.wss.module.wan.ui.setup.mvp;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.module.wan.ui.setup.mvp.contract.SystemContract;

/**
 * Describe：体系Module
 * Created by 吴天强 on 2018/11/21.
 */

public class SystemModel implements SystemContract.Model {

    @Override
    public void getSystem(ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(Api.TREE, callback);
    }
}
