package com.wss.module.main.ui.page.adapter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.SwipeItemLayout;
import com.wss.module.main.R;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Describe：侧滑菜单适配器
 * Created by 吴天强 on 2020/11/4.
 */
public class SlewedAdapter extends BaseListAdapter<String> {

    public SlewedAdapter(Context context, List<String> mData) {
        super(context, mData, R.layout.main_item_of_slewed, null);
    }

    @Override
    public void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull String data) {
        holder.setText(R.id.tv_text, data);
        SwipeItemLayout swipeItemLayout = holder.findViewById(R.id.swipe_layout);
        holder.setOnClickListener(R.id.tv_ignore, v -> {
            swipeItemLayout.close();
            ToastUtils.show("忽略");
        });
        holder.setOnClickListener(R.id.tv_delete, v -> {
            swipeItemLayout.close();
            ToastUtils.show("删除");
        });
        swipeItemLayout.setOnItemSwipeListener(new SwipeItemLayout.OnItemSwipeListener() {
            @Override
            public void onItemSwipe(boolean isOpen) {
                Logger.e("打开状态：" + isOpen);
            }
        });
    }
}
