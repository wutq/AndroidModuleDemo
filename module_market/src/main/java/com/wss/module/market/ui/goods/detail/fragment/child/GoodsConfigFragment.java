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
        data.add(new GoodsConfigBean("品牌", "小米（MI）"));
        data.add(new GoodsConfigBean("型号", "小米MIX3"));
        data.add(new GoodsConfigBean("入网型号", "以官网信息为准"));
        data.add(new GoodsConfigBean("上市时间", "2018年10月"));
        data.add(new GoodsConfigBean("操作系统", "Android"));
        data.add(new GoodsConfigBean("CPU", "骁龙845 八核"));
        data.add(new GoodsConfigBean("ROM", "128GB"));
        data.add(new GoodsConfigBean("RAM", "8GM"));
        data.add(new GoodsConfigBean("网络支持", "2G/3G/4G"));
        data.add(new GoodsConfigBean("屏幕尺寸", "3.98英寸"));
        data.add(new GoodsConfigBean("分辨率", "2340*1080"));
        data.add(new GoodsConfigBean("摄像头", "2400万+2000万像素"));
        data.add(new GoodsConfigBean("电池容量（mAh）", "3200mAh （typ）/ 3100mAh (min)"));
        data.add(new GoodsConfigBean("数据接口", "蓝牙/NFC"));
        listView.setAdapter(new GoodsConfigAdapter(mContext, data, R.layout.market_item_of_goods_config_list));
    }


}
