package com.wss.module.market.ui.goods.detail.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.module.market.ui.goods.detail.mvp.contract.GoodsCommentContract;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe： 商品评论Model
 * Created by 吴天强 on 2020/8/11.
 */
public class GoodsCommentModel extends BaseModel implements GoodsCommentContract.Model {

    public GoodsCommentModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
}
