package com.wss.module.main.ui.refresh;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wss.common.base.RefreshListActivity;
import com.wss.module.main.R;
import com.wss.module.main.ui.refresh.adapter.StringAdapter;
import com.wss.module.main.ui.refresh.mvp.StringPresenter;
import com.wss.module.main.ui.refresh.mvp.contract.RefreshContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：下拉 上拉刷新控件
 * Created by 吴天强 on 2018/10/23.
 */

public class RefreshStringActivity extends RefreshListActivity<StringPresenter> implements RefreshContract.View {


    private List<String> data = new ArrayList<>();

    private int page = 1;

    @Override
    protected void initView() {
        super.initView();
        setTitleText("下拉刷新、上拉加载更多");
        initData();
    }

    private void initData() {
        page = 1;
        data.clear();
        presenter.getStringList();
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getStringList();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new StringAdapter(mContext, data, R.layout.main_item_of_string_list, this);
    }


    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void stringList(List<String> articles) {
        this.data.addAll(articles);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected StringPresenter createPresenter() {
        return new StringPresenter();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
