package com.wss.module.wan.ui.project.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.ui.project.mvp.contract.ProjectContract;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class ProjectPresenter extends BasePresenter<ProjectContract.Model, ProjectContract.View>
        implements ProjectContract.Presenter {

    @Override
    protected ProjectModel createModule() {
        return new ProjectModel();
    }

    @Override
    public void start() {
        getProjectType();
    }

    @Override
    public void getProjectType() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getProjectType(new OnResultCallBack<List<Classification>>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, List<Classification> response) {
                    if (code == Constants.SUCCESS_CODE) {
                        if (response != null && response.size() > 0) {
                            getView().projectTypeList(response);
                        } else {
                            getView().onEmpty(tag);
                        }

                    } else {
                        getView().onError(tag, msg);
                    }
                }

                @Override
                public void onFailure(Object tag, Exception e) {
                    getView().onError(tag, Constants.ERROR_MESSAGE);
                }

                @Override
                public void onCompleted() {
                    getView().dismissLoading();
                }
            });
        }
    }

    @Override
    public void getProject() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getProject(getView().getPage(), getView().getTypeId(), new OnResultCallBack<String>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {
                    if (code == Constants.SUCCESS_CODE) {
                        final List<Article> articleList = JSON.parseArray(JSON.parseObject(response).getString("datas"), Article.class);
                        if (articleList == null || articleList.size() < 1) {
                            getView().onProjectEmpty();
                        } else {
                            getView().projectList(articleList);
                        }

                    } else {
                        getView().onProjectError(msg);
                    }
                }

                @Override
                public void onFailure(Object tag, Exception e) {
                    getView().onProjectError(Constants.ERROR_MESSAGE);
                }

                @Override
                public void onCompleted() {
                    getView().dismissLoading();
                }
            });
        }
    }
}