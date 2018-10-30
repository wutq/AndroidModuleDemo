package com.wss.module.market.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.base.ActionBarActivity;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.bean.Event;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.constants.EventConstant;
import com.wss.common.utils.EventBusUtils;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.MarketInfo;
import com.wss.module.market.main.adapter.MarketMainAdapter;
import com.wss.module.market.main.mvp.IMarketMainView;
import com.wss.module.market.main.mvp.MarketMainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：商场首页
 * Created by 吴天强 on 2018/10/19.
 */

@Route(path = ARouterConfig.MARKET_MAIN_ACTIVITY)
public class MarketMainActivity extends ActionBarActivity<MarketMainPresenter> implements IMarketMainView,
        OnListItemClickListener {

    @BindView(R2.id.rv_list)
    RecyclerView recyclerView;

    private MarketMainAdapter adapter;
    private List<MarketInfo> marketInfoList = new ArrayList<>();

    @Override
    protected MarketMainPresenter createPresenter() {
        return new MarketMainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.market_activity_market_mian;
    }

    @Override
    protected void initView() {
        setTitleText("商城");
        adapter = new MarketMainAdapter(mContext, marketInfoList, R.layout.market_item_of_market_list, this);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(adapter);
        presenter.getData();
    }

    @Override
    public void dataList(List<MarketInfo> dataList) {
        this.marketInfoList.addAll(dataList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        EventBusUtils.sendEvent(new Event<>(EventConstant.EVENT_MARKET_CLICK, "来自Market Module的消息"));
    }
}
