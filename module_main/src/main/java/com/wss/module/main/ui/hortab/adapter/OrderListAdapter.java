package com.wss.module.main.ui.hortab.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.Goods;
import com.wss.module.main.bean.Order;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe：订单列表适配器
 * Created by 吴天强 on 2018/10/23.
 */

public class OrderListAdapter extends BaseListAdapter<Order> {


    public OrderListAdapter(Context context, List<Order> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }


    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Order data) {
        holder.setText(R.id.tv_date, data.getOrderDate());
        holder.setText(R.id.tv_state, getState(data.getOrderState()));
        LinearLayout layout = holder.findViewById(R.id.layout_goods);
        layout.removeAllViews();
        int sum = 0;
        for (Goods goods : data.getGoodsList()) {
            View childView = View.inflate(getContext(), R.layout.main_item_of_order_goods_list, null);
            new GoodsVH(childView).bindingData(goods);
            layout.addView(childView);
            sum += goods.getGoodsNum();
        }
        holder.setText(R.id.tv_total, String.format("共%s种商品，合计：¥%s", sum, data.getOrderTotal()));
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
            ImageUtils.loadImage(ivGoods, goods.getGoodsImg());
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