package com.wss.module.wan.ui.collection.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：收藏契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface CollectionContract {

    interface Model {
        /**
         * 获取收藏列表
         *
         * @param page 分页
         * @return string
         */
        Observable<String> getCollectionList(int page);
    }

    interface View extends IBaseView {
        /**
         * 获取分页
         *
         * @return page
         */
        int getPage();

        /**
         * 收藏列表
         *
         * @param collections collections
         */
        void refreshCollectionList(List<Article> collections);
    }

    interface Presenter {
        /**
         * 获取我的收藏列表
         */
        void getCollectionList();
    }
}
