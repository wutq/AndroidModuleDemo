package com.wss.module.wan.ui.collection.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：收藏契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface CollectionContract {

    interface Model extends IBaseModel {
        /**
         * 获取收藏列表
         *
         * @param page     分页
         * @param callback 回调
         */
        void getCollectionList(int page, OnResultCallBack callback);
    }

    interface View extends IBaseView {
        /**
         * 获取分页
         */
        int getPage();

        /**
         * 收藏列表
         *
         * @param collections collections
         */
        void collectionList(List<Article> collections);
    }

    interface Presenter {
        /**
         * 获取我的收藏列表
         */
        void getCollectionList();
    }
}
