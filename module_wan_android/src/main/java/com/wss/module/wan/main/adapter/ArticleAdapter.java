package com.wss.module.wan.main.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：文章适配器
 * Created by 吴天强 on 2018/10/18.
 */

public class ArticleAdapter extends BaseListAdapter<Article> {


    public ArticleAdapter(Context context, List<Article> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Article data) {
        holder.setText(R.id.tv_title, data.getTitle());
        holder.setText(R.id.tv_author, data.getAuthor());
        holder.setText(R.id.tv_date, String.format("时间：%s", data.getNiceDate()));

    }

}
