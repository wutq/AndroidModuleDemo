package com.wss.module.market.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseRcyAdapter;
import com.wss.common.base.adapter.holder.BaseRcyHolder;
import com.wss.common.base.adapter.listener.OnRcyItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.MarketInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe：商场适配器
 * Created by 吴天强 on 2018/10/19.
 */

public class MarketMainAdapter extends BaseRcyAdapter<MarketInfo, MarketMainAdapter.MarketMainVH> {


    public MarketMainAdapter(Context mContext, List<MarketInfo> mData, OnRcyItemClickListener listener) {
        super(mContext, mData, listener);
    }

    @Override
    protected MarketMainVH createVH(ViewGroup parent, int viewType) {
        return new MarketMainVH(View.inflate(mContext, R.layout.market_item_of_market_list, null));
    }

    public class MarketMainVH extends BaseRcyHolder<MarketInfo> {

        @BindView(R2.id.iv_img)
        ImageView ivImg;

        @BindView(R2.id.tv_title)
        TextView tvTitle;

        @BindView(R2.id.tv_price)
        TextView tvPrice;


        MarketMainVH(View itemView) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindingData(MarketInfo data, int position) {
            ImageUtils.loadImage(mContext, data.getImg(), ivImg);
            tvTitle.setText(data.getTitle());
            tvPrice.setText(data.getPrice());
        }
    }
}
