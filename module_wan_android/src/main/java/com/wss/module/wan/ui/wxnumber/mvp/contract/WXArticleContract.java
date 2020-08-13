package com.wss.module.wan.ui.wxnumber.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：微信文章契约类
 * Created by 吴天强 on 2020/8/13.
 */
public interface WXArticleContract {

    interface Model {

        /**
         * 获取微信公众号文章
         *
         * @param wxId 公众号ID
         * @param page 分页
         * @return String
         */
        Observable<String> getWxArticle(int wxId, int page);

    }

    interface View extends IBaseView {

        /**
         * 分页
         *
         * @return 页码
         */
        int getPage();

        /**
         * 公众号ID
         *
         * @return id
         */
        int getWxId();

        /**
         * 刷新列表
         *
         * @param articleList 文章
         */
        void refreshWxArticle(List<Article> articleList);
    }

    interface Presenter {
        /**
         * 获取文章
         */
        void getArticle();
    }
}
