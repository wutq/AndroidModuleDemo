package com.wss.module.wan.ui.project.mvp;


import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.module.wan.ui.project.mvp.contract.ProjectContract;

/**
 * Describe：项目Module
 * Created by 吴天强 on 2018/11/15.
 */

public class ProjectModel implements ProjectContract.Model {


    @Override
    public void getProjectType(ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(Api.PROJECT, callback);
    }

    @Override
    public void getProject(int page, int id, ResponseCallback callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.PROJECT_LIST, page, id), callback);
    }
}
