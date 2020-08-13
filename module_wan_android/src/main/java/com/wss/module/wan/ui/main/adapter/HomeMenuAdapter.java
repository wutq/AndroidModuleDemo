package com.wss.module.wan.ui.main.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.base.adapter.listener.OnListItemClickListener;
import com.wss.common.bean.Template;
import com.wss.module.wan.R;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：首页菜单适配器
 * Created by 吴天强 on 2018/10/18.
 */
public class HomeMenuAdapter extends BaseListAdapter<Template> {


    public HomeMenuAdapter(Context context, List<Template> items, OnListItemClickListener<Template> listener) {
        super(context, items, R.layout.wan_item_of_block_list, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Template data) {
        holder.setText(R.id.tv_text, data.getTitle());
        holder.setBackgroundResource(R.id.iv_icon, data.getRes());
    }
}
