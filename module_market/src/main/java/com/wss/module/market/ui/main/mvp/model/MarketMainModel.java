package com.wss.module.market.ui.main.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.module.market.ui.main.mvp.contract.MarketMainContract;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：商品首页Model
 * Created by 吴天强 on 2020/8/11.
 */
public class MarketMainModel extends BaseModel implements MarketMainContract.Model {

    public MarketMainModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
}
