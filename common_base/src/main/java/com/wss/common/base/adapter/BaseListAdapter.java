package com.wss.common.base.adapter;

import android.content.Context;

import com.wss.common.listener.OnListItemClickListener;

import org.byteam.superadapter.SuperAdapter;

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

    public BaseListAdapter(Context context, List<T> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId);
        this.listener = listener;
    }

    /**
     * 添加点击事件监听
     */
    public void setOnListItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }

}
