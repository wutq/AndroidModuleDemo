package com.wss.module.wan.ui.collection;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.base.RefreshListActivity;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.utils.ActivityToActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.collection.mvp.contract.CollectionContract;
import com.wss.module.wan.ui.collection.mvp.CollectionPresenter;
import com.wss.module.wan.ui.project.adapter.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：我的收藏
 * Created by 吴天强 on 2018/11/16.
 */
@Route(path = ARouterConfig.WAN_COLLECTION)
public class CollectionActivity extends RefreshListActivity<CollectionPresenter> implements CollectionContract.View {

    private int page = 0;

    private List<Article> collectionList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        setTitleText("我的收藏");
        presenter.getCollectionList();
    }

    @Override
    public void onRefresh() {
        page = 0;
        collectionList.clear();
        presenter.start();
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getCollectionList();
    }

    @Override
    public void onItemClick(View view, int position) {
        ActivityToActivity.toWebView(mContext, collectionList.get(position).getLink());
    }

    @Override
    protected CollectionPresenter createPresenter() {
        return new CollectionPresenter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ProjectListAdapter(mContext, collectionList, R.layout.wan_item_of_project_list, this);
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void collectionList(List<Article> collections) {
        this.collectionList.addAll(collections);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        if (page == 0) {
            showEmptyView("您还没有收藏任何东西");
        } else {
            ToastUtils.showToast(mContext, "暂无更多收藏~");
        }
    }

    @Override
    public void onError(Object tag, String msg) {
        super.onEmpty(tag);
        if (page == 0) {
            showEmptyView(msg);
        } else {
            ToastUtils.showToast(mContext, msg);
        }
    }
}
