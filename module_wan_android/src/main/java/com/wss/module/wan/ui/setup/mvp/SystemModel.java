package com.wss.module.wan.ui.setup.mvp;

import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.ui.setup.mvp.contract.SystemContract;

/**
 * Describe：体系Module
 * Created by 吴天强 on 2018/11/21.
 */

public class SystemModel implements SystemContract.Model {

    @Override
    public void getSystem(OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(Api.TREE, callback);
    }
}
