package com.wss.module.main.ui.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wss.common.base.BaseMvpFragment;
import com.wss.common.base.adapter.BaseRcyAdapter;
import com.wss.common.base.adapter.listener.OnRcyItemClickListener;
import com.wss.common.utils.ActivityToActivity;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.MainBlock;
import com.wss.module.main.ui.count.CountViewActivity;
import com.wss.module.main.ui.home.adapter.CenterRcyAdapter;
import com.wss.module.main.ui.home.mvp.CenterPresenter;
import com.wss.module.main.ui.home.mvp.ICenterView;
import com.wss.module.main.ui.hortab.OrderListActivity;
import com.wss.module.main.ui.refresh.ArticleListActivity;
import com.wss.module.main.ui.selector.SelectorActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：中间
 * Created by 吴天强 on 2018/10/17.
 */
public class CenterFragment extends BaseMvpFragment<CenterPresenter> implements ICenterView, OnRcyItemClickListener {

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;


    private List<MainBlock> data = new ArrayList<>();
    private BaseRcyAdapter adapter;

    @Override
    protected void initView() {

        adapter = new CenterRcyAdapter(mContext, data, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        presenter.getTabList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_center;
    }

    @Override
    protected CenterPresenter createPresenter() {
        return new CenterPresenter();
    }

    @Override
    public void tabList(List<MainBlock> blockList) {
        this.data.addAll(blockList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                //水平Tab
//                test1();
                ActivityToActivity.toActivity(mContext, OrderListActivity.class);
                break;
            case 1:
                //上/下拉刷新
                ActivityToActivity.toActivity(mContext, ArticleListActivity.class);
                break;
            case 2:
                //多功能选择器
                ActivityToActivity.toActivity(mContext, SelectorActivity.class);
                break;
            case 3:
                //数量加减
                ActivityToActivity.toActivity(mContext, CountViewActivity.class);
                break;
            case 4:
                break;
            case 5:
                break;

        }
    }


}
