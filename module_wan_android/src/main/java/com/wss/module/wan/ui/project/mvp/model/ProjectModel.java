package com.wss.module.wan.ui.project.mvp.model;


import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.ui.project.mvp.contract.ProjectContract;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：项目Module
 * Created by 吴天强 on 2018/11/15.
 */
public class ProjectModel extends BaseModel implements ProjectContract.Model {

    public ProjectModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<List<Classification>> getProjectType() {
        return NetworkManage.createGet().requestList(getLifecycleOwner(), Api.PROJECT, Classification.class);
    }

    @Override
    public Observable<String> getProject(int page, int id) {
        return NetworkManage.createGet().request(getLifecycleOwner(), String.format(Api.PROJECT_LIST, page, id));
    }
}
