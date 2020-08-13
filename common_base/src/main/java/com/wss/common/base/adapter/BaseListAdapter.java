package com.wss.common.base.adapter;

import android.content.Context;

import com.wss.common.base.adapter.listener.OnListItemClickListener;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Describe：万能适配器基类 适用于RecycleView ListView GridView等
 * 注意点：Item的最外层高度不能设置为 match_parent 否则滑动会出现混乱[TODO]
 * Created by 吴天强 on 2018/10/30.
 */
public abstract class BaseListAdapter<T> extends SuperAdapter<T> {
    /**
     * Item点击监听
     */
    private OnListItemClickListener<T> listener;

    /**
     * 常规列表重写该方法
     *
     * @param context     context
     * @param mData       数据源
     * @param layoutResId 布局文件
     * @param listener    Item点击回调
     */
    public BaseListAdapter(Context context, List<T> mData, int layoutResId, OnListItemClickListener<T> listener) {
        super(context, mData, layoutResId);
        this.listener = listener;
    }

    /**
     * 多布局列表重写该方法
     *
     * @param context           context
     * @param mData             数据源
     * @param multiItemViewType 多布局类型
     */
    public BaseListAdapter(Context context, List<T> mData, IMulItemViewType<T> multiItemViewType) {
        super(context, mData, multiItemViewType);
    }

    /**
     * Item点击事件
     *
     * @param listener listener
     */
    public void setOnListItemClickListener(OnListItemClickListener<T> listener) {
        this.listener = listener;
    }


    @Override
    public void onBind(@NotNull final SuperViewHolder viewHolder, int viewType, final int layoutPosition, T data) {
        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(data, layoutPosition);
            }
        });
        onBindData(viewHolder, viewType, layoutPosition, data);
    }

    public abstract void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull T data);

}
