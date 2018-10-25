package com.wss.module.main.ui.hortab.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.main.bean.Order;

import java.util.List;

/**
 * Describe：订单View
 * Created by 吴天强 on 2018/10/23.
 */

public interface IOrderView extends IBaseView {

    void orderList(List<Order> orders);

    int getPage();

    int getType();
}
