package com.wss.module.market.ui.goods.cart.adapter;

import android.content.Context;
import android.view.View;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.utils.ImageUtils;
import com.wss.module.market.R;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.bean.Vendor;
import com.wss.module.market.utils.ShoppingCartUtils;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：订单页商品-供应商适配器
 * Created by 吴天强 on 2020/10/20.
 */
public class OrderSettlementGoodsAdapter extends BaseListAdapter<Vendor> {

    private OnGoodsItemClickListener onGoodsItemClickListener;

    public OrderSettlementGoodsAdapter(Context context, List<Vendor> mData, OnGoodsItemClickListener onGoodsItemClickListener) {
        super(context, mData, R.layout.market_item_of_order_goods_vendor, null);
        this.onGoodsItemClickListener = onGoodsItemClickListener;
    }

    @Override
    public void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull Vendor data) {
        holder.setText(R.id.tv_vendor_name, data.getVendorName());
        if (data.getGoodsInfos().size() == 1) {
            //一种商品
            GoodsInfo goodsInfo = data.getGoodsInfos().get(0);
            holder.setVisibility(R.id.rl_only_goods, View.VISIBLE);
            ImageUtils.loadImage(holder.findViewById(R.id.iv_goods), goodsInfo.getGoodsMasterImg());
            holder.setText(R.id.tv_goods_name, goodsInfo.getGoodsName());
            holder.setText(R.id.tv_goods_price, String.format("¥%s", goodsInfo.getGoodsPrice()));
            holder.setText(R.id.tv_goods_count, String.format("x%s", goodsInfo.getNum()));
        } else {
            //多种商品
            holder.setVisibility(R.id.rl_many, View.VISIBLE);
            holder.setText(R.id.tv_goods_total_count, String.format("共%s件", ShoppingCartUtils.calculationGoodsCount(data.getGoodsInfos())));
            RecyclerView recyclerView = holder.findViewById(R.id.rv_goods_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            recyclerView.setAdapter(new OrderSettlementGoodsInfoAdapter(getContext(), data.getGoodsInfos()));
            if (onGoodsItemClickListener != null) {
                holder.setOnClickListener(R.id.ll_more_goods_list,
                        v -> onGoodsItemClickListener.onMoreGoodsClick(data, layoutPosition));
            }
        }
    }

    /**
     * 商品Item点击事件回调
     */
    public interface OnGoodsItemClickListener {

        /**
         * 更多商品点击
         *
         * @param data     供应商
         * @param position position
         */
        void onMoreGoodsClick(Vendor data, int position);
    }
}
