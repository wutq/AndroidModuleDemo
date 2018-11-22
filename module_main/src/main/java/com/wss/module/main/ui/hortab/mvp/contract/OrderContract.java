package com.wss.module.main.ui.hortab.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.module.main.bean.Order;

import java.util.List;

/**
 * Describe：订单契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface OrderContract {

    interface Module extends IBaseModel {

    }

    interface View extends IBaseView {

        /**
         * 订单列表
         */
        void orderList(List<Order> orders);

        /**
         * 分页page
         */
        int getPage();

        /**
         * 订单类型
         */
        int getType();
    }

    interface Presenter {
        /**
         * 获取订单列表
         */
        void getOrderList();
    }
}
