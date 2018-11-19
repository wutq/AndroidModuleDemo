package com.wss.module.wan.ui.project.mvp;

import android.content.Context;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.BaseModule;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class ProjectModule extends BaseModule {

    void getType(Context context, String tag, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(Api.PROJECT, tag, callback);
    }

    void getProject(Context context, String tag, int page, int id, ResponseCallback callback) {
        HttpUtils.getInstance(context)
                .getRequest(String.format(Api.PROJECT_LIST, page, id), tag, callback);
    }
}
