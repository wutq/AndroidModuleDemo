package com.wss.module.wan.ui.series.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.base.adapter.listener.OnListItemClickListener;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Classification;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：体系适配器
 * Created by 吴天强 on 2018/11/15.
 */
public class SeriesAdapter extends BaseListAdapter<Classification> {

    private int selectIndex;

    public SeriesAdapter(Context context, List<Classification> items, OnListItemClickListener<Classification> listener) {
        super(context, items, R.layout.wan_item_of_setup_list, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Classification data) {
        TextView textView = holder.findViewById(R.id.tv_text);
        textView.setText(data.getName());
        if (layoutPosition == selectIndex) {
            textView.setTextColor(getContext().getResources().getColor(R.color.white));
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.orange));
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.black));
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        }
    }

    public void notifyData(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }
}
