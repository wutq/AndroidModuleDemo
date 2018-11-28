package com.wss.module.market.ui.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.bean.Event;
import com.wss.common.constants.EventAction;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.ImageUtils;
import com.wss.module.market.R;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.utils.ShoppingCartUtils;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：商场适配器
 * Created by 吴天强 on 2018/10/19.
 */

public class MarketMainAdapter extends BaseListAdapter<GoodsInfo> {

    public MarketMainAdapter(Context context, List<GoodsInfo> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, GoodsInfo data) {
        ImageUtils.loadImage((ImageView) holder.findViewById(R.id.iv_img), data.getGoodsMasterImg());
        holder.setText(R.id.tv_title, data.getGoodsName());
        holder.setText(R.id.tv_price, data.getGoodsPrice());
        TextView tvOldPrice = holder.findViewById(R.id.tv_old_price);
        tvOldPrice.setText(data.getGoodsOldPrice());

        final GoodsInfo goodsInfo = data;
        //设置文字中间一条横线,
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.findViewById(R.id.iv_add_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入购物车
                ShoppingCartUtils.addCartGoods(goodsInfo);
                EventBusUtils.sendEvent(new Event(EventAction.EVENT_SHOPPING_CART_CHANGED));
            }
        });
    }
}
