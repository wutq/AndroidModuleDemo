package com.wss.module.wan.ui.series;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.BaseRefreshListActivity;
import com.wss.common.constants.Dic;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.manager.ScrollSpeedLinearLayoutManger;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.ui.main.adapter.ArticleAdapter;
import com.wss.module.wan.ui.series.mvp.SeriesArticlePresenter;
import com.wss.module.wan.ui.series.mvp.contract.SeriesArticleContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：体系文章
 * Created by 吴天强 on 2018/11/15.
 */
public class SeriesArticleActivity extends BaseRefreshListActivity<SeriesArticlePresenter> implements
        SeriesArticleContract.View {


    private List<Article> articleList = new ArrayList<>();

    private int page;

    private Classification children;

    @Override
    protected void initView() {
        super.initView();
        children = (Classification) getIntent().getSerializableExtra(Dic.CLASSIFICATION_CHILD);
        if (children == null) {
            children = new Classification();
        }
        setCenterText(children.getName());
        getPresenter().getArticle();
    }

    @Override
    protected SeriesArticlePresenter createPresenter() {
        return new SeriesArticlePresenter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new ScrollSpeedLinearLayoutManger(context);
    }

    @Override
    protected OnRefreshLoadMoreListener createRefreshListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPresenter().getArticle();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                resetPage();
            }
        };
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ArticleAdapter(context, articleList,
                ((data, position) -> ActivityToActivity.toWebView(context, data.getLink())));
    }

    @Override
    public String getSeriesId() {
        return String.valueOf(children.getId());
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void refreshArticleList(List<Article> articles) {
        int position = this.articleList.size();
        this.articleList.addAll(articles);
        getAdapter().notifyDataSetChanged();
        if (page > 0) {
            getRecyclerView().smoothScrollToPosition(position + 3);
        }
        page++;
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        if (page == 0) {
            showErrorView(errorMsg);
        } else {
            ToastUtils.show(errorMsg);
        }
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        if (page == 0) {
            showEmptyView();
        } else {
            ToastUtils.show("暂无更多数据");
        }
    }

    @Override
    protected void onRefreshRetry() {
        super.onRefreshRetry();
        resetPage();
    }

    private void resetPage() {
        page = 0;
        articleList.clear();
        getPresenter().getArticle();
    }
}
