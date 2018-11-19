package com.wss.common.base.adapter;

import android.content.Context;
import android.view.View;

import com.wss.common.listener.OnListItemClickListener;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：万能适配器基类 适用于RecycleView ListView GridView等
 * 注意点：Item的最外层高度不能设置为 match_parent 否则滑动会出现混乱 目前还不知道原因、
 * Created by 吴天强 on 2018/10/30.
 */

public abstract class BaseListAdapter<T> extends SuperAdapter<T> {
    /**
     * Item点击监听
     */
    protected OnListItemClickListener listener;

    /**
     * 常规列表重写该方法
     */
    public BaseListAdapter(Context context, List<T> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId);
        this.listener = listener;
    }

    /**
     * 多布局列表重写该方法
     */
    public BaseListAdapter(Context context, List<T> items, IMulItemViewType<T> multiItemViewType) {
        super(context, items, multiItemViewType);
    }

    /**
     * 添加点击事件监听
     */
    public void setOnListItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onBind(final SuperViewHolder viewHolder, int viewType, final int layoutPosition, T data) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick((View) viewHolder.itemView.getParent(), layoutPosition);
                }
            }
        });
        onBindData(viewHolder, viewType, layoutPosition, data);
    }

    /**
     * 绑定数据
     */
    public abstract void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, T data);

}
