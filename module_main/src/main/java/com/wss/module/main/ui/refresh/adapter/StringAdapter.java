package com.wss.module.main.ui.refresh.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.main.R;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：适配器
 * Created by 吴天强 on 2018/10/23.
 */

public class StringAdapter extends BaseListAdapter<String> {


    public StringAdapter(Context context, List<String> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, String data) {
        holder.setText(R.id.tv_text, data);
    }
}
