package com.wss.module.market.ui.goods.cart.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.utils.ImageUtils;
import com.wss.module.market.R;
import com.wss.module.market.bean.GoodsInfo;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Describe：订单页商品信息适配器
 * Created by 吴天强 on 2020/10/20.
 */
public class OrderSettlementGoodsInfoAdapter extends BaseListAdapter<GoodsInfo> {


    OrderSettlementGoodsInfoAdapter(Context context, List<GoodsInfo> mData) {
        super(context, mData, R.layout.market_item_of_order_goods_info, null);
    }

    @Override
    public void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull GoodsInfo data) {
        ImageUtils.loadImage(holder.findViewById(R.id.iv_goods), data.getGoodsMasterImg());
    }
}
