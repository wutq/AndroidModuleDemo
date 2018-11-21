package com.wss.module.market.ui.goods.detail.fragment.child;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wss.common.base.BaseMvpFragment;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsComment;
import com.wss.module.market.ui.goods.detail.adapter.GoodsCommentAdapter;
import com.wss.module.market.ui.goods.detail.mvp.GoodsCommentPresent;
import com.wss.module.market.ui.goods.detail.mvp.contract.GoodsCommentContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：商品详情 -商品评价
 * Created by 吴天强 on 2018/10/19.
 */
public class GoodsCommentFragment extends BaseMvpFragment<GoodsCommentPresent> implements GoodsCommentContract.View {


    @BindView(R2.id.tv_comment_count)
    TextView tvCommentCount;

    @BindView(R2.id.tv_praise_rate)
    TextView tvPraiseRate;

    @BindView(R2.id.tv_empty_comment)
    TextView tvEmptyComment;

    @BindView(R2.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R2.id.iv_right)
    ImageView ivRight;

    private List<GoodsComment> commentList = new ArrayList<>();
    private GoodsCommentAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.market_fragment_goods_comment;
    }

    @Override
    protected void initView() {

        adapter = new GoodsCommentAdapter(mContext, commentList, R.layout.market_item_of_goods_comment_list);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleView.setAdapter(adapter);

        ivRight.setVisibility(View.GONE);
        tvEmptyComment.setVisibility(View.GONE);
        recycleView.setVisibility(View.VISIBLE);
        tvCommentCount.setText("用户点评(999)");
        tvPraiseRate.setText("好评率97.8%");

        presenter.start();
    }


    @Override
    public void commentList(List<GoodsComment> commentList) {
        this.commentList.addAll(commentList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected GoodsCommentPresent createPresenter() {
        return new GoodsCommentPresent();
    }
}
