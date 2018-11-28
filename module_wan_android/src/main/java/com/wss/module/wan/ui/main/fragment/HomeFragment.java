package com.wss.module.wan.ui.main.fragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.bean.Template;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ActivityToActivity;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.PxUtils;
import com.wss.common.widget.pulltorefresh.OnPullRefreshListener;
import com.wss.common.widget.pulltorefresh.PullToRefreshLayout;
import com.wss.module.wan.R;
import com.wss.module.wan.R2;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.BannerInfo;
import com.wss.module.wan.bean.WXNumber;
import com.wss.module.wan.ui.main.adapter.ArticleAdapter;
import com.wss.module.wan.ui.main.adapter.HomeRcyAdapter;
import com.wss.module.wan.ui.main.mvp.HomePresenter;
import com.wss.module.wan.ui.main.mvp.contract.HomeContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：首页
 * Created by 吴天强 on 2018/10/17.
 */
@Route(path = ARouterConfig.WAN_MAIN_FRAGMENT)
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, OnListItemClickListener,
        OnPullRefreshListener, ArticleAdapter.OnTagClickListener {

    @BindView(R2.id.cb_banner)
    ConvenientBanner<String> banner;

    @BindView(R2.id.rv_block)
    RecyclerView rvBlockList;//豆腐块

    @BindView(R2.id.rv_list)
    RecyclerView rvArticleList;//文章列表

    @BindView(R2.id.ptrl_list)
    PullToRefreshLayout refreshLayout;

    @BindView(R2.id.ll_go_top)
    View goTop;

    @BindView(R2.id.nsv_view)
    NestedScrollView scrollView;


    private HomeRcyAdapter blockAdapter;
    private ArticleAdapter articleAdapter;
    private List<BannerInfo> bannerList = new ArrayList<>();
    private List<Template> blockList = new ArrayList<>();
    private List<Article> articleList = new ArrayList<>();
    private ArrayList<WXNumber> wxNumbers = new ArrayList<>();//公众号列表
    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.wan_fragment_main;
    }

    @Override
    protected void initView() {

        blockAdapter = new HomeRcyAdapter(mContext, blockList, R.layout.wan_item_of_block_list, this);
        rvBlockList.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvBlockList.setAdapter(blockAdapter);

        articleAdapter = new ArticleAdapter(mContext, articleList, R.layout.wan_item_of_article_list, this);
        rvArticleList.setLayoutManager(new LinearLayoutManager(mContext));
        rvArticleList.setAdapter(articleAdapter);
        rvArticleList.setNestedScrollingEnabled(false);
        refreshLayout.setOnPullRefreshListener(this);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY >= PxUtils.getScreenHeight(mContext) / 5) {
                    goTop.setVisibility(View.VISIBLE);
                } else if (scrollY < PxUtils.getScreenHeight(mContext) / 5) {
                    goTop.setVisibility(View.GONE);
                }
            }
        });
        articleAdapter.setOnTagListener(this);
        onRefresh();
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void bannerList(List<BannerInfo> banners) {
        this.bannerList.addAll(banners);
        List<String> strings = new ArrayList<>();
        for (BannerInfo bannerInfo : banners) {
            strings.add(bannerInfo.getImagePath());
        }
        ImageUtils.loadBanner(banner, strings, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ActivityToActivity.toWebView(mContext, bannerList.get(position).getUrl());
            }
        });
    }

    @Override
    public void blockList(List<Template> blockList) {
        this.blockList.addAll(blockList);
        blockAdapter.notifyDataSetChanged();
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void articleList(List<Article> articles) {
        this.articleList.addAll(articles);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void wxNumber(List<WXNumber> wxNumbers) {
        this.wxNumbers.addAll(wxNumbers);
    }

    @Override
    public void onItemClick(View view, int position) {

        if (view.getId() == R.id.rv_block) {
            Template template = blockList.get(position);
            Map<String, ArrayList<WXNumber>> wx = new HashMap<>();
            wx.put("WXNumbers", wxNumbers);
            template.setParams(wx);

            //点击豆腐块
            ActivityToActivity.toActivity(mContext, template);
        } else if (view.getId() == R.id.rv_list) {
            //点击文章
            ActivityToActivity.toWebView(mContext, articleList.get(position).getLink());
        }
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
    public void onRefresh() {
        page = 0;
        bannerList.clear();
        blockList.clear();
        articleList.clear();
        presenter.start();
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getArticleList();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }


    @OnClick(R2.id.ll_go_top)
    public void onViewClicked() {
        scrollView.fling(0);
        scrollView.smoothScrollTo(0, 0);
    }


    @Override
    public void onCollectionClick(int position) {

    }
}
