package com.wss.module.market.ui.goods.detail.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.base.adapter.listener.OnListItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.module.market.R;
import com.wss.module.market.bean.GoodsInfo;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;


/**
 * Describe：商品详情 店主推荐 商品详情适配器
 * Created by 吴天强 on 2018/10/19.
 */
public class RecommendGoodsInfoAdapter extends BaseListAdapter<GoodsInfo> {


    public RecommendGoodsInfoAdapter(Context context, List<GoodsInfo> items, OnListItemClickListener<GoodsInfo> listener) {
        super(context, items, R.layout.market_item_of_goods_recommend_list, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, GoodsInfo data) {
        ImageUtils.loadImage(holder.findViewById(R.id.iv_goods), data.getGoodsMasterImg());
        holder.setText(R.id.tv_goods_name, data.getGoodsName());
        holder.setText(R.id.tv_goods_price, String.format("¥%s", data.getGoodsPrice()));

        TextView oldPrice = holder.findViewById(R.id.tv_goods_old_price);
        oldPrice.setText(String.format("¥%s", data.getGoodsOldPrice()));
        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
