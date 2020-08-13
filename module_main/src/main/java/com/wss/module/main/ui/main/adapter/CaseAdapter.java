package com.wss.module.main.ui.main.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.base.adapter.listener.OnListItemClickListener;
import com.wss.common.bean.Template;
import com.wss.module.main.R;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：案例适配器
 * Created by 吴天强 on 2018/10/18.
 */
public class CaseAdapter extends BaseListAdapter<Template> {

    public CaseAdapter(Context context, List<Template> items, OnListItemClickListener<Template> listener) {
        super(context, items, R.layout.main_item_of_case_list, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Template data) {
        holder.setText(R.id.tv_title, data.getTitle());
        holder.setText(R.id.tv_describe, data.getDescribe());
    }
}
