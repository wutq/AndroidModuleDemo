package com.wss.common.base;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.mvp.BasePresenter;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：带下拉刷新 上拉加载更多的Activity
 * 内部实现为刷新控件 SmartRefreshLayout + 列表控件 RecyclerView
 * Created by 吴天强 on 2018/10/23.
 */
public abstract class BaseRefreshListActivity<P extends BasePresenter> extends BaseActionBarActivity<P> {

    @BindView(R2.id.ptrl_list)
    SmartRefreshLayout refreshLayout;

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;

    /**
     * 适配器
     */
    private RecyclerView.Adapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh;
    }

    @CallSuper
    @Override
    protected void initView() {
        adapter = createAdapter();
        refreshLayout.setOnRefreshLoadMoreListener(createRefreshListener());
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);

    }

    @CallSuper
    @Override
    public void dismissLoading() {
        super.dismissLoading();
        stopRefresh();
    }


    @CallSuper
    @Override
    public void showLoading() {
        super.showLoading();
        hideEmptyView();
    }

    /**
     * 停止刷新
     */
    protected void stopRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    /**
     * 获取刷新控件
     *
     * @return PullToRefreshLayout
     */
    protected SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    /**
     * 获取加载数据列表控件
     *
     * @return RecyclerView
     */
    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 返回适配器
     *
     * @return RecyclerView.Adapter
     */
    protected RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    /**
     * 创建 RecyclerView LayoutManager
     *
     * @return RecyclerView.LayoutManager
     */
    protected abstract RecyclerView.LayoutManager getLayoutManager();

    /**
     * 创建刷新监听器
     *
     * @return OnPullRefreshListener
     */
    protected abstract OnRefreshLoadMoreListener createRefreshListener();

    /**
     * 创建列表适配器
     *
     * @return RecyclerView.Adapter
     */
    protected abstract RecyclerView.Adapter createAdapter();
}
