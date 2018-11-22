package com.wss.module.wan.ui.setup.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：体系文章契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface SystemArticleContract {

    interface Model extends IBaseModel {

        /**
         * 获取体系文章
         *
         * @param page     页码
         * @param id       体系二级分类ID
         * @param callback 回调
         */
        void getArticle(int page, String id, OnResultCallBack callback);
    }

    interface View extends IBaseView {
        /**
         * 返回二级菜单ID
         */
        String getSetupId();

        /**
         * 返回页码
         */
        int getPage();

        /**
         * 体系文章
         */
        void articleList(List<Article> articles);
    }

    interface Presenter {
        /**
         * 获取体系文章
         */
        void getArticle();
    }


}
