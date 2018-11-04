package com.wss.module.market.main.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.manage.CircleTransform;
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


    public GoodsCommentAdapter(Context context, List<GoodsComment> items, int layoutResId) {
        super(context, items, layoutResId, null);
    }

    @Override
    public void onBind(SuperViewHolder superViewHolder, int i, int i1, GoodsComment goodsComment) {
        ImageUtils.loadImage((ImageView) superViewHolder.findViewById(R.id.iv_head), goodsComment.getUserHead(), new CircleTransform());
        superViewHolder.setText(R.id.tv_name, goodsComment.getUserName());
        superViewHolder.setText(R.id.tv_comment, goodsComment.getComment());
    }
}
