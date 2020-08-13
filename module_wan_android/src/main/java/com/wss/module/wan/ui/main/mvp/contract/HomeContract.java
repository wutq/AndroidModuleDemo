package com.wss.module.wan.ui.main.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.Template;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.BannerInfo;
import com.wss.module.wan.bean.WXNumber;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：wanAndroid首页契约类
 * Created by 吴天强 on 2018/11/21.
 */
public interface HomeContract {

    interface Model {

        /**
         * 获取Banner
         *
         * @return Banner
         */
        Observable<List<BannerInfo>> getBanner();

        /**
         * 获取文章列表
         *
         * @param page 分页
         * @return string
         */
        Observable<String> getArticleList(int page);

        /**
         * 获取微信公众号
         *
         * @return WXNumber
         */
        Observable<List<WXNumber>> getWxNumber();
    }

    interface View extends IBaseView {
        /**
         * banner列表
         *
         * @param banners banner列表
         */
        void refreshBannerList(List<BannerInfo> banners);

        /**
         * 模块列表
         *
         * @param menuList 菜单列表
         */
        void refreshMenuList(List<Template> menuList);

        /**
         * 返回分页
         *
         * @return 分页
         */
        int getPage();

        /**
         * 文章列表
         *
         * @param articles 文章列表
         */
        void refreshArticleList(List<Article> articles);
    }

    interface Presenter {
        /**
         * 获取文章列表
         */
        void getArticleList();
    }
}
