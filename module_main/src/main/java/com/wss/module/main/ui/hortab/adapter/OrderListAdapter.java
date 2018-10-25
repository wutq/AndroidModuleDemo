package com.wss.module.main.ui.hortab.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseRcyAdapter;
import com.wss.common.base.adapter.holder.BaseRcyHolder;
import com.wss.common.base.adapter.listener.OnRcyItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.Goods;
import com.wss.module.main.bean.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe：订单列表适配器
 * Created by 吴天强 on 2018/10/23.
 */

public class OrderListAdapter extends BaseRcyAdapter<Order, OrderListAdapter.OrderVH> {


    public OrderListAdapter(Context mContext, List<Order> mData, OnRcyItemClickListener listener) {
        super(mContext, mData, listener);
    }

    @Override
    protected OrderVH createVH(ViewGroup parent, int viewType) {
        return new OrderVH(View.inflate(mContext, R.layout.main_item_of_order_list, null), listener);
    }

    public class OrderVH extends BaseRcyHolder<Order> {

        @BindView(R2.id.tv_date)
        TextView tvDate;

        @BindView(R2.id.tv_state)
        TextView tvState;

        @BindView(R2.id.tv_total)
        TextView tvTotal;

        @BindView(R2.id.layout_goods)
        LinearLayout layout;


        OrderVH(View itemView, OnRcyItemClickListener listener) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindingData(Order data, int position) {
            tvDate.setText(data.getOrderDate());
            tvState.setText(getState(data.getOrderState()));
            layout.removeAllViews();
            int sum = 0;
            for (Goods goods : data.getGoodsList()) {
                View childView = View.inflate(mContext, R.layout.main_item_of_order_goods_list, null);
                new GoodsVH(childView).bindingData(goods);
                layout.addView(childView);
                sum += goods.getGoodsNum();
            }
            tvTotal.setText(String.format("共%s种商品，合计：¥%s", sum, data.getOrderTotal()));
        }
    }


    public class GoodsVH {

        @BindView(R2.id.tv_goods)
        ImageView ivGoods;

        @BindView(R2.id.tv_name)
        TextView tvName;

        @BindView(R2.id.tv_price)
        TextView tvPrice;

        @BindView(R2.id.tv_num)
        TextView tvNum;

        GoodsVH(View item) {
            ButterKnife.bind(this, item);
        }

        void bindingData(Goods goods) {
            ImageUtils.loadImage(mContext, goods.getGoodsImg(), ivGoods);
            tvName.setText(goods.getGoodsName());
            tvPrice.setText(String.format("¥%s", goods.getGoodsPrice()));
            tvNum.setText(String.format("x%s", goods.getGoodsNum()));
        }
    }


    private String getState(int code) {
        switch (code) {
            case 1:
                return "待支付";
            case 2:
                return "待发货";
            case 3:
                return "待确认";
            case 4:
                return "已失效";
            default:
                return "已完成";
        }
    }
}