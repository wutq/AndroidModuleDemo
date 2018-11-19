package com.wss.module.wan.ui.setup.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.ClassificationChild;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public class SetupRightAdapter extends BaseListAdapter<ClassificationChild> {

    private int selectIndex;

    public SetupRightAdapter(Context context, List<ClassificationChild> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, ClassificationChild data) {
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
