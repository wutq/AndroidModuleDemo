package com.wss.module.market.ui.goods.cart.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.Vendor;

import java.util.List;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface ShoppingCartContract {

    interface Model extends IBaseModel{

    }

    interface View extends IBaseView {

        /**
         * 购物车数据
         *
         * @param dataList dataList
         */
        void cartData(List<Vendor> dataList);
    }

    interface Presenter {
        /**
         * 获取购物车数据
         */
        void getCartData();
    }
}
