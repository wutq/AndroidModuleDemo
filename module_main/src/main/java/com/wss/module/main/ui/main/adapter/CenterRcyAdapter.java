package com.wss.module.main.ui.main.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.bean.Template;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.main.R;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：中间适配器
 * Created by 吴天强 on 2018/10/18.
 */

public class CenterRcyAdapter extends BaseListAdapter<Template> {


    public CenterRcyAdapter(Context context, List<Template> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Template data) {
        holder.setText(R.id.tv_title, data.getTitle());
        holder.setText(R.id.tv_describe, data.getDescribe());
        holder.setBackgroundResource(R.id.iv_icon, data.getRes());
    }
}
