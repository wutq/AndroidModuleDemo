package com.wss.module.wan.ui.project.mvp;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultListCallBack;
import com.wss.common.net.callback.OnResultStringCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class ProjectPresenter extends BasePresenter<ProjectModule, IProjectView> {


    public void getType(String tag) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getType(getContext(), tag, new OnResultListCallBack<List<Classification>>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, List<Classification> response) {
                    if (code == Constants.SUCCESS_CODE) {
                        if (response != null && response.size() > 0) {
                            getView().projectType(response);
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

    public void getProject(String tag) {
        Logger.e("page:" + getView().getPage());
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getProject(getContext(), tag, getView().getPage(), getView().getTypeId(), new OnResultStringCallBack() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {
                    if (code == Constants.SUCCESS_CODE) {
                        final List<Article> articleList = JSON.parseArray(JSON.parseObject(response).getString("datas"), Article.class);
                        if (articleList == null || articleList.size() < 1) {
                            getView().onEmpty(tag);
                        } else {
                            getView().projectList(articleList);
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
    protected ProjectModule createModule() {
        return new ProjectModule();
    }
}
