package com.wss.module.wan.ui.main.fragment;


import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.bean.Banner;
import com.wss.common.bean.Template;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.PxUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.module.wan.R;
import com.wss.module.wan.R2;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.BannerInfo;
import com.wss.module.wan.ui.main.adapter.ArticleAdapter;
import com.wss.module.wan.ui.main.adapter.HomeMenuAdapter;
import com.wss.module.wan.ui.main.mvp.HomePresenter;
import com.wss.module.wan.ui.main.mvp.contract.HomeContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：玩android首页
 * Created by 吴天强 on 2018/10/17.
 */
@Route(path = ARouterConfig.WAN_MAIN_FRAGMENT)
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View/*, OnListItemClickListener,
        OnPullRefreshListener, ArticleAdapter.OnTagClickListener*/ {

    @BindView(R2.id.cb_banner)
    ConvenientBanner<Banner> banner;

    @BindView(R2.id.rv_block)
    RecyclerView rvMenuList;

    @BindView(R2.id.rv_list)
    RecyclerView rvArticleList;

    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @BindView(R2.id.ll_go_top)
    View goTop;

    @BindView(R2.id.nsv_view)
    NestedScrollView scrollView;


    private ArticleAdapter articleAdapter;
    private List<Article> articleList = new ArrayList<>();
    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.wan_fragment_main;
    }

    @Override
    protected void initView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPresenter().getArticleList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                resetPage();
            }
        });
        refreshLayout.setEnableAutoLoadMore(true);


        articleAdapter = new ArticleAdapter(context, articleList,
                (data, position) -> ActivityToActivity.toWebView(context, data.getLink()));
        articleAdapter.setOnTagListener(position -> {
        });
        rvArticleList.setLayoutManager(new LinearLayoutManager(context));
        rvArticleList.setAdapter(articleAdapter);
        rvArticleList.setNestedScrollingEnabled(false);

        scrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY >= PxUtils.getScreenHeight(context) / 5) {
                        goTop.setVisibility(View.VISIBLE);
                    } else if (scrollY < PxUtils.getScreenHeight(context) / 5) {
                        goTop.setVisibility(View.GONE);
                    }
                });
        getPresenter().start();
    }

    private void resetPage() {
        page = 0;
        articleList.clear();
        getPresenter().getArticleList();
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void refreshBannerList(List<BannerInfo> banners) {
        List<Banner> bannerList = new ArrayList<>();
        for (BannerInfo bannerInfo : banners) {
            bannerList.add(new Banner(bannerInfo.getImagePath(), (bannerInfo.getUrl())));
        }
        ImageUtils.loadBanner(banner, bannerList, position -> ActivityToActivity.toWebView(context, bannerList.get(position).getRedirectUrl()));
    }

    @Override
    public void refreshMenuList(List<Template> menuList) {
        rvMenuList.setLayoutManager(new GridLayoutManager(context, 4));
        rvMenuList.setNestedScrollingEnabled(false);
        rvMenuList.setAdapter(new HomeMenuAdapter(context, menuList, (data, position) -> ActivityToActivity.toActivity(getActivity(), data)));
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void refreshArticleList(List<Article> articles) {
        int position = this.articleList.size();
        this.articleList.addAll(articles);
        articleAdapter.notifyDataSetChanged();
        if (page > 0) {
            rvArticleList.smoothScrollToPosition(position + 3);
        }
        stopRefresh(refreshLayout);
        page++;
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        banner.startTurning();
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止自动翻页
        banner.stopTurning();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        if (getPage() > 0) {
            ToastUtils.show(errorMsg);
        } else {
            showErrorView(errorMsg);
        }
        stopRefresh(refreshLayout);
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        if (getPage() > 0) {
            ToastUtils.show("暂无更多数据啦~");
        } else {
            showEmptyView();
        }
        stopRefresh(refreshLayout);
    }

    @Override
    protected void onRefreshRetry() {
        super.onRefreshRetry();
        getPresenter().start();
    }

    @OnClick(R2.id.ll_go_top)
    public void onViewClicked() {
        scrollView.fling(0);
        scrollView.smoothScrollTo(0, 0);
    }
}
