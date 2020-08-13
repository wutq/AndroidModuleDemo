package com.wss.module.wan.ui.project.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.project.mvp.contract.ProjectContract;
import com.wss.module.wan.ui.project.mvp.model.ProjectModel;

import java.util.List;

/**
 * Describe：项目控制器
 * Created by 吴天强 on 2018/11/15.
 */
public class ProjectPresenter extends BasePresenter<ProjectModel, ProjectContract.View>
        implements ProjectContract.Presenter {

    @Override
    protected ProjectModel createModule() {
        return new ProjectModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        getProjectType();
    }

    /**
     * 获取项目分类
     */
    private void getProjectType() {
        getModel().getProjectType().subscribe(classifications -> getView().refreshProjectTypeList(classifications));
    }

    @Override
    public void getProject() {
        showLoading();
        getModel().getProject(getView().getPage(), getView().getTypeId()).subscribe(
                string -> {
                    dismissLoading();
                    List<Article> articleList = JsonUtils.getList(JsonUtils.getString(string, "datas"), Article.class);
                    if (ValidUtils.isValid(articleList)) {
                        getView().refreshProjectList(articleList);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> getView().onError("", t.getMessage()));
    }
}