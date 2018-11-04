package com.wss.module.market.main.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.module.market.R;
import com.wss.module.market.bean.GoodsConfigBean;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：商品详情 规格参数适配器
 * Created by 吴天强 on 2018/10/19.
 */
public class GoodsConfigAdapter extends BaseListAdapter<GoodsConfigBean> {


    public GoodsConfigAdapter(Context context, List<GoodsConfigBean> items, int layoutResId) {
        super(context, items, layoutResId, null);
    }

    @Override
    public void onBind(SuperViewHolder superViewHolder, int i, int i1, GoodsConfigBean goodsConfigBean) {
        superViewHolder.setText(R.id.tv_config_key, goodsConfigBean.getKeyProp());
        superViewHolder.setText(R.id.tv_config_value, goodsConfigBean.getValue());
    }
}
