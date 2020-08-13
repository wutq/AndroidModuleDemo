package com.wss.module.market.ui.goods.cart.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.market.bean.Vendor;
import com.wss.module.market.ui.goods.cart.mvp.contract.ShoppingCartContract;
import com.wss.module.market.ui.goods.cart.mvp.model.ShoppingCartModel;
import com.wss.module.market.utils.ShoppingCartUtils;

import java.util.List;

/**
 * Describe：购物车Presenter
 * Created by 吴天强 on 2018/11/5.
 */
public class CartPresenter extends BasePresenter<ShoppingCartModel, ShoppingCartContract.View> implements ShoppingCartContract.Presenter {

    @Override
    public void getCartData() {
        List<Vendor> localData = ShoppingCartUtils.getLocalData();
        if (localData == null || localData.size() < 1) {
            getView().onEmpty("");
        } else {
            getView().refreshCartData(localData);
        }
    }

    @Override
    protected ShoppingCartModel createModule() {
        return new ShoppingCartModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        getCartData();
    }
}
