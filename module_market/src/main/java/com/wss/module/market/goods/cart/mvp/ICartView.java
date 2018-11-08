package com.wss.module.market.goods.cart.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.Vendor;

import java.util.List;

/**
 * Describe：购物车View
 * Created by 吴天强 on 2018/11/5.
 */

public interface ICartView extends IBaseView {

    void cartData(List<Vendor> dataList);
}
