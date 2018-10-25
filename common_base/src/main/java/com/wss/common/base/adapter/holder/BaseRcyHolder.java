package com.wss.common.base.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wss.common.base.adapter.listener.OnRcyItemClickListener;

/**
 * Describe：RecycleView View基类
 * Created by 吴天强 on 2018/10/18.
 */

public abstract class BaseRcyHolder<T> extends RecyclerView.ViewHolder {

    private OnRcyItemClickListener mListener;

    public BaseRcyHolder(View itemView, OnRcyItemClickListener listener) {
        super(itemView);
        this.mListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(getAdapterPosition());
                }
            }
        });
    }


    public abstract void bindingData(T data, int position);

}
