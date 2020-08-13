package com.wss.module.market.ui.goods.detail.adapter;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.wss.common.manage.ActivityToActivity;
import com.wss.module.market.R;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.ui.goods.detail.GoodsDetailActivity;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：商品详情-店主推荐-商品滑动适配器 配合ConvenientBanner 使用
 * Created by 吴天强 on 2018/10/19.
 */
public class RecommendGoodsAdapter implements CBViewHolderCreator {

    private Context mContext;

    public RecommendGoodsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Holder createHolder(View itemView) {
        return new Holder<List<GoodsInfo>>(itemView) {
            RecyclerView recyclerView;

            @Override
            protected void initView(View itemView) {
                recyclerView = itemView.findViewById(R.id.recycle_view);
            }

            @Override
            public void updateUI(List<GoodsInfo> data) {
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                recyclerView.setAdapter(new RecommendGoodsInfoAdapter(mContext, data, (data1, position) -> {
                    //推荐位商品详情
                    ActivityToActivity.toActivity(mContext, GoodsDetailActivity.class);
                }));
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.market_item_of_goods_recommend;
    }
}
