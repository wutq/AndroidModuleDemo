package com.wss.module.main.ui.home.adapter;

import android.content.Context;

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
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, MainBlock data) {
        holder.setText(R.id.tv_text, data.getTitle());
        holder.setBackgroundResource(R.id.iv_icon, data.getRes());
    }
}
