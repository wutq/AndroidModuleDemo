package com.wss.module.wan.ui.wxnumber.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：公众号文章适配器
 * Created by 吴天强 on 2018/11/15.
 */

public class WXArticleAdapter extends BaseListAdapter<Article> {

    public WXArticleAdapter(Context context, List<Article> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Article data) {
        TextView textView = holder.findViewById(R.id.tv_text);
        if (layoutPosition % 2 == 0) {
            textView.setBackgroundColor(getColor(R.color.light_blue));
        } else {
            textView.setBackgroundColor(getColor(R.color.light_yellow));
        }
        textView.setText(data.getTitle());

    }

    private int getColor(int res) {
        return getContext().getResources().getColor(res);
    }

}
