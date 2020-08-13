package com.wss.module.wan.ui.series.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：体系文章契约类
 * Created by 吴天强 on 2018/11/21.
 */
public interface SeriesArticleContract {

    interface Model {

        /**
         * 获取体系文章
         *
         * @param page 页码
         * @param id   体系二级分类ID
         * @return WanListBase
         */
        Observable<String> getArticle(int page, String id);
    }

    interface View extends IBaseView {
        /**
         * id
         *
         * @return 系列id
         */
        String getSeriesId();

        /**
         * 返回页码
         *
         * @return 页码
         */
        int getPage();

        /**
         * 刷新体系文章
         *
         * @param articles 体系文章
         */
        void refreshArticleList(List<Article> articles);
    }

    interface Presenter {
        /**
         * 获取体系文章
         */
        void getArticle();
    }

}
