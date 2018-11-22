package com.wss.module.market.ui.main.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.GoodsInfo;

import java.util.List;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface MarketMainContract {

    interface Model extends IBaseModel {

    }

    interface View extends IBaseView {
        /**
         * 商品列表
         *
         * @param dataList dataList
         */
        void dataList(List<GoodsInfo> dataList);

        /**
         * 购物车商品数量
         *
         * @param count count
         */
        void cartCount(long count);
    }

    interface Presenter {

        /**
         * 商品列表
         */
        void getMarketData();

        /**
         * 购物车数量
         */
        void getCartCount();
    }
}
