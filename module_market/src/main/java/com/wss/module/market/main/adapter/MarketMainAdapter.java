package com.wss.module.market.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.module.market.R;
import com.wss.module.market.bean.MarketInfo;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：商场适配器
 * Created by 吴天强 on 2018/10/19.
 */

public class MarketMainAdapter extends BaseListAdapter<MarketInfo> {

    public MarketMainAdapter(Context context, List<MarketInfo> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, MarketInfo item) {
        ImageUtils.loadImage(getContext(), item.getImg(), (ImageView) holder.findViewById(R.id.iv_img));
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_price, item.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(layoutPosition);
                }
            }
        });
    }
}
