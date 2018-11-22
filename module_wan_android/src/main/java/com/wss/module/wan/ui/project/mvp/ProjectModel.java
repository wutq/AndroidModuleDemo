package com.wss.module.wan.ui.project.mvp;


import com.wss.common.net.Api;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.ui.project.mvp.contract.ProjectContract;

/**
 * Describe：项目Module
 * Created by 吴天强 on 2018/11/15.
 */

public class ProjectModel implements ProjectContract.Model {


    @Override
    public void getProjectType(OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(Api.PROJECT, callback);
    }

    @Override
    public void getProject(int page, int id, OnResultCallBack callback) {
        HttpUtils.getInstance()
                .getRequest(String.format(Api.PROJECT_LIST, page, id), callback);
    }
}
