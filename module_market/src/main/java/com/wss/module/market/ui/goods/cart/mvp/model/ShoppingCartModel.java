package com.wss.module.market.ui.goods.cart.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.module.market.ui.goods.cart.mvp.contract.ShoppingCartContract;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：购物车Model
 * Created by 吴天强 on 2020/8/11.
 */
public class ShoppingCartModel extends BaseModel implements ShoppingCartContract.Model {

    public ShoppingCartModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
}
