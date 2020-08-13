package com.wss.module.market.ui.goods.detail.adapter;

import android.content.Context;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.utils.ImageUtils;
import com.wss.module.market.R;
import com.wss.module.market.bean.GoodsComment;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：商品评论列表适配器
 * Created by 吴天强 on 2018/11/2.
 */

public class GoodsCommentAdapter extends BaseListAdapter<GoodsComment> {


    public GoodsCommentAdapter(Context context, List<GoodsComment> items) {
        super(context, items, R.layout.market_item_of_goods_comment_list, null);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, GoodsComment data) {
        ImageUtils.loadImageCircle(holder.findViewById(R.id.iv_head), data.getUserHead());
        holder.setText(R.id.tv_name, data.getUserName());
        holder.setText(R.id.tv_comment, data.getComment());
    }
}
