package com.wss.module.main.ui.page.viscosity.fragment;

import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Dic;
import com.wss.common.widget.ViewPagerForScrollView;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.page.viscosity.bean.TabItem;
import com.wss.module.main.ui.page.viscosity.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：复杂黏性滑动子Fragment
 * Created by 吴天强 on 2020/8/13.
 */
public class ChildListFragment extends BaseMvpFragment {


    @BindView(R2.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R2.id.pull_to_refresh)
    SmartRefreshLayout pullToRefreshLayout;

    private List<String> dataList = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    private ViewPagerForScrollView viewPager;
    private TabItem tabItem;

    public static ChildListFragment newInstance(ViewPagerForScrollView viewPager) {
        ChildListFragment houseAllFragment = new ChildListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Dic.HOUSE_FRAGMENT_VIEWPAGER, viewPager);
        houseAllFragment.setArguments(bundle);
        return houseAllFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getSerializable(Dic.HOUSE_FRAGMENT_VIEWPAGER) != null) {
            viewPager = (ViewPagerForScrollView) getArguments().getSerializable(Dic.HOUSE_FRAGMENT_VIEWPAGER);
        } else {
            viewPager = new ViewPagerForScrollView(getContext());
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_child_list;
    }

    @Override
    protected void initView() {
        Object data = getTabTitle().getData();
        tabItem = data != null ? (TabItem) data : new TabItem();
        //把当前View与position一一对应存放在ViewPager里面
        viewPager.setObjectForPosition(getRootView(), tabItem.getPosition());

        //禁用子Fragment的下拉刷新操作，交给外层的Fragment做
        pullToRefreshLayout.setEnableRefresh(false);
        //禁止滑倒底部自动加载数据
        pullToRefreshLayout.setEnableAutoLoadMore(false);
        //调整触发加载更多的敏感度比率
        pullToRefreshLayout.setFooterTriggerRate(0.5f);
        pullToRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //加载更多
            getData();

        });
        recycleView.setAdapter(simpleAdapter = new SimpleAdapter(context, dataList));
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        getData();
    }

    private void getData() {
        int start = dataList.size();
        int max = start + (tabItem.getType() % 2 == 0 ? 10 : 5);
        for (int i = start; i < max + 5; i++) {
            dataList.add("黏性的Item" + (i + 1));
        }
        simpleAdapter.notifyDataSetChanged();
        stopRefresh(pullToRefreshLayout);
    }
}

