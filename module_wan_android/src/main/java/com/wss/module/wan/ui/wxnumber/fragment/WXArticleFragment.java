package com.wss.module.wan.ui.wxnumber.fragment;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.BaseRefreshListFragment;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.common.widget.manager.ScrollSpeedLinearLayoutManger;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.WXNumber;
import com.wss.module.wan.ui.wxnumber.adapter.WXArticleAdapter;
import com.wss.module.wan.ui.wxnumber.mvp.WXArticlePresenter;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXArticleContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：公众号文章列表
 * Created by 吴天强 on 2018/11/15.
 */
public class WXArticleFragment extends BaseRefreshListFragment<WXArticlePresenter> implements WXArticleContract.View {


    private List<Article> wxArticles = new ArrayList<>();
    private int page = 0;

    @Override
    protected void initView() {
        super.initView();
        getPresenter().start();
    }

    private void resetPage() {
        page = 0;
        wxArticles.clear();
        getRefreshLayout().setEnableLoadMore(true);
    }

    @Override
    protected WXArticlePresenter createPresenter() {
        return new WXArticlePresenter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new ScrollSpeedLinearLayoutManger(context);
    }

    @Override
    protected OnRefreshLoadMoreListener createRefreshListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                resetPage();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPresenter().getArticle();
            }
        };
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new WXArticleAdapter(context, wxArticles,
                ((data, position) -> ActivityToActivity.toWebView(context, data.getLink())));
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getWxId() {
        HorizontalTabTitle tabTitle = getTabTitle();
        if (ValidUtils.isValid(tabTitle) && ValidUtils.isValid(tabTitle.getData())) {
            return ((WXNumber) tabTitle.getData()).getId();
        }
        return -1;
    }

    @Override
    public void refreshWxArticle(List<Article> articleList) {
        int position = this.wxArticles.size();
        this.wxArticles.addAll(articleList);
        getAdapter().notifyDataSetChanged();
        if (page > 0) {
            getRecyclerView().smoothScrollToPosition(position + 3);
        }
        page++;
        stopRefresh();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        ToastUtils.show(errorMsg);
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        ToastUtils.show("暂无数据");
    }
}
