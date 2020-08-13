package com.wss.module.main.ui.page.viscosity.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.module.main.R;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Describe：一个简单的RC适配器
 * Created by 吴天强 on 2020/8/13.
 */
public class SimpleAdapter extends BaseListAdapter<String> {

    public SimpleAdapter(Context context, List<String> mData) {
        super(context, mData, R.layout.main_item_of_text_view, null);
    }

    @Override
    public void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull String data) {
        holder.setText(R.id.tv_text, data);
    }
}
