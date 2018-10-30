package com.wss.module.main.ui.refresh;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wss.common.activity.WebViewActivity;
import com.wss.common.base.RefreshListActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.bean.Article;
import com.wss.module.main.ui.refresh.adapter.ArticleAdapter;
import com.wss.module.main.ui.refresh.mvp.ArticlePresenter;
import com.wss.module.main.ui.refresh.mvp.IArticleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：下拉 上拉刷新控件
 * Created by 吴天强 on 2018/10/23.
 */

public class ArticleListActivity extends RefreshListActivity<ArticlePresenter> implements IArticleView {


    private List<Article> data = new ArrayList<>();
    private int page = 0;

    @Override
    protected void initView() {
        super.initView();
        setTitleText("玩安卓文章列表");
        initData();
    }

    private void initData() {
        data.clear();
        page = 0;
        presenter.getArticleList();
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getArticleList();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ArticleAdapter(mContext, data, R.layout.main_item_of_article_list, this);
    }


    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void articleList(List<Article> articles) {
        data.addAll(articles);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArticlePresenter createPresenter() {
        return new ArticlePresenter();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        if (page == 0) {
            showErrorView();
        } else {
            ToastUtils.showToast(mContext, errorMsg);
        }
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        if (page == 0) {
            showEmptyView();
        } else {
            ToastUtils.showToast(mContext, "暂无更多数据");
        }
    }

    @Override
    protected void onEmptyViewClick() {
        super.onEmptyViewClick();
        initData();
    }

    @Override
    public void onItemClick(int position) {
        WebViewActivity.actionStart(mContext, data.get(position).getLink());
    }
}
