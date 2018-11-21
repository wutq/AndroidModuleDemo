package com.wss.module.market.ui.goods.detail.fragment.child;

import com.wss.common.base.BaseFragment;
import com.wss.common.widget.HinderListView;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsConfigBean;
import com.wss.module.market.ui.goods.detail.adapter.GoodsConfigAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：商品详情 - 规格参数Fragment
 * Created by 吴天强 on 2018/10/19.
 */
public class GoodsConfigFragment extends BaseFragment {

    @BindView(R2.id.hlv_config)
    HinderListView listView;


    @Override
    protected int getLayoutId() {
        return R.layout.market_fragment_goods_config;
    }

    @Override
    protected void initView() {
        listView.setFocusable(false);
        List<GoodsConfigBean> data = new ArrayList<>();
        data.add(new GoodsConfigBean("品牌", "小米Mix 3"));
        data.add(new GoodsConfigBean("型号", "全面屏 小米Mix 3"));
        listView.setAdapter(new GoodsConfigAdapter(mContext, data, R.layout.market_item_of_goods_config_list));
    }


}
