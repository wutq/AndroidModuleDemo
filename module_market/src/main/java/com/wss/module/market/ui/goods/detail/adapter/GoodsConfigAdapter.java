package com.wss.module.market.ui.goods.detail.adapter;

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
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, GoodsConfigBean data) {
        holder.setText(R.id.tv_config_key, data.getKeyProp());
        holder.setText(R.id.tv_config_value, data.getValue());
    }
}
