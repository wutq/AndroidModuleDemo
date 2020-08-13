package com.wss.module.market.ui.goods.detail.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.module.market.ui.goods.detail.mvp.contract.GoodsDetailContract;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：商品详情Model
 * Created by 吴天强 on 2020/8/11.
 */
public class GoodsDetailModel extends BaseModel implements GoodsDetailContract.Model {

    public GoodsDetailModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
}
