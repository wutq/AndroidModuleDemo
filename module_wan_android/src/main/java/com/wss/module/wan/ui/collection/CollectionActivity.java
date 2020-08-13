package com.wss.module.wan.ui.collection;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.BaseRefreshListActivity;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.manager.ScrollSpeedLinearLayoutManger;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.collection.mvp.CollectionPresenter;
import com.wss.module.wan.ui.collection.mvp.contract.CollectionContract;
import com.wss.module.wan.ui.project.adapter.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：我的收藏
 * Created by 吴天强 on 2018/11/16.
 */
@Route(path = ARouterConfig.WAN_COLLECTION)
public class CollectionActivity extends BaseRefreshListActivity<CollectionPresenter> implements CollectionContract.View {

    private int page = 0;

    private List<Article> collectionList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        setCenterText("我的收藏");
        getPresenter().getCollectionList();
        getRefreshLayout().setEnableAutoLoadMore(true);
    }

    @Override
    protected OnRefreshLoadMoreListener createRefreshListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPresenter().getCollectionList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                collectionList.clear();
                getPresenter().getCollectionList();
                getRefreshLayout().setEnableLoadMore(true);
            }
        };
    }

    @Override
    protected CollectionPresenter createPresenter() {
        return new CollectionPresenter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new ScrollSpeedLinearLayoutManger(context);
    }


    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ProjectListAdapter(context, collectionList,
                ((data, position) -> ActivityToActivity.toWebView(context, collectionList.get(position).getLink())));
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void refreshCollectionList(List<Article> collections) {
        int position = this.collectionList.size();
        this.collectionList.addAll(collections);
        getAdapter().notifyDataSetChanged();
        if (page > 0) {
            //向下滚动一点
            getRecyclerView().smoothScrollToPosition(position + 3);
        }
        page++;
    }

    @Override
    public void onEmpty(Object tag) {
        if (page == 0) {
            showEmptyView("您还没有收藏任何东西");
        } else {
            ToastUtils.show("暂无更多收藏~");
            getRefreshLayout().setEnableLoadMore(false);
        }
    }

    @Override
    public void onError(Object tag, String msg) {
        super.onEmpty(tag);
        if (page == 0) {
            showEmptyView(msg);
        } else {
            ToastUtils.show(context, msg);
        }
    }
}
