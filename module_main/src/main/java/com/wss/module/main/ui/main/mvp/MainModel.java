package com.wss.module.main.ui.main.mvp;

import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.NetConfig;
import com.wss.common.net.RequestParam;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.common.utils.Utils;
import com.wss.module.main.ui.main.mvp.contract.MainContract;

/**
 * Describe：首页Module
 * Created by 吴天强 on 2018/11/21.
 */

public class MainModel implements MainContract.Model {


    @Override
    public void checkUpdate(OnResultCallBack callback) {
        RequestParam param = new RequestParam();
        param.addParameter("versionCode", Utils.getVersionCode());
        HttpUtils.getInstance()
                .setBaseUrl(NetConfig.Url.MY_SERVICE_URL)
                .getRequest(Api.CHECK_UPDATE, param, callback);
    }
}
