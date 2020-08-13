package com.wss.module.market.ui.main;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.bean.Event;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.constants.Dic;
import com.wss.common.constants.EventAction;
import com.wss.common.manage.ActivityToActivity;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.ui.goods.cart.ShoppingCartActivity;
import com.wss.module.market.ui.goods.detail.GoodsDetailActivity;
import com.wss.module.market.ui.main.adapter.MarketMainAdapter;
import com.wss.module.market.ui.main.mvp.MarketMainPresenter;
import com.wss.module.market.ui.main.mvp.contract.MarketMainContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：商场首页
 * Created by 吴天强 on 2018/10/19.
 */
@Route(path = ARouterConfig.MARKET_MAIN_ACTIVITY)
public class MarketMainActivity extends BaseActionBarActivity<MarketMainPresenter> implements MarketMainContract.View {

    @BindView(R2.id.rv_list)
    RecyclerView recyclerView;

    private TextView tvCount;

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
        setCenterText("商城");
        adapter = new MarketMainAdapter(context, marketInfoList, (data, position) -> {
            Map<String, Object> param = new HashMap<>();
            param.put(Dic.GOODS_INFO, marketInfoList.get(position));
            ActivityToActivity.toActivity(context, GoodsDetailActivity.class, param);
        });
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);

        View cartView = View.inflate(context, R.layout.market_layout_market_main_shopping_cart, null);
        tvCount = cartView.findViewById(R.id.tv_count);
        cartView.findViewById(R.id.iv_cart).setOnClickListener(
                v -> ActivityToActivity.toActivity(context, ShoppingCartActivity.class));
        getTitleBar().setRightView(cartView);
        getPresenter().start();
    }

    @Override
    public void refreshGoodsList(List<GoodsInfo> dataList) {
        this.marketInfoList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshCartCount(long count) {
        if (count > 0) {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(String.valueOf(count));
        } else {
            tvCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEventBus(Event event) {
        super.onEventBus(event);
        if (TextUtils.equals(EventAction.EVENT_SHOPPING_CART_CHANGED, event.getAction()) ||
                TextUtils.equals(EventAction.EVENT_SHOPPING_CART_REFRESH, event.getAction())) {
            //刷新购物车
            getPresenter().getCartCount();
        }
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }
}
