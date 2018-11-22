package com.wss.module.wan.ui.main.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.Template;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.BannerInfo;
import com.wss.module.wan.bean.WXNumber;

import java.util.List;

/**
 * Describe：wanAndroid首页契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface HomeContract {

    interface Model extends IBaseModel {

        /**
         * 获取Banner
         *
         * @param callback 回调
         */
        void getBanner(OnResultCallBack callback);

        /**
         * 获取文章列表
         *
         * @param callback 回调
         */
        void getArticleList(int page, OnResultCallBack callback);

        /**
         * 获取微信公众号
         *
         * @param callback 回调
         */
        void getWXNumber(OnResultCallBack callback);
    }

    interface View extends IBaseView {
        /**
         * banner列表
         */
        void bannerList(List<BannerInfo> banners);

        /**
         * 模块列表
         */
        void blockList(List<Template> blockList);

        /**
         * 返回分页
         */
        int getPage();

        /**
         * 文章列表
         */
        void articleList(List<Article> articles);

        /**
         * 公众号列表
         */
        void wxNumber(List<WXNumber> wxNumbers);
    }

    interface Presenter {
        /**
         * 获取Banner
         */
        void getBanner();

        /**
         * 获取文章列表
         */
        void getArticleList();

        /**
         * 获取微信公众号
         */
        void getWXNumber();

        /**
         * 豆腐块
         */
        void getBlockList();
    }


}
