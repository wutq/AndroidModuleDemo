package com.wss.module.main.ui.refresh.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.main.R;
import com.wss.module.main.bean.Article;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：文章列表适配器
 * Created by 吴天强 on 2018/10/23.
 */

public class ArticleAdapter extends BaseListAdapter<Article> {


    public ArticleAdapter(Context context, List<Article> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Article data) {
        holder.setText(R.id.tv_title, data.getTitle());
        holder.setText(R.id.tv_author, String.format("作者：%s", data.getAuthor()));
        holder.setText(R.id.tv_date, String.format("时间：%s", data.getNiceDate()));
        holder.setText(R.id.tv_type1, data.getChapterName());
        holder.setText(R.id.tv_type2, data.getSuperChapterName());
    }
}
