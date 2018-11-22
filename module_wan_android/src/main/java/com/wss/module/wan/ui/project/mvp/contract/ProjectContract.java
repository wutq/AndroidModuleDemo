package com.wss.module.wan.ui.project.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;

import java.util.List;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface ProjectContract {

    interface Model extends IBaseModel {

        /**
         * 获取项目分类
         *
         * @param callback 回调
         */
        void getProjectType(OnResultCallBack callback);

        /**
         * 获取分类下的项目
         *
         * @param page     分页
         * @param id       分类ID
         * @param callback 回调
         */
        void getProject(int page, int id, OnResultCallBack callback);
    }

    interface View extends IBaseView {
        /**
         * 返回分页
         */
        int getPage();

        /**
         * 返回
         */
        int getTypeId();

        /**
         * 项目分类列表
         *
         * @param types types
         */
        void projectTypeList(List<Classification> types);

        /**
         * 分类下的项目列表
         *
         * @param projects projects
         */
        void projectList(List<Article> projects);

        /**
         * 项目列表请求Error
         */
        void onProjectError(String message);

        /**
         * 项目列表请求为空
         */
        void onProjectEmpty();
    }

    interface Presenter {
        /**
         * 获取项目分类
         */
        void getProjectType();

        /**
         * 获取分类下的项目
         */
        void getProject();
    }
}
