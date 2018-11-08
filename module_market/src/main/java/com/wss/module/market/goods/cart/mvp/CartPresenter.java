package com.wss.module.market.goods.cart.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.market.bean.Vendor;
import com.wss.module.market.utils.ShoppingCartUtils;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/5.
 */

public class CartPresenter extends BasePresenter<CartModule, ICartView> {


    public void getCartData() {
        if (isViewAttached()) {
            List<Vendor> localData = ShoppingCartUtils.getLocalData();
            if (localData == null || localData.size() < 1) {
                getView().onEmpty("");
            } else {
                getView().cartData(localData);
            }
        }
    }

    @Override
    protected CartModule createModule() {
        return new CartModule();
    }
}
