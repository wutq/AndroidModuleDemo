package com.wss.module.main.ui.home.adapter;

import android.content.Context;
import android.view.View;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.main.R;
import com.wss.module.main.bean.MainBlock;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：首页适配器
 * Created by 吴天强 on 2018/10/18.
 */

public class HomeRcyAdapter extends BaseListAdapter<MainBlock> {


    public HomeRcyAdapter(Context context, List<MainBlock> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, MainBlock item) {
        holder.setText(R.id.tv_text, item.getTitle());
        holder.setBackgroundResource(R.id.iv_icon, item.getRes());
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
