package com.wss.module.wan.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：文章列表适配器
 * Created by 吴天强 on 2018/10/23.
 */

public class ArticleAdapter extends BaseListAdapter<Article> {

    private OnTagClickListener listener;


    public void setOnTagListener(OnTagClickListener listener) {
        this.listener = listener;
    }

    public ArticleAdapter(Context context, List<Article> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, final int layoutPosition, Article data) {
        holder.setText(R.id.tv_title, data.getTitle());
        holder.setText(R.id.tv_author, String.format("作者：%s", data.getAuthor()));
        holder.setText(R.id.tv_date, String.format("时间：%s", data.getNiceDate()));
        if (!TextUtils.isEmpty(data.getChapterName())) {
            holder.setText(R.id.tv_type1, data.getChapterName());
        }
        if (!TextUtils.isEmpty(data.getSuperChapterName())) {
            holder.setText(R.id.tv_type2, data.getSuperChapterName());
        }
        holder.findViewById(R.id.iv_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCollectionClick(layoutPosition);
                }
            }
        });
    }

    /**
     * Item 标签被点击
     */
    public interface OnTagClickListener {

        //收藏按钮被点击
        void onCollectionClick(int position);
    }
}
