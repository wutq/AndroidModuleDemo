package com.wss.module.wan.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseRcyAdapter;
import com.wss.common.base.adapter.holder.BaseRcyHolder;
import com.wss.common.base.adapter.listener.OnRcyItemClickListener;
import com.wss.module.wan.R;
import com.wss.module.wan.R2;
import com.wss.module.wan.bean.Article;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe：文章适配器
 * Created by 吴天强 on 2018/10/18.
 */

public class ArticleAdapter extends BaseRcyAdapter<Article, ArticleAdapter.ArticleVH> {

    public ArticleAdapter(Context mContext, List<Article> mData, OnRcyItemClickListener listener) {
        super(mContext, mData, listener);
    }

    @Override
    protected ArticleVH createVH(ViewGroup parent, int viewType) {
        return new ArticleVH(View.inflate(mContext, R.layout.wan_item_of_article_list, null));
    }


    public class ArticleVH extends BaseRcyHolder<Article> {

        @BindView(R2.id.tv_title)
        TextView tvTitle;

        @BindView(R2.id.tv_author)
        TextView tvAuthor;

        @BindView(R2.id.tv_date)
        TextView tvDate;

        ArticleVH(View itemView) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void bindingData(Article data, int position) {
            tvTitle.setText(data.getTitle());
            tvAuthor.setText(data.getAuthor());
            tvDate.setText(String.format("时间：%s", data.getNiceDate()));
        }
    }
}
