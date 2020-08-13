package com.wss.module.wan.ui.project.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface ProjectContract {

    interface Model {

        /**
         * 获取项目分类
         *
         * @return 项目分类
         */
        Observable<List<Classification>> getProjectType();

        /**
         * 获取分类下的项目
         *
         * @param page 分页
         * @param id   分类ID
         * @return sting
         */
        Observable<String> getProject(int page, int id);
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
        void refreshProjectTypeList(List<Classification> types);

        /**
         * 分类下的项目列表
         *
         * @param projects projects
         */
        void refreshProjectList(List<Article> projects);
    }

    interface Presenter {
        /**
         * 获取分类下的项目
         */
        void getProject();
    }
}
