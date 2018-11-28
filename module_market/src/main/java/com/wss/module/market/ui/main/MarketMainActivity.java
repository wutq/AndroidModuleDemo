package com.wss.module.market.ui.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.base.ActionBarActivity;
import com.wss.common.bean.Event;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.constants.EventAction;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ActivityToActivity;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.ui.goods.cart.ShoppingCartActivity;
import com.wss.module.market.ui.goods.detail.GoodsDetailActivity;
import com.wss.module.market.ui.main.adapter.MarketMainAdapter;
import com.wss.module.market.ui.main.mvp.contract.MarketMainContract;
import com.wss.module.market.ui.main.mvp.MarketMainPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Describe：商场首页
 * Created by 吴天强 on 2018/10/19.
 */

@Route(path = ARouterConfig.MARKET_MAIN_ACTIVITY)
public class MarketMainActivity extends ActionBarActivity<MarketMainPresenter> implements MarketMainContract.View,
        OnListItemClickListener {

    @BindView(R2.id.rv_list)
    RecyclerView recyclerView;

    private TextView tvCount;//购物车商品数量

    private MarketMainAdapter adapter;
    private List<GoodsInfo> marketInfoList = new ArrayList<>();

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

        View cartView = View.inflate(mContext, R.layout.market_layout_market_main_shopping_cart, null);
        tvCount = cartView.findViewById(R.id.tv_count);
        cartView.findViewById(R.id.iv_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO  购物车
                ActivityToActivity.toActivity(mContext, ShoppingCartActivity.class);
            }
        });
        actionBar.setRightView(cartView);
        presenter.start();
    }

    @Override
    public void dataList(List<GoodsInfo> dataList) {
        this.marketInfoList.addAll(dataList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void cartCount(long count) {
        if (count > 0) {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(String.valueOf(count));
        } else {
            tvCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Map<String, Object> param = new HashMap<>();
        param.put("GoodsInfo", marketInfoList.get(position));
        ActivityToActivity.toActivity(mContext, GoodsDetailActivity.class, param);
    }

    @Override
    public void onEventBus(Event event) {
        super.onEventBus(event);
        if (TextUtils.equals(EventAction.EVENT_SHOPPING_CART_CHANGED, event.getAction()) ||
                TextUtils.equals(EventAction.EVENT_SHOPPING_CART_REFRESH, event.getAction())) {
            //刷新购物车
            presenter.getCartCount();
        }
    }

    @Override
    protected boolean regEvent() {
        return true;
    }
}
