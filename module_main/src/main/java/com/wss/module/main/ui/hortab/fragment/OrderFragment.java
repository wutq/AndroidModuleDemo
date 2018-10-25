package com.wss.module.main.ui.hortab.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wss.common.base.RefreshListFragment;
import com.wss.common.base.adapter.BaseRcyAdapter;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.bean.Order;
import com.wss.module.main.ui.hortab.adapter.OrderListAdapter;
import com.wss.module.main.ui.hortab.mvp.IOrderView;
import com.wss.module.main.ui.hortab.mvp.OrderPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：订单列表
 * Created by 吴天强 on 2018/10/22.
 */

public class OrderFragment extends RefreshListFragment<OrderPresenter> implements IOrderView {


    private int page = 0;
    private HorizontalTabTitle title;
    private List<Order> orderList = new ArrayList<>();
    private OrderListAdapter adapter;

    @Override
    public void setFragmentData(Object data) {
        super.setFragmentData(data);
        this.title = (HorizontalTabTitle) data;
    }

    @Override
    protected void initView() {
        super.initView();
        initData();
    }

    private void initData() {
        page = 0;
        orderList.clear();
        presenter.getOrderList();
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        presenter.getOrderList();
    }

    @Override
    public void onItemClick(int position) {

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
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    protected BaseRcyAdapter createAdapter() {
        adapter = new OrderListAdapter(mContext, orderList, this);
        return adapter;
    }

    @Override
    protected OrderPresenter createPresenter() {
        return new OrderPresenter();
    }

    @Override
    public void orderList(List<Order> orders) {
        this.orderList.addAll(orders);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getType() {
        if (title != null) {
            return (int) title.getData();
        }
        return 0;
    }
}
