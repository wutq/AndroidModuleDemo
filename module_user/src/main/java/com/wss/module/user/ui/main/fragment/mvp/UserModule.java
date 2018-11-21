package com.wss.module.user.ui.main.fragment.mvp;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.RequestParam;
import com.wss.common.utils.Utils;
import com.wss.module.user.ui.main.fragment.mvp.contract.UserContract;

/**
 * Describe：我的Module
 * Created by 吴天强 on 2018/11/21.
 */

public class UserModule implements UserContract.Module {

    @Override
    public void checkUpdate(ResponseCallback callback) {
        RequestParam param = new RequestParam();
        param.addParameter("versionCode", Utils.getVersionCode());
        HttpUtils.getInstance()
                .getRequest(Api.CHECK_UPDATE, param, callback);
    }
}
