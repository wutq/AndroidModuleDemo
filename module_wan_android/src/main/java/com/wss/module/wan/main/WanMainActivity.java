package com.wss.module.wan.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.activity.WebViewActivity;
import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.adapter.listener.OnRcyItemClickListener;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.utils.ToastUtils;
import com.wss.module.wan.R;
import com.wss.module.wan.R2;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.main.adapter.ArticleAdapter;
import com.wss.module.wan.main.mvp.IWanMainView;
import com.wss.module.wan.main.mvp.WanMainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：玩安卓首页
 * Created by wss on 2018/10/18.
 */
@Route(path = ARouterConfig.WAN_MAIN_ACTIVITY)
public class WanMainActivity extends ActionBarActivity<WanMainPresenter> implements IWanMainView, OnRcyItemClickListener {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;


    private ArticleAdapter adapter;
    private List<Article> articleList = new ArrayList<>();
    private int page = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.wan_activity_wan_main;
    }

    @Override
    protected void initView() {
        setTitleText("文章列表");
        adapter = new ArticleAdapter(mContext, articleList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        presenter.getArticleList(page);
    }

    @Override
    protected WanMainPresenter createPresenter() {
        return new WanMainPresenter();
    }

    @Override
    public void articleList(List<Article> articleList) {
        this.articleList.addAll(articleList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        ToastUtils.showToast(mContext, "没有数据");
    }

    @Override
    public void onError(Object tag, String error) {
        super.onError(tag, error);
        ToastUtils.showToast(mContext, "Error");
    }

    @Override
    public void onItemClick(int position) {
        WebViewActivity.actionStart(mContext, articleList.get(position).getApkLink());
    }
}