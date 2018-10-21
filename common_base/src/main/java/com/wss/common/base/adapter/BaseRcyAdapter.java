package com.wss.common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wss.common.base.adapter.holder.BaseRcyHolder;
import com.wss.common.base.adapter.listener.OnRcyItemClickListener;

import java.util.List;

/**
 * Describe：RecycleView 适配器基类
 * Created by 吴天强 on 2018/10/18.
 */

public abstract class BaseRcyAdapter<T, V extends BaseRcyHolder> extends RecyclerView.Adapter<V> {


    private List<T> mData;//数据源
    protected Context mContext;
    protected OnRcyItemClickListener listener;

    public BaseRcyAdapter(Context mContext, List<T> mData, OnRcyItemClickListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createVH(parent, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.bindingData(mData.get(position), position);
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    protected abstract V createVH(ViewGroup parent, int viewType);
}
